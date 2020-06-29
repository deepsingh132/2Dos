package com.hackbaba.Doto;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Visibility;
import android.view.View;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackbaba.Doto.models.User;
import com.hackbaba.Doto.models.UserwithPhone;

import java.util.Arrays;

import es.dmoral.toasty.Toasty;

import static com.hackbaba.Doto.LoginActivity.isgoogle;


public class RegisterActivity extends BaseDetailActivity {

    private static final String TAG = "SignInActivity";
    private static final String TAG2 = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private EditText mEmailField;
    private EditText myPhoneField;
    private EditText mPasswordField;
    private EditText mNameField;
    private Button mSignInButton;
    private Button mSignUpButton;



    public static boolean isgoogleReg = false;
    public static String isgooglenameReg;
    public static String isemailnameReg;
    public static String isEmailidReg;
    public static String isgoogleemailReg;

    // [START declare_auth]
    // private FirebaseAuth mAuth;
    // [END declare_auth]
    private GoogleSignInClient mGoogleSignInClient;
    private TextView mStatusTextView;
    private TextView mDetailTextView;
    public static boolean isEmailReg=false;
    public static Boolean isFacebookReg=false;


    private static final String TAG3 = "FacebookLogin";


    private CallbackManager mCallbackManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setupWindowAnimations();
        setContentView(R.layout.activity_register);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        // Views
        mNameField = findViewById(R.id.editTextName);
        mEmailField = findViewById(R.id.editTextEmail);
        mPasswordField = findViewById(R.id.editTextPassword);
        myPhoneField = findViewById(R.id.editTextMobile);

        Typeface GameOfThrones = Typeface.createFromAsset(getAssets(), "font/got.ttf");

        //mSignInButton = findViewById(R.id.buttonSignIn);
        //mSignUpButton = findViewById(R.id.buttonSignUp);
        setProgressBar(R.id.progressBar);

        Toasty.Config.getInstance()
                .setToastTypeface(GameOfThrones)
                .allowQueue(false)
                .apply();

        // Button listeners
        //findViewById(R.id.signInButton).setOnClickListener(this);
        //findViewById(R.id.signOutButton).setOnClickListener(this);
        //findViewById(R.id.disconnectButton).setOnClickListener(this);

        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //.requestIdToken("605710786041-8oto4dprq61rq2lbsf7776iai8qg8kfg.apps.googleusercontent.com")
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //finish();
        mCallbackManager = CallbackManager.Factory.create();
        // LoginButton loginButton = findViewById(R.id.buttonFacebookLogin);
        //loginButton.setPermissions("email", "public_profile");
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
                isFacebookReg=true;
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }
        });




        setupWindowAnimations();
    }



    public void onSignIn(View view) {
       setupWindowAnimations();
        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
        //startActivity(intent);
        transitionTo(intent);
        finish();
    }


    private void setupWindowAnimations() {
       // Fade fade = new Fade();
        //fade.setDuration(1000);

        Visibility enterTransition = buildEnterTransition();
        getWindow().setEnterTransition(enterTransition);
        //getWindow().setEnterTransition(fade);
    }



    private Visibility buildEnterTransition() {
        Fade enterTransition = new Fade();
        enterTransition.setDuration(2000);
        // This view will not be affected by enter transition animation
        //enterTransition.excludeTarget(R.id.square_red, true);
        return enterTransition;
    }

    private Visibility buildReturnTransition() {
        Visibility enterTransition = new Slide();
        enterTransition.setDuration(1000);
        return enterTransition;
    }






    public void onRegisterbutton(View view) {
        Log.d(TAG, "signUp");
        isEmailReg=true;
        if (!validateForm()) {
            return;
        }

        showProgressBar();
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();
        String name = mNameField.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
                        hideProgressBar();

                        if (task.isSuccessful()) {
                            isEmailReg=true;
                            onAuthSuccess(task.getResult().getUser());
                            isEmailReg=true;
                        } else {
                            //Toast.makeText(RegisterActivity.this, "Sign Up Failed",
                                   // Toast.LENGTH_SHORT).show();

                            FirebaseAuthException e = (FirebaseAuthException) task.getException();

                            Toasty.error(RegisterActivity.this, "Sign Up Failed: " + e.getMessage(), Toasty.LENGTH_LONG).show();

                            Log.e("LoginActivity", "Failed Registration", e);

                        }
                    }
                });
    }



    private void onAuthSuccess(FirebaseUser user) {
        String username = mNameField.getText().toString();//usernameFromEmail(user.getEmail());

        String phone= myPhoneField.getText().toString();
        // Write new user

        if(TextUtils.isEmpty(myPhoneField.getText().toString())) {

            writeNewUser(user.getUid(), username, user.getEmail());
        }

        else {

            writeNewUserWithPhone(user.getUid(), username, user.getEmail(),phone);

        }

        isEmailReg=true;

        isemailnameReg= user.getEmail();

        isEmailidReg = username;



        if (isFirstTimeStartApp()) {


            setFirstTimeStartStatus(false);

            //Toast.makeText(RegisterActivity.this, "Lucky To Have You Here " + mNameField.getText().toString() + " :)", Toast.LENGTH_LONG).show();


            Toasty.custom(RegisterActivity.this, "Lucky To Have You Here " + mNameField.getText().toString() + " :)", getResources().getDrawable(R.drawable.ic_tag_faces_red_24dp),
                    getResources().getColor(R.color.YELLOW), getResources().getColor(R.color.RED), Toasty.LENGTH_LONG, true, true).show();


            // Go to MainActivity
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            finish();
            //finish();

        } else {
            // Go to MainActivity
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            finish();
        }
    }


    // [START on_start_check_user]
   /* @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        finish();
    }*/

    /*private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }*/


    // [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately

                Toasty.error(RegisterActivity.this, "Sign Up Failed: " + e.getMessage(), Toasty.LENGTH_LONG).show();
                Log.w(TAG, "Google sign in failed", e);
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        showProgressBar();
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            isgoogleReg=true;

                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //Snackbar.make(findViewById(R.id.mPasswordField), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            Snackbar.make(mPasswordField,"Authentication Failed !",Snackbar.LENGTH_LONG).show();
                            FirebaseAuthException e = (FirebaseAuthException) task.getException();

                            Toasty.error(RegisterActivity.this, "Registration Failed: " + e.getMessage(), Toasty.LENGTH_LONG, true).show();
                           // Toast.makeText(RegisterActivity.this, "Failed Registration: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                            Log.e("LoginActivity", "Failed Registration", e);

                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressBar();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_google]


    private boolean isFirstTimeStartApp() {

        SharedPreferences ref = getApplicationContext().getSharedPreferences("ToDos", Context.MODE_PRIVATE);
        return ref.getBoolean("FirstTimeStartAppFlag3", true);

    }


    private void setFirstTimeStartStatus(boolean stt) {

        SharedPreferences ref = getApplicationContext().getSharedPreferences("ToDos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = ref.edit();
        editor.putBoolean("FirstTimeStartAppFlag3", stt);
        editor.commit();


    }


    private void updateUI(FirebaseUser user) {
        hideProgressBar();
        if (user != null) {

            //mStatusTextView.setText(getString(R.string.google_status_fmt, user.getEmail()));
            //mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            //findViewById(R.id.signInButton).setVisibility(View.GONE);
            //findViewById(R.id.signOutAndDisconnect).setVisibility(View.VISIBLE);


            isgoogleReg =true;
            isgooglenameReg = user.getDisplayName();
            //imageUri = user.getPhotoUrl();
            isgoogleemailReg = user.getEmail();


            if (isFirstTimeStartApp()) {


                setFirstTimeStartStatus(false);


                //Toast.makeText(RegisterActivity.this, "Lucky To Have You Here " + user.getDisplayName() + " :)", Toast.LENGTH_LONG).show();

                Toasty.custom(RegisterActivity.this, "Lucky To Have You Here " + user.getDisplayName() + " !", getResources().getDrawable(R.drawable.ic_tag_faces_red_24dp),
                        getResources().getColor(R.color.YELLOW), getResources().getColor(R.color.RED), Toasty.LENGTH_LONG, true, true).show();
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                //finish();
            } else {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

        }
    }


    private void updateUIwithFacebook(FirebaseUser user) {
        hideProgressBar();
        if (user != null) {

            //mStatusTextView.setText(getString(R.string.google_status_fmt, user.getEmail()));
            //mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            //findViewById(R.id.signInButton).setVisibility(View.GONE);
            //findViewById(R.id.signOutAndDisconnect).setVisibility(View.VISIBLE);


            //isgoogleReg =true;
            //isgooglenameReg = user.getDisplayName();
            //imageUri = user.getPhotoUrl();
            //isgoogleemailReg = user.getEmail();

                isFacebookReg=true;

            if (isFirstTimeStartApp()) {


                setFirstTimeStartStatus(false);


                //Toast.makeText(RegisterActivity.this, "Lucky To Have You Here " + user.getDisplayName() + " :)", Toast.LENGTH_LONG).show();

                Toasty.custom(RegisterActivity.this, "Lucky To Have You Here " + user.getDisplayName() + " !", getResources().getDrawable(R.drawable.ic_tag_faces_red_24dp),
                        getResources().getColor(R.color.YELLOW), getResources().getColor(R.color.RED), Toasty.LENGTH_LONG, true, true).show();
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                //finish();
            } else {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                //finish();
            }

        }
    }


    private boolean validateForm() {
        boolean result = true;

        if (TextUtils.isEmpty(mNameField.getText().toString())) {
            mNameField.setError("Required");
            result = false;
        } else {
            mNameField.setError(null);
        }

        if (TextUtils.isEmpty(mEmailField.getText().toString())) {
            mEmailField.setError("Required");
            result = false;
        } else {
            mEmailField.setError(null);
        }

        if (TextUtils.isEmpty(mPasswordField.getText().toString())) {
            mPasswordField.setError("Required");
            result = false;
        } else {
            mPasswordField.setError(null);
        }

        return result;
    }


    // [START basic_write]
    private void writeNewUser(String userId, String name, String email) {
        User user = new User(email,name);

        mDatabase.child("users").child(userId).setValue(user);
    }

    // [START basic_write]
    private void writeNewUserWithPhone(String userId, String name, String email,String phone) {

        UserwithPhone userwithPhone = new UserwithPhone(email,name,phone);

        mDatabase.child("users").child(userId).setValue(userwithPhone);
    }

    public void onGoogleButton(View view) {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    public void onFacebookbutton(View view) {


        LoginManager.getInstance().logInWithReadPermissions(
                this,
                Arrays.asList("email", "user_birthday", "public_profile")
        );
    }
    // [END basic_write]


    // [START auth_with_facebook]
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        // [START_EXCLUDE silent]
        //showProgressBar();
        // [END_EXCLUDE]

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            isFacebookReg=true;
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            isFacebookReg=true;
                            updateUIwithFacebook(user);
                            isFacebookReg=true;
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                           // Toast.makeText(RegisterActivity.this, "Authentication failed.",
                             //       Toast.LENGTH_LONG).show();

                            Toasty.error(RegisterActivity.this, "Sign Up Failed: " + task.getException(), Toasty.LENGTH_LONG).show();

                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressBar();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_facebook]





}
