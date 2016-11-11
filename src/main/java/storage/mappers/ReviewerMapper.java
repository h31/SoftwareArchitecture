package storage.mappers;

import objects.Reviewer;
import repository.Repository;
import storage.ReviewerStorage;

import javax.persistence.TypedQuery;
import java.util.Optional;

/**
 * Created by artyom on 29.05.16.
 */
public class ReviewerMapper extends Mapper<Reviewer> implements ReviewerStorage {
    public ReviewerMapper(Repository repo) {
        super(repo);
    }

    @Override
    protected Class<Reviewer> getEntityClass() {
        return Reviewer.class;
    }

    @Override
    public Optional<Reviewer> get(String name) {
        TypedQuery<Reviewer> query = em.createQuery(
                "SELECT r FROM Reviewer r WHERE r.name = name", Reviewer.class);
        Reviewer reviewer = query.getSingleResult();
        return Optional.ofNullable(reviewer);
    }
}
