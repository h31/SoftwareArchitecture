import objects.Reviewer;
import objects.ReviewerRemark;
import objects.Submission;
import org.junit.Before;
import org.junit.Test;
import repository.Repository;
import repository.RepositoryInit;
import services.SubmissionUpdate;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

/**
 * Created by artyom on 23.05.16.
 */
public class ReviewTest {
    private Repository repo;

    @Before
    public void init() {
        RepositoryInit init = new RepositoryInit();
        init.addResearchers();
        init.addReviewers();
        init.addJournals();
        init.addSubmission();
        init.setEnqueuedSubmissions();
        repo = init.getRepo();
    }

    @Test
    public void reviewPaper() {
        assertTrue(repo.submissions.getList().size() > 0);

        List<Submission> enqueued = repo.submissions.get(Submission.State.REVIEWER_ENQUEUED);
        assertTrue(enqueued.size() > 0);
        Submission submission = enqueued.get(0);

        assertTrue(repo.reviewers.getList().size() > 0);
        Reviewer reviewer = repo.reviewers.getList().get(0);

        ReviewerRemark remark = new ReviewerRemark(reviewer, ReviewerRemark.Mark.ACCEPT, "Good", UUID.randomUUID());
        new SubmissionUpdate(repo).reviewerUpdate(submission, remark);

        assertTrue(repo.submissions.get(Submission.State.IN_POOL).size() > 0);
    }

    @Test
    public void reviewRejectPaper() {
        assertTrue(repo.submissions.getList().size() > 0);

        List<Submission> enqueued = repo.submissions.get(Submission.State.REVIEWER_ENQUEUED);
        assertTrue(enqueued.size() > 0);
        Submission submission = enqueued.get(0);

        assertTrue(repo.reviewers.getList().size() > 0);
        Reviewer reviewer = repo.reviewers.getList().get(0);

        ReviewerRemark remark = new ReviewerRemark(reviewer, ReviewerRemark.Mark.REJECT, "Good", UUID.randomUUID());
        new SubmissionUpdate(repo).reviewerUpdate(submission, remark);

        assertTrue(repo.submissions.get(Submission.State.REJECTED).size() > 0);
    }
}
