package flyingpenguincarcesses.ema;

import android.content.Context;

import java.io.BufferedReader;
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
    //public static final  String CONTACT_FILE = "contact";


    //constructs contact and adds name to contacts file
    public  Contact(String name, String number, Context context){
        this.name = name;
        this.phoneNumber = number;
        sent = new ArrayList<Message>();
        received = new ArrayList<Message>();
        history = 0;

        FileOutputStream fileOut;
        String buffer;

        try{
            fileOut = context.openFileOutput("contacts", Context.MODE_APPEND);
            buffer = name + "\n";
            fileOut.write(buffer.getBytes());
            fileOut.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //read the contact file of the contact given by name
    public static Contact readContact(String name, Context context) {
        BufferedReader reader;
        Contact contact = null;

        try{

            reader = new BufferedReader(new InputStreamReader(context.openFileInput(name)));
            String inputString;


            //cycle as long as there is a new contact
            inputString = reader.readLine();

            //read number
            String number = reader.readLine();

            //make new contact
            contact = new Contact(inputString, number, context);

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
            reader.close();
        }catch(Exception e) {
            e.printStackTrace();
        }


        return contact;


    }

    //read all contacts found in contact file
    public static ArrayList<Contact> readContacts(Context context){
        FileInputStream fileIn;
        BufferedReader reader;
        ArrayList<Contact> contacts = new ArrayList<Contact>();
        ArrayList<String> names = new ArrayList<String>();
        String name;

        try{
            reader = new BufferedReader(new InputStreamReader(context.openFileInput("contacts")));

            while ((name = reader.readLine()) != null){
                names.add(name);
            }

            for(int i=0; i<names.size(); i++){
                contacts.add(Contact.readContact(names.get(i), context));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return  contacts;

    }


    public  void writeContact(Context context) {
        FileOutputStream fileOut;


        try {
            fileOut = context.openFileOutput(this.getName(), Context.MODE_PRIVATE);

            //write name
            String buffer = this.getName() + "\n";
            fileOut.write(buffer.getBytes());

            //write number
            buffer = this.getNumber() + "\n";
            fileOut.write(buffer.getBytes());

            //write number of sent messages
            buffer = this.sent.size() + "\n";
            fileOut.write(buffer.getBytes());

            //write sent messages
            for(int i=0; i<this.sent.size(); i++)
            {
                buffer = this.sent.get(i) + "\n";
                fileOut.write(buffer.getBytes());
            }

            //write number of received messages
            buffer = this.received.size() + "\n";
            fileOut.write(buffer.getBytes());

            //write received messages
            for(int i=0; i<this.received.size(); i++)
            {
                buffer = this.received.get(i) + "\n";
                fileOut.write(buffer.getBytes());
            }

            //write message history
            buffer = this.history + "\n";
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