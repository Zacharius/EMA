package flyingpenguincarcesses.ema;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactDetails extends Activity {
    TextView name;
    ListView mess;
    public static final String msg ="msg";
    ArrayList<String> messages;
    int contactIndex;
    String buffer;
    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        messages = new ArrayList<String>();

        contactIndex = getIntent().getIntExtra(msg, -1);
        contact = ContactList.contactArrayList.get(contactIndex);


        name = (TextView) findViewById(R.id.name);
        name.setText(contact.getName());



        int i = 0;
        while(i < contact.receivedSize() || i <  contact.sentSize() ){

            if(contact.getSent(i) != null){
                buffer = "You: " + contact.getSent(i).getMessage();
                messages.add(buffer);
            }

            if(contact.getReceived(i) != null){
                buffer = contact.getName() + ": " + contact.getReceived(i).getMessage();
                messages.add(buffer);
            }

            i++;

        }

        ArrayAdapter<String> messAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, messages );

        mess = (ListView) findViewById(R.id.mess);
        mess.setAdapter(messAdapter);



    }
}
