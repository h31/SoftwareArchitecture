import objects.EditorialRemark;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by artyom on 22.05.16.
 */
public class SubmitPaperTest {
    private RepositoryLocal repo;

    @Before
    public void init() throws NamingException {
        InitialContext context = new InitialContext();
        repo = (RepositoryLocal) context.lookup("java:module/Repository");
        repo.recreate();

        RepositoryInit init = new RepositoryInit(repo);
        init.addResearchers();
        init.addJournals();
        init.addSubmission();
    }

    @Test
    public void submitPaper() {
        assertTrue(repo.getSubmissions().getList().size() > 0);

        List<Submission> pending = repo.getSubmissions().get(Submission.State.PENDING);
        assertTrue(pending.size() > 0);
        Submission submission = pending.get(0);

        EditorialRemark remark = new EditorialRemark(EditorialRemark.Decision.ACCEPT, "Good");
        new SubmissionUpdate(repo).editorialUpdate(submission, remark);

        assertTrue(repo.getSubmissions().get(Submission.State.REVIEWER_ENQUEUED).size() > 0);
    }

    @Test
    public void rejectPaper() {
        assertTrue(repo.getSubmissions().getList().size() > 0);

        List<Submission> pending = repo.getSubmissions().get(Submission.State.PENDING);
        assertTrue(pending.size() > 0);
        Submission submission = pending.get(0);

        EditorialRemark remark = new EditorialRemark(EditorialRemark.Decision.NEEDS_REWORK, "Bad");
        new SubmissionUpdate(repo).editorialUpdate(submission, remark);

        assertTrue(repo.getSubmissions().get(Submission.State.REJECTED).size() > 0);
    }
}
