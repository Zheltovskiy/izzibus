package kz.izzi.bus.service.dto.sms;

/**
 * @author Vladislav Zheltovskiy
 */
public enum ResponseCode {
    API_OK(0),
    API_ERROR_VALIDATION(1),
    API_ERROR_RECORD_NOT_FOUND(2),
    API_ERROR_UNKNOWN(3),
    API_ERROR_PROVIDER_NOT_FOUND(4),
    API_ERROR_METHOD_NOT_FOUND(5),
    API_ERROR_FORMAT_NOT_SUPPORTED(6),
    API_ERROR_PARAMETER_NOT_FOUND(7),
    API_ERROR_AUTHENTICATION(8),
    API_ERROR_AUTHORIZATION(9),
    API_ERROR_DATA_UPDATE(10),
    API_ERROR_API_PARAMS(11),
    API_ERROR_INCORRECT_PARAMETER(12),
    API_ERROR_INCORRECT_DOMAIN(13),
    API_ERROR_USER_BLOCKED(14),
    API_ERROR_PARTIALLY_DONE(98),
    API_ERROR_NOTHING_DONE(99),
    API_BACKGROUND_WAIT(100),
    API_ERROR_COMMON(999);

    private int code;

    private ResponseCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    //    integer	API_OK
    //    Операция завершена успешно.
    //    #0
    //    integer	API_ERROR_VALIDATION
    //    Ошибка валидации передаваемых данных во время создания или обновления какой-либо сущности. В поле data представлена информация о том, какие поля заполнены неверно. Следует исправить ошибки и повторить запрос с новыми данными.
    //    #1
    //    integer	API_ERROR_RECORD_NOT_FOUND
    //    Указанная запись не найдена. Скорее всего она была удалена, ID записи указан неверно или у пользователя, пытающегося получить доступ к этой записи, нет соответствующих прав доступа к этой записи.
    //            #2
    //    integer	API_ERROR_UNKNOWN
    //    Неопознанная ошибка приложения. Обратитесь в службу поддержки и сообщите детали запроса, при котором она была получена.
    //            #3
    //    integer	API_ERROR_PROVIDER_NOT_FOUND
    //    Неверно указан параметр module. Проверьте правильность написания параметра в документации к API.
    //            #4
    //    integer	API_ERROR_METHOD_NOT_FOUND
    //    Неверно указан параметр method. Проверьте правильность написания параметра в документации к API.
    //            #5
    //    integer	API_ERROR_FORMAT_NOT_SUPPORTED
    //    Неверно указан параметр format. Проверьте правильность написания параметра в документации к API.
    //            #6
    //    integer	API_ERROR_PARAMETER_NOT_FOUND
    //    Возникает в случае, когда один из обязательных параметров запроса (module, method) не был указан.
    //            #7
    //    integer	API_ERROR_AUTHENTICATION
    //    Ошибка входа в систему. Ошибка возникает в случаях: 1. Неправильно указанных данных для входа. 2. Когда во время работы с системой сессия пользователя истекла или была принудительно закрыта сервером. Более подробную информацию можно увидеть в поле message.
    //            #8
    //    integer	API_ERROR_AUTHORIZATION
    //    Ошибка доступа к указанному методу
    //    #9
    //    integer	API_ERROR_DATA_UPDATE
    //    Ошибка во время сохранения данных на сервере непосредственно в процессе выполнения данной операции. Обычно эта ошибка связана с одновременным доступом к данным из нескольких клиентов или изменением условий сохранения данных в процессе их сохранения.
    //    #10
    //    integer	API_ERROR_API_PARAMS
    //    Некоторые обязательные параметры отсутствуют в запросе. Проверьте правильность написания параметров в документации к API и дополните запрос необходимыми параметрами.
    //    #11
    //    integer	API_ERROR_INCORRECT_PARAMETER
    //    Входной параметр запроса не удовлетворяет установленным условиям или ограничениям. Данный код ошибки возникает в случаях, когда при выполнении запроса с параметрами какой-либо параметр нарушает ограничения. Похоже на ошибку валидации атрибутов, но может быть получено в запросах, которые не производять создание или изменение данных.
    //            #12
    //    integer	API_ERROR_INCORRECT_DOMAIN
    //    Попытка сделать запрос к серверу апи, который не обслуживает данного пользователя. В случае получения этого кода правильный домен можно получить в поле data
    //    #13
    //    integer	API_ERROR_USER_BLOCKED
    //    Данная ошибка возникает в случае если аккаунт пользователя заблокирован
    //    #14
    //    integer	API_ERROR_PARTIALLY_DONE
    //    Операция выполнена не в полном объеме, а только с частью данных. Обычно данный код возвращается при каких либо массовых операциях, во время выполнения которых некоторые элементы не были обработаны из-за ошибок или ограничений, но часть элементов обработана. В случае получения этого кода можно получить информацию о том, какие элементы были обработаны, а какие нет и с какими ошибками, получив содержимое поля data.
    //            #98
    //    integer	API_ERROR_NOTHING_DONE
    //    Ни один из элементов массовой операции не был обработан. Подробную информацию об ошибках в каждом конкретном элементе можно получить в поле data, а общее описание ошибки в поле message
    //    #99
    //    integer	API_BACKGROUND_WAIT
    //    Данный код не является ошибкой и означает, что операция была отправлена в фоновое выполнение. В этом случае поле data содержит ID фоновой операции, процесс и окончание которой можно отследить при помощи Taskqueue::GetStatus
    //    #100
    //    integer	API_ERROR_COMMON
    //    Общая ошибка. Детали можно получить в поле message.
    //            #999
}