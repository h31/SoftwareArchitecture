package services;

import objects.*;
import repository.RepositoryLocal;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static spark.Spark.halt;

/**
 * Created by artyom on 11.11.16.
 */
@Stateful
public class FacadeImpl implements Facade {
    @EJB
    private RepositoryLocal repo;

    public RepositoryLocal getRepo() {
        return repo;
    }

    public void setRepo(RepositoryLocal repo) {
        this.repo = repo;
    }

    public void addPaper(Paper paper) {
        repo.getPapers().add(paper);
        Submission submission = new Submission(paper);
        repo.getSubmissions().add(submission);

    }

    public void editorDecision(String uuidString, Set<String> params, String remarkText) {
        UUID uuid = UUID.fromString(uuidString);
        Optional<Submission> submission = repo.getSubmissions().get(uuid);
        if (!submission.isPresent()) {
            halt(500, "No such submission");
        }
        EditorialRemark.Decision decision;
        if (params.contains("accepted")) {
            decision = EditorialRemark.Decision.ACCEPT;
        } else if (params.contains("rework")) {
            decision = EditorialRemark.Decision.NEEDS_REWORK;
        } else if (params.contains("redirect")) {
            decision = EditorialRemark.Decision.REDIRECT;
        } else {
            halt(500, "Invalid decision");
            return;
        }
        EditorialRemark remark = new EditorialRemark(decision, remarkText);
        repo.getSubmissionUpdate().editorialUpdate(submission.get(), remark);
    }

    public void reviewerDecision(String uuidString, Set<String> params, String user, String remarkText) {
        UUID uuid = UUID.fromString(uuidString);
        Optional<Submission> submission = repo.getSubmissions().get(uuid);
        if (!submission.isPresent()) {
            halt(500, "No such submission");
        }
        ReviewerRemark.Mark mark;
        if (params.contains("accept")) {
            mark = ReviewerRemark.Mark.ACCEPT;
        } else if (params.contains("neutral")) {
            mark = ReviewerRemark.Mark.NEUTRAL;
        } else if (params.contains("reject")) {
            mark = ReviewerRemark.Mark.REJECT;
        } else {
            halt(HttpServletResponse.SC_BAD_REQUEST, "Invalid decision");
            return;
        }
        if (user == null) {
            halt(HttpServletResponse.SC_BAD_REQUEST, "Please choose reviewer first");
        }
        Optional<Reviewer> reviewer = repo.getReviewers().get(user);
        if (!reviewer.isPresent()) {
            halt(HttpServletResponse.SC_BAD_REQUEST, "Unknown reviewer");
        }
        ReviewerRemark remark = new ReviewerRemark(reviewer.get(), mark, remarkText, UUID.randomUUID());
        repo.getSubmissionUpdate().reviewerUpdate(submission.get(), remark);
    }
}
