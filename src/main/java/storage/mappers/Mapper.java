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

    public EntityManager em = Persistence.createEntityManagerFactory("Journal").createEntityManager();

    @Override
    public List<T> getList() {
        CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.select(root);
        return em.createQuery(query).getResultList();
    }

    @Override
    public T add(T entry) {
        em.getTransaction().begin();
        T newEntry = em.merge(entry);
        em.getTransaction().commit();
        return newEntry;
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
