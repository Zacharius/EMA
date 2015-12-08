package flyingpenguincarcesses.ema;

import java.math.BigInteger;

/**
 * Created by Zacharius on 11/19/2015.
 */
public class Message {

    private String message;
    private long time;
    private static final int module=26;

    public Message(String message){
        time = System.currentTimeMillis();

        this.message = message;
    }
    /*
     *We need passOne and passTwo from the contact Data
     *
     */
    public Message(int passOne, int passTwo, String message, long time){
        this.message = message;
        this.time = time;
    }

    public String getMessage(){
        return message;
    }

    public long getTime(){
        return time;
    }

    public static String encrypt(String message, String key1, String key2) {
        int firstKey = Integer.parseInt(key1);
        int secondKey = Integer.parseInt(key2);
        char c;
        StringBuilder builder = new StringBuilder();
        //String result = "";

        for (int i = 0; i < message.length(); i++) {
            c = message.charAt(i);
            if (Character.isLetter(c)) {
                c = (char) ((firstKey * (c - 'a') + secondKey) % module + 'a');
            }
            //key = (int) c;
            builder.append(c);
        }
        return builder.toString();
    }

           /* for(int x=0; x<message.length(); i++) {
                m = message.charAt(x);
                mess = (int) m;

                end = (mess + key) % 126;
                result += (char)end;

            }
        }
        return result;
    }*/

    public static String decrypt(String message, String key1, String key2){
        int firstKey = Integer.parseInt(key1);
        int secondKey = Integer.parseInt(key2);
        StringBuilder builder = new StringBuilder();
        BigInteger inverse = BigInteger.valueOf(firstKey).modInverse((BigInteger.valueOf(module)));
        for(int in =0; in < message.length(); in++){
            char c = message.charAt(in);
            if(Character.isLetter(c)) {
                int decoded = inverse.intValue() * (c - 'a' - secondKey + module);
                c = (char) (decoded % module + 'a');
            }
            builder.append(c);
        }

        return builder.toString();
    }

}
