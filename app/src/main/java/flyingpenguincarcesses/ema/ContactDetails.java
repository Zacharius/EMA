package flyingpenguincarcesses.ema;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ContactDetails extends Activity {
    TextView name;
    public static final String msg ="msg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        name = (TextView) findViewById(R.id.name);
        name.setText(ContactList.contactArrayList.get(getIntent().getIntExtra(msg, -1)).getName());


    }
}
