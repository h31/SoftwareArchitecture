package storage.mappers;

import objects.Reviewer;
import objects.ReviewerRemark;
import org.sql2o.Connection;
import org.sql2o.Query;
import repository.Repository;
import storage.ReviewerRemarkStorage;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by artyom on 29.05.16.
 */
public class ReviewerRemarkMapper extends Mapper<ReviewerRemark> implements ReviewerRemarkStorage {
    public ReviewerRemarkMapper(Repository repo) {
        super(repo);
    }

    @Override
    protected ReviewerRemark makeObject(Connection connection, Map<String, Object> result) {
        String markString = (String) result.get("mark");
        ReviewerRemark.Mark decision = ReviewerRemark.Mark.valueOf(markString);
        String text = (String) result.get("text");
        UUID id = (UUID) result.get("id");
        UUID reviewer_id = (UUID) result.get("reviewer_id");
        Optional<Reviewer> reviewer = repo.reviewers.get(reviewer_id);
        assert reviewer.isPresent();
        return new ReviewerRemark(reviewer.get(), decision, text, id);
    }

    @Override
    protected Query selectQuery(Connection connection) {
        return connection.createQuery("SELECT * FROM REVIEWER_REMARK");
    }

    @Override
    protected Query insertQuery(Connection connection, ReviewerRemark entry) {
        return connection.createQuery("INSERT INTO REVIEWER_REMARK (id, mark, text, reviewer_id)" +
                " VALUES (:id, :mark, :text, :reviewer_id)")
                .addParameter("id", entry.getId())
                .addParameter("mark", entry.getMark())
                .addParameter("text", entry.getText())
                .addParameter("reviewer_id", entry.getReviewer().getId());
    }

    @Override
    protected void createTableQuery(Connection connection) {
        connection.createQuery("CREATE TABLE IF NOT EXISTS REVIEWER_REMARK (" +
                "id UUID PRIMARY KEY," +
                "mark VARCHAR(20)," +
                "text VARCHAR(100)," +
                "reviewer_id UUID," +
                "FOREIGN KEY (reviewer_id) REFERENCES reviewers(id) ON DELETE CASCADE" +
                ")").executeUpdate();
    }

    @Override
    protected Query deleteAllData(Connection connection) {
        return connection.createQuery("DELETE FROM REVIEWER_REMARK");
    }
}
