package repository;

import storage.*;
import storage.java.ReviewerJavaStorage;
import storage.java.EditorialRemarkJavaStorage;
import storage.mappers.PaperMapper;
import storage.mappers.ResearcherMapper;
import storage.java.JournalJavaStorage;
import storage.mappers.SubmissionMapper;

public class Repository {
    // Singleton

    private static Repository instance = null;

    private Repository() {}

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
            instance.papers.recreate();
            instance.researchers.recreate();
            instance.journals.recreate();
            instance.reviewers.recreate();
            instance.editorialRemarks.recreate();
        }
        return instance;
    }

    public static Repository recreate() {
        instance = null;
        return getInstance();
    }

    public PaperStorage papers = new PaperMapper(this);
    public ResearcherStorage researchers = new ResearcherMapper(this);
    public JournalStorage journals = new JournalJavaStorage();
    public SubmissionStorage submissions = new SubmissionMapper(this);
    public ReviewerStorage reviewers = new ReviewerJavaStorage();
    public EditorialRemarkStorage editorialRemarks = new EditorialRemarkJavaStorage();
}