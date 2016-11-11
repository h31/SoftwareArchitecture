package storage.mappers;

import objects.Submission;
import repository.Repository;
import storage.SubmissionStorage;

/**
 * Created by artyom on 22.05.16.
 */
public class SubmissionMapper extends Mapper<Submission> implements SubmissionStorage {
    public SubmissionMapper(Repository repo) {
        super(repo);
    }

    @Override
    protected Class<Submission> getEntityClass() {
        return Submission.class;
    }

    @Override
    public void update(Submission submission) {
        add(submission);
    }
}
