package storage.mappers;

import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import repository.Repository;
import repository.Storage;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by artyom on 22.05.16.
 */
abstract public class Mapper<T> implements Storage<T> {
    protected Sql2o sql2o = new Sql2o("jdbc:hsqldb:file:db/testdb", "sa", "");
    protected Repository repo;

    public Mapper(Repository repo) {
        this.repo = repo;
    }

    @Override
    public List<T> getList() {
        try(Connection con = sql2o.open()) {
            return selectQuery(con)
                    .executeAndFetchTable()
                    .asList().stream()
                    .map(it -> makeObject(con, it))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public void add(T entry) {
        try(Connection con = sql2o.open()) {
            insertQuery(con, entry)
                    .executeUpdate();
        }
    }

    @Override
    public void recreate() {
        try(Connection con = sql2o.open()) {
            createTableQuery(con);
        }
    }

    abstract protected T makeObject(Connection connection, Map<String, Object> result);
    abstract protected Query selectQuery(Connection connection);
    abstract protected Query insertQuery(Connection connection, T entry);
    abstract protected void createTableQuery(Connection connection);
}
