package flyingpenguincarcesses.ema;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class addContact extends Activity {

    EditText contactName;
    EditText contactNumber;
    EditText ReceivedPass;
    EditText SentPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);


    }

    public void onAddContact(View v){
        //initialize EditTexts
        contactName = (EditText) findViewById(R.id.contactName);
        contactNumber = (EditText) findViewById(R.id.contactNumber);
        ReceivedPass = (EditText) findViewById(R.id.ReceivedPass);
        SentPass = (EditText) findViewById(R.id.SentPass);

        //grab new contact details
        String name = contactName.getText().toString();
        String number = contactNumber.getText().toString();
        String receive = ReceivedPass.getText().toString();
        String send = SentPass.getText().toString();

        //write new contact
        new Contact(name, number, receive, send, getApplicationContext());

        //go back to contact list
        Intent i = new Intent(v.getContext(), ContactList.class);
        startActivity(i);


    }
}
