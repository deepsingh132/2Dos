package com.hackbaba.Doto;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.WindowManager;

import com.codemybrainsout.onboarder.AhoyOnboarderActivity;
import com.codemybrainsout.onboarder.AhoyOnboarderCard;

import java.util.ArrayList;
import java.util.List;

public class OnBoardingActivity extends AhoyOnboarderActivity {



    private boolean isFirstTimeStartApp() {

        SharedPreferences ref = getApplicationContext().getSharedPreferences("ToDos", Context.MODE_PRIVATE);
        return ref.getBoolean("FirstTimeStartAppFlag4", true);

    }


    private void setFirstTimeStartStatus(boolean stt) {

        SharedPreferences ref = getApplicationContext().getSharedPreferences("ToDos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = ref.edit();
        editor.putBoolean("FirstTimeStartAppFlag4", stt);
        editor.commit();


    }
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_on_boarding);


        Typeface face = Typeface.createFromAsset(getAssets(), "font/ml.ttf");
        setFont(face);

        if (!isFirstTimeStartApp()) {
            startMainActivity();
            finish();
        }

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            int iconWidth = 400;
            int iconHeight = 400;
            int marginTop = 20;
            int marginLeft = 20;
            int marginRight = 20;
            int marginBottom = 20;

            // AhoyOnboarderCard ahoyOnboarderCard1 = new AhoyOnboarderCard("Get Your Work Done", "Complete Your Tasks On Time.", R.drawable.todo);
            //AhoyOnboarderCard ahoyOnboarderCard2 = new AhoyOnboarderCard("Achieve Your Goals", "Experience Rapid Growth.", R.drawable.grow2);
            //AhoyOnboarderCard ahoyOnboarderCard3 = new AhoyOnboarderCard("Save Time", "Ensure Better Time Management.", R.drawable.savetime3);


            AhoyOnboarderCard ahoyOnboarderCard1 = new AhoyOnboarderCard("Write To Do's", "Complete Your Tasks On Time", R.drawable.task);
            AhoyOnboarderCard ahoyOnboarderCard2 = new AhoyOnboarderCard("Save Time", "Get Better Time Management", R.drawable.clock);
            AhoyOnboarderCard ahoyOnboarderCard3 = new AhoyOnboarderCard("Achieve Goals", "Experience Rapid Growth", R.drawable.goal);

            ahoyOnboarderCard1.setTitleTextSize(dpToPixels(9, this));
            ahoyOnboarderCard2.setTitleTextSize(dpToPixels(9, this));
            ahoyOnboarderCard3.setTitleTextSize(dpToPixels(9, this));

            ahoyOnboarderCard1.setDescriptionTextSize(dpToPixels(7, this));
            ahoyOnboarderCard2.setDescriptionTextSize(dpToPixels(7, this));
            ahoyOnboarderCard3.setDescriptionTextSize(dpToPixels(7, this));

            ahoyOnboarderCard1.setIconLayoutParams(512, 512, 400, 2, 2, 0);
            ahoyOnboarderCard2.setIconLayoutParams(512, 512, 400, 2, 2, 0);
            ahoyOnboarderCard3.setIconLayoutParams(512, 512, 400, 2, 2, 0);

            //ahoyOnboarderCard2.setIconLayoutParams(256, 256, 0, 0, 0, 0);
            //ahoyOnboarderCard2.setIconLayoutParams(iconWidth, iconHeight, marginTop, marginLeft, marginRight, marginBottom);
            //ahoyOnboarderCard3.setIconLayoutParams(iconWidth, iconHeight, marginTop, marginLeft, marginRight, marginBottom);

            //ahoyOnboarderCard1.getIconHeight();
            //ahoyOnboarderCard1.getIconWidth();

            //ahoyOnboarderCard1.setIconLayoutParams(600,900,2,2,2,2);

            //ahoyOnboarderCard2.getIconHeight();
            //ahoyOnboarderCard2.getIconWidth();


            //ahoyOnboarderCard3.getIconHeight();
            //ahoyOnboarderCard3.getIconWidth();

            //ahoyOnboarderCard1.iconHeight(8);

            ahoyOnboarderCard1.setBackgroundColor(R.color.black_transparent);
            ahoyOnboarderCard2.setBackgroundColor(R.color.black_transparent);
            ahoyOnboarderCard3.setBackgroundColor(R.color.black_transparent);

            List<AhoyOnboarderCard> pages = new ArrayList<>();
            pages.add(ahoyOnboarderCard1);
            pages.add(ahoyOnboarderCard2);
            pages.add(ahoyOnboarderCard3);

            for (AhoyOnboarderCard page : pages) {
                page.setTitleColor(R.color.white);
                page.setDescriptionColor(R.color.grey_200);
                //page.setTitleTextSize(dpToPixels(12, this));
                //page.setDescriptionTextSize(dpToPixels(8, this));
                //page.setIconLayoutParams(width, height, marginTop, marginLeft, marginRight, marginBottom);
            }

            setFinishButtonTitle("Get Started");
            showNavigationControls(true);

            setGradientBackground();
            setOnboardPages(pages);

        }




    @Override
    public void onFinishButtonPressed() {
        setFirstTimeStartStatus(false);
        Intent intent = new Intent(OnBoardingActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
    private void startMainActivity() {

        setFirstTimeStartStatus(false);
        startActivity(new Intent(OnBoardingActivity.this, LoginActivity.class));
        finish();
    }
}
