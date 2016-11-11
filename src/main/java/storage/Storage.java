package storage;

import java.util.List;
import java.util.Optional;

/**
 * Created by Artyom on 16.05.2016.
 */
public interface Storage<T> {
    List<T> getList();
    T add(T entry);
    void recreate();
}
