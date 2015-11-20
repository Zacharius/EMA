package flyingpenguincarcesses.ema;

import java.util.ArrayList;

/**
 * Created by Zacharius on 11/18/2015.
 */
public class Contact {

    private String name;
    private ArrayList<SentMessage> sent;
    private ArrayList<ReceivedMessage> received;
    int history;



    public  Contact(String name){
        this.name = name;
        sent = new ArrayList<SentMessage>();
        received = new ArrayList<ReceivedMessage>();
        history = 0;
    }

    public void send(String message, String to){
        sent.add(new SentMessage(message, to));
        history++;

    }

    public void receive(String message, String from){
        received.add(new ReceivedMessage(message, from));
        history++;
    }

    public String getName(){
        return name;
    }

    public void updateContact(String name){
        this.name = name;
    }

    public SentMessage getSent(int index){
        return sent.get(index);
    }

    public ReceivedMessage getReceived(int index){
        return received.get(index);
    }






}
