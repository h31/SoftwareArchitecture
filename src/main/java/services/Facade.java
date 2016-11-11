package services;

import objects.Paper;
import repository.RepositoryLocal;

import java.util.Set;

/**
 * Created by artyom on 11.11.16.
 */
public interface Facade {
    RepositoryLocal getRepo();
    void addPaper(Paper paper);
    void editorDecision(String uuidString, Set<String> params, String remarkText);
    void reviewerDecision(String uuidString, Set<String> params, String user, String remarkText);
}
