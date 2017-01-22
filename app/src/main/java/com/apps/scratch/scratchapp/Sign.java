package com.apps.scratch.scratchapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import entity.Users;

public class Sign extends AppCompatActivity implements View.OnClickListener {

    EditText name;
    EditText phone;
    EditText mail;

    ImageButton SignIn , SignUp , Face , Gmail;


    Users user;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN=9001;
    private CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_sign);



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("664562489425-6brm72uvr58cf8q5j42f2316fopa2fb0.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                FirebaseUser user = mFirebaseAuth.getCurrentUser();
                //updateUI(user);

            }
        };



        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        mail =  (EditText) findViewById(R.id.mail);

        SignIn = (ImageButton) findViewById(R.id.Sign_In);
        SignUp = (ImageButton) findViewById(R.id.Sign_Up);
        Gmail = (ImageButton) findViewById(R.id.Gmail_Sign);


        SignIn.setOnClickListener(this);
        SignUp.setOnClickListener(this);
        Gmail.setOnClickListener(this);




        //FaceBook
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.button_facebook_login);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("FACE", "facebook:onSuccess:" + loginResult);
                signInWithFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("FACE", "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("FACE", "facebook:onError", error);
            }
        });
        //


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Sign_In:
                signIn();
                break;

            case R.id.Sign_Up:
                signUp();
                break;


            case R.id.Gmail_Sign:
                gmailLogin();
                break;
        }
    }



    void signIn(){
        String Name = name.getText().toString();
        String Phone = phone.getText().toString();
        String Mail = mail.getText().toString();

        Name = Name.trim();
        Phone = Phone.trim();
        Mail = Mail.trim();

        if(Name.isEmpty() || Phone.isEmpty() || Mail.isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(Sign.this);
            builder.setMessage(R.string.login_error_message)
                    .setTitle(R.string.login_error_title)
                    .setPositiveButton(android.R.string.ok, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }else{

            mFirebaseAuth.signInWithEmailAndPassword(Mail, Phone)
                    .addOnCompleteListener(Sign.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Intent intent = new Intent(Sign.this , Products.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(Sign.this);
                                builder.setMessage(task.getException().getMessage())
                                        .setTitle(R.string.login_error_title)
                                        .setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    });

        }
    }

    private void setUpUser(String name, String phone, String mail) {
        user = new Users();
        user.setPhone(phone);
        user.setMail(mail);
        user.setName(name);
    }


    void signUp(){

        String Name = name.getText().toString();
        String Phone = phone.getText().toString();
        String Mail = mail.getText().toString();

        Name = Name.trim();
        Phone = Phone.trim();
        Mail = Mail.trim();

        if(Name.isEmpty() || Phone.isEmpty() || Mail.isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(Sign.this);
            builder.setMessage(R.string.signup_error_message)
                    .setTitle(R.string.signup_error_title)
                    .setPositiveButton(android.R.string.ok, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }else{
            setUpUser(Name , Phone , Mail);

            mFirebaseAuth.createUserWithEmailAndPassword(Mail, Phone)
                    .addOnCompleteListener(Sign.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                saveNewUser(task.getResult().getUser().getUid(),user.getName() ,user.getMail(),user.getPhone());
                                Intent intent = new Intent(Sign.this , Products.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(Sign.this);
                                builder.setMessage(task.getException().getMessage())
                                        .setTitle(R.string.login_error_title)
                                        .setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    });

        }
    }

    private void saveNewUser(String uid, String name, String mail, String phone) {
        Users user = new Users(uid , name , mail ,phone);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        mDatabase.child(uid).setValue(user);
    }


    void gmailLogin(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(Sign.this);
                builder.setMessage("Error in signing")
                        .setTitle(R.string.login_error_title)
                        .setPositiveButton(android.R.string.ok, null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            saveNewUser( account.getId() , account.getDisplayName(),account.getEmail(),"");
                            Intent intent = new Intent(Sign.this, Products.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Sign.this);
                            builder.setMessage(task.getException().getMessage())
                                    .setTitle(R.string.login_error_title)
                                    .setPositiveButton(android.R.string.ok, null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });
    }





    private void signInWithFacebook(AccessToken token) {
        Log.d("Face", "signInWithFacebook:" + token);


        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("FAce", "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("Face", "signInWithCredential", task.getException());
                            Toast.makeText(Sign.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            String uid=task.getResult().getUser().getUid();
                            String name=task.getResult().getUser().getDisplayName();
                            String email=task.getResult().getUser().getEmail();
                            String image=task.getResult().getUser().getPhotoUrl().toString();

                            saveNewUser(uid , name , email , "");
                            Intent intent = new Intent(getApplicationContext(), Products.class);
                            intent.putExtra("user_id",uid);
                            intent.putExtra("profile_picture",image);
                            startActivity(intent);
                            finish();
                        }

                    }
                });
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
