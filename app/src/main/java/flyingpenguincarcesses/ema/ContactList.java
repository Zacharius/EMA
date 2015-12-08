package flyingpenguincarcesses.ema;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class  ContactList extends Activity implements AdapterView.OnItemClickListener {

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
        Intent i = new Intent(v.getContext(), addContact.class);
        startActivity(i);
    }

    public void onItemClick(AdapterView<?> parent,View v, int position, long id){
        Intent i = new Intent(v.getContext(), ContactDetails.class);
        i.putExtra(ContactDetails.msg,position);

        startActivity(i);
    }
}
