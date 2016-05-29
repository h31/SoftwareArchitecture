package services;

import objects.Paper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by artyom on 29.05.16.
 */
public class SimilarPapers {
    OkHttpClient client = new OkHttpClient();
    String expression = "/feed/entry/title";
    XPathExpression xPath;

    public SimilarPapers() {
        try {
            xPath = XPathFactory.newInstance().newXPath().compile(expression);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    private HttpUrl makeUrl(Paper paper) {
        // http://export.arxiv.org/api/query?search_query=cats&start=0&max_results=5
        return new HttpUrl.Builder()
                .scheme("http")
                .host("export.arxiv.org")
                .addPathSegment("api")
                .addPathSegment("query")
                .addQueryParameter("search_query", paper.getTitle())
                .addQueryParameter("max_results", "3")
                .build();
    }

    private Optional<InputStream> fetchXml(HttpUrl url) {
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return Optional.ofNullable(response.body().byteStream());
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private List<String> parseXml(InputStream stream) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(stream);
            doc.getDocumentElement().normalize();

            NodeList titles = (NodeList) xPath.evaluate(doc, XPathConstants.NODESET);
            List<String> output = new ArrayList<>();
            for (int i = 0; i < titles.getLength(); i++) {
                String value = titles.item(i).getFirstChild().getNodeValue();
                output.add(value);
            }
            return output;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<String> getSimilarTitles(Paper paper) {
        HttpUrl url = makeUrl(paper);
        Optional<InputStream> stream = fetchXml(url);
        if (stream.isPresent()) {
            return parseXml(stream.get());
        } else {
            return Collections.emptyList();
        }
    }
}
