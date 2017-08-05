package kz.izzi.bus.service;

import kz.izzi.bus.service.dto.sms.SmsRequest;
import kz.izzi.bus.service.dto.sms.SmsResponse;
import kz.izzi.bus.service.util.SmsResponseHttpConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.Collections;

/**
 * @author Vladislav Zheltovskiy
 */
@Service
public class SmsService {
    public static final Logger log = LoggerFactory.getLogger(SmsService.class);

    private static final String API_KEY = "1af314aeebec6685f8af53cecf84895608f84e50";
    private static final String QUERY_PARAMS_TEMPLATE = "?apiKey=%s&recipient=%s&text=%s";
    private static final String BASE_URL = "https://api.mobizon.com/service";
    private static final String SINGLE_MESSAGE_PATH = "/message/sendsmsmessage";
    private RestTemplate template;

    public SmsService() {
        template = new RestTemplate();
        template.setMessageConverters(Collections.singletonList(new SmsResponseHttpConverter()));
    }

    public SmsResponse sendMessage(SmsRequest request) {
        log.debug("Sending sms request {}", request);

        try {
            return template.getForObject(toSendMessageRequest(request.getRecipient(), request.getText()), SmsResponse.class);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unable to encode URL", e);
        }

    }

    private static String toSendMessageRequest(String recipient, String text) throws UnsupportedEncodingException {
        return new StringBuffer(BASE_URL)
                .append(SINGLE_MESSAGE_PATH)
                .append(String.format(QUERY_PARAMS_TEMPLATE, API_KEY, recipient, text))
                .toString();
    }

}
