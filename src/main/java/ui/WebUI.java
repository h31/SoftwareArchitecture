package ui;

/**
 * Created by artyom on 23.05.16.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import objects.*;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import repository.RepositoryLocal;
import services.Facade;
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
        Facade facade;
        try {
            InitialContext context = new InitialContext();
            facade = (Facade) context.lookup("java:module/FacadeImpl");
            repo = facade.getRepo();
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
            facade.addPaper(paper);
            response.redirect("researcher");
            return "";
        });

        post("/editorDecision", (request, response) -> {
            Set<String> params = request.queryParams();
            String uuidString = request.queryParams("uuid");
            String remarkText = request.queryParams("remark");
            facade.editorDecision(uuidString, params, remarkText);
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
            String remarkText = request.queryParams("remark");
            String user = request.cookie("reviewer");
            facade.reviewerDecision(uuidString, params, user, remarkText);
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
