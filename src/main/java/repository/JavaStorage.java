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

    @Override
    public List<T> getList() {
        return data;
    }

    @Override
    public void add(T entry) {
        data.add(entry);
    }

    @Override
    public void recreate() {
        data = new ArrayList<>();
    }
}

