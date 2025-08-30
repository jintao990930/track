package cn.mastercom.easymessaging.core;

/**
 * 消息发送异常
 *
 * @author zhongjintao
 * @date 2025-08-22
 */
public class MessageSendException extends Exception {

    public MessageSendException() {
        super();
    }

    public MessageSendException(String message) {
        super(message);
    }

    public MessageSendException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageSendException(Throwable cause) {
        super(cause);
    }
}