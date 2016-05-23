package storage.mappers;

import objects.Paper;
import objects.Researcher;
import org.sql2o.Connection;
import org.sql2o.Query;
import repository.Repository;
import storage.PaperStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        List<String> rnames = connection.createQuery("SELECT RNAME FROM PAPERS_AUTHORS WHERE PAPER_TITLE = :title")
                .addParameter("title", title).executeScalarList(String.class);

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

        return new Paper(title, authors, keywords, abstractTxt, content);
    }

    @Override
    protected Query selectQuery(Connection connection) {
        return connection.createQuery("SELECT * FROM PAPERS");
    }

    @Override
    protected Query insertQuery(Connection connection, Paper entry) {
        return connection.createQuery("INSERT INTO papers (TITLE, ABSTRACT, CONTENT)" +
                " VALUES (:title, :abstract, :content)")
                .addParameter("title", entry.getTitle())
                .addParameter("abstract", entry.getAbstractTxt())
                .addParameter("content", entry.getContent());
    }

    @Override
    protected void createTableQuery(Connection connection) {
        connection.createQuery("CREATE TABLE IF NOT EXISTS papers (" +
                "title VARCHAR(100)," +
                "abstract VARCHAR(100)," +
                "content VARCHAR(100)" +
                ")").executeUpdate();

        connection.createQuery("DELETE FROM papers").executeUpdate();

        connection.createQuery("CREATE TABLE IF NOT EXISTS keywords (" +
                "paper_title VARCHAR(100)," +
                "keyword VARCHAR(100)" +
                ")").executeUpdate();

        connection.createQuery("CREATE TABLE IF NOT EXISTS papers_authors (" +
                "paper_title VARCHAR(100)," +
                "rname VARCHAR(100)" +
                ")").executeUpdate();
    }

    @Override
    public Optional<Paper> get(String title) {
        try(Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM PAPERS WHERE TITLE = :title")
                    .addParameter("title", title)
                    .executeAndFetchTable()
                    .asList().stream()
                    .map(it -> makeObject(con, it))
                    .findFirst();
        }
    }
}
