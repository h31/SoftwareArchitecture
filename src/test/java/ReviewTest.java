import objects.Reviewer;
import objects.ReviewerRemark;
import objects.Submission;
import org.junit.Before;
import org.junit.Test;
import repository.Repository;
import repository.RepositoryInit;
import repository.RepositoryLocal;
import services.SubmissionUpdate;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

/**
 * Created by artyom on 23.05.16.
 */
public class ReviewTest {
    private RepositoryLocal repo;

    @Before
    public void init() throws NamingException {
        InitialContext context = new InitialContext();
        repo = (RepositoryLocal) context.lookup("java:module/Repository");
        repo.recreate();

        RepositoryInit init = new RepositoryInit(repo);
        init.addResearchers();
        init.addReviewers();
        init.addJournals();
        init.addSubmission();
        init.setEnqueuedSubmissions();
        repo = init.getRepo();
    }

    @Test
    public void reviewPaper() {
        assertTrue(repo.getSubmissions().getList().size() > 0);

        List<Submission> enqueued = repo.getSubmissions().get(Submission.State.REVIEWER_ENQUEUED);
        assertTrue(enqueued.size() > 0);
        Submission submission = enqueued.get(0);

        assertTrue(repo.getReviewers().getList().size() > 0);
        Reviewer reviewer = repo.getReviewers().getList().get(0);

        ReviewerRemark remark = new ReviewerRemark(reviewer, ReviewerRemark.Mark.ACCEPT, "Good", UUID.randomUUID());
        new SubmissionUpdate(repo).reviewerUpdate(submission, remark);

        assertTrue(repo.getSubmissions().get(Submission.State.IN_POOL).size() > 0);
    }

    @Test
    public void reviewRejectPaper() {
        assertTrue(repo.getSubmissions().getList().size() > 0);

        List<Submission> enqueued = repo.getSubmissions().get(Submission.State.REVIEWER_ENQUEUED);
        assertTrue(enqueued.size() > 0);
        Submission submission = enqueued.get(0);

        assertTrue(repo.getReviewers().getList().size() > 0);
        Reviewer reviewer = repo.getReviewers().getList().get(0);

        ReviewerRemark remark = new ReviewerRemark(reviewer, ReviewerRemark.Mark.REJECT, "Good", UUID.randomUUID());
        new SubmissionUpdate(repo).reviewerUpdate(submission, remark);

        assertTrue(repo.getSubmissions().get(Submission.State.REJECTED).size() > 0);
    }
}
