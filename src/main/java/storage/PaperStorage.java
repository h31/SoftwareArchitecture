package storage;

import objects.Paper;
import repository.Storage;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by artyom on 22.05.16.
 */
public interface PaperStorage extends Storage<Paper> {
    Optional<Paper> get(UUID id);
}
