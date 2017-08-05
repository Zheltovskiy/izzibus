package kz.izzi.bus.service.dto.sms;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Vladislav Zheltovskiy
 */
public class SmsResponse<T> {
    /**
     * Код ответа API
     */
    private ResponseCode code;
    /**
     * Текст статуса, обычно в общих чертах описывающий ошибки при их наличии
     */
    @JsonProperty(value = "message")
    private String responseMessage;

    /**
     * Возвращаемые данные в формате, предусмотренном выполняемым методом API, а в случае кода ошибки возможно содержание данных, расшифровывающих
     * причины ошибки (например, список ошибочных полей и тексты ошибок в них в случае ошибки валидации)
     */
    private T data;

    /**
     *
     */
    private boolean isError;

    public ResponseCode getCode() {
        return code;
    }

    public void setCode(ResponseCode code) {
        this.code = code;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isError() {
        return this.code != ResponseCode.API_BACKGROUND_WAIT && this.code != ResponseCode.API_OK;
    }


}
