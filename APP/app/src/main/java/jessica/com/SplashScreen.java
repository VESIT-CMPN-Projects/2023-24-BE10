package jessica.com;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    private int[] imageArray = {R.drawable.image2, R.drawable.image3, R.drawable.image4};
    private int currentIndex = 0;
    private ImageView girlVector;
    private ProgressBar progressBar;
    private TextView loadingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        girlVector = findViewById(R.id.girlVector);
        progressBar = findViewById(R.id.progressBar);
        loadingText = findViewById(R.id.loadingText);

        // Center the images within the RelativeLayout
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) girlVector.getLayoutParams();
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        girlVector.setLayoutParams(layoutParams);

        // Start the image transition
        showNextImageWithDelay();
    }

    private void showNextImageWithDelay() {
        final Handler handler = new Handler();
        final int imageDuration = 1000; // Display each image for 3 seconds
        final int fadeDuration = 500; // Set your desired fade duration
        final int progressIncrement = 25; // Adjust based on the number of images

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                currentIndex = (currentIndex + 1) % imageArray.length;

                // Update progress bar
                int progress = progressBar.getProgress() + progressIncrement;
                progressBar.setProgress(progress);

                if (currentIndex == 0) {
                    // Transition to MainActivity after all images are shown
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    finish();
                } else {
                    // Set the next image and fade it in
                    girlVector.setImageResource(imageArray[currentIndex]);

                    final Animation fadeIn = new AlphaAnimation(0, 1);
                    fadeIn.setDuration(fadeDuration);

                    fadeIn.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            // Do nothing
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            // Schedule the next image transition
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    showNextImageWithDelay();
                                }
                            }, imageDuration);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                            // Do nothing
                        }
                    });

                    girlVector.startAnimation(fadeIn);
                }
            }
        }, imageDuration);
    }
}
