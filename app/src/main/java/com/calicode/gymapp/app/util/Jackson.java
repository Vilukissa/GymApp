package com.calicode.gymapp.app.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

public class Jackson {

    public static final ObjectReader OBJECT_READER;

    /**
     * https://github.com/FasterXML/jackson-databind/wiki/Mapper-Features
     */
    static {
        // Disabling some unnecessary features
        final ObjectMapper mapper = new ObjectMapper()
                .disable(MapperFeature.AUTO_DETECT_IS_GETTERS)
                .disable(MapperFeature.AUTO_DETECT_GETTERS)
                .disable(MapperFeature.AUTO_DETECT_SETTERS)
                .disable(MapperFeature.USE_GETTERS_AS_SETTERS)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        OBJECT_READER = mapper.reader();
    }
}
