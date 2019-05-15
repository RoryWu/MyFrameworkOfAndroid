package com.jake.experience.thirdpartlib.event_bus_tool;

/**
 * @author jake
 * @version 1.0
 * @date 19-1-22
 */
public class EventBusMsg<T> {
    private int code;
    private T data;

    public EventBusMsg(int code) {
        this.code = code;
    }

    public EventBusMsg(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
