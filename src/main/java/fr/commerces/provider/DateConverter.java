package fr.commerces.provider;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.ext.ParamConverter;

public class DateConverter implements ParamConverter<Date> {

    public static final String format = "yyyy-MM-dd"; // set the format to whatever you need

    @Override
    public Date fromString(String string) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
 
            try {
				return simpleDateFormat.parse(string);
			} catch (java.text.ParseException e) {
				throw new RuntimeException("Format invalide", e);
			}
    }

    @Override
    public String toString(Date t) {
        return new SimpleDateFormat(format).format(t);
    }

}