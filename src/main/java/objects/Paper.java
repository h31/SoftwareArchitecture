package objects;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

/**
 * Created by Artyom on 11.04.2016.
 */

@Entity
public class Paper {
    @Id @GeneratedValue
    private UUID id;
    private String title;
    @OneToMany(cascade = CascadeType.MERGE)
    private List<Researcher> authors;
    @ElementCollection
    private List<String> keywords;
    private String abstractTxt;
    private String content;

    public Paper(String title, List<Researcher> authors, List<String> keywords, String abstractTxt, String content) {
        this.title = title;
        this.authors = authors;
        this.keywords = keywords;
        this.abstractTxt = abstractTxt;
        this.content = content;
    }

    public Paper(String title, List<Researcher> authors, List<String> keywords,
                 String abstractTxt, String content, UUID id) {
        this(title, authors, keywords, abstractTxt, content);
//        this.id = id;
    }

    public Paper() {
    }

    public String getTitle() {
        return title;
    }

    public List<Researcher> getAuthors() {
        return authors;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public String getAbstractTxt() {
        return abstractTxt;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthors(List<Researcher> authors) {
        this.authors = authors;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public void setAbstractTxt(String abstractTxt) {
        this.abstractTxt = abstractTxt;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UUID getId() {
        return id;
    }
}