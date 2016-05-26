package services;

import objects.EditorialRemark;
import objects.ReviewerRemark;
import objects.Submission;
import repository.Repository;

import java.util.List;

/**
 * Created by artyom on 22.05.16.
 */
public class SubmissionUpdate {
    Repository repo;

    public SubmissionUpdate(Repository repository) {
        this.repo = repository;
    }

    public List<Submission> getPending() {
        return repo.submissions.get(Submission.State.PENDING);
    }

    public void editorialUpdate(Submission submission, EditorialRemark remark) {
        if (submission.getState() != Submission.State.PENDING) {
            throw new IllegalArgumentException();
        }
        switch (remark.getDecision()) {
            case ACCEPT:
                submission.setState(Submission.State.REVIEWER_ENQUEUED);
                break;
            case REDIRECT:
            case NEEDS_REWORK:
                submission.setState(Submission.State.REJECTED);
                break;
        }
        submission.setEditorialRemark(remark);
        repo.submissions.update(submission);
    }

    public void reviewerUpdate(Submission submission, ReviewerRemark remark) {
        if (submission.getState() != Submission.State.REVIEWER_ENQUEUED) {
            throw new IllegalArgumentException();
        }
        switch (remark.getMark()) {
            case ACCEPT:
            case NEUTRAL:
                submission.setState(Submission.State.IN_POOL);
                break;
            case REJECT:
                submission.setState(Submission.State.REJECTED);
                break;
        }

        submission.setReviewerRemark(remark);
        repo.submissions.update(submission);
    }
}
