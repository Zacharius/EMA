package flyingpenguincarcesses.ema;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by adrianapadilla on 11/22/15.
 */

public class ContactDetails extends Activity {
    TextView name;
    static ListView mess;
    public static final String msg ="msg";
    static ArrayList<String> messages;
    static ArrayAdapter<String> messAdapter;
    int contactIndex;
    String buffer;
    Contact contact;
    Button smsbutton;
    TextView number;
    //EditText messageBody = (EditText) findViewById(R.id.sendSMS);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        messages = new ArrayList<String>();

        contactIndex = getIntent().getIntExtra(msg, -1);
        contact = Contact.readContact(ContactList.contactArrayList.get(contactIndex).getName(), getApplicationContext());


        name = (TextView) findViewById(R.id.name);
        name.setText(contact.getName());

        number = (TextView) findViewById(R.id.number);
        number.setText(contact.getNumber());

        smsbutton = (Button) findViewById(R.id.smsbutton);


        /*
        for(int i=0; i<contact.sentSize(); i++)
        {
            messages.add(contact.getSent(i).getMessage());
        }

        for(int i=0; i<contact.receivedSize(); i++)
        {
            messages.add(contact.getReceived(i).getMessage());
        }*/
        int recieved = contact.receivedSize();
        int sent = contact.sentSize();

        int r = 0;
        int s = 0;
        while((contact.getReceived(r) != null)  || (contact.getSent(s) != null)){
            if(contact.getReceived(r) == null)
            {
                messages.add( "you: " + contact.getSent(s++).getMessage());
                continue;

            }

            if(contact.getSent(s) == null){
                messages.add(contact.getName() + ": " +  contact.getReceived(r++).getMessage());
                continue;
            }


            if(contact.getSent(s).getTime() <= contact.getReceived(r).getTime()){
                messages.add("you: " + contact.getSent(s++).getMessage());
                continue;
            }

            if(contact.getReceived(r).getTime() <= contact.getSent(s).getTime()){
                messages.add(contact.getName() +  ": " + contact.getReceived(r++).getMessage());
            }
        }

        messAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, messages);

        mess = (ListView) findViewById(R.id.mess);
        mess.setAdapter(messAdapter);
        mess.setSelection(messages.size()-1);
    }

    public void sendSMS(String phoneNumber, String message)
    {
        try{
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(getApplicationContext(),"SMS Sent", Toast.LENGTH_LONG).show();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Sending SMS failed.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }//ends sendSMS

    public void onSendSMS(View v){
        EditText message = (EditText) findViewById(R.id.sendSMS);
        buffer = message.getText().toString();

        if(!buffer.equals("")){
            contact.send(buffer);
            contact.writeContact(getApplicationContext());

            messages.add("you: " + buffer);
            mess.setAdapter(messAdapter);
            mess.setSelection(messages.size() - 1);

            String send = Message.encrypt(buffer, contact.getKey1(), contact.getKey2());


            sendSMS(contact.getNumber(), send);
        }

        message.setText("");
    }

}