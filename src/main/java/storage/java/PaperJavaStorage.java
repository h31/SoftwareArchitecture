package storage.java;

import objects.Paper;
import storage.PaperStorage;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by artyom on 29.10.16.
 */
public class PaperJavaStorage extends JavaStorage<Paper> implements PaperStorage {
    @Override
    public Optional<Paper> get(UUID id) {
        return getList().stream().filter(paper -> paper.getId() == id).findFirst();
    }
}
