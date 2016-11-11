package storage.mappers;

import repository.Repository;
import storage.Storage;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

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

    abstract protected Class<T> getEntityClass();
}
