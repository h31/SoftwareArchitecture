package storage.mappers;

import objects.Reviewer;
import objects.ReviewerRemark;
import org.sql2o.Connection;
import org.sql2o.Query;
import repository.Repository;
import storage.ReviewerRemarkStorage;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by artyom on 29.05.16.
 */
public class ReviewerRemarkMapper extends Mapper<ReviewerRemark> implements ReviewerRemarkStorage {
    public ReviewerRemarkMapper(Repository repo) {
        super(repo);
    }

    @Override
    protected Class<ReviewerRemark> getEntityClass() {
        return ReviewerRemark.class;
    }
}
