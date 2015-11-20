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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        contactArrayList = new ArrayList<Contact>();
        names = new ArrayList<String>();
        contacts = (ListView) findViewById(R.id.contacts);


        contactArrayList.add(new Contact("Zach"));
        contactArrayList.add(new Contact("Steven"));
        contactArrayList.add(new Contact("Adriana"));

        for(int i=0; i<contactArrayList.size(); i++)
        {
            names.add(contactArrayList.get(i).getName());
        }

        ArrayAdapter<String> list = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names );

        contacts.setAdapter(list);
        contacts.setOnItemClickListener(this);


    }

    public void onAddContact(View v){
        EditText addContact = (EditText) findViewById(R.id.addContact);
        String name = addContact.getText().toString();

        contactArrayList.add(new Contact(name));
        names.add(contactArrayList.get(contactArrayList.size() - 1).getName());

        addContact.setText("");
    }

    public void onItemClick(AdapterView<?> parent,View v, int position, long id){
        Intent i = new Intent(v.getContext(), ContactDetails.class);
        i.putExtra(ContactDetails.msg,position);

        startActivity(i);

    }
}
