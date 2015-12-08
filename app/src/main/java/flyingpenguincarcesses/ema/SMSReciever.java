package flyingpenguincarcesses.ema;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.util.ArrayList;

/**
 * Created by adrianapadilla on 12/1/15.
 */

public class SMSReciever extends BroadcastReceiver{
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String buff = null;
        String str = "";
        ArrayList<Contact> contactArrayList = Contact.readContacts(context);

        if (bundle != null) {
            //---retrieve the SMS message received---
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                /*str += "SMS from " + msgs[i].getOriginatingAddress();
                str += " :";
                str += msgs[i].getMessageBody();
                str += "\n";*/

                boolean found = false;
                String buffer = msgs[i].getOriginatingAddress();
                buff = buffer.substring(buffer.length() - 10);
                for (Contact x : contactArrayList) {
                    if (buff.equals(x.getNumber())) {
                            found = true;
                            String receive = Message.decrypt(msgs[i].getMessageBody(), x.getKey1(), x.getKey2());
                            x.receive(receive);
                            x.writeContact(context);
                            try {
                                ContactDetails.messages.add(x.getName() + ": " + receive);
                                ContactDetails.mess.setAdapter(ContactDetails.messAdapter);
                                ContactDetails.mess.setSelection(ContactDetails.messages.size() - 1);
                            }catch(Exception e){

                            }
                    }

                }
                if(!found){
                    new Contact(msgs[i].getOriginatingAddress(), msgs[i].getOriginatingAddress(), "0", "0");
                }
            }


           // Toast.makeText(context, buff, Toast.LENGTH_SHORT).show();
        }
    }
}