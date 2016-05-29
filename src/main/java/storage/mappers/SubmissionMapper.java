package storage.mappers;

import objects.Journal;
import objects.Paper;
import objects.Researcher;
import objects.Submission;
import org.sql2o.Connection;
import org.sql2o.Query;
import repository.Repository;
import storage.SubmissionStorage;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by artyom on 22.05.16.
 */
public class SubmissionMapper extends Mapper<Submission> implements SubmissionStorage {
    public SubmissionMapper(Repository repo) {
        super(repo);
    }

    @Override
    protected Submission makeObject(Connection connection, Map<String, Object> result) {
        Date date = (Date) result.get("date");
        UUID paperId = (UUID) result.get("paper_uuid");
        String state = (String) result.get("state");
        Optional<Paper> paper = repo.papers.get(paperId);
        UUID editorial_remark_id = (UUID) result.get("editorial_remark_id");
        UUID reviewer_remark_id = (UUID) result.get("reviewer_remark_id");
        assert paper.isPresent();
        Submission submission = new Submission(date, paper.get(), Submission.State.valueOf(state));
        submission.setEditorialRemark(repo.editorialRemarks.get(editorial_remark_id).orElse(null));
        submission.setReviewerRemark(repo.reviewerRemarks.get(reviewer_remark_id).orElse(null));
        return submission;
    }

    @Override
    protected Query selectQuery(Connection connection) {
        return connection.createQuery("SELECT * FROM SUBMISSIONS");
    }

    @Override
    protected Query insertQuery(Connection connection, Submission entry) {
        return connection.createQuery("INSERT INTO SUBMISSIONS (paper_uuid, state, date, editorial_remark_id, reviewer_remark_id)" +
                " VALUES (:paper, :state, :date, :ed, :rev)")
                .addParameter("paper", entry.getPaper().getId())
                .addParameter("state", entry.getState())
                .addParameter("date", entry.getDate())
                .addParameter("ed", entry.getEditorialRemark().orElse(null))
                .addParameter("rev", entry.getReviewerRemark().orElse(null));
    }

    @Override
    protected void createTableQuery(Connection connection) {
        connection.createQuery("CREATE TABLE IF NOT EXISTS SUBMISSIONS (" +
                "paper_uuid UUID," +
                "state VARCHAR(20)," +
                "date TIMESTAMP," +
                "editorial_remark_id UUID," +
                "reviewer_remark_id UUID," +
                "PRIMARY KEY (paper_uuid, date)," +
                "FOREIGN KEY (paper_uuid) REFERENCES papers(id)," +
                "FOREIGN KEY (editorial_remark_id) REFERENCES editorial_remark(id)," +
                "FOREIGN KEY (reviewer_remark_id) REFERENCES reviewer_remark(id)" +
                ")").executeUpdate();

        connection.createQuery("DELETE FROM SUBMISSIONS").executeUpdate();
    }

    @Override
    protected Query deleteAllData(Connection connection) {
        return connection.createQuery("DELETE FROM SUBMISSIONS");
    }

    @Override
    public void update(Submission submission) {
        try(Connection con = sql2o.open()) {
            con.createQuery("UPDATE SUBMISSIONS SET STATE = :state, editorial_remark_id = :ed, reviewer_remark_id = :rev" +
                    " WHERE PAPER_UUID = :id AND DATE = :date")
                    .addParameter("state", submission.getState())
                    .addParameter("ed", repo.submissionUpdate.getEditorialID(submission))
                    .addParameter("rev", repo.submissionUpdate.getReviewerID(submission))
                    .addParameter("id", submission.getPaper().getId())
                    .addParameter("date", submission.getDate())
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
