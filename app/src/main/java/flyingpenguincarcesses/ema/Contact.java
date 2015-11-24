package flyingpenguincarcesses.ema;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


/**
 * Created by Zacharius on 11/18/2015.
 */
public class Contact {

    private String name;
    private String phoneNumber;
    private ArrayList<Message> sent;
    private ArrayList<Message> received;
    int history;
    public static final  String CONTACT_FILE = "contact";



    public  Contact(String name, String number){
        this.name = name;
        this.phoneNumber = number;
        sent = new ArrayList<Message>();
        received = new ArrayList<Message>();
        history = 0;

    }

    public static ArrayList<Contact> readContacts(Context context) {
        FileInputStream fileIn;
        BufferedReader reader;
        ArrayList<Contact> contacts = new ArrayList<Contact>();
        int index=0;



        try{

            reader = new BufferedReader(new InputStreamReader(context.openFileInput(CONTACT_FILE)));
            String inputString;

            //cycle as long as there is a new contact
            while((inputString = reader.readLine()) != null){

                //read number
                String number = reader.readLine();

                //make new contact
                contacts.add(new Contact(inputString, number));
                Contact contact = contacts.get(index);

                //read number of sent messages
                int sents = Integer.parseInt(reader.readLine());

                //read sent messages
                for(int i=0; i<sents; i++){
                    contact.send(reader.readLine());
                }

                //read number of received messages
                int received = Integer.parseInt(reader.readLine());

                //read receved messages
                for(int i=0; i<received; i++){
                    contact.send(reader.readLine());
                }

                //read message history
                contact.history = Integer.parseInt(reader.readLine());
            }
            reader.close();

        }catch(Exception e) {
            e.printStackTrace();
        }


        return contacts;


    }///


    public static void writeContact(Contact contact, Context context) {
        FileOutputStream fileOut;
        BufferedWriter writer;


        try {
            fileOut = context.openFileOutput(CONTACT_FILE, Context.MODE_APPEND);

            //write name
            String buffer = contact.getName() + "\n";
            fileOut.write(buffer.getBytes());

            //write number
            buffer = contact.getNumber() + "\n";
            fileOut.write(buffer.getBytes());

            //write number of sent messages
            buffer = contact.sent.size() + "\n";
            fileOut.write(buffer.getBytes());

            //write sent messages
            for(int i=0; i<contact.sent.size(); i++)
            {
                buffer = contact.sent.get(i) + "\n";
                fileOut.write(buffer.getBytes());
            }

            //write number of received messages
            buffer = contact.received.size() + "\n";
            fileOut.write(buffer.getBytes());

            //write received messages
            for(int i=0; i<contact.received.size(); i++)
            {
                buffer = contact.received.get(i) + "\n";
                fileOut.write(buffer.getBytes());
            }

            //write message history
            buffer = contact.history + "\n";
            fileOut.write(buffer.getBytes());

            fileOut.close();
        }catch(Exception e) {
            e.printStackTrace();
        }




    }

    public void send(String message){
        sent.add(new Message(message));
        history++;

    }

    public void receive(String message){
        received.add(new Message(message));
        history++;
    }

    public String getName(){
        return name;
    }

    public void updateContact(String name){
        this.name = name;
    }

    public Message getSent(int index){

        try{
            return sent.get(index);
        }catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    public Message getReceived(int index){

        try{
            return received.get(index);
        }catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    public String getNumber(){
        return this.phoneNumber;
    }

    public int receivedSize(){
        return received.size();
    }

    public int sentSize(){
        return sent.size();
    }

}