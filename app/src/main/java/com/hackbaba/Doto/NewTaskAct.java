package com.hackbaba.Doto;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;




import java.util.Random;

import de.mateware.snacky.Snacky;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

public class NewTaskAct extends  MainActivity {

    TextView titlepage, addtitle, adddesc, adddate;
    EditText titledoes, descdoes, datedoes,userNameStory;
    Button btnSaveTask, btnCancel;
    DatabaseReference reference;
    Integer doesNum = new Random().nextInt();
    String keydoes = Integer.toString(doesNum);
    private DatabaseReference mDatabaseRef;

    private static final String SHOWCASE_ID = "sequence example2";

    private ValueEventListener mDBListener;
    public static String uploadID;
    public static String Key;
    DoesAdapter doesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.md_red_700));
        }
        setContentView(R.layout.activity_new_task);

        FirebaseApp.initializeApp(this);


        titlepage = findViewById(R.id.titlepage);


        userNameStory = findViewById(R.id.userNameStory);

        addtitle = findViewById(R.id.addtitle);
        adddesc = findViewById(R.id.adddesc);
        adddate = findViewById(R.id.adddate);

        titledoes = findViewById(R.id.titledoes);
        descdoes = findViewById(R.id.descdoes);
        datedoes = findViewById(R.id.datedoes);



        btnSaveTask = findViewById(R.id.btnSaveTask);
        btnCancel = findViewById(R.id.btnCancel);

        // get a value from previous page
       // titledoes.setText(getIntent().getStringExtra("titledoes"));

        presentShowcaseSequence();

        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // insert data to database
               // reference = FirebaseDatabase.getInstance().getReference().child("Users");

                final String userId= getUid();

                // insert data to database
               // mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("ToDos").
                 //       child(userId + doesNum);

                mDatabaseRef = FirebaseDatabase.getInstance().getReference();

                mDBListener = mDatabaseRef.child("ToDos").child(userId).child("MyDos").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //mDatabaseRef.getRef().child("titledoes").setValue(titledoes.getText().toString());

                        String uploadId = mDatabaseRef.push().getKey();

                        uploadID = uploadId;

                        final String title= titledoes.getText().toString();
                        final String date = datedoes.getText().toString();
                        final String desc = descdoes.getText().toString();

                        writeNewPost(userId,uploadId,title,date,desc);



                       // Toast.makeText(NewTaskAct.this, "New Task Created", Toast.LENGTH_SHORT).show();

                        //Snacky.Builder(btnCancel,"",);



                        //doesAdapter.notifyDataSetChanged();
                        Intent a = new Intent(NewTaskAct.this,MainActivity.class);
                        updateList();

                       // updateList();
                        startActivity(a);
                        Snacky.builder()
                                .setBackgroundColor(Color.parseColor("#FFFF00"))
                                .setTextSize(18)
                                .setTextColor(Color.parseColor("#ED0000"))
                                .setTextTypefaceStyle(Typeface.BOLD)
                                .setText(
                                        "Task Created")
                                .centerText()
                                .setIcon(getDrawable(R.drawable.ic_check_red_24dp))
                                .setActivity(NewTaskAct.this)
                                .setDuration(Snacky.LENGTH_SHORT)
                                .build()
                                .show();
                        finish();
                       // finish();
                        //updateList();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });






        // import font
        Typeface MLight = Typeface.createFromAsset(getAssets(), "font/ml.ttf");
     //   Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/MM.ttf");
        Typeface GameOfThrones = Typeface.createFromAsset(getAssets(),"font/got.ttf");

        // customize font
        titlepage.setTypeface(GameOfThrones);

        addtitle.setTypeface(GameOfThrones);
        titledoes.setTypeface(MLight);

        adddesc.setTypeface(GameOfThrones);
        descdoes.setTypeface(MLight);

        adddate.setTypeface(GameOfThrones);
        datedoes.setTypeface(MLight);

        btnSaveTask.setTypeface(GameOfThrones);
        btnCancel.setTypeface(MLight);

    }
    private void writeNewPost(String userId,String uploadID, String title,String date,String desc) {

        String key = mDatabaseRef.child("ToDos").push().getKey();



        ToDos toDos = new ToDos(title,date,desc,key);

        Key = key;
        mDatabaseRef.child("ToDos").child(userId).child(key).setValue(toDos);
    }


    public void btnCancel(View view) {

        finish();

    }

    private void presentShowcaseSequence() {

        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view
        config.setMaskColor(R.color.colorTransBg);
        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, SHOWCASE_ID);

        sequence.setOnItemShownListener(new MaterialShowcaseSequence.OnSequenceItemShownListener() {
            @Override
            public void onShow(MaterialShowcaseView itemView, int position) {
                //Toast.makeText(itemView.getContext(), "Item #" + position, Toast.LENGTH_SHORT).show();
            }
        });

        sequence.setConfig(config);

        sequence.addSequenceItem(
        new MaterialShowcaseView.Builder(this)
                .setTarget(titledoes)
                .setDismissText("GOT IT")
                .setContentText("Add Title")
                .withRectangleShape(true)
                .build()
        );


        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(descdoes)
                        .setDismissOnTouch(true)
                        .setDismissOnTargetTouch(true)
                        .setDismissText("GOT IT")
                        .setContentText("Add Description")
                        .withRectangleShape(true)
                        .build()
        );

        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(datedoes)
                        .setDismissText("GOT IT")
                        .setDismissOnTouch(true)
                        .setDismissOnTargetTouch(true)
                        .setContentText("Add Time")
                        .withRectangleShape()
                        .build()
        );

        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(btnSaveTask)
                        .setDismissText("Ok")
                        .setDismissOnTouch(true)
                        .setDismissOnTargetTouch(true)
                        .setContentText("You're Good To Go !")
                        .withRectangleShape()
                        .build()
        );

        sequence.start();

    }

}
