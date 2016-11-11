package storage.mappers;

import objects.ReviewerRemark;
import repository.Repository;
import storage.ReviewerRemarkStorage;

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
