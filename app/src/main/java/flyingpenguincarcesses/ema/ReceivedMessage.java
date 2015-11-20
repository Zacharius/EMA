package flyingpenguincarcesses.ema;

/**
 * Created by Zacharius on 11/19/2015.
 */
public class ReceivedMessage extends Message {

    String from;

    public ReceivedMessage(String message, String from){
        super(message);
        this.from = from;
    }

    public String fromWho(){
        return from;
    }
}
