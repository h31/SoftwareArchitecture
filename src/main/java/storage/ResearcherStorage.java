package storage;

import objects.Researcher;
import repository.Storage;

import java.util.Optional;

/**
 * Created by Artyom on 16.05.2016.
 */
public interface ResearcherStorage extends Storage<Researcher> {
    Optional<Researcher> get(String name);
}