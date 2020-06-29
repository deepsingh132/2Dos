package com.hackbaba.Doto;

import android.content.Intent;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;

import static com.hackbaba.Doto.NewTaskAct.Key;
import static com.hackbaba.Doto.NewTaskAct.uploadID;

public class EditTaskDesk extends BaseActivity implements DoesAdapter.OnItemClickListener{

    EditText titleDoes, descDoes , dateDoes,userNameStory;
    TextView addtitle, adddesc, adddate,titlepage;
    Button btnSaveUpdate, btnDelete;
    DatabaseReference reference;

    private DoesAdapter mAdapter;
    private DatabaseReference mDatabaseRef;

    private ValueEventListener mDBListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.md_red_700));
        }*/
        setContentView(R.layout.activity_edit_task_desk);

        titleDoes = findViewById(R.id.titledoes);
        descDoes = findViewById(R.id.descdoes);
        dateDoes = findViewById(R.id.datedoes);
        userNameStory = findViewById(R.id.userNameStory);


        //importing font
        Typeface MLight = Typeface.createFromAsset(getAssets(), "font/ml.ttf");
        Typeface GameOfThrones = Typeface.createFromAsset(getAssets(), "font/got.ttf");

        //set font
        titleDoes.setTypeface(MLight);
        descDoes.setTypeface(MLight);
        dateDoes.setTypeface(MLight);

        addtitle = findViewById(R.id.addtitle);
        adddesc = findViewById(R.id.adddesc);
        adddate = findViewById(R.id.adddate);
        titlepage = findViewById(R.id.titlepage);

        titlepage.setTypeface(GameOfThrones);
        addtitle.setTypeface(GameOfThrones);
        adddesc.setTypeface(GameOfThrones);
        adddate.setTypeface(GameOfThrones);

        //btnSaveUpdate = findViewById(R.id.btnSaveUpdate);
        btnDelete = findViewById(R.id.btnDelete);


        //btnSaveUpdate.setTypeface(GameOfThrones);
        btnDelete.setTypeface(MLight);

        FirebaseApp.initializeApp(this);

        // get a value from previous page
        titleDoes.setText(getIntent().getStringExtra("titledoes"));
        descDoes.setText(getIntent().getStringExtra("descdoes"));
        dateDoes.setText(getIntent().getStringExtra("datedoes"));

       final String keykeyDoes = getIntent().getStringExtra("keydoes");

        //reference = FirebaseDatabase.getInstance().getReference().child("DoesApp").
//                child("Does");

                final String userId= getUid();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ToDos toDos;
                mDatabaseRef.child("ToDos").child(userId).child(keykeyDoes).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            //Intent a = new Intent(EditTaskDesk.this,MainActivity.class);
                            //startActivity(a);

                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            //Toast.makeText(EditTaskDesk.this, "Task Deleted", Toast.LENGTH_SHORT).show();
                            Toasty.success(getApplicationContext(), "Task Deleted", Toast.LENGTH_SHORT, true).show();
                            //mAdapter.notifyDataSetChanged();
                            finish();
                        } else {
                           // Toast.makeText(getApplicationContext(),"Fail", Toast.LENGTH_SHORT).show();

                            Toasty.error(getApplicationContext(), "Authentication Error !", Toast.LENGTH_LONG, true).show();
                            finish();
                        }

                    }
                });

            }
        });

        //make an event for button
        /*btnSaveUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               mDBListener = mDatabaseRef.child("ToDos").child(userId).child(keykeyDoes).addValueEventListener(new ValueEventListener()

                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String uploadId = mDatabaseRef.push().getKey();

                        uploadID = uploadId;
                        final String title= titleDoes.getText().toString();
                        final String date = dateDoes.getText().toString();
                        final String desc = descDoes.getText().toString();
                        final String key = keykeyDoes;



                         //mDatabaseRef.child("ToDos").child(userId).child(keykeyDoes).child("date").removeValue();
                        //mDatabaseRef.child("ToDos").child(userId).child(keykeyDoes).child("title").removeValue();
                         //mDatabaseRef.child("ToDos").child(userId).child(keykeyDoes).child("desc").removeValue();
                        //mDatabaseRef.child("ToDos").child(userId).child(keykeyDoes).child("key").removeValue();

                        dataSnapshot.getRef().child("ToDos").child(userId).child(keykeyDoes).setValue(titleDoes.getText().toString());
                        dataSnapshot.getRef().child("ToDos").child(userId).child(keykeyDoes).setValue(descDoes.getText().toString());
                        dataSnapshot.getRef().child("ToDos").child(userId).child(keykeyDoes).setValue(dateDoes.getText().toString());
                        dataSnapshot.getRef().child("ToDos").child(userId).child(keykeyDoes).setValue(keykeyDoes);

                        writeNewPost(userId,uploadId,title,date,desc);
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                           // mAdapter.notifyDataSetChanged();
                        //Intent a = new Intent(EditTaskDesk.this,MainActivity.class);
                        //startActivity(a);
                        Toast.makeText(EditTaskDesk.this, "Task Updated", Toast.LENGTH_SHORT).show();
                        finish();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
*/

    }




    private boolean writeNewPost(String userId,String uploadID, String title,String date,String desc) {

        //String key = mDatabaseRef.child("ToDos").push().getKey();
        final String key = getIntent().getStringExtra("keydoes");


        ToDos toDos = new ToDos(title,date,desc,key);

        //DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Artist/"+id+"/name");
        //Update the nickname to new value
        //dbRef.setValue(newName);

        //mDatabaseRef.getRef().child("ToDos").child(userId).child("/" + keykeyDoes).removeValue();//Value(toDos)
        //mDatabaseRef =  FirebaseDatabase.getInstance().getReference("ToDos" + userId + "/" + key);
        mDatabaseRef.child("ToDos").child(userId).child("/" + key).setValue(toDos);


        return true;
    }


}
