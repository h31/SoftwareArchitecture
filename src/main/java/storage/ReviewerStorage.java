package storage;

import objects.Reviewer;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by artyom on 22.05.16.
 */
public interface ReviewerStorage extends Storage<Reviewer> {
    default Optional<Reviewer> get(String name) {
        return getList().stream()
                .filter(p -> p.getName().equals(name))
                .findFirst();
    }

    default Optional<Reviewer> get(UUID id) {
        return getList().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }
}
