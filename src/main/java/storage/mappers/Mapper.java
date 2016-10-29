package storage.mappers;

import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import repository.Repository;
import storage.Storage;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by artyom on 22.05.16.
 */
abstract public class Mapper<T> implements Storage<T> {
    protected Repository repo;

    public Mapper(Repository repo) {
        this.repo = repo;
    }

    public EntityManager em = Persistence.createEntityManagerFactory("COLIBRI").createEntityManager();

    @Override
    public List<T> getList() {
        CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.select(root);
        List<T> result = em.createQuery(query).getResultList();
        return result;
    }

    @Override
    public void add(T entry) {
        em.getTransaction().begin();
        em.persist(entry);
        em.getTransaction().commit();
    }

    @Override
    public void recreate() {
//        em.clear();
//        try(Connection con = sql2o.open()) {
//            createTableQuery(con);
//            deleteAllData(con).executeUpdate();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
    }

    abstract protected T makeObject(Connection connection, Map<String, Object> result);
    abstract protected Query selectQuery(Connection connection);
    abstract protected Query insertQuery(Connection connection, T entry);
    abstract protected void createTableQuery(Connection connection);
    abstract protected Query deleteAllData(Connection connection);
    abstract protected Class<T> getEntityClass();
}
