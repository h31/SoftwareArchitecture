package ui;

/**
 * Created by artyom on 23.05.16.
 */

import objects.Paper;
import objects.Researcher;
import objects.Submission;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import repository.Repository;
import repository.RepositoryInit;

import static spark.Spark.get;
import static spark.Spark.post;

import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class WebUI {
    public static void main(String[] args) {
//        String projectDir = System.getProperty("user.dir");
//        staticFiles.externalLocation(projectDir + "/tmp/journal/dist");

        RepositoryInit init = new RepositoryInit();
        init.addResearchers();
        init.addJournals();
        init.addSubmission();
        Repository repo = init.getRepo();

        get("/researcher", (request1, response1) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("repo", repo);
            String r = request1.cookie("researcher");
            attributes.put("currentRes", r != null ? r : "Not selected");
            return new ModelAndView(attributes, "researcher.html");
        }, new ThymeleafTemplateEngine(new ClassLoaderTemplateResolver()));

        post("/chooseResearcher", (request, response) -> {
            String r = request.queryParams("researcher");
            response.cookie("researcher", r);
            response.redirect("/researcher");
            return "";
        });

        post("/addSubmission", (request, response) -> {
            String r = request.cookie("researcher");
            if (r == null) {
                return "Please select researcher first";
            }

            Optional<Researcher> researcher = repo.researchers.get(r);
            if (!researcher.isPresent()) {
                return "Please select researcher first";
            }

            String title = request.queryParams("title");
            String abstractTxt = request.queryParams("abstract");;
            String content = request.queryParams("content");;
            Paper paper = new Paper(title, Collections.singletonList(researcher.get()),
                    Collections.emptyList(), abstractTxt, content);
            repo.papers.add(paper);
            Submission submission = new Submission(paper);
            repo.submissions.add(submission);
            response.redirect("/researcher");
            return "";
        });

        post("/papers", (request, response) -> {
            response.header("Access-Control-Allow-Origin", "http://localhost:4200");
            System.out.println(request.body());
            System.out.println(request.queryParams());
            return "[]";
        });
    }
}
