package flyingpenguincarcesses.ema;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ContactList extends Activity implements AdapterView.OnItemClickListener {

    ListView contacts;
    public static ArrayList<Contact> contactArrayList;
    public static ArrayList<String>  names;
    ArrayAdapter<String> list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        contactArrayList = new ArrayList<Contact>();
        names = new ArrayList<String>();
        contacts = (ListView) findViewById(R.id.contacts);



        /*contactArrayList.get(0).send("AYY Baby, U Wat Sum Fuk?");
        contactArrayList.get(0).receive("You're drunk Steven, go to bed");
        contactArrayList.get(0).send("Awww :(");
        contactArrayList.get(0).receive("Goodnight Steven");*/




        contactArrayList = Contact.readContacts(getApplicationContext());

        for(int i=0; i<contactArrayList.size(); i++)
        {
            names.add(contactArrayList.get(i).getName());
        }

        list = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names );

        contacts.setAdapter(list);
        contacts.setOnItemClickListener(this);


    }

    public void onAddContact(View v){
        EditText addContact = (EditText) findViewById(R.id.addContact);
        EditText addNumber = (EditText) findViewById(R.id.number);
        String name = addContact.getText().toString();
        String number = addNumber.getText().toString();

        Contact.writeContact(new Contact(name, number),getApplicationContext());
        contactArrayList = Contact.readContacts(getApplicationContext());

        names.add(contactArrayList.get(contactArrayList.size() - 1).getName());
        contacts.setAdapter(list);

        addContact.setText("");
    }

    public void onItemClick(AdapterView<?> parent,View v, int position, long id){
        Intent i = new Intent(v.getContext(), ContactDetails.class);
        i.putExtra(ContactDetails.msg,position);

        startActivity(i);

    }
}
