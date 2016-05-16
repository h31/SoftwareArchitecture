package repository;

import objects.Journal;
import objects.Paper;
import objects.Researcher;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public static Repository renewRepo() {
        instance = null;
        return getInstance();
    }

    // Paper

    public JavaStorage<Paper> papers = new JavaStorage<>();

    // Researcher

    public class ResearcherStorage extends JavaStorage<Researcher> {
        public Researcher get(String name) {
            return data.stream().filter(p -> p.getName().equals(name)).findFirst().get();
        }
    }

    public ResearcherStorage researchers = new ResearcherStorage();

    // Journal

    public JavaStorage<Journal> journals = new JavaStorage<>();
}