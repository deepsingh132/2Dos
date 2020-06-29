package com.hackbaba.Doto;

import android.content.Intent;

import android.content.IntentSender;
import android.graphics.Typeface;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import com.facebook.AccessToken;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
/*import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.people.v1.People;
import com.google.api.services.people.v1.PeopleScopes;
import com.google.api.services.people.v1.model.Birthday;*/
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


import es.dmoral.toasty.Toasty;


public class LoginActivity extends inheritAll {

    public static boolean isgoogle = false;
    public static String isgooglename;
    public static String isemailname;

    public static boolean isFacebook=false;
    public static String facebookName;

    public static String isEmail;
    public static Uri imageUri;
    public static String isgoogleemail;
    public static String first_name;
    public static String last_name;

    public static String email;

    /** Global instance of the HTTP transport. */
    // private static HttpTransport HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();
    /** Global instance of the JSON factory. */
    // private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    //public NoInternetDialog noInternetDialog;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mSignInButton;
    private Button mSignUpButton;
   // static ImageView imageView;
    //public ImageView profileIcon;

    public static String image_url;


    private static final String TAG3 = "FacebookLogin";


    private CallbackManager mCallbackManager;



    private static final String TAG2 = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    // [START declare_auth]
    // private FirebaseAuth mAuth;
    // [END declare_auth]

    private GoogleSignInClient mGoogleSignInClient;
    private TextView mStatusTextView;
    private TextView mDetailTextView;


    private AppUpdateManager appUpdateManager;
    private static final String TAG = "SignInActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);




        mDatabase = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();

        Typeface GameOfThrones = Typeface.createFromAsset(getAssets(), "font/got.ttf");

        Toasty.Config.getInstance()
                .setToastTypeface(GameOfThrones)
                .allowQueue(false)
                            .apply();

        // [START config_signin]
        // Configure Google Sign In

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build();
        // [END config_signin]

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);











        // Views
        mEmailField = findViewById(R.id.editTextEmail);
        mPasswordField = findViewById(R.id.editTextPassword);
        mSignInButton = findViewById(R.id.cirLoginButton);
        //mSignUpButton = findViewById(R.id.buttonSignUp);
        //imageView = findViewById(R.id.userImg);
        setProgressBar(R.id.progressBar);
       // profileIcon = findViewById(R.id.profileIcon);


        boolean loggedOut = AccessToken.getCurrentAccessToken() == null;

       /* if (!loggedOut) {
            //setContentView(R.layout.profile);
            //Using Graph API
            getUserProfile(AccessToken.getCurrentAccessToken());
            Picasso.with(LoginActivity.this).load(Profile.getCurrentProfile().getProfilePictureUri(200, 200)).into(profileIcon);
            Log.d("TAG", "Username is: " + Profile.getCurrentProfile().getName());

            //Using Graph API
            getUserProfile(AccessToken.getCurrentAccessToken());
        }*/


        // mStatusTextView = findViewById(R.id.status);
        //mDetailTextView = findViewById(R.id.detail);
        // setProgressBar(R.id.progressBar);




   //     noInternetDialog = new NoInternetDialog.Builder(getApplicationContext()).build();


        // Click listeners
        //mSignInButton.setOnClickListener(this);
        //mSignUpButton.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        //finish();

        setupWindowAnimations();


        mCallbackManager = CallbackManager.Factory.create();
       // LoginButton loginButton = findViewById(R.id.buttonFacebookLogin);
        //loginButton.setPermissions("email", "public_profile");
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
                isFacebook=true;
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


    }

    public void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("TAG", object.toString());
                        try {
                            //setContentView(R.layout.profile);

                            first_name = object.getString("first_name");
                             last_name = object.getString("last_name");
                             email = object.getString("email");
                            String id = object.getString("id");
                            image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";

                            //txtUsername.setText("First Name: " + first_name + "\nLast Name: " + last_name);
                            //txtEmail.setText(email);
                            //Picasso.get().load(image_url).into(profileIcon);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    private void updateUIwithFacebook(FirebaseUser user) {
        hideProgressBar();
        if (user != null) {



            isFacebook=true;
            //mStatusTextView.setText(getString(R.string.google_status_fmt, user.getEmail()));
            //mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            //findViewById(R.id.signInButton).setVisibility(View.GONE);
            //findViewById(R.id.signOutAndDisconnect).setVisibility(View.VISIBLE);


            //isgoogleReg =true;
            //isgooglenameReg = user.getDisplayName();
            //imageUri = user.getPhotoUrl();
            //isgoogleemailReg = user.getEmail();


            if (isFirstTimeStartApp()) {


                setFirstTimeStartStatus(false);


                //Toast.makeText(RegisterActivity.this, "Lucky To Have You Here " + user.getDisplayName() + " :)", Toast.LENGTH_LONG).show();

                Toasty.custom(LoginActivity.this, "Lucky To Have You Here " + user.getDisplayName() + " !", getResources().getDrawable(R.drawable.ic_tag_faces_red_24dp),
                        getResources().getColor(R.color.YELLOW), getResources().getColor(R.color.RED), Toasty.LENGTH_LONG, true, true).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                //finish();
            } else {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // Check auth on Activity start
        if (mAuth.getCurrentUser() != null) {
            FirebaseUser currentUser = mAuth.getCurrentUser();

            if (isFacebook) {
                updateUIwithFacebook(currentUser);
            }
            else {
                onAuthSuccess(mAuth.getCurrentUser());
                updateUI(currentUser);
            }
            //finish();
        }
    }







    public void onSignUp(View view) {


        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);

    }

    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);
    }

    public void onLogin(View view) {

        Log.d(TAG, "signIn");
        if (!validateForm()) {
            return;
        }

        showProgressBar();
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signIn:onComplete:" + task.isSuccessful());
                        hideProgressBar();

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {

                            FirebaseAuthException e = (FirebaseAuthException) task.getException();
                            //Toast.makeText(LoginActivity.this, "Failed Registration: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                            Toasty.error(LoginActivity.this, "Login Failed: " + e.getMessage(), Toasty.LENGTH_LONG, true).show();
                            Log.e("LoginActivity", "Failed Registration", e);
                            //message.hide();
                            // return;
                        }
                    }
                });


    }



    private String getNameFromEmail(FirebaseUser user)
    {
        Name = getName();

        return Name;
    }

    private void onAuthSuccess(FirebaseUser user) {



        String username;// =  getNameFromEmail(user);
        username =getName();
        //String email= mEmailField.getText().toString();

        // Write new user
        //writeNewUser(user.getUid(), username, user.getEmail());

        // Go to MainActivity
        //mDatabase.getDatabase().getReference().child(username);

        isemailname= username;

        isEmail = user.getEmail();


        if (isFirstTimeStartApp()) {


            setFirstTimeStartStatus(false);


            //Toast.makeText(LoginActivity.this, "Welcome Back " + username + " !", Toast.LENGTH_LONG).show();


            Toasty.custom(LoginActivity.this, "Welcome Back " + username + " !", getResources().getDrawable(R.drawable.ic_tag_faces_red_24dp),
                    getResources().getColor(R.color.YELLOW), getResources().getColor(R.color.RED), Toasty.LENGTH_SHORT, true, true).show();

            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }


    private boolean validateForm() {
        boolean result = true;
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


   /* // [START basic_write]
    private void writeNewUser(String userId, String username, String email) {
        User user = new User(email, username);

        mDatabase.child("users").child(userId).setValue(user);
    }*/
    // [END basic_write]




    public void ongooglebutton(View view) {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);


    }
    public void onFacebookButton(View view){

        LoginManager.getInstance().logInWithReadPermissions(
                this,
                Arrays.asList("email", "user_birthday", "public_profile")
        );

    }


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
                isgoogle=true;
                //     PeopleApiCall(account.getServerAuthCode());// call people api to get more user info e.g user gender, data of birth



            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // FirebaseAuthException a = (FirebaseAuthException) task.getException();
                //Toast.makeText(LoginActivity.this, "Failed Registration: " + a.getMessage(), Toast.LENGTH_SHORT).show();

                Toasty.error(LoginActivity.this, "Login Failed: " + e.getMessage(), Toasty.LENGTH_LONG, true).show();
                Log.e("LoginActivity", "Failed Registration", e);

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
                            isgoogle=true;
                            updateUI(user);
                            isgoogle=true;
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //Snackbar.make(findViewById(R.id.mSignInButton), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            Snackbar.make(mSignInButton,"Authentication Failed.",Snackbar.LENGTH_LONG).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressBar();
                        // [END_EXCLUDE]
                    }
                });
    }

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
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            isFacebook=true;
                            updateUIwithFacebook(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                           // Toast.makeText(LoginActivity.this, "Authentication failed.",
                             //       Toast.LENGTH_SHORT).show();
                            Toasty.error(LoginActivity.this, "Sign In Failed: " + task.getException(), Toasty.LENGTH_LONG).show();

                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressBar();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_facebook]


    private boolean isFirstTimeStartApp() {

        SharedPreferences ref = getApplicationContext().getSharedPreferences("ToDos", Context.MODE_PRIVATE);
        return ref.getBoolean("FirstTimeStartAppFlag2", true);

    }


    private void setFirstTimeStartStatus(boolean stt) {

        SharedPreferences ref = getApplicationContext().getSharedPreferences("ToDos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = ref.edit();
        editor.putBoolean("FirstTimeStartAppFlag2", stt);
        editor.commit();


    }

    private void updateUI(FirebaseUser user) {
        hideProgressBar();
        if (user != null) {

            //mStatusTextView.setText(getString(R.string.google_status_fmt, user.getEmail()));
            //mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            //findViewById(R.id.signInButton).setVisibility(View.GONE);
            //findViewById(R.id.signOutAndDisconnect).setVisibility(View.VISIBLE);



            isgoogle = true;
            isgooglename = user.getDisplayName();
            isgoogleemail = user.getEmail();

            if (isFirstTimeStartApp()) {


                setFirstTimeStartStatus(false);
                //Toast.makeText(LoginActivity.this, "Welcome Back " + user.getDisplayName() + " !", Toast.LENGTH_LONG).show();


                Toasty.custom(LoginActivity.this, "Welcome Back " + user.getDisplayName() + " !", getResources().getDrawable(R.drawable.ic_tag_faces_red_24dp),
                        getResources().getColor(R.color.YELLOW), getResources().getColor(R.color.RED), Toasty.LENGTH_SHORT, true, true).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);


                finish();
                //finish();

            } else {

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);

                finish();
            }

            //finish();

        }

    }
}
