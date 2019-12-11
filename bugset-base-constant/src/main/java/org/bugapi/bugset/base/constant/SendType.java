package org.bugapi.bugset.base.constant;

/**
 * 消息发送类型
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class SendType {

    /**
     * 单独发送
     */
    public static final int SINGLE_SEND = 0;

    /**
     * 群组发送
     */
    public static final int GROUP_SEND = 1;

    /**
     * 所有人发送【貌似消息类型中的公告消息包含了这种情况】
     */
    public static final int ALL_SEND = 2;
}
