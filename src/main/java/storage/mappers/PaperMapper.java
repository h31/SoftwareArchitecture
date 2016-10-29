package storage.mappers;

import objects.Paper;
import objects.Researcher;
import org.sql2o.Connection;
import org.sql2o.Query;
import repository.Repository;
import storage.PaperStorage;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by artyom on 22.05.16.
 */
public class PaperMapper extends Mapper<Paper> implements PaperStorage {
    public PaperMapper(Repository repo) {
        super(repo);
    }

    @Override
    protected Paper makeObject(Connection connection, Map<String, Object> result) {
        String title = (String) result.get("title");
        UUID id = (UUID) result.get("id");
        List<String> rnames = connection.createQuery("SELECT RNAME FROM PAPERS_AUTHORS WHERE paper_id = :id")
                .addParameter("id", id).executeScalarList(String.class);

        List<Researcher> authors = new ArrayList<>();
        for (String rname: rnames) {
            Optional<Researcher> researcher = repo.researchers.get(rname);
            if (researcher.isPresent()) {
                authors.add(researcher.get());
            }
        }

        List<String> keywords = connection.createQuery("SELECT KEYWORD FROM KEYWORDS WHERE PAPER_TITLE = :title")
                .addParameter("title", title).executeScalarList(String.class);

        String abstractTxt = (String) result.get("abstract");
        String content = (String) result.get("content");

        return new Paper(title, authors, keywords, abstractTxt, content, id);
    }

    @Override
    protected Query selectQuery(Connection connection) {
        return connection.createQuery("SELECT * FROM PAPERS");
    }

    @Override
    protected Query insertQuery(Connection connection, Paper entry) {
        return connection.createQuery("INSERT INTO papers (ID, TITLE, ABSTRACT, CONTENT)" +
                " VALUES (:id, :title, :abstract, :content);" +
                " INSERT INTO papers_authors (paper_id, rname) VALUES (:id, :rname)")
                .addParameter("id", entry.getId())
                .addParameter("title", entry.getTitle())
                .addParameter("abstract", entry.getAbstractTxt())
                .addParameter("content", entry.getContent())
                .addParameter("rname", entry.getAuthors().get(0).getName());
    }

    @Override
    protected void createTableQuery(Connection connection) {
        connection.createQuery("CREATE TABLE IF NOT EXISTS papers (" +
                "id UUID PRIMARY KEY ," +
                "title VARCHAR(100)," +
                "abstract VARCHAR(100)," +
                "content VARCHAR(100)" +
                ")").executeUpdate();

        connection.createQuery("CREATE TABLE IF NOT EXISTS keywords (" +
                "paper_title VARCHAR(100)," +
                "keyword VARCHAR(100)" +
                ")").executeUpdate();

        connection.createQuery("CREATE TABLE IF NOT EXISTS papers_authors (" +
                "paper_id UUID," +
                "rname VARCHAR(100)" +
                ")").executeUpdate();
    }

    @Override
    protected Query deleteAllData(Connection connection) {
        return connection.createQuery("DELETE FROM PAPERS; DELETE FROM KEYWORDS; DELETE FROM PAPERS_AUTHORS");
    }

    @Override
    protected Class<Paper> getEntityClass() {
        return Paper.class;
    }

    @Override
    public Optional<Paper> get(UUID uuid) {
//        try(Connection con = sql2o.open()) {
//            return con.createQuery("SELECT * FROM PAPERS WHERE id = :id")
//                    .addParameter("id", uuid)
//                    .executeAndFetchTable()
//                    .asList().stream()
//                    .map(it -> makeObject(con, it))
//                    .findFirst();
//        }
        return Optional.empty();
    }
}
