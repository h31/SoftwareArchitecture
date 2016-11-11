package storage.mappers;

import objects.EditorialRemark;
import repository.Repository;
import storage.EditorialRemarkStorage;

/**
 * Created by artyom on 29.05.16.
 */
public class EditorialRemarkMapper extends Mapper<EditorialRemark> implements EditorialRemarkStorage {

    public EditorialRemarkMapper(Repository repo) {
        super(repo);
    }

    @Override
    protected Class<EditorialRemark> getEntityClass() {
        return EditorialRemark.class;
    }
}
