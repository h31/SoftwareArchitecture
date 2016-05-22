package repository;

import objects.Journal;
import objects.Researcher;
import storage.JournalStorage;
import storage.ResearcherStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Artyom on 11.04.2016.
 */

public class JavaStorage<T> implements Storage<T> {
    private List<T> data;

    public JavaStorage() {
        recreate();
    }

//    @Override
//    public Optional<T> get(int id) {
//        if (data.size() > id) {
//            return Optional.ofNullable(data.get(id));
//        } else {
//            return Optional.empty();
//        }
//
//    }

    @Override
    public List<T> getList() {
        return data;
    }

    @Override
    public void add(T entry) {
        data.add(entry);
//        return data.size() - 1;
    }

    @Override
    public void recreate() {
        data = new ArrayList<>();
    }
}

// Researcher

class ResearcherJavaStorage extends JavaStorage<Researcher> implements ResearcherStorage {
    public Optional<Researcher> get(String name) {
        return getList().stream()
                .filter(p -> p.getName().equals(name))
                .findFirst();
    }
}

// Journal

class JournalJavaStorage extends JavaStorage<Journal> implements JournalStorage {
    public Optional<Journal> getFormattedByEditors() {
        return getList().stream()
                .filter(p -> !p.isFormattedByEditors())
                .findFirst();
    }
}