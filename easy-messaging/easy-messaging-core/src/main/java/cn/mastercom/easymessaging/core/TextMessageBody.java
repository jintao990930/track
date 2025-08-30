package cn.mastercom.easymessaging.core;

import java.nio.charset.StandardCharsets;

/**
 *
 * @author zhongjintao
 * @date 2025-08-22
 */
public interface TextMessageBody extends MessageBody {

    default byte[] getContent() {
        return getTextContent().getBytes(StandardCharsets.UTF_8);
    }

    String getTextContent();

}
