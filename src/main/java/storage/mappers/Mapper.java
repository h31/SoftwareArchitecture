package storage.mappers;

import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import repository.Repository;
import storage.Storage;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by artyom on 22.05.16.
 */
abstract public class Mapper<T> implements Storage<T> {
    private final String dbPath = "jdbc:postgresql://localhost:5432/journal";
    private final String user = "journal";
    private final String password = "qwerty";

    protected Sql2o sql2o = new Sql2o(dbPath, user, password);
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
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void add(T entry) {
        try(Connection con = sql2o.open()) {
            insertQuery(con, entry)
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void recreate() {
        try(Connection con = sql2o.open()) {
            createTableQuery(con);
            deleteAllData(con).executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    abstract protected T makeObject(Connection connection, Map<String, Object> result);
    abstract protected Query selectQuery(Connection connection);
    abstract protected Query insertQuery(Connection connection, T entry);
    abstract protected void createTableQuery(Connection connection);
    abstract protected Query deleteAllData(Connection connection);
}
