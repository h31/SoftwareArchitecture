package mappers;

import objects.Researcher;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import storage.ResearcherStorage;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Artyom on 16.05.2016.
 */

public class ResearcherMapper implements ResearcherStorage {
    private Sql2o sql2o = new Sql2o("jdbc:hsqldb:file:db/testdb", "sa", "");

    private Researcher makeObject(Map<String, Object> result) {
        String name = (String) result.get("rname");
        String university = (String) result.get("university");
        return new Researcher(name, university);
    }

    @Override
    public List<Researcher> getList() {
        try(Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM RESEARCHERS")
                    .executeAndFetchTable()
                    .asList().stream()
                    .map(this::makeObject)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public void add(Researcher entry) {
        try(Connection con = sql2o.open()) {
            con.createQuery("INSERT INTO RESEARCHERS (RNAME, UNIVERSITY) VALUES (:rname, :university)")
                    .addParameter("rname", entry.getName())
                    .addParameter("university", entry.getUniversity())
                    .executeUpdate();
        }
    }

    @Override
    public void recreate() {
        try(Connection con = sql2o.open()) {
            con.createQuery("CREATE TABLE IF NOT EXISTS researchers (" +
                    "rname VARCHAR(100)," +
                    "university VARCHAR(100)" +
                    ")").executeUpdate();
        }
    }

    @Override
    public Optional<Researcher> get(String name) {
        try(Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM RESEARCHERS WHERE RNAME = :rname")
                    .addParameter("rname", name)
                    .executeAndFetchTable()
                    .asList().stream()
                    .map(this::makeObject)
                    .findFirst();
        }
    }
}