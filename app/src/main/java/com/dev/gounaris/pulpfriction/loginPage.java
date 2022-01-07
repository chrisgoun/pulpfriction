package com.dev.gounaris.pulpfriction;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

public class loginPage extends AppCompatActivity {

    private TextView buttonSignUp;
    private Button buttonSignIn;
    private TextView forgotPass;
    private TextInputLayout email;
    private TextInputLayout password;
    private ProgressBar pB;
    private String emailTx;
    private String passTx;
    private SharedPreferences sharedPreferences;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.login_page);
        pB = findViewById(R.id.pB);
        email = findViewById(R.id.inputEmail);
        password = findViewById(R.id.inputPass);
        mAuth = FirebaseAuth.getInstance();
        buttonSignUp = findViewById(R.id.btSU);
        buttonSignIn = findViewById(R.id.btSI);
        forgotPass = findViewById(R.id.fPass);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String sPEmail = sharedPreferences.getString("myPrefsEmail", "");
        String sPPass = sharedPreferences.getString("myPrefsPass", "");
        if (!sPEmail.equals("") && !sPPass.equals("")){
            email.getEditText().setText(sPEmail);
            password.getEditText().setText(sPPass);
            pB.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(sPEmail, sPPass).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pB.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Sign in failed", Toast.LENGTH_SHORT).show();
                    email.getEditText().setText("");
                    password.getEditText().setText("");
                }
            }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    pB.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Sign in successful", Toast.LENGTH_SHORT).show();
                    openListPage();
                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegister();
            }
        });
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailTx = email.getEditText().getText().toString().trim();
                passTx = password.getEditText().getText().toString().trim();
                if (Validation()){
                    pB.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(emailTx, passTx).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pB.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Sign in failed", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            pB.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Sign in successful", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editorUser = sharedPreferences.edit();
                            editorUser.putString("myPrefsEmail", emailTx).apply();
                            editorUser.putString("myPrefsPass", passTx).apply();
                            openListPage();
                        }
                    });
                }
            }
        });
        email.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        password.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openForgotPassPage();
            }
        });

    }

    private void openForgotPassPage(){
        final Intent intent = new Intent(this, ForgotPassword.class);
        startActivity(intent);
    }

    private void openListPage() {
        final Intent intent = new Intent(this, List.class);
        new Timer().schedule(new TimerTask(){
            @Override
            public void run(){ startActivity(intent); }
        }, 500);
    }

    public void goToRegister(){
        Intent intent = new Intent(this, RegisterPage.class);
        startActivity(intent);
    }

    @NonNull
    private Boolean Vemail(){
        if (emailTx.isEmpty()){
            email.setErrorEnabled(true);
            email.setError("Field can't be empty");
            return false;
        }else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    @NonNull
    private Boolean Vpass(){
        if (passTx.isEmpty()){
            password.setErrorEnabled(true);
            password.setError("Field can't be empty");
            return false;
        }else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    @NonNull
    private Boolean Validation(){
        Boolean vE = Vemail();
        Boolean vP = Vpass();
        if (vE && vP){
            return true;
        }else {
            return false;
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
