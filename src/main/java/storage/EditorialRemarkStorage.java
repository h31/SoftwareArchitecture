package storage;

import objects.EditorialRemark;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by artyom on 22.05.16.
 */
public interface EditorialRemarkStorage extends Storage<EditorialRemark> {
    default Optional<EditorialRemark> get(UUID id) {
        return getList().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

}
