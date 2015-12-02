package flyingpenguincarcesses.ema;


import android.app.Activity;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.EditText;

import java.util.ArrayList;

import android.content.Intent;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import android.content.*;
import android.util.Log;


/**
 * Created by adrianapadilla on 11/22/15.
 */

public class ContactDetails extends Activity{
    TextView name;
    ListView mess;
    public static final String msg ="msg";
    ArrayList<String> messages;
    ArrayAdapter<String> messAdapter;
    int contactIndex;
    String buffer;
    Contact contact;
    Button smsbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        messages = new ArrayList<String>();

        contactIndex = getIntent().getIntExtra(msg, -1);
        contact = ContactList.contactArrayList.get(contactIndex);


        name = (TextView) findViewById(R.id.name);
        name.setText(contact.getName());

        smsbutton = (Button) findViewById(R.id.smsbutton);

        int i = 0;
        while (i < contact.receivedSize() || i < contact.sentSize()) {

            if (contact.getSent(i) != null) {
                buffer = "You: " + contact.getSent(i).getMessage();
                messages.add(buffer);
            }

            if (contact.getReceived(i) != null) {
                buffer = contact.getName() + ": " + contact.getReceived(i).getMessage();
                messages.add(buffer);
            }

            i++;

        }

        ArrayAdapter<String> messAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, messages);

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
        messages.add("you: " + buffer);
        sendSMS(contact.getNumber(), buffer);
        mess.setAdapter(messAdapter);

        message.setText("");
    }

//    public void refreshSmsInbox() {
//        ContentResolver contentResolver = getContentResolver();
//        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
//        int indexBody = smsInboxCursor.getColumnIndex("body");
//        int indexAddress = smsInboxCursor.getColumnIndex("address");
//        long timeMillis = smsInboxCursor.getColumnIndex("date");
//        Date date = new Date(timeMillis);
//        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
//        String dateText = format.format(date);
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, messages);
//        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
//        arrayAdapter.clear();
//        do {
//            String str = smsInboxCursor.getString(indexAddress) +" at "+
//                    "\n" + smsInboxCursor.getString(indexBody) +dateText+ "\n";
//            messages.add(str);
//        } while (smsInboxCursor.moveToNext());
//    }
//    @Override
//    public void onMessageReceived(Context context, Intent intent){
//        Object[] rawMsg = (Object[])intent.getExtras().get("pdus");
//        for(Object raw : rawMsg){
//            SmsMessage msg = SmsMessage.createFromPdu((byte[])raw);
//            if(msg.getMessageBody().toUpperCase().contains("SERKIT")){
//                Log.w("SMS:" + msg.getOriginatingAddress(), msg.getMessageBody());
//
//            }
//        }
//    }

}