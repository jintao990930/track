package cn.mastercom.easymessaging.core;

/**
 * 消息模板解析异常
 *
 * @author zhongjintao
 * @date 2025-08-22
 */
public class MessageTemplateParseException extends Exception {

    public MessageTemplateParseException() {
        super();
    }

    public MessageTemplateParseException(String message) {
        super(message);
    }

    public MessageTemplateParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageTemplateParseException(Throwable cause) {
        super(cause);
    }
}