package repository;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Artyom on 16.05.2016.
 */
public class DBStorage<T> {
//    private Mapper<T> mapper;
//    public DBStorage(Mapper<T> mapper) {
//        this.mapper = mapper;
//    }
//
//    private Sql2o sql2o = new Sql2o("jdbc:hsqldb:file:db/testdb", "sa", "");
//
//    private void createTables(String query) {
//        try(Connection con = sql2o.open()) {
//            con.createQuery(query).executeUpdate();
//        }
//    }
//
//    private List<Map<String, Object>> selectAll(String query) {
//        try(Connection con = sql2o.open()) {
//            return con.createQuery(query).executeAndFetchTable().asList();
//        }
//    };
//
//    @Override
//    public List<T> getList() {
//        return selectAll("SELECT * FROM " + mapper.getTableName()).stream()
//                .map(it -> mapper.unpack(it)).collect(Collectors.toList());
//    }
//
//    @Override
//    public void add(T entry) {
//        Map<String, Object> packed = mapper.pack(entry);
//        List<Map.Entry<String, Object>> entries = new ArrayList<>(packed.entrySet());
//
//        String keys = entries.stream()
//                .map(Map.Entry::getKey)
//                .collect(Collectors.joining(", "));
//
//        String values = entries.stream()
//                .map(it -> it.getValue().toString())
//                .collect(Collectors.joining(", "));
//
//        String query = String.format("INSERT INTO %s(%s) VALUES (%s)",
//                mapper.getTableName(), keys, values);
//
//        try(Connection con = sql2o.open()) {
//            con.createQuery(query).executeUpdate();
//        }
//    }
//
//    @Override
//    public void recreate() {
//        createTables("CREATE TABLE ");
//    }
}
