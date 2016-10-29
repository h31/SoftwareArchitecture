package storage.mappers;

import objects.Researcher;
import org.sql2o.Connection;
import org.sql2o.Query;
import repository.Repository;
import storage.ResearcherStorage;

import java.util.Map;
import java.util.Optional;

/**
 * Created by Artyom on 16.05.2016.
 */

public class ResearcherMapper extends Mapper<Researcher> implements ResearcherStorage {
    public ResearcherMapper(Repository repo) {
        super(repo);
    }

    @Override
    protected Researcher makeObject(Connection connection, Map<String, Object> result) {
        String name = (String) result.get("rname");
        String university = (String) result.get("university");
        return new Researcher(name, university);
    }

    @Override
    protected Query selectQuery(Connection connection) {
        return connection.createQuery("SELECT * FROM RESEARCHERS");
    }

    @Override
    protected Query insertQuery(Connection connection, Researcher entry) {
        return connection.createQuery("INSERT INTO RESEARCHERS (RNAME, UNIVERSITY) VALUES (:rname, :university)")
                .addParameter("rname", entry.getName())
                .addParameter("university", entry.getUniversity());
    }

    @Override
    protected void createTableQuery(Connection connection) {
        connection.createQuery("CREATE TABLE IF NOT EXISTS researchers (" +
                "rname VARCHAR(100)," +
                "university VARCHAR(100)" +
                ")").executeUpdate();
    }

    @Override
    protected Query deleteAllData(Connection connection) {
        return connection.createQuery("DELETE FROM RESEARCHERS");
    }

    @Override
    protected Class<Researcher> getEntityClass() {
        return Researcher.class;
    }
}