package com.dev.gounaris.pulpfriction;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import io.grpc.internal.SharedResourceHolder;

public class RegisterPage extends AppCompatActivity {

    private Boolean male;
    private Boolean employer;
    private Long bDay;
    private Long bMonth;
    private Long bYear;
    private SharedPreferences sharedPreferences;
    private FirebaseAuth mAuth;
    private DocumentReference docRef;

    private TextInputLayout email;
    private TextInputLayout pass;
    private TextInputLayout confPass;
    private TextInputLayout ssn;
    private TextInputLayout job;
    private TextInputLayout name;
    private TextInputLayout lastName;
    private TextInputLayout compName;
    private TextInputLayout compTin;
    private ProgressBar pB;
    private Spinner compJob;

    private String emailTx;
    private String passTx;
    private String confPassTx;
    private String ssnTx;
    private String jobTx;
    private String compJobTx;
    private String nameTx;
    private String lastNameTx;
    private String compNameTx;
    private String compTinTx;
    private Long ssnNum;
    private Long compTinNum;
    private Uri uri;
    private String[] jobFields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.register_page);
        email = findViewById(R.id.inputEmail);
        pass = findViewById(R.id.inputPass);
        confPass = findViewById(R.id.inputConfPass);
        ssn = findViewById(R.id.inputSsn);
        job = findViewById(R.id.inputJob);
        name = findViewById(R.id.name);
        lastName = findViewById(R.id.lastname);
        compName = findViewById(R.id.inputCompName);
        compTin = findViewById(R.id.inputCompTin);
        compJob = findViewById(R.id.fieldOfWork);
        jobFields = getResources().getStringArray(R.array.fieldOfWork);
        compJobTx = jobFields[0];
    }

    @Override
    public void onStart() {
        super.onStart();
        pB = findViewById(R.id.pB);
        mAuth = FirebaseAuth.getInstance();
        male = true;
        employer = true;
        final ViewFlipper vf = findViewById(R.id.extension);
        vf.setDisplayedChild(0);
        vf.setVisibility(View.GONE);
        Spinner accountType = findViewById(R.id.spinner1);
        Spinner gender = findViewById(R.id.spinner);
        compJob.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                compJobTx = jobFields[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        accountType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position == 1){
                    vf.setVisibility(View.VISIBLE);
                    employer = false;
                }
                else{
                    vf.setVisibility(View.GONE);
                    employer = true;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 1){
                    male = false;
                }
                else{
                    male = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Spinner spYear = findViewById(R.id.spYear);
        Spinner spMonth = findViewById(R.id.spMonth);
        Spinner spDay = findViewById(R.id.spDay);
        bDay = (long) 1;
        bMonth = (long) 1;
        bYear = (long) 2018;
        spDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bDay = (long) (1+i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bMonth = (long) (1+i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bYear = (long) (2018-i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Button bt = findViewById(R.id.btSU);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                emailTx = email.getEditText().getText().toString().trim();
                passTx = pass.getEditText().getText().toString().trim();
                confPassTx = confPass.getEditText().getText().toString().trim();
                ssnTx = ssn.getEditText().getText().toString().trim();
                jobTx = job.getEditText().getText().toString().trim();
                nameTx = name.getEditText().getText().toString().trim();
                lastNameTx = lastName.getEditText().getText().toString().trim();
                compNameTx = compName.getEditText().getText().toString().trim();
                compTinTx = compTin.getEditText().getText().toString().trim();
                if (Validation()) {
                    pB.setVisibility(View.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(emailTx, passTx).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                try {
                                    throw task.getException();
                                }
                                // if user enters wrong email.
                                catch (FirebaseAuthWeakPasswordException weakPassword) {
                                    pB.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), "Password too weak", Toast.LENGTH_SHORT).show();
                                }
                                // if user enters wrong password.
                                catch (FirebaseAuthInvalidCredentialsException malformedEmail) {
                                    pB.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), "'" + emailTx + "'" + " is not an email address", Toast.LENGTH_SHORT).show();
                                }
                                //if user already exists
                                catch (FirebaseAuthUserCollisionException existEmail) {
                                    pB.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), "An account with that email address already exists", Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    pB.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), "Sign up failed", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                mAuth.getCurrentUser().sendEmailVerification().addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), "Failed to send verification Email", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getApplicationContext(), "Verification Email sent", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                final String strMauth = String.valueOf(mAuth.getCurrentUser().getUid());
                                final FirebaseStorage storage = FirebaseStorage.getInstance();
                                StorageReference storageRef = storage.getReference();
                                final StorageReference profPicRef = storageRef.child(strMauth + "/profPic.png");
                                if (male){
                                    uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                                            "://" + getResources().getResourcePackageName(R.drawable.male)
                                            + '/' + getResources().getResourceTypeName(R.drawable.male) + '/' + getResources().getResourceEntryName(R.drawable.male) );
                                }else{
                                    uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                                            "://" + getResources().getResourcePackageName(R.drawable.female)
                                            + '/' + getResources().getResourceTypeName(R.drawable.female) + '/' + getResources().getResourceEntryName(R.drawable.female) );
                                }
                                profPicRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                                        docRef = db.collection("users").document(strMauth);
                                        final Map<String, Object> users = new HashMap<>();
                                        ssnNum = Long.valueOf(ssnTx);
                                        users.put("amka", ssnNum);
                                        users.put("employer", employer);
                                        users.put("job", jobTx);
                                        users.put("name", nameTx);
                                        if (!employer) {
                                            compTinNum = Long.valueOf(compTinTx);
                                            users.put("companyAfm", compTinNum);
                                            users.put("companyName", compNameTx);
                                            users.put("companyJob", compJobTx);
                                        }else {
                                            compTinNum = (long) 0;
                                            users.put("companyAfm", compTinNum);
                                            users.put("companyName", "");
                                            users.put("companyJob", "");
                                        }
                                        users.put("lastName", lastNameTx);
                                        users.put("male", male);
                                        users.put("bDay", bDay);
                                        users.put("bMonth", bMonth);
                                        users.put("bYear", bYear);
                                        users.put("interestedJobs", null);
                                        users.put("myJobs", null);
                                        users.put("transactions", null);
                                        users.put("pastJobs", null);
                                        users.put("workFieldRating",0);
                                        users.put("ConsistencyRating",0);
                                        users.put("behaviorRating",0);
                                        users.put("employeeRatingsNum", 0);
                                        users.put("employerRatingsNum", 0);
                                        users.put("knowledgeRating",0);
                                        users.put("behaviorERating",0);
                                        users.put("efficiencyRating",0);
                                        users.put("appearanceRating",0);
                                        users.put("extra", "");
                                        users.put("experience", "");
                                        users.put("profPicRef", taskSnapshot.getMetadata().getPath());
                                        profPicRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Uri> task) {
                                                users.put("profPic",task.getResult().toString());
                                                docRef.set(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            pB.setVisibility(View.GONE);
                                                            Toast.makeText(getApplicationContext(), "Sign up successful", Toast.LENGTH_SHORT).show();
                                                            SharedPreferences.Editor editorUser = sharedPreferences.edit();
                                                            editorUser.putString("myPrefsEmail", emailTx).apply();
                                                            editorUser.putString("myPrefsPass", passTx).apply();
                                                            openListPage();
                                                        }else {
                                                            pB.setVisibility(View.GONE);
                                                            Toast.makeText(getApplicationContext(), "Sign up failed", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            }
                                        });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        pB.setVisibility(View.GONE);
                                        Toast.makeText(getApplicationContext(), "Sign up failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
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
        pass.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        confPass.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        ssn.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        job.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        name.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        lastName.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        compName.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        compTin.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }

    private void openListPage() {
        final Intent intent = new Intent(this, List.class);
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
            pass.setErrorEnabled(true);
            pass.setError("Field can't be empty");
            return false;
        }else if (passTx.length()<8){
            pass.setErrorEnabled(true);
            pass.setError("Password must be at least 8 characters long");
            return false;
        } else {
            pass.setError(null);
            pass.setErrorEnabled(false);
            return true;
        }
    }

    @NonNull
    private Boolean VconfPass(){
        if (confPassTx.isEmpty()){
            confPass.setErrorEnabled(true);
            confPass.setError("Field can't be empty");
            return false;
        }else if (!confPassTx.equals(passTx)){
            confPass.setErrorEnabled(true);
            confPass.setError("'Password' and 'Confirm Password' fields do not match");
            return false;
        }else {
            confPass.setError(null);
            confPass.setErrorEnabled(false);
            return true;
        }
    }

    @NonNull
    private Boolean Vssn(){
        if (ssnTx.isEmpty()){
            ssn.setErrorEnabled(true);
            ssn.setError("Field can't be empty");
            return false;
        }else {
            ssn.setError(null);
            ssn.setErrorEnabled(false);
            return true;
        }
    }

    @NonNull
    private Boolean Vjob(){
        if (jobTx.isEmpty()){
            job.setErrorEnabled(true);
            job.setError("Field can't be empty");
            return false;
        }else {
            job.setError(null);
            job.setErrorEnabled(false);
            return true;
        }
    }

    @NonNull
    private Boolean Vname(){
        if (nameTx.isEmpty()){
            name.setErrorEnabled(true);
            name.setError("Field can't be empty");
            return false;
        }else {
            name.setError(null);
            name.setErrorEnabled(false);
            return true;
        }
    }

    @NonNull
    private Boolean VlastName(){
        if (lastNameTx.isEmpty()){
            lastName.setErrorEnabled(true);
            lastName.setError("Field can't be empty");
            return false;
        }else {
            lastName.setError(null);
            lastName.setErrorEnabled(false);
            return true;
        }
    }

    @NonNull
    private Boolean VcompName(){
        if (compNameTx.isEmpty() && !employer){
            compName.setErrorEnabled(true);
            compName.setError("Field can't be empty");
            return false;
        }else {
            compName.setError(null);
            compName.setErrorEnabled(false);
            return true;
        }
    }

    @NonNull
    private Boolean VcompTin(){
        if (compTinTx.isEmpty() && !employer){
            compTin.setErrorEnabled(true);
            compTin.setError("Field can't be empty");
            return false;
        }else {
            compTin.setError(null);
            compTin.setErrorEnabled(false);
            return true;
        }
    }

    @NonNull
    private Boolean Validation(){
        Boolean vE = Vemail();
        Boolean vP = Vpass();
        Boolean vCP = VconfPass();
        Boolean vS = Vssn();
        Boolean vJ = Vjob();
        Boolean vN = Vname();
        Boolean vLN = VlastName();
        Boolean vCN = VcompName();
        Boolean vCT = VcompTin();

        if (vE && vP && vCP && vS && vJ && vN && vLN && vCN && vCT){
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