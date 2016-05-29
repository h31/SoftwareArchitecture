package ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import spark.ResponseTransformer;

/**
 * Created by artyom on 23.05.16.
 */

public class JacksonTransformer implements ResponseTransformer {
    ObjectMapper mapper;

    public JacksonTransformer(ObjectMapper mapper) {
        this.mapper = mapper;
        mapper.registerModule(new Jdk8Module());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public String render(Object model) {
        try {
            return mapper.writeValueAsString(model);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }
}