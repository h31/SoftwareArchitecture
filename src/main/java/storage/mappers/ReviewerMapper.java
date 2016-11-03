package storage.mappers;

import objects.Reviewer;
import org.sql2o.Connection;
import org.sql2o.Query;
import repository.Repository;
import storage.ReviewerStorage;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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
