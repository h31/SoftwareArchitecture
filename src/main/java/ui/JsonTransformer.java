package ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.ResponseTransformer;

import java.io.File;

/**
 * Created by artyom on 23.05.16.
 */

public class JsonTransformer implements ResponseTransformer {
    ObjectMapper jsonMapper = new ObjectMapper();

    @Override
    public String render(Object model) {
        try {
            return jsonMapper.writeValueAsString(model);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }
}