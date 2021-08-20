package fr.mycommerce.view.categories;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("zoneDateTimeConverter")
public class ZoneDateTimeConverter implements Converter<LocalDateTime> {

    @Override
    public LocalDateTime getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        try {
        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
        			.withZone(ZoneOffset.UTC);
            return LocalDateTime.parse(value, formatter);
        } catch (IllegalArgumentException | DateTimeException e) {
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "Message"), e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, LocalDateTime value) {
        if (value == null) {
            return "";
        }
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        		.withZone(ZoneId.of("Europe/Paris"))
        		.withLocale(Locale.FRENCH)
        		.format(value.atZone(ZoneOffset.UTC));
    }
}