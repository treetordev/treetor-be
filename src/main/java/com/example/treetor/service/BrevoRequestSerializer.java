package com.example.treetor.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class BrevoRequestSerializer extends JsonSerializer<BrevoRequest> {
    @Override
    public void serialize(BrevoRequest request, JsonGenerator generator, SerializerProvider serializers) throws IOException {
        generator.writeStartObject();
        generator.writeArrayFieldStart("to");
        for (String receiver : request.getReceivers()) {
            generator.writeStartObject();
            generator.writeStringField("email", receiver);
            generator.writeEndObject();
        }
        generator.writeEndArray();
        generator.writeNumberField("templateId", request.getTemplate().template());
        if (!request.getTemplate().params().isEmpty()) {
            generator.writeObjectFieldStart("params");
            for (java.util.Map.Entry<String, Object> entry : request.getTemplate().params().entrySet()) {
                generator.writeStringField(entry.getKey(), entry.getValue().toString());
            }
            generator.writeEndObject();
        }
        generator.writeEndObject();
    }
}
