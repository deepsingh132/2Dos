package com.hackbaba.Doto;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.appcompat.app.AlertDialog;

import android.os.Build;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
// import com.facebook.FacebookCallback;
 // import com.facebook.FacebookException;
// import com.facebook.FacebookSdk;
// import com.facebook.login.LoginResult;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
// import com.google.firebase.FirebaseApp;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;

import org.imaginativeworld.oopsnointernet.ConnectionCallback;
import org.imaginativeworld.oopsnointernet.NoInternetDialog;
import org.imaginativeworld.oopsnointernet.NoInternetSnackbar;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

import static com.hackbaba.Doto.LoginActivity.first_name;
import static com.hackbaba.Doto.LoginActivity.image_url;
import static com.hackbaba.Doto.LoginActivity.isEmail;
import static com.hackbaba.Doto.LoginActivity.isFacebook;
import static com.hackbaba.Doto.LoginActivity.isemailname;
import static com.hackbaba.Doto.LoginActivity.isgoogle;
import static com.hackbaba.Doto.LoginActivity.isgoogleemail;
import static com.hackbaba.Doto.LoginActivity.isgooglename;
import static com.hackbaba.Doto.LoginActivity.last_name;
import static com.hackbaba.Doto.RegisterActivity.isEmailReg;
import static com.hackbaba.Doto.RegisterActivity.isEmailidReg;
import static com.hackbaba.Doto.RegisterActivity.isemailnameReg;
import static com.hackbaba.Doto.RegisterActivity.isgoogleReg;
import static com.hackbaba.Doto.RegisterActivity.isgoogleemailReg;
import static com.hackbaba.Doto.RegisterActivity.isgooglenameReg;

public class MainActivity extends BaseActivity {

    TextView titlepage, subtitlepage, endpage;
    TextView textView;
    FloatingActionButton btnAddNew;
    View view;
    int RC_SIGN_IN = 132;
    //ConstraintLayout constraintLayout;
    CardView loadArea;
    String TAG = "Google Sign In";
    FirebaseAuth mAuth;
    FirebaseUser user;
    LoginButton loginButton;   
   CallbackManager callbackManager;
    public static String first_name;
    public static String last_name;

    public static String emailFacebook;
    public static String id;

    public AccountHeader accountHeader;

    //public NoInternetDialog noInternetDialog;
    //String uid;
    SignInButton googlesigninbtn;
    GoogleSignInClient mGoogleSignInClient;
    private int backpress=0;

    Toolbar toolbar;
    ProgressBar progressBar;
    public TextView input;
    public DrawerLayout drawer;
    public final static String versionName = BuildConfig.VERSION_NAME;
    private AccountHeader headerResult = null;
    private Drawer result = null;
    DatabaseReference reference;
    RecyclerView ourdoes;
    ArrayList<ToDos> list;
   public DoesAdapter doesAdapter;
    RecyclerView recyclerView;
    public String facebookId;
    private static final String SHOWCASE_ID = "sequence example";

    // No Internet Dialog
    NoInternetDialog noInternetDialog;

    // No Internet Snackbar
    NoInternetSnackbar noInternetSnackbar;

    RadioGroup radio_group;

   //  int selectedRadioId = R.id.radio_dialog;

    public int vCode = BuildConfig.VERSION_CODE;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;

    private ValueEventListener mDBListener;
    private Uri ic_Url;
    private String emailId;
    private String nameId;


    //FancyShowCaseView fancyShowCaseView;
    public static boolean Facebook = false;

    public void rateApp() {
        try {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        } catch (ActivityNotFoundException e) {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
            startActivity(rateIntent);
        }
    }


    @SuppressWarnings("deprecation")
    private Intent rateIntentForUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21) {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        } else {
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }



    private int MY_REQUEST_CODE = 1;
    private Context mContext = MainActivity.this;
    private AppUpdateManager appUpdateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //for changing status bar icon colors
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.status));
        }*/

           /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                Context context =getApplicationContext();
                Drawable background = context.getResources().getDrawable(R.drawable.mydosbg);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(context.getResources().getColor(android.R.color.transparent));
                window.setNavigationBarColor(context.getResources().getColor(android.R.color.transparent));
                window.setBackgroundDrawable(background);
            }*/

           setContentView(R.layout.activity_main);




        appUpdateManager = AppUpdateManagerFactory.create(mContext);

        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        appUpdateInfo -> {

                            // Checks that the platform will allow the specified type of update.
                            if ((appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE)
                                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                                // Request the update.
                                try {
                                    appUpdateManager.startUpdateFlowForResult(
                                            appUpdateInfo,
                                            AppUpdateType.IMMEDIATE,
                                            MainActivity.this,
                                            MY_REQUEST_CODE);
                                } catch (IntentSender.SendIntentException e) {
                                    e.printStackTrace();
                                }
                            }
                        });





            //noInternetDialog = new NoInternetDialog.Builder(this).build();

           //NetworkChecker.isNetworkConnected(getApplicationContext());



        //doesAdapter.notifyDataSetChanged();

        //noInternetDialog = new NoInternetDialog.Builder(getApplicationContext()).build();

        titlepage = findViewById(R.id.titlepage);
        subtitlepage = findViewById(R.id.subtitlepage);
        endpage = findViewById(R.id.endpage);
        textView = findViewById(R.id.textView);
        progressBar = findViewById(R.id.load);
        btnAddNew = findViewById(R.id.btnAddNew);
        loadArea = findViewById(R.id.loadArea);
        view = findViewById(R.id.invisible);
        //recyclerView= findViewById(R.id.ourdoes);

        // import font
        Typeface MLight = Typeface.createFromAsset(getAssets(), "font/ml.ttf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(),"font/mm.ttf");
        Typeface GameOfThrones = Typeface.createFromAsset(getAssets(), "font/got.ttf");

        // customize font
        titlepage.setTypeface(GameOfThrones);
        subtitlepage.setTypeface(GameOfThrones);
        endpage.setTypeface(MLight);


        toolbar = findViewById(R.id.toolbar2);


        //toolbar.setBackground(getResources().getDrawable(R.color.colorAccent));
        // getSupportActionBar();
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.YELLOW));
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        view.setVisibility(View.VISIBLE);
        loadArea.setVisibility(View.VISIBLE);
        btnAddNew.setVisibility(View.INVISIBLE);
        //btnAddNew.setTypeface(MLight);




        Toasty.Config.getInstance()
                .setToastTypeface(MLight)
                .allowQueue(false)
                .apply();




        int versionCode = 0;
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {

            e.printStackTrace();

        }

        SharedPreferences sharedPreferences = getSharedPreferences("PREFS", 0);
        boolean firstRun = sharedPreferences.getBoolean("firstRun" + versionCode, true);

        if (firstRun) {

            SharedPreferences sharedPreferences1 = getSharedPreferences("PREFS", 0);
            SharedPreferences.Editor editor = sharedPreferences1.edit();
            editor.putBoolean("firstRun" + versionCode, false);
            editor.apply();
            //show changelog


            String titleDialog = "<center><b><font color=red>Hi !</font></b></center><br><br><br>";
            String message = "<br><br><p><b><font color=green>Thanks For Downloading The App & Be Safe ! </font> </b></p>";
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
            //alertDialogBuilder.setTitle(message)

            //alertDialogBuilder.setMessage("What's New in 1.6\n  *Bugs Fixed \n  *Performance Improvements\n  *Crash Fixed On Devices Running Android 7\n  *Storage Permissions Fixed   \n-Thanks For Updating & Be Safe !")
            //input = findViewById(R.id.input);
            //= new TextView(this);



            alertDialogBuilder.setMessage(Html.fromHtml(message))

                    .setCancelable(true)
                    .setView(input)
                    .setTitle(Html.fromHtml(titleDialog))

                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setCanceledOnTouchOutside(true);
            alertDialog.show();

        }











        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this, NewTaskAct.class);
                startActivity(a);
            }



        });

        //initialise firebase


        String userId= getUid();

        // working with data
        ourdoes = findViewById(R.id.ourdoes);
        ourdoes.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();




        ourdoes.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && btnAddNew.getVisibility() == View.VISIBLE) {
                    btnAddNew.hide();

                    //
                } else if (dy < 0 && btnAddNew.getVisibility() != View.VISIBLE) {
                    btnAddNew.show();
                }
                else{
                    btnAddNew.show();
                }
            }
        });


       /* appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        appUpdateInfo -> {

                            // Checks that the platform will allow the specified type of update.
                            if ((appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE)
                                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                                // Request the update.
                                try {
                                    appUpdateManager.startUpdateFlowForResult(
                                            appUpdateInfo,
                                            AppUpdateType.IMMEDIATE,
                                            MainActivity.this,
                                            MY_REQUEST_CODE);
                                } catch (IntentSender.SendIntentException e) {
                                    e.printStackTrace();
                                }
                            }
                        });*/
        String username;
        String email;
//Using Graph API

        /*getUserProfile(AccessToken.getCurrentAccessToken());
        ImageView imageView = findViewById(R.id.profileIcon);
        Drawable profileIcon = imageView.getDrawable();*/

        /*AccessToken currentAccessToken = AccessToken.getCurrentAccessToken();
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("TAG", object.toString());
                        try {
                            //setContentView(R.layout.profile);

                            first_name = object.getString("first_name");
                            last_name = object.getString("last_name");
                            emailFacebook = object.getString("email");
                            facebookId = object.getString("id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });*/

        /*FacebookCallback<LoginResult> mCallBack = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                Log.e("response: ", response + "");
                                try {
                                    String id = object.getString("id").toString();
                                    String email = object.getString("email").toString();
                                    String name = object.getString("name").toString();
                                    String profilePicUrl = "https://graph.facebook.com/" + loginResult.getAccessToken().getUserId() + "/picture?type=large";
                                    Log.d("imageFB", profilePicUrl);
                                    Log.d("FB_ID:", id);


 //                                   checkFBLogin(id, email, name, profilePicUrl);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                finish();
                            }

                        });
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        };
*/
               // getUserProfile(AccessToken.getCurrentAccessToken());
   //     final String ic_Url = "http://graph.facebook.com/" + facebookId + "/picture?type=square";


      // Uri ic_Url = user.getPhotoUrl(); //here you get the picture

       //String photoUrlstr = ic_Url.toString(); //here you store the link to quality

        //photoUrlstr = photoUrlstr + "?height=500";

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                String providerId = profile.getProviderId();

                // UID specific to the provider
                String uid = profile.getUid();

                // Name, email address, and profile photo Url
                  nameId = profile.getDisplayName();
                 emailId = profile.getEmail();
                 ic_Url = profile.getPhotoUrl();
            }
        }


        //initialize and create the image loader logic
        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder, String tag) {
                Picasso.get().load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.get().cancelRequest(imageView);
            }
        });


        final IProfile profile2 = new ProfileDrawerItem().withName(nameId).withEmail(emailId)
                .withIcon(ic_Url)
                .withIdentifier(100);

  /*      private void overrideDrawerImageLoaderPicasso(){
            //initialize and create the image loader logic
            DrawerImageLoader.init(new AbstractDrawerImageLoader() {
                @Override
                public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                    Picasso.get().load(uri).placeholder(placeholder).into(imageView);
                }

                @Override
                public void cancel(ImageView imageView) {
                    Picasso.get().cancelRequest(imageView);
                }


            @Override
            public Drawable placeholder(Context ctx) {
                return super.placeholder(ctx);
            }

            @Override
            public Drawable placeholder(Context ctx, String tag) {
                return super.placeholder(ctx, tag);
            }

            });
        }
*/
        if (isFacebook){
            Facebook=true;
        }

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



         if(isgoogle || isgoogleReg || Facebook || isFacebook){
            accountHeader = new AccountHeaderBuilder()
                    .withActivity(this)
                    .withHeaderBackground(R.drawable.valeriya)
                    .withCompactStyle(true)
                    //.withHeightDp(70)
                    .withDividerBelowHeader(true)
                    .withSelectionListEnabledForSingleProfile(false)
                    .withProfileImagesVisible(true)
                    .withProfileImagesClickable(false)
                    .withSavedInstance(savedInstanceState)
                    .withTextColorRes(R.color.white)

                    //.withTextColor(getResources())


                    .addProfiles(
                            profile2
                            // new ProfileDrawerItem().withName(first_name + " " + last_name).withEmail(LoginActivity.email).withTypeface(GameOfThrones).withTextColor(getResources().getColor(R.color.white)).withIcon(p)//.withIcon(getDrawable(R.drawable.avatar))
                    )


                    //.withOnAccountHeaderListener((view, profile, currentProfile) -> false)
                    //.withTranslucentStatusBar(true)
                    .withSavedInstance(savedInstanceState)
                    .build();

            setSupportActionBar(toolbar);
            new DrawerBuilder().withActivity(this).build();
        }


        else {
            accountHeader = new AccountHeaderBuilder()
                    .withActivity(this)
                    .withHeaderBackground(R.drawable.valeriya)
                    .withCompactStyle(true)
                    //.withHeightDp(70)
                    .withDividerBelowHeader(true)
                    .withSelectionListEnabledForSingleProfile(false)
                    .withProfileImagesVisible(true)
                    .withProfileImagesClickable(false)
                    // .withProfile
                    .withSavedInstance(savedInstanceState)
                    .withTextColorRes(R.color.white)

                    //.withTextColor(getResources())


                    .addProfiles(
                            new ProfileDrawerItem().withName(username).withEmail(email).withTypeface(GameOfThrones).withTextColor(getResources().getColor(R.color.white)).withIcon(R.drawable.ic_account_circle_white_24dp)//.withIcon(getDrawable(R.drawable.avatar))
                    )


                    .withOnAccountHeaderListener((View view, IProfile profile, boolean currentProfile) -> {
                        return false;
                    })
                    //.withTranslucentStatusBar(true)
                    .withSavedInstance(savedInstanceState)
                    .build();

            setSupportActionBar(toolbar);
            new DrawerBuilder().withActivity(this).build();

        }




        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName("Home").withTypeface(GameOfThrones).withIcon(getDrawable(R.drawable.ic_home_black_24dp)).withIdentifier(1);
        //PrimaryDrawerItem item2 = new PrimaryDrawerItem().withName("About").withTypeface(GameOfThrones).withIcon(getDrawable(R.drawable.ic_info_outline_black_24dp)).withIdentifier(2);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withName("Privacy Policy").withTypeface(GameOfThrones).withSelectable(false).withIcon(getDrawable(R.drawable.ic_chat_black_24dp)).withIdentifier(2);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withName("Credits").withTypeface(GameOfThrones).withIcon(getDrawable(R.drawable.ic_credits_black_24dp)).withIdentifier(3);

        //PrimaryDrawerItem item5 = new PrimaryDrawerItem().withName("Rate App On Play Store").withIcon(getDrawable(R.drawable.ic_copyright_black_24dp)).withIdentifier(5);

        SecondaryDrawerItem s1 = new SecondaryDrawerItem().withName("Visit Project Site").withTypeface(GameOfThrones).withIdentifier(4).withSelectable(false).withIcon(getDrawable(R.drawable.ic_public_black_24dp));

        // SecondaryDrawerItem s2= new SecondaryDrawerItem().withName("Rate App On Play Store").withIdentifier(6).withIcon(getDrawable(R.drawable.ic_play_arrow_black_24dp));

        //SecondaryDrawerItem s2 = new SecondaryDrawerItem().withName("Launch CU's Site").withTypeface(Gameofthrones).withIdentifier(6).withSelectable(false).withIcon(getDrawable(R.drawable.ic_public_black_24dp));

        //SecondaryDrawerItem s3 = new SecondaryDrawerItem().withName("Open Cuims").withTypeface(GameOfThrones).withIdentifier(7).withSelectable(false).withIcon(getDrawable(R.drawable.ic_school_black_24dp));

        SecondaryDrawerItem s2 = new SecondaryDrawerItem().withName("Rate App On Play Store").withTypeface(GameOfThrones).withSelectable(false).withIdentifier(5).withIcon(getDrawable(R.drawable.ic_shop_black_24dp));

        SecondaryDrawerItem s3 = new SecondaryDrawerItem().withName("Share App").withTypeface(GameOfThrones).withSelectable(false).withIdentifier(6).withIcon(getDrawable(R.drawable.ic_send_black_24dp));


        // SecondaryDrawerItem s2= new SecondaryDrawerItem().withName();


//create the drawer and remember the `Drawer` result object
        result = new DrawerBuilder()
                .withActivity(this)
//                .withRootView(R.id.drawer_layout)
                .withActionBarDrawerToggle(true)
                .withToolbar(toolbar)
                .withActionBarDrawerToggleAnimated(true)
                .withDisplayBelowStatusBar(true)
                .withDelayOnDrawerClose(1)
                .withStickyFooterShadow(false)
                .withStickyFooterDivider(true)
                .withSelectedItem(1)
                .withSavedInstance(savedInstanceState)


                .withAccountHeader(accountHeader)

                .addDrawerItems(
                        item1,
                        item2,
                        item3,
                        new DividerDrawerItem(),
                        s1,
                        s2,
                        s3

                )

                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    // do something with the clicked item :D
                    result.closeDrawer();
                    if (drawerItem.getIdentifier() == 1) {

                        //result.setSelection(1,false);
                        result.closeDrawer();

                        // set the selection to the item with the identifier 5
                        if (savedInstanceState == null) {
                            result.setSelection(1, false);
                        }

                        return true;
                    } else if (drawerItem.getIdentifier() == 2) {
                        // Uri uri = Uri.parse("https://deepsingh132.github.io/CUScan/privacy-policy.html"); // missing 'http://' will cause crashed
                        //Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        Intent intent = new Intent(MainActivity.this, web.class);
                        startActivity(intent);
                        return true;
                    } else if (drawerItem.getIdentifier() == 3) {

                        //result.setSelection(4);
                        // result.closeDrawer();
                        //setup();
                        startActivity(new Intent(getApplicationContext(), Credits.class));

                        // set the selection to the item with the identifier 5
                        if (savedInstanceState == null) {
                            result.setSelection(4, false);
                        }

                        return true;
                    } else if (drawerItem.getIdentifier() == 4) {
                        Uri uri = Uri.parse("https://deepsingh132.github.io/2Dos/"); // missing 'http://' will cause crashed
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        return true;
                    } else if (drawerItem.getIdentifier() == 5) {
                        rateApp();
                        return true;
                    } else if (drawerItem.getIdentifier() == 6) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        String link = "https://play.google.com/store/apps/details?id=com.hackbaba.doto";
                        String subject = "Download This Awesome App !";
                        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                        intent.putExtra(Intent.EXTRA_TEXT, link);
                        startActivity(intent);
                        result.closeDrawer();
                        return true;
                    }


                    return false;
                })
                .withSavedInstance(savedInstanceState)
                .withSelectedItem(1)
                .build();

        result.addStickyFooterItem(new PrimaryDrawerItem().withName("                        App Version: " + (versionName)).withSelectable(false));









        mDatabaseRef = FirebaseDatabase.getInstance().getReference();//.child("Uploads");


        // get data from firebase
        //reference = FirebaseDatabase.getInstance().getReference().child("ToDos").child(userId);

        mDBListener = mDatabaseRef.child("ToDos").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // set code to retrive data and replace layout


                //titlepage.setVisibility(View.INVISIBLE);
                //subtitlepage.setVisibility(View.INVISIBLE);
                //endpage.setVisibility(View.INVISIBLE);
                // Change the code to retrieve only the data of the current user
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    ToDos toDos= dataSnapshot1.getValue(ToDos.class);
                    //p.setKey(dataSnapshot1.getKey());
                    list.add(toDos);
                }


                doesAdapter = new DoesAdapter(MainActivity.this, list);
                ourdoes.setAdapter(doesAdapter);
                doesAdapter.notifyDataSetChanged();

                progressBar.setVisibility(View.GONE);
                view.setVisibility(View.GONE);
                loadArea.setVisibility(View.GONE);

                btnAddNew.setVisibility(View.VISIBLE);




                //titlepage.setVisibility(View.VISIBLE);
                //subtitlepage.setVisibility(View.VISIBLE);
                //endpage.setVisibility(View.VISIBLE);
            }






            @Override
            public void onCancelled(DatabaseError databaseError) {
                // set code to show an error
                //
                //Toast.makeText(getApplicationContext(), "Authentication Error", Toast.LENGTH_SHORT).show();

                Toasty.error(getApplicationContext(), "Authentication Error !", Toast.LENGTH_LONG, true).show();

                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        presentShowcaseSequence();

    }


    private void presentShowcaseSequence() {

        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, SHOWCASE_ID);

        sequence.setOnItemShownListener(new MaterialShowcaseSequence.OnSequenceItemShownListener() {
            @Override
            public void onShow(MaterialShowcaseView itemView, int position) {
                //Toast.makeText(itemView.getContext(), "Item #" + position, Toast.LENGTH_SHORT).show();
            }
        });

        sequence.setConfig(config);

        sequence.addSequenceItem(btnAddNew, "Add Tasks", "OK");

       /* sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setSkipText("SKIP")
                        .setTarget()
                        .setDismissText("GOT IT")
                        .setContentText("This is button two")
                        .withRectangleShape(true)
                        .build()
        );

        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(mButtonThree)
                        .setDismissText("GOT IT")
                        .setContentText("This is button three")
                        .withRectangleShape()
                        .build()
        );
*/
        sequence.start();

    }



    private void changeStatusBarColor()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.status));
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

    public void updateList(){


        doesAdapter.notifyDataSetChanged();

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
                            emailFacebook = object.getString("email");
                             id = object.getString("id");
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

    @Override
    protected void onResume() {
        super.onResume();


        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        appUpdateInfo -> {

                            if (appUpdateInfo.updateAvailability()
                                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                                // If an in-app update is already running, resume the update.
                                try {
                                    appUpdateManager.startUpdateFlowForResult(
                                            appUpdateInfo,
                                            AppUpdateType.IMMEDIATE,
                                            MainActivity.this,
                                            MY_REQUEST_CODE);
                                } catch (IntentSender.SendIntentException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
        // No Internet Dialog
        NoInternetDialog.Builder builder1 = new NoInternetDialog.Builder(this);

        builder1.setConnectionCallback(new ConnectionCallback() { // Optional
            @Override
            public void hasActiveConnection(boolean hasActiveConnection) {
                // ...
            }
        });
        builder1.setCancelable(false); // Optional
        builder1.setNoInternetConnectionTitle("No Internet"); // Optional
        builder1.setNoInternetConnectionMessage("Check your Internet connection and try again"); // Optional
        builder1.setShowInternetOnButtons(true); // Optional
        builder1.setPleaseTurnOnText("Please turn on"); // Optional
        builder1.setWifiOnButtonText("Wifi"); // Optional
        builder1.setMobileDataOnButtonText("Mobile data"); // Optional

        builder1.setOnAirplaneModeTitle("No Internet"); // Optional
        builder1.setOnAirplaneModeMessage("You have turned on the airplane mode."); // Optional
        builder1.setPleaseTurnOffText("Please turn off"); // Optional
        builder1.setAirplaneModeOffButtonText("Airplane mode"); // Optional
        builder1.setShowAirplaneModeOffButtons(true); // Optional

        noInternetDialog = builder1.build();


        // No Internet Snackbar
        NoInternetSnackbar.Builder builder2 = new NoInternetSnackbar.Builder(this, (ViewGroup) findViewById(android.R.id.content));

        builder2.setConnectionCallback(new ConnectionCallback() { // Optional
            @Override
            public void hasActiveConnection(boolean hasActiveConnection) {
                // ...
            }
        });
        builder2.setIndefinite(true); // Optional
        builder2.setNoInternetConnectionMessage("No active Internet connection!"); // Optional
        builder2.setOnAirplaneModeMessage("You have turned on the airplane mode!"); // Optional
        builder2.setSnackbarActionText("Settings");
        builder2.setShowActionToDismiss(false);
        builder2.setSnackbarDismissActionText("OK");

        noInternetSnackbar = builder2.build();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // No Internet Dialog
        if (noInternetDialog != null) {
            noInternetDialog.destroy();
        }

        // No Internet Snackbar
        if (noInternetSnackbar != null) {
            noInternetSnackbar.destroy();
        }
    }


    public void btnCancel(View view) {
    }
}























