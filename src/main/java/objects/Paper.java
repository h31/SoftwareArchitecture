package objects;

import java.util.List;

/**
 * Created by Artyom on 11.04.2016.
 */

public class Paper {
    private String title;
    private List<Researcher> authors;
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
}