package fr.commerces.commons.mapper;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.valueextraction.ExtractedValue;
import javax.validation.valueextraction.UnwrapByDefault;
import javax.validation.valueextraction.ValueExtractor;

import org.openapitools.jackson.nullable.JsonNullable;

@ApplicationScoped
@UnwrapByDefault
public class JsonNullableValueExtractor implements ValueExtractor<@ExtractedValue JsonNullable<?>> {

    @Override
    public void extractValues(JsonNullable<?> originalValue, 
      ValueExtractor.ValueReceiver receiver) {
        receiver.value(null, originalValue.orElse(null));
    }
}