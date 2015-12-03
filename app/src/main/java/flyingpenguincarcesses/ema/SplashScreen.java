package flyingpenguincarcesses.ema;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Steven on 11/25/2015.
 * Splash Screen for Encrypted Message Application
 *
 * + SplashScreen extends Activity
 *
 * Methods used:
 * Thread()
 * run()
 * Intent()
 * startActivity()
 * super.onPause()
 * finish()
 */

public class SplashScreen extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);     //Default
        setContentView(R.layout.splash_screen); //for layout info (WIP)

        Thread timerThread = new Thread(){

            public void run(){

                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{

                    Intent intent = new Intent(SplashScreen.this,ContactList.class); //next screen is ContactList
                    //AndroidManifest.xml uses the intent filter .LAUNCHER -- needs to work without it.
                    startActivity(intent); //The Splash Screen is considered a short activity.
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

}
