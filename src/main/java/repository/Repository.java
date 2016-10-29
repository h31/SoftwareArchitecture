package repository;

import objects.ReviewerRemark;
import services.SimilarPapers;
import services.SubmissionUpdate;
import storage.*;
import storage.java.ReviewerJavaStorage;
import storage.java.EditorialRemarkJavaStorage;
import storage.mappers.*;
import storage.java.JournalJavaStorage;

import javax.ejb.Singleton;

@Singleton
public class Repository implements RepositoryLocal {
    public void recreate() {
        papers.recreate();
        researchers.recreate();
        journals.recreate();
        reviewers.recreate();
        editorialRemarks.recreate();
        reviewerRemarks.recreate();
        submissions.recreate();
    }

    public void init() {
        RepositoryInit init = new RepositoryInit(this);
        init.addResearchers();
        init.addJournals();
        init.addSubmission();
        init.addReviewers();
    }

    public PaperStorage papers = new PaperMapper(this);
    public ResearcherStorage researchers = new ResearcherMapper(this);
    public JournalStorage journals = new JournalJavaStorage();
    public SubmissionStorage submissions = new SubmissionMapper(this);
    public ReviewerStorage reviewers = new ReviewerMapper(this);
    public EditorialRemarkStorage editorialRemarks = new EditorialRemarkMapper(this);
    public ReviewerRemarkStorage reviewerRemarks = new ReviewerRemarkMapper(this);

    public SubmissionUpdate submissionUpdate = new SubmissionUpdate(this);
    public SimilarPapers similarPapers = new SimilarPapers();

    public PaperStorage getPapers() {
        return papers;
    }

    public ResearcherStorage getResearchers() {
        return researchers;
    }

    public JournalStorage getJournals() {
        return journals;
    }

    public SubmissionStorage getSubmissions() {
        return submissions;
    }

    public ReviewerStorage getReviewers() {
        return reviewers;
    }

    public EditorialRemarkStorage getEditorialRemarks() {
        return editorialRemarks;
    }

    public ReviewerRemarkStorage getReviewerRemarks() {
        return reviewerRemarks;
    }

    public SubmissionUpdate getSubmissionUpdate() {
        return submissionUpdate;
    }

    public SimilarPapers getSimilarPapers() {
        return similarPapers;
    }
}