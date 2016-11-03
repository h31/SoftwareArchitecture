package storage.mappers;

import objects.EditorialRemark;
import org.sql2o.Connection;
import org.sql2o.Query;
import repository.Repository;
import storage.EditorialRemarkStorage;

import java.util.Map;
import java.util.UUID;

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
