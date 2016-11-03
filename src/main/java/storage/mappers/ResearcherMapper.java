package storage.mappers;

import objects.Researcher;
import org.sql2o.Connection;
import org.sql2o.Query;
import repository.Repository;
import storage.ResearcherStorage;

import java.util.Map;
import java.util.Optional;

/**
 * Created by Artyom on 16.05.2016.
 */

public class ResearcherMapper extends Mapper<Researcher> implements ResearcherStorage {
    public ResearcherMapper(Repository repo) {
        super(repo);
    }

    @Override
    protected Class<Researcher> getEntityClass() {
        return Researcher.class;
    }
}