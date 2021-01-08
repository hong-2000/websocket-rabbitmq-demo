package cn.zqhblog.clientnoparam.service;

/**
 * @author hong-2000
 * @version 1.0.0
 * @description WebSocketService服务
 * @create 2020/11/17 15:02
 */
public interface WebSocketService {
    /**
     * 群发
     * @param message
     */
    void groupSending(String message);

    /**
     * 指定发送
     * @param name
     * @param message
     */
    void appointSending(String name, String message);
}
