import objects.EditorialRemark;
import objects.Submission;
import org.junit.Before;
import org.junit.Test;
import repository.Repository;
import services.SubmissionUpdate;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by artyom on 22.05.16.
 */
public class SubmitPaperTest {
    private Repository repo;

    @Before
    public void init() {
        RepositoryInit init = new RepositoryInit();
        init.addResearchers();
        init.addJournals();
        init.addSubmission();
        repo = init.getRepo();
    }

    @Test
    public void submitPaper() {
        assertTrue(repo.submissions.getList().size() > 0);

        List<Submission> pending = repo.submissions.get(Submission.State.PENDING);
        assertTrue(pending.size() > 0);
        Submission submission = pending.get(0);

        EditorialRemark remark = new EditorialRemark(EditorialRemark.Decision.ACCEPT, "Good");
        new SubmissionUpdate(repo).editorialUpdate(submission, remark);

        assertTrue(repo.submissions.get(Submission.State.REVIEWER_ENQUEUED).size() > 0);
    }

    @Test
    public void rejectPaper() {
        assertTrue(repo.submissions.getList().size() > 0);

        List<Submission> pending = repo.submissions.get(Submission.State.PENDING);
        assertTrue(pending.size() > 0);
        Submission submission = pending.get(0);

        EditorialRemark remark = new EditorialRemark(EditorialRemark.Decision.NEEDS_REWORK, "Bad");
        new SubmissionUpdate(repo).editorialUpdate(submission, remark);

        assertTrue(repo.submissions.get(Submission.State.REJECTED).size() > 0);
    }
}
