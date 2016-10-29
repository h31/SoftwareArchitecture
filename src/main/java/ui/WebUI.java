package ui;

/**
 * Created by artyom on 23.05.16.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import objects.*;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import repository.Repository;

import repository.RepositoryInit;
import repository.RepositoryLocal;
import spark.ModelAndView;
import spark.servlet.SparkApplication;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static spark.Spark.*;

public class WebUI implements SparkApplication {
    @Override
    public void init() {
        staticFiles.location("/"); // Static files

        RepositoryLocal repo;
        try {
            InitialContext context = new InitialContext();
            repo = (RepositoryLocal) context.lookup("java:module/Repository");
        } catch (NamingException e) {
            e.printStackTrace();
            return;
        }
        repo.recreate();
        repo.init();

        get("/researcher", (request1, response1) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("repo", repo);
            String r = request1.cookie("researcher");
            attributes.put("currentRes", r != null ? r : "Not selected");
            return new ModelAndView(attributes, "researcher.html");
        }, new ThymeleafTemplateEngine(new ClassLoaderTemplateResolver()));

        get("/editor", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("repo", repo);
            return new ModelAndView(attributes, "editor.html");
        }, new ThymeleafTemplateEngine(new ClassLoaderTemplateResolver()));

        post("/chooseResearcher", (request, response) -> {
            String r = request.queryParams("researcher");
            response.cookie("researcher", r);
            response.redirect("researcher");
            return "";
        });

        post("/addSubmission", (request, response) -> {
            String r = request.cookie("researcher");
            if (r == null) {
                return "Please select researcher first";
            }

            Optional<Researcher> researcher = repo.getResearchers().get(r);
            if (!researcher.isPresent()) {
                return "Please select researcher first";
            }

            String title = request.queryParams("title");
            String abstractTxt = request.queryParams("abstract");;
            String content = request.queryParams("content");;
            Paper paper = new Paper(title, Collections.singletonList(researcher.get()),
                    Collections.emptyList(), abstractTxt, content);
            repo.getPapers().add(paper);
            Submission submission = new Submission(paper);
            repo.getSubmissions().add(submission);
            response.redirect("researcher");
            return "";
        });

        post("/editorDecision", (request, response) -> {
            Set<String> params = request.queryParams();
            String uuidString = request.queryParams("uuid");
            UUID uuid = UUID.fromString(uuidString);
            Optional<Submission> submission = repo.getSubmissions().get(uuid);
            if (!submission.isPresent()) {
                halt(500, "No such submission");
            }
            String remarkText = request.queryParams("remark");
            EditorialRemark.Decision decision;
            if (params.contains("accepted")) {
                decision = EditorialRemark.Decision.ACCEPT;
            } else if (params.contains("rework")) {
                decision = EditorialRemark.Decision.NEEDS_REWORK;
            } else if (params.contains("redirect")) {
                decision = EditorialRemark.Decision.REDIRECT;
            } else {
                halt(500, "Invalid decision");
                return "";
            }
            EditorialRemark remark = new EditorialRemark(decision, remarkText);
            repo.getSubmissionUpdate().editorialUpdate(submission.get(), remark);

            response.redirect("editor");
            return "";
        });

        get("/reviewer", (request1, response1) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("repo", repo);
            String r = request1.cookie("reviewer");
            attributes.put("currentUser", r != null ? r : "Not selected");
            return new ModelAndView(attributes, "reviewer.html");
        }, new ThymeleafTemplateEngine(new ClassLoaderTemplateResolver()));

        post("/chooseReviewer", (request, response) -> {
            String r = request.queryParams("user");
            response.cookie("reviewer", r);
            response.redirect("reviewer");
            return "";
        });

        post("/reviewerDecision", (request, response) -> {
            Set<String> params = request.queryParams();
            String uuidString = request.queryParams("uuid");
            UUID uuid = UUID.fromString(uuidString);
            Optional<Submission> submission = repo.getSubmissions().get(uuid);
            if (!submission.isPresent()) {
                halt(500, "No such submission");
            }
            String remarkText = request.queryParams("remark");
            ReviewerRemark.Mark mark;
            if (params.contains("accept")) {
                mark = ReviewerRemark.Mark.ACCEPT;
            } else if (params.contains("neutral")) {
                mark = ReviewerRemark.Mark.NEUTRAL;
            } else if (params.contains("reject")) {
                mark = ReviewerRemark.Mark.REJECT;
            } else {
                halt(HttpServletResponse.SC_BAD_REQUEST, "Invalid decision");
                return "";
            }
            String user = request.cookie("reviewer");
            if (user == null) {
                halt(HttpServletResponse.SC_BAD_REQUEST, "Please choose reviewer first");
            }
            Optional<Reviewer> reviewer = repo.getReviewers().get(user);
            if (!reviewer.isPresent()) {
                halt(HttpServletResponse.SC_BAD_REQUEST, "Unknown reviewer");
            }
            ReviewerRemark remark = new ReviewerRemark(reviewer.get(), mark, remarkText, UUID.randomUUID());
            repo.getSubmissionUpdate().reviewerUpdate(submission.get(), remark);

            response.redirect("reviewer");
            return "";
        });

        get("/paperFetch", (request, response) -> {
            response.type("application/json");
            return repo.getSubmissionUpdate().getInPool();
        }, new JacksonTransformer(new ObjectMapper()));

        get("/paperFetchXML", (request, response) -> {
            response.type("application/xml");
            return repo.getSubmissionUpdate().getInPool();
        }, new JacksonTransformer(new XmlMapper()));

        get("/similar", (request1, response1) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("repo", repo);
            return new ModelAndView(attributes, "similar.html");
        }, new ThymeleafTemplateEngine(new ClassLoaderTemplateResolver()));
    }
}
