package fr.webmaker.restfull.databind;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
    @Override
    public void serialize(LocalDateTime arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException {
    	DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    	formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm'Z'");
    	
    	String formattedDateTime = arg0.format(formatter);
    	
    	arg1.writeString(arg0.atOffset(ZoneOffset.UTC)
        		.format(formatter));
    }
}