package flyingpenguincarcesses.ema;

/**
 * Created by Zacharius on 11/19/2015.
 */
public class Message {

    private String message;
    private int time;

    public Message(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public int getTime(){
        return time;
    }

}
