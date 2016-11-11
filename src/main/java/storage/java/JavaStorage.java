package storage.java;

import storage.Storage;

import java.util.ArrayList;
import java.util.List;

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
    public T add(T entry) {
        data.add(entry);
        return entry;
    }

    @Override
    public void recreate() {
        data = new ArrayList<>();
    }
}

