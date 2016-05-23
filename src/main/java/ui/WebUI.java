package ui;

/**
 * Created by artyom on 23.05.16.
 */

import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import repository.Repository;
import repository.RepositoryInit;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.HashMap;
import java.util.Map;

public class WebUI {
    public static void main(String[] args) {
//        String projectDir = System.getProperty("user.dir");
//        staticFiles.externalLocation(projectDir + "/tmp/journal/dist");

        RepositoryInit init = new RepositoryInit();
        init.addResearchers();
        init.addJournals();
        init.addSubmission();
        Repository repo = init.getRepo();

        get("/papers", (request1, response1) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("papers", repo.papers.getList());
            return new ModelAndView(attributes, "stats.html");
        }, new ThymeleafTemplateEngine(new ClassLoaderTemplateResolver()));

        post("/papers", (request, response) -> {
            response.header("Access-Control-Allow-Origin", "http://localhost:4200");
            System.out.println(request.body());
            System.out.println(request.queryParams());
            return "[]";
        });
    }
}
