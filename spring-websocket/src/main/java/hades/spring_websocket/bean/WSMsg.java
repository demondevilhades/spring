package hades.spring_websocket.bean;

public class WSMsg {
    
    public static final String TYPE_MSG = "msg";
    public static final String TYPE_CHANNEL = "channel";

    private String type;
    private String msg;
    private String from;

    public WSMsg() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
