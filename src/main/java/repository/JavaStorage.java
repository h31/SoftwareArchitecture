package repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artyom on 11.04.2016.
 */

public class JavaStorage<T> {
    List<T> data = new ArrayList<>();

    public T get(int id) {
        return data.get(id);
    }

    public List<T> getList() {
        return data;
    }

    public int add(T entry) {
        data.add(entry);
        return data.size() - 1;
    }
}
