package com.hackbaba.Doto;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.lib.widget.verticalmarqueetextview.VerticalMarqueeTextView;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Scroller;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mikepenz.iconics.typeface.ITypeface;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.nio.channels.GatheringByteChannel;
import java.util.List;

import static com.hackbaba.Doto.LoginActivity.isEmail;
import static com.hackbaba.Doto.LoginActivity.isemailname;
import static com.hackbaba.Doto.LoginActivity.isgoogle;
import static com.hackbaba.Doto.LoginActivity.isgoogleemail;
import static com.hackbaba.Doto.LoginActivity.isgooglename;
import static com.hackbaba.Doto.RegisterActivity.isEmailReg;
import static com.hackbaba.Doto.RegisterActivity.isEmailidReg;
import static com.hackbaba.Doto.RegisterActivity.isemailnameReg;
import static com.hackbaba.Doto.RegisterActivity.isgoogleReg;
import static com.hackbaba.Doto.RegisterActivity.isgoogleemailReg;
import static com.hackbaba.Doto.RegisterActivity.isgooglenameReg;


public class Credits extends AppCompatActivity {


    private TextView scrollView;
    private Animation animation;
    private Scroller scroller;
    // private float speed = DEFAULT_SPEED;
    private boolean continuousScrolling = true;
    private VerticalMarqueeTextView _txtView1; //declare a member variable

    private Drawer result = null;
    private List<ITypeface> mFonts;
    private TextView text2, text3, tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.scroll);


        Toolbar toolbar = findViewById(R.id.toolbar2);
        //change toolbar color from here
        //toolbar.setBackground(getResources().getDrawable(R.color.accent));

        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.YELLOW));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //getSupportActionBar().
        getSupportActionBar().setTitle("Credits");
        //toolbar.setBackground(getResources().getDrawable(R.color.Vampblack));


        Typeface GameOfThrones = Typeface.createFromAsset(getAssets(), "font/got.ttf");
        Typeface MLight = Typeface.createFromAsset(getAssets(), "font/ml.ttf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "font/mm.ttf");


        scrollView = findViewById(R.id.scroll);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        scrollView.setTypeface(MMedium);


        String username;
        String email;
        //URL url = null;
        //Uri uri;
        if(isgoogle) {
            username = isgooglename;
            email = isgoogleemail;
        }
        else if(isgoogleReg) {
            username = isgooglenameReg;
            email = isgoogleemailReg;
        }
        else if (isEmailReg){
            username = isEmailidReg;
            email = isemailnameReg ;
        }
        else {
            username = isemailname;
            email = isEmail;
        }





    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}






/*

package com.hackbaba.cuscan;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class Credits extends AppCompatActivity {


    private TextView textView;
    private Animation animation;
    private Scroller scroller;
   // private float speed = DEFAULT_SPEED;
    private boolean continuousScrolling = true;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.scroll);
       // textView= findViewById(R.id.textView6);
        textView.setText("CREDITS \n" + "Actor 1 - NAME \n" + "Actor 1 - NAME \n" + "Actor 1 - NAME \n" + "Actor 1 - NAME \n");


        textView.startAnimation(animation);

        ani



    }




    @Override
    public void run() {
        if (scroller.isFinished()) {
            textView.startAnimation(animation);
        } else {
            post(this);
        }
    }

    public boolean isContinuousScrolling() {
        return continuousScrolling;
    }



}
*/