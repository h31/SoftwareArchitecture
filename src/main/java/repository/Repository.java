package repository;

import mappers.ResearcherMapper;
import objects.Journal;
import objects.Paper;
import objects.Researcher;
import storage.JournalStorage;
import storage.ResearcherStorage;

import java.util.*;

public class Repository {
    // Singleton

    private static Repository instance = null;

    private Repository() {
    }

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
            instance.papers.recreate();
            instance.researchers.recreate();
            instance.journals.recreate();
        }
        return instance;
    }

    public static Repository recreate() {
        instance = null;
        return getInstance();
    }

    // Paper

    public Storage<Paper> papers = new JavaStorage<>();

    public ResearcherStorage researchers = new ResearcherMapper();

    public JournalStorage journals = new JournalJavaStorage();
}