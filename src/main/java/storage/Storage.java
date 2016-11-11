package storage;

import java.util.List;
import java.util.Optional;

/**
 * Created by Artyom on 16.05.2016.
 */
public interface Storage<T> {
    public List<T> getList();
    public T add(T entry);
    public void recreate();
}
