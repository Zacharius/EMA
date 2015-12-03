package flyingpenguincarcesses.ema;

/**
 * Created by Zacharius on 11/19/2015.
 */
public class Message {

    private String message;
    private long time;

    public Message(String message){
        time = System.currentTimeMillis();
        this.message = message;
    }

    public Message(String message, long time){
        this.message = message;
        this.time = time;
    }

    public String getMessage(){
        return message;
    }

    public long getTime(){
        return time;
    }

}
