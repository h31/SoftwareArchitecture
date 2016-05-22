package storage;

import objects.Journal;
import objects.Researcher;
import repository.Storage;

import java.util.Optional;

/**
 * Created by Artyom on 16.05.2016.
 */
public interface JournalStorage extends Storage<Journal> {
    Optional<Journal> getFormattedByEditors();
}