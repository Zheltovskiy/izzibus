package kz.izzi.bus.service.util;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.Arrays;
import java.util.List;

/**
 * @author Vladislav Zheltovskiy
 */
public class SmsResponseHttpConverter extends MappingJackson2HttpMessageConverter {
    public SmsResponseHttpConverter() {
        List<MediaType> types = Arrays.asList(
                new MediaType("text", "javascript", DEFAULT_CHARSET),
                new MediaType("application", "json", DEFAULT_CHARSET),
                new MediaType("application", "*json", DEFAULT_CHARSET)
        );
        super.setSupportedMediaTypes(types);
    }
}
