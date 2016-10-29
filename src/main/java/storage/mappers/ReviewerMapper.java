package storage.mappers;

import objects.Reviewer;
import org.sql2o.Connection;
import org.sql2o.Query;
import repository.Repository;
import storage.ReviewerStorage;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by artyom on 29.05.16.
 */
public class ReviewerMapper extends Mapper<Reviewer> implements ReviewerStorage {
    public ReviewerMapper(Repository repo) {
        super(repo);
    }

    @Override
    protected Reviewer makeObject(Connection connection, Map<String, Object> result) {
        String name = (String) result.get("rname");
        String university = (String) result.get("university");
        return new Reviewer(name, university);
    }

    @Override
    protected Query selectQuery(Connection connection) {
        return connection.createQuery("SELECT * FROM reviewers");
    }

    @Override
    protected Query insertQuery(Connection connection, Reviewer entry) {
        return connection.createQuery("INSERT INTO reviewers (ID, RNAME, UNIVERSITY)" +
                " VALUES (:id, :rname, :university)")
                .addParameter("id", entry.getId())
                .addParameter("rname", entry.getName())
                .addParameter("university", entry.getUniversity());
    }

    @Override
    protected void createTableQuery(Connection connection) {
        connection.createQuery("CREATE TABLE IF NOT EXISTS reviewers (" +
                "id UUID PRIMARY KEY," +
                "rname VARCHAR(100)," +
                "university VARCHAR(100)" +
                ")").executeUpdate();
    }

    @Override
    protected Query deleteAllData(Connection connection) {
        return connection.createQuery("DELETE FROM reviewers");
    }

    @Override
    protected Class<Reviewer> getEntityClass() {
        return Reviewer.class;
    }

    @Override
    public Optional<Reviewer> get(String name) {
//        try(Connection con = sql2o.open()) {
//            return con.createQuery("SELECT * FROM reviewers WHERE RNAME = :rname")
//                    .addParameter("rname", name)
//                    .executeAndFetchTable()
//                    .asList().stream()
//                    .map(it -> makeObject(con, it))
//                    .findFirst();
//        }
        return Optional.empty();
    }
}
