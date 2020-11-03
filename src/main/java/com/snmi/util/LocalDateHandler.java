package com.snmi.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.snmi.exception.IllegalArgumentException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.stream.Stream;

/**
 * LocalDateHandler
 * This handler is used for date parsing.
 * It operates with almost all well-known date formats.
 * @version 0.0.1
 */
public class LocalDateHandler extends StdDeserializer<LocalDate> {

    private static final String MESSAGE = "Date (%s) can't be parsed";
    private static final DateTimeFormatter[] formatter = Stream.of(
            "yyyy-MM-dd", "yyyy-dd-MM", "MM-dd-yyyy", "dd-MM-yyyy", "yyyy/MM/dd", "yyyy/dd/MM", "MM/dd/yyyy",
            "dd/MM/yyyy", "yyyy.MM.dd", "yyyy.dd.MM", "MM.dd.yyyy", "dd.MM.yyyy", "M/d/yy", "d/M/yy", "yy/M/d",
            "dd-M-yyyy", "M-dd-yyyy", "yyyy-M-dd", "yyyy-dd-M", "dd/M/yyyy", "M/dd/yyyy", "yyyy/M/dd", "yyyy/dd/M",
            "dd.M.yyyy", "M.dd.yyyy", "yyyy.M.dd", "yyyy.dd.M", "yyyy-M-d", "yyyy-d-M", "M-d-yyyy", "d-M-yyyy",
            "yyyy/M/d", "yyyy/d/M", "M/d/yyyy", "d/M/yyyy", "yyyy.M.d", "yyyy.d.M", "M.d.yyyy", "d.M.yyyy")
            .map(DateTimeFormatter::ofPattern)
            .toArray(DateTimeFormatter[]::new);

    protected LocalDateHandler() {
        super(LocalDate.class);
    }

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        for (DateTimeFormatter formatter : formatter) {
            try {
                return LocalDate.parse(jsonParser.getValueAsString(), formatter);
            } catch (DateTimeParseException ignored) {

            }
        }

        throw new IllegalArgumentException(String.format(MESSAGE, jsonParser.getValueAsString()));
    }
}
