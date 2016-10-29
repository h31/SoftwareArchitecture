package repository;

import services.SimilarPapers;
import services.SubmissionUpdate;
import storage.*;

/**
 * Created by artyom on 29.10.16.
 */
public interface RepositoryLocal {
    public PaperStorage getPapers();
    public ResearcherStorage getResearchers();
    public JournalStorage getJournals();
    public SubmissionStorage getSubmissions();
    public ReviewerStorage getReviewers();
    public EditorialRemarkStorage getEditorialRemarks();
    public ReviewerRemarkStorage getReviewerRemarks();
    public SubmissionUpdate getSubmissionUpdate();
    public SimilarPapers getSimilarPapers();
    public void init();
    public void recreate();
}
