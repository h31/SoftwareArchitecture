package services;

import objects.EditorialRemark;
import objects.ReviewerRemark;
import objects.Submission;
import repository.Repository;
import repository.RepositoryLocal;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;

/**
 * Created by artyom on 22.05.16.
 */
public class SubmissionUpdate {
    private RepositoryLocal repo;

    public SubmissionUpdate(RepositoryLocal repository) {
        this.repo = repository;
    }

    public List<Submission> getPending() {
        return repo.getSubmissions().get(Submission.State.PENDING);
    }

    public List<Submission> getEnqueued() {
        return repo.getSubmissions().get(Submission.State.REVIEWER_ENQUEUED);
    }

    public List<Submission> getInPool() {
        return repo.getSubmissions().get(Submission.State.IN_POOL);
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

        repo.getEditorialRemarks().add(remark);
        submission.setEditorialRemark(remark);
        repo.getSubmissions().update(submission);
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

        repo.getReviewerRemarks().add(remark);
        submission.setReviewerRemark(remark);
        repo.getSubmissions().update(submission);
    }

    public UUID getEditorialID(Submission submission) {
        Optional<EditorialRemark> editorialRemark = submission.getEditorialRemark();
        return editorialRemark.isPresent() ? editorialRemark.get().getId() : null;
    }

    public UUID getReviewerID(Submission submission) {
        Optional<ReviewerRemark> reviewerRemark = submission.getReviewerRemark();
        return reviewerRemark.isPresent() ? reviewerRemark.get().getId() : null;
    }
}
