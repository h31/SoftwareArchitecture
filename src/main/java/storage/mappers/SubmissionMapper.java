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
        Date date = (Date) result.get("rname");
        String paperTitle = (String) result.get("paper_title");
        Optional<Paper> paper = repo.papers.get(paperTitle);
        assert paper.isPresent();
        return new Submission(date, paper.get());
    }

    @Override
    protected Query selectQuery(Connection connection) {
        return connection.createQuery("SELECT * FROM SUBMISSIONS");
    }

    @Override
    protected Query insertQuery(Connection connection, Submission entry) {
        return connection.createQuery("INSERT INTO SUBMISSIONS (PAPER_TITLE, STATE, DATE)" +
                " VALUES (:paper, :state, :date)")
                .addParameter("paper", entry.getPaper().getTitle())
                .addParameter("state", entry.getState());
    }

    @Override
    protected void createTableQuery(Connection connection) {
        connection.createQuery("CREATE TABLE IF NOT EXISTS submissions (" +
                "paper_title VARCHAR(100)," +
                "state VARCHAR(20)," +
                "date TIMESTAMP" +
                ")").executeUpdate();
    }

    @Override
    public void update(Submission submission) {
        try(Connection con = sql2o.open()) {
            con.createQuery("UPDATE SUBMISSIONS SET STATE = :state WHERE PAPER_TITLE = :title AND DATE = :date")
                    .addParameter("state", submission.getState())
                    .addParameter("title", submission.getPaper().getTitle())
                    .addParameter("date", submission.getDate())
                    .executeUpdate();
        }
    }
}
