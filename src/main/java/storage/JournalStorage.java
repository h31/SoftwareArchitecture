package storage;

import objects.Journal;

import java.util.Optional;

/**
 * Created by Artyom on 16.05.2016.
 */
public interface JournalStorage extends Storage<Journal> {
    default Optional<Journal> getFormattedByEditors() {
        return getList().stream()
                .filter(p -> !p.isFormattedByEditors())
                .findFirst();
    }
}