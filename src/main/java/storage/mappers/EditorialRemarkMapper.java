package storage.mappers;

import objects.EditorialRemark;
import org.sql2o.Connection;
import org.sql2o.Query;
import repository.Repository;
import storage.EditorialRemarkStorage;

import java.util.Map;
import java.util.UUID;

/**
 * Created by artyom on 29.05.16.
 */
public class EditorialRemarkMapper extends Mapper<EditorialRemark> implements EditorialRemarkStorage {

    public EditorialRemarkMapper(Repository repo) {
        super(repo);
    }

    @Override
    protected EditorialRemark makeObject(Connection connection, Map<String, Object> result) {
        String decisionString = (String) result.get("decision");
        EditorialRemark.Decision decision = EditorialRemark.Decision.valueOf(decisionString);
        String remark = (String) result.get("remark");
        UUID id = (UUID) result.get("id");
        return new EditorialRemark(decision, remark, id);
    }

    @Override
    protected Query selectQuery(Connection connection) {
        return connection.createQuery("SELECT * FROM editorial_remark");
    }

    @Override
    protected Query insertQuery(Connection connection, EditorialRemark entry) {
        return connection.createQuery("INSERT INTO editorial_remark (id, decision, remark)" +
                " VALUES (:id, :decision, :remark)")
                .addParameter("id", entry.getId())
                .addParameter("decision", entry.getDecision())
                .addParameter("remark", entry.getRemark());
    }

    @Override
    protected void createTableQuery(Connection connection) {
        connection.createQuery("CREATE TABLE IF NOT EXISTS EDITORIAL_REMARK (" +
                "id UUID PRIMARY KEY," +
                "decision VARCHAR(20)," +
                "remark VARCHAR(100)" +
                ")").executeUpdate();
    }

    @Override
    protected Query deleteAllData(Connection connection) {
        return connection.createQuery("DELETE FROM editorial_remark");
    }
}
