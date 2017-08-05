package kz.izzi.bus.service.dto.sms;

/**
 * @author Vladislav Zheltovskiy
 */
public class SendSmsResponseData {
    // Поля, получаемые в случае успешной отправки SMS
    private String campaignId;
    private String messageId;
    private String status;

    // Поля, которые могут быть получены в случае ошибок

    private String from;
    private String apiKey;
    private String recipient;
    private String text;

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
