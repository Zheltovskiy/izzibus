package kz.izzi.bus.service.dto.sms;

/**
 * @author Vladislav Zheltovskiy
 */
public class SmsRequest {
    private String recipient;
    private String text;

    public SmsRequest(String recipient, String text) {
        this.recipient = recipient;
        this.text = text;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getText() {
        return text;
    }


    @Override
    public String toString() {
        return "SmsRequest{" +
                "recipient='" + recipient + "'" +
                ", text='" + text + "'" +
                '}';
    }
}
