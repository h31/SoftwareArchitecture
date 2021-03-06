package storage;

import objects.ReviewerRemark;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by artyom on 22.05.16.
 */
public interface ReviewerRemarkStorage extends Storage<ReviewerRemark> {
    default Optional<ReviewerRemark> get(UUID id) {
        return getList().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }
}
