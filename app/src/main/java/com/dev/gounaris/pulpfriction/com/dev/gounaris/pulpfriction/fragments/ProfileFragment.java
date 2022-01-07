package com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.dev.gounaris.pulpfriction.List;
import com.dev.gounaris.pulpfriction.R;
import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.customListViewAdapters.CustomAdapterAllPubs;
import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.objects.JobAd;
import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    private View view;
    private ImageView profPic;
    private TextView name;
    private TextView age;
    private TextView experience;
    private ViewFlipper vFprevJobs;
    private ListView prevJobsListView;
    private ViewFlipper vFRating;
    private TextView extra;
    private User user;
    private Button edit;
    private ImageView profPicChange;
    private TextInputLayout inputName;
    private TextInputLayout inputLastName;
    private TextInputLayout inputWorkExp;
    private Button bDate;
    private TextInputLayout inputExtra;
    private ViewFlipper vfProf;
    private TextView confirm;
    private TextView cancel;
    private int PICK_IMAGE_REQUEST = 1;
    private Uri changeUri;
    private DocumentReference docRef;
    private Calendar c;
    private Calendar cl;
    private Date dateTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private Boolean flag;
    private Boolean updating;
    private ProgressBar pB;
    private ArrayList<DocumentReference> allMyJobs;
    private ArrayList<JobAd> allJobs;
    private int counterU;
    private ArrayAdapter listUAdapter;
    private RatingBar rBehavior, rKnowledge, rAppearance, rConsistency, rBehaviorE, rEnvironment, rConsistencyE;
    private TextView rBehaviorTV, rKnowledgeTV, rAppearanceTV, rConsistencyTV, rBehaviorETV, rEnvironmentTV, rConsistencyETV;
    private RatingBar rBehaviord, rKnowledged, rAppearanced, rConsistencyd, rBehaviorEG, rEnvironmentG, rConsistencyEG;
    private TextView rBehaviorTVd, rKnowledgeTVd, rAppearanceTVd, rConsistencyTVd, rBehaviorETVd, rEnvironmentTVd, rConsistencyETVd;
    private ViewFlipper vFRatingd;
    private TextView pubName;
    private TextView jobPos;
    private TextView jobField;
    private TextView dated;
    private TextView hour;
    private TextView payment;
    private TextView extrad;
    private String[] jobPoss;
    private Date dateTimed;
    private View vi, vii;
    private DisplayMetrics dm;
    private PopupWindow popupWindow;
    private Button back, backd;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        user = ((List) getActivity()).getUser();
        vii = LayoutInflater.from(getContext()).inflate(R.layout.interested_pubs_about_layout,null,false);
        docRef = FirebaseFirestore.getInstance().collection("users").document(user.getUid());
        dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        Double height = dm.heightPixels*0.8;
        Double width = dm.widthPixels*0.9;
        jobPoss = getResources().getStringArray(R.array.jobPos);
        vfProf = view.findViewById(R.id.vFProf);
        vfProf.setDisplayedChild(0);
        profPic = view.findViewById(R.id.profPic);
        name = view.findViewById(R.id.name);
        age = view.findViewById(R.id.age);
        experience = view.findViewById(R.id.experience);
        vFprevJobs = view.findViewById(R.id.vFprevJobs);
        prevJobsListView = view.findViewById(R.id.prevJobs);
        vFRating = view.findViewById(R.id.vFRating);
        extra = view.findViewById(R.id.extra);
        edit = view.findViewById(R.id.edit);
        profPicChange = view.findViewById(R.id.inputProfPic);
        inputName = view.findViewById(R.id.inputName);
        inputLastName = view.findViewById(R.id.inputLastName);
        inputWorkExp = view.findViewById(R.id.inputWorkExp);
        bDate = view.findViewById(R.id.bdate);
        inputExtra = view.findViewById(R.id.inputExtra);
        confirm = view.findViewById(R.id.confirm);
        cancel = view.findViewById(R.id.cancel);
        pB = view.findViewById(R.id.pB);
        rBehaviorTV = view.findViewById(R.id.ratingsBeh);
        rKnowledgeTV = view.findViewById(R.id.ratingsKn);
        rAppearanceTV = view.findViewById(R.id.ratingsAp);
        rConsistencyTV = view.findViewById(R.id.ratingsCo);
        rBehaviorETV = view.findViewById(R.id.ratingsBehE);
        rEnvironmentTV = view.findViewById(R.id.ratingsApE);
        rConsistencyETV = view.findViewById(R.id.ratingsCoE);

        rBehavior = view.findViewById(R.id.ratingBarBeh);
        rKnowledge = view.findViewById(R.id.ratingBarKn);
        rAppearance = view.findViewById(R.id.ratingBarAp);
        rConsistency = view.findViewById(R.id.ratingBarCo);
        rBehaviorE = view.findViewById(R.id.ratingBarBehE);
        rEnvironment = view.findViewById(R.id.ratingBarApE);
        rConsistencyE = view.findViewById(R.id.ratingBarCoE);

        vFRatingd = vii.findViewById(R.id.vFRating);
        jobField = vii.findViewById(R.id.vFJobFieldTV);
        backd = vii.findViewById(R.id.back);
        pubName = vii.findViewById(R.id.vFNameTV);
        jobPos = vii.findViewById(R.id.vFJobPosTV);
        payment = vii.findViewById(R.id.vFPaymentTV);
        extrad = vii.findViewById(R.id.vFExtraTV);
        dated = vii.findViewById(R.id.Date);
        hour = vii.findViewById(R.id.Hour);

        rBehaviorTVd = vii.findViewById(R.id.ratingsBeh);
        rKnowledgeTVd = vii.findViewById(R.id.ratingsKn);
        rAppearanceTVd = vii.findViewById(R.id.ratingsAp);
        rConsistencyTVd = vii.findViewById(R.id.ratingsCo);
        rBehaviorETVd = vii.findViewById(R.id.ratingsBehE);
        rEnvironmentTVd = vii.findViewById(R.id.ratingsApE);
        rConsistencyETVd = vii.findViewById(R.id.ratingsCoE);

        rBehaviord = vii.findViewById(R.id.ratingBarBeh);
        rKnowledged = vii.findViewById(R.id.ratingBarKn);
        rAppearanced = vii.findViewById(R.id.ratingBarAp);
        rConsistencyd = vii.findViewById(R.id.ratingBarCo);
        rBehaviorEG = vii.findViewById(R.id.ratingBarBehE);
        rEnvironmentG = vii.findViewById(R.id.ratingBarApE);
        rConsistencyEG = vii.findViewById(R.id.ratingBarCoE);

        popupWindow = new PopupWindow(vii , width.intValue(), height.intValue(), true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0xff61a635));
        popupWindow.setAnimationStyle(R.style.popup_window_animation);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(15);
        }
        flag = false;
        updating = false;

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (flag){
            vfProf.setDisplayedChild(1);
            flag = false;
        }else {
            vfProf.setDisplayedChild(0);
            Picasso.with(getActivity()).load(user.getProfilePicUrl()).fit().placeholder(R.mipmap.ic_launcher_round).centerCrop().into(profPic);
            name.setText(user.getName() + " " + user.getLastName());
            cl = Calendar.getInstance();
            c = Calendar.getInstance();
            cl.set(Integer.valueOf(user.getbYear().toString()), Integer.valueOf(user.getbMonth().toString()), Integer.valueOf(user.getbDay().toString()));
            Integer ageNum = c.get(Calendar.YEAR) - cl.get(Calendar.YEAR);
            if (cl.get(Calendar.DAY_OF_YEAR) < c.get(Calendar.DAY_OF_YEAR)) {
                ageNum--;
            }
            age.setText(ageNum.toString());
            if (user.getExperience().equals("")) {
                experience.setText(user.getExperience());
            }
            if (user.getRatingsNum()>0 || user.getRatingsNumE()>0) {
                vFRating.setDisplayedChild(0);
                rBehavior.setRating(Float.valueOf(user.getrBehavior().toString()));
                rKnowledge.setRating(Float.valueOf(user.getrKnowledge().toString()));
                rAppearance.setRating(Float.valueOf(user.getrAppearance().toString()));
                rConsistency.setRating(Float.valueOf(user.getrConsistency().toString()));
                rBehaviorE.setRating(Float.valueOf(user.getrBehaviorE().toString()));
                rEnvironment.setRating(Float.valueOf(user.getrEnvironment().toString()));
                rConsistencyE.setRating(Float.valueOf(user.getrConsistencyE().toString()));

                rBehaviorTV.setText("(" + user.getRatingsNum() + ")");
                rKnowledgeTV.setText("(" + user.getRatingsNum() + ")");
                rAppearanceTV.setText("(" + user.getRatingsNum() + ")");
                rConsistencyTV.setText("(" + user.getRatingsNum() + ")");
                rBehaviorETV.setText("(" + user.getRatingsNumE() + ")");
                rEnvironmentTV.setText("(" + user.getRatingsNumE() + ")");
                rConsistencyETV.setText("(" + user.getRatingsNumE() + ")");

            } else {
                vFRating.setDisplayedChild(1);
            }
            if (user.getExtra() != null && !user.getExtra().equals("")) {
                extra.setText(user.getExtra());
            }
            if (user.getPastJobs() != null && user.getPastJobs().size() > 0) {
                vFprevJobs.setDisplayedChild(0);
                pB.setVisibility(View.VISIBLE);
                allMyJobs = user.getPastJobs();
                counterU = allMyJobs.size()-1;
                allJobs = new ArrayList<>();
                listUAdapter = new CustomAdapterAllPubs(getContext(), allJobs);
                prevJobsListView.setAdapter(listUAdapter);
                getJobs();
                prevJobsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        popupWindow.setContentView(vii);
                        final JobAd tempJob = allJobs.get(i);
                        pubName.setText(tempJob.getPubName());
                        jobPos.setText(jobPoss[tempJob.getType()]);
                        if (!tempJob.getEmployer()){
                            vii.findViewById(R.id.divider1).setVisibility(View.VISIBLE);
                            jobField.setVisibility(View.VISIBLE);
                            jobField.setText(tempJob.getJobField());
                        }else{
                            vii.findViewById(R.id.divider1).setVisibility(View.GONE);
                            jobField.setVisibility(View.GONE);
                        }
                        if (tempJob.getPayment() != null) {
                            payment.setText(tempJob.getPayment() + " €");
                        } else {
                            payment.setText("- €");
                        }
                        if (!tempJob.getExtra().equals(null) && !tempJob.getExtra().equals("")) {
                            extrad.setText(tempJob.getExtra());
                        } else {
                            extrad.setText("-");
                        }
                        vFRatingd.setDisplayedChild(1);
                        tempJob.getPublisherId().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful() && task.getResult() != null){
                                    DocumentSnapshot document = task.getResult();
                                    if ((document.getLong("employeeRatingsNum")>0) || (document.getLong("employerRatingsNum")>0)){
                                        vFRatingd.setDisplayedChild(0);
                                        rBehaviord.setRating(Float.valueOf(document.getDouble("behaviorRating").toString()));
                                        rKnowledged.setRating(Float.valueOf(document.getDouble("knowledgeRating").toString()));
                                        rAppearanced.setRating(Float.valueOf(document.getDouble("appearanceRating").toString()));
                                        rConsistencyd.setRating(Float.valueOf(document.getDouble("ConsistencyRating").toString()));
                                        rBehaviorEG.setRating(Float.valueOf(document.getDouble("behaviorERating").toString()));
                                        rEnvironmentG.setRating(Float.valueOf(document.getDouble("workFieldRating").toString()));
                                        rConsistencyEG.setRating(Float.valueOf(document.getDouble("efficiencyRating").toString()));

                                        rBehaviorTVd.setText("(" + document.getLong("employeeRatingsNum") + ")");
                                        rKnowledgeTVd.setText("(" + document.getLong("employeeRatingsNum") + ")");
                                        rAppearanceTVd.setText("(" + document.getLong("employeeRatingsNum") + ")");
                                        rConsistencyTVd.setText("(" + document.getLong("employeeRatingsNum") + ")");
                                        rBehaviorETVd.setText("(" + document.getLong("employerRatingsNum") + ")");
                                        rEnvironmentTVd.setText("(" + document.getLong("employerRatingsNum") + ")");
                                        rConsistencyETVd.setText("(" + document.getLong("employerRatingsNum") + ")");
                                    }
                                }
                            }
                        });
                        dateTimed = tempJob.getDateTime();
                        cl.setTime(dateTimed);
                        mYear = cl.get(Calendar.YEAR);
                        mMonth = cl.get(Calendar.MONTH);
                        mDay = cl.get(Calendar.DAY_OF_MONTH);
                        mHour = cl.get(Calendar.HOUR_OF_DAY);
                        mMinute = cl.get(Calendar.MINUTE);
                        dated.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);
                        hour.setText(mHour + ":" + mMinute);
                        popupWindow.showAtLocation(getView().getRootView(),Gravity.CENTER, 0, 0);
                        View popupBlur = (View) popupWindow.getContentView().getRootView();
                        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
                        WindowManager.LayoutParams p = (WindowManager.LayoutParams) popupBlur.getLayoutParams();
                        p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                        p.dimAmount = 0.35f;
                        wm.updateViewLayout(popupBlur, p);
                        backd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupWindow.dismiss();
                            }
                        });
                    }
                });
            } else {
                vFprevJobs.setDisplayedChild(1);
            }
        }
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Picasso.with(getActivity()).load(user.getProfilePicUrl()).fit().placeholder(R.mipmap.ic_launcher_round).centerCrop().into(profPicChange);
                inputExtra.getEditText().setText(extra.getText());
                inputName.getEditText().setText(user.getName());
                inputLastName.getEditText().setText(user.getLastName());
                inputWorkExp.getEditText().setText(experience.getText());
                bDate.setText(user.getbDay()+"/"+user.getbMonth()+"/"+user.getbYear());
                vfProf.setDisplayedChild(1);
                profPicChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openFileChooser();
                    }
                });
                bDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
                                        cl = Calendar.getInstance();
                                        cl.set(year, monthOfYear, dayOfMonth);
                                        dateTime = cl.getTime();
                                        mDay = dayOfMonth;
                                        mMonth = monthOfYear;
                                        mYear = year;
                                        bDate.setText(mDay+"/"+(mMonth+1)+"/"+mYear);
                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                    }
                });
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!updating) {
                            Boolean vN = Vname();
                            Boolean vLN = VlastName();
                            Boolean vWE = Vwork();
                            Boolean vBD = Vbirthday();
                            if (vN && vLN && vBD) {
                                updating = true;
                                pB.setVisibility(View.VISIBLE);
                                update();
                            }
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        vfProf.setDisplayedChild(0);
                    }
                });
            }
        });
        inputExtra.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        inputName.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        inputLastName.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        inputWorkExp.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        flag = true;
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            changeUri = data.getData();
            Picasso.with(getActivity()).load(changeUri).resize(300,300).into(profPicChange);
            vfProf.setDisplayedChild(1);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void update(){
        final Map<String, Object> users = new HashMap<>();
        if(!inputExtra.getEditText().getText().toString().trim().equals(user.getExtra())){
            users.put("extra", inputExtra.getEditText().getText().toString().trim());
        }
        if(!inputName.getEditText().getText().toString().trim().equals(user.getName())){
            users.put("name", inputName.getEditText().getText().toString().trim());
        }
        if(!inputLastName.getEditText().getText().toString().trim().equals(user.getLastName())){
            users.put("lastName", inputLastName.getEditText().getText().toString().trim());
        }
        if(!inputWorkExp.getEditText().getText().toString().trim().equals(user.getExperience())){
            users.put("experience", inputWorkExp.getEditText().getText().toString().trim());
        }
        users.put("bDay", cl.get(Calendar.DAY_OF_MONTH));
        users.put("bMonth", cl.get(Calendar.MONTH));
        users.put("bYear", cl.get(Calendar.YEAR));
        final StorageReference fileReference = FirebaseStorage.getInstance().getReference(user.getUid()).child("profPic." + getFileExtension(changeUri));
        fileReference.putFile(changeUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                users.put("profPicRef", taskSnapshot.getMetadata().getPath());
                fileReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        users.put("profPic",task.getResult().toString());
                        docRef.update(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(getActivity(), "Changes applied successfully", Toast.LENGTH_SHORT).show();
                                    updating = false;
                                    pB.setVisibility(View.GONE);
                                    onStart();
                                }else {
                                    Toast.makeText(getActivity(), "Failed to apply changes", Toast.LENGTH_SHORT).show();
                                    updating = false;
                                    pB.setVisibility(View.GONE);
                                    onStart();
                                }
                                }
                            });
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "Failed to apply changes", Toast.LENGTH_SHORT).show();
                }
            });
    }
    @NonNull
    private Boolean Vname(){
        if (inputName.getEditText().getText().toString().trim().isEmpty()){
            inputName.setErrorEnabled(true);
            name.setError("Field can't be empty");
            return false;
        }else {
            inputName.setError(null);
            inputName.setErrorEnabled(false);
            return true;
        }
    }

    @NonNull
    private Boolean VlastName(){
        if (inputLastName.getEditText().getText().toString().trim().isEmpty()){
            inputLastName.setErrorEnabled(true);
            inputLastName.setError("Field can't be empty");
            return false;
        }else {
            inputLastName.setError(null);
            inputLastName.setErrorEnabled(false);
            return true;
        }
    }
    @NonNull
    private Boolean Vwork(){
        if (inputWorkExp.getEditText().getText().toString().trim().isEmpty()){
            inputWorkExp.getEditText().setText("-");
        }
        return true;
    }
    @NonNull
    private Boolean Vbirthday(){
        Integer ageNum = c.get(Calendar.YEAR) - cl.get(Calendar.YEAR);
        if (cl.get(Calendar.DAY_OF_YEAR)< c.get(Calendar.DAY_OF_YEAR)) {
            ageNum--;
        }
        if (ageNum<16){
            Toast.makeText(getActivity(), "Must be over 16 years old", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void getJobs() {
        if (counterU < 0) {
            pB.setVisibility(View.GONE);
            return;
        } else {
            allMyJobs.get((int) counterU).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    counterU--;
                    getJobs();
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            allJobs.add(new JobAd(document.getDate("uploadDate"), document.getString("pubName"), document.getString("jobField"),
                                    document.getString("extra"), Integer.parseInt(document.getLong("type").toString()), document.getDate("dateTime"), false,
                                    document.getString("region"), document.getDocumentReference("publisherId"), (ArrayList<DocumentReference>) document.get("interestedIds"),
                                    document.getDouble("payment"), document.getBoolean("employer"), document.getId()));
                            listUAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }
}
