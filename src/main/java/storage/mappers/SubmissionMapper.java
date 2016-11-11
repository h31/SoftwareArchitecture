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
//        try(Connection con = sql2o.open()) {
//            con.createQuery("UPDATE SUBMISSIONS SET STATE = :state, editorial_remark_id = :ed, reviewer_remark_id = :rev" +
//                    " WHERE PAPER_UUID = :id AND DATE = :date")
//                    .addParameter("state", submission.getState())
//                    .addParameter("ed", repo.submissionUpdate.getEditorialID(submission))
//                    .addParameter("rev", repo.submissionUpdate.getReviewerID(submission))
//                    .addParameter("id", submission.getPaper().getId())
//                    .addParameter("date", submission.getDate())
//                    .executeUpdate();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
