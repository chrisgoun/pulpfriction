package com.dev.gounaris.pulpfriction;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private TextInputLayout email;
    private String emailTx;
    private TextView sendResetPass;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.forgot_password);
        email = findViewById(R.id.inputEmail);
        sendResetPass = findViewById(R.id.sendResetPass);
        auth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        sendResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailTx = email.getEditText().getText().toString().trim();
                if (Vemail()){
                    auth.sendPasswordResetEmail(emailTx).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Failed to send password reset email", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(), "Password reset email sent successfully", Toast.LENGTH_SHORT).show();
                            openLogin();
                        }
                    });
                }
            }
        });
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
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

    private void openLogin(){
        final Intent intent = new Intent(this, loginPage.class);
        startActivity(intent);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
