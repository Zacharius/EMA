package flyingpenguincarcesses.ema;

/**
 * Created by Zacharius on 11/19/2015.
 */
public class SentMessage extends Message {

    String to;

    public SentMessage(String message, String to){
        super(message);
        this.to = to;

    }

    public String toWho(){
        return to;
    }
}
