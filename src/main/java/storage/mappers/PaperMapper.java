package storage.mappers;

import objects.Paper;
import objects.Researcher;
import org.sql2o.Connection;
import org.sql2o.Query;
import repository.Repository;
import storage.PaperStorage;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by artyom on 22.05.16.
 */
public class PaperMapper extends Mapper<Paper> implements PaperStorage {
    public PaperMapper(Repository repo) {
        super(repo);
    }

    @Override
    protected Class<Paper> getEntityClass() {
        return Paper.class;
    }

    @Override
    public Optional<Paper> get(UUID uuid) {
//        try(Connection con = sql2o.open()) {
//            return con.createQuery("SELECT * FROM PAPERS WHERE id = :id")
//                    .addParameter("id", uuid)
//                    .executeAndFetchTable()
//                    .asList().stream()
//                    .map(it -> makeObject(con, it))
//                    .findFirst();
//        }
        return Optional.empty();
    }
}
