package jessica.com;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenTwo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_two);

        // Display the splash image for 3 seconds
        int splashDuration = 3000; // 3 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the next activity
                Intent intent = new Intent(SplashScreenTwo.this, SplashScreen.class);
                startActivity(intent);
                finish(); // Close the splash screen activity
            }
        }, splashDuration);
    }
}
