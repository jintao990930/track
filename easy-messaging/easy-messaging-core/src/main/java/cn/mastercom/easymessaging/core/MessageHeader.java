package cn.mastercom.easymessaging.core;

import java.util.Collection;

/**
 *
 * @author zhongjintao
 * @date 2025-08-22
 */
public interface MessageHeader {

    String getMessageId();

    Sender getSender();

    Collection<? extends Receiver> getReceivers();

}
