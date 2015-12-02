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
    ListView mess;
    public static final String msg ="msg";
    ArrayList<String> messages = new ArrayList<String>();
    ArrayAdapter<String> messAdapter;
    int contactIndex;
    String buffer;
    Contact contact;
    Button smsbutton;
    //EditText messageBody = (EditText) findViewById(R.id.sendSMS);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);



        contactIndex = getIntent().getIntExtra(msg, -1);
        contact = ContactList.contactArrayList.get(contactIndex);


        name = (TextView) findViewById(R.id.name);
        name.setText(contact.getName());

        smsbutton = (Button) findViewById(R.id.smsbutton);
//        smsbutton.setOnClickListener(new View.OnClickListener()
//        {
//            public void onClick(View v){
//                sendSMS("2103812814", messageBody.getText().toString());
//            }
//        });

        int reicevedSize = contact.receivedSize();
        int sentSize = contact.sentSize();
        int r = 0;
        int s = 0;
        while((s<sentSize) || (r<reicevedSize)){
            if(contact.getReceived(r) == null)
            {
                messages.add("You: " + contact.getSent(s++).getMessage());
                continue;
            }

            if(contact.getSent(s) == null){
                messages.add(contact.getName() + ": " +  contact.getReceived(r++).getMessage());
                continue;
            }


            if(contact.getSent(s).getTime() > contact.getReceived(r).getTime()){
                messages.add("you: " + contact.getSent(s++).getMessage());
            }

            if(contact.getReceived(r).getTime() > contact.getSent(s).getTime()){
                messages.add(contact.getName() +  ": " + contact.getReceived(r++ ).getMessage());
            }
        }


        messAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, messages);

        mess = (ListView) findViewById(R.id.mess);
        mess.setAdapter(messAdapter);
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

        contact.send(buffer);
        contact.writeContact(getApplicationContext());

        messages.add("you: " + buffer);
        mess.setAdapter(messAdapter);

        sendSMS(contact.getNumber(), buffer);


        message.setText("");
    }
//    @Override
//    public void onMessageReceived(MessageEvent messageEvent){
//        if(messageEvent.getPath().equals(VOICE_TRANSCRIPTION_MESSAGE_PATH)){
//            Intent startIntent = new Intent(this, MainActivity.class);
//            startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startIntent.putExtra("VOICE_DATA", messageEvent.getData());
//            startActivity(startIntent);
//        }
//    }
}