package repository;

import objects.ReviewerRemark;
import services.SimilarPapers;
import services.SubmissionUpdate;
import storage.*;
import storage.java.ReviewerJavaStorage;
import storage.java.EditorialRemarkJavaStorage;
import storage.mappers.*;
import storage.java.JournalJavaStorage;

public class Repository {
    // Singleton

    private static Repository instance = null;

    private Repository() {}

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    public static Repository recreate() {
        instance = new Repository();
        instance.papers.recreate();
        instance.researchers.recreate();
        instance.journals.recreate();
        instance.reviewers.recreate();
        instance.editorialRemarks.recreate();
        instance.reviewerRemarks.recreate();
        instance.submissions.recreate();

        return getInstance();
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
}