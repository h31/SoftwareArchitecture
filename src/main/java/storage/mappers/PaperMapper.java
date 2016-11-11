package storage.mappers;

import objects.Paper;
import repository.Repository;
import storage.PaperStorage;

import java.util.Optional;
import java.util.UUID;

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
        Paper paper = em.find(Paper.class, uuid);
        return Optional.ofNullable(paper);
    }
}
