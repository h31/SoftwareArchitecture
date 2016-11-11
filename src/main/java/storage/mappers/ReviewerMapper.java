package storage.mappers;

import objects.Reviewer;
import repository.Repository;
import storage.ReviewerStorage;

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
