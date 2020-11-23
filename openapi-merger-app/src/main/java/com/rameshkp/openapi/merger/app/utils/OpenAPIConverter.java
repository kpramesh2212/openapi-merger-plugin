package com.rameshkp.openapi.merger.app.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.core.util.Yaml;
import io.swagger.v3.oas.models.OpenAPI;

/**
 This is just a work around.
 Implementing the below methods in kotlin is causing a weired issue where json and yaml are not serialized properly
 */
public class OpenAPIConverter {
    /**
     * Provided an open api object this method converts it to a json string
     * @param openAPI object to convert
     * @return json string of the object
     * @throws JsonProcessingException when json parsing fails
     */
    public static String toJson(OpenAPI openAPI) throws JsonProcessingException {
        return Json.pretty().writeValueAsString(openAPI);
    }

    /**
     * Provided an open api object this method converts it to a json string
     * @param openAPI object to convert
     * @return yaml string of the object
     * @throws JsonProcessingException when yaml parsing fails
     */
    public static String toYaml(OpenAPI openAPI) throws JsonProcessingException {
        return Yaml.pretty().writeValueAsString(openAPI);
    }
}
