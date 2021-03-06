package storage;

import objects.Submission;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by artyom on 22.05.16.
 */
public interface SubmissionStorage extends Storage<Submission> {
    void update(Submission submission);
    default List<Submission> get(Submission.State state) {
        return getList().stream()
                .filter(p -> p.getState().equals(state))
                .collect(Collectors.toList());
    }

    default Optional<Submission> get(UUID uuid) {
        return getList().stream().filter(p -> p.getPaper().getId().equals(uuid)).findFirst();
    }
}
