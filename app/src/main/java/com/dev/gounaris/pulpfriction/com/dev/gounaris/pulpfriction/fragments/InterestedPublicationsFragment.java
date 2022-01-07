package com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.fragments;

import android.app.Activity;
import android.content.Context;
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
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.dev.gounaris.pulpfriction.List;
import com.dev.gounaris.pulpfriction.R;
import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.customListViewAdapters.CustomAdapterIntPubs;
import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.customListViewAdapters.CustomAdapterMyPubs;
import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.objects.JobAd;
import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class InterestedPublicationsFragment extends Fragment {

    View view;
    private View v;
    private FirebaseFirestore db;
    private ArrayAdapter listAdapter;
    private ArrayList<JobAd> allJobs;
    private Long red;
    private Long targetPubSize;
    private User user;
    private ArrayList<DocumentReference> allMyJobs;
    private ListView listView;
    private ViewFlipper vf;
    private int listSize;
    private int counter;
    private ProgressBar pb;
    private View footer;
    private DisplayMetrics dm;
    private PopupWindow popupWindow;
    private String[] jobPoss;
    private TextView pubName;
    private TextView jobPos;
    private TextView jobField;
    private TextView date;
    private TextView hour;
    private TextView payment;
    private TextView extra;
    private TextInputLayout verificationCode;
    private Button complete;
    private Calendar cl,c;
    private Boolean flag;
    private Date dateTime;
    private Button back;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private View vi;
    private RatingBar rBehaviorE, rEnvironment, rConsistencyE;
    private ViewFlipper rVF;
    private Boolean popDis, rated;
    private Button rate;
    private Double rBehaviorED, rEnvironmentD, rConsistencyED;
    private RatingBar rBehavior, rKnowledge, rAppearance, rConsistency, rBehaviorEG, rEnvironmentG, rConsistencyEG;
    private TextView rBehaviorTV, rKnowledgeTV, rAppearanceTV, rConsistencyTV, rBehaviorETV, rEnvironmentTV, rConsistencyETV;
    private ViewFlipper vFRating;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_interested_publications, container, false);
        footer = LayoutInflater.from(getContext()).inflate(R.layout.footer, null);
        dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        Double height = dm.heightPixels*0.8;
        Double width = dm.widthPixels*0.9;
        v = LayoutInflater.from(getContext()).inflate(R.layout.interested_pubs_about_layout,null,false);
        vi = LayoutInflater.from(getContext()).inflate(R.layout.rating_popup,null,false);
        popupWindow = new PopupWindow(v , width.intValue(), height.intValue(), true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0xff61a635));
        popupWindow.setAnimationStyle(R.style.popup_window_animation);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(15);
        }
        rated = false;
        popDis = true;
        rVF = vi.findViewById(R.id.rVF);
        rBehaviorE = vi.findViewById(R.id.ratingBarBehE);
        rEnvironment = vi.findViewById(R.id.ratingBarApE);
        rConsistencyE = vi.findViewById(R.id.ratingBarCoE);
        rate = vi.findViewById(R.id.rate);
        jobPoss = getResources().getStringArray(R.array.jobPos);
        jobField = v.findViewById(R.id.vFJobFieldTV);
        back = v.findViewById(R.id.back);
        pubName = v.findViewById(R.id.vFNameTV);
        jobPos = v.findViewById(R.id.vFJobPosTV);
        payment = v.findViewById(R.id.vFPaymentTV);
        extra = v.findViewById(R.id.vFExtraTV);
        verificationCode = v.findViewById(R.id.verificationCode);
        complete = v.findViewById(R.id.complete);
        date = v.findViewById(R.id.Date);
        hour = v.findViewById(R.id.Hour);
        rBehaviorTV = v.findViewById(R.id.ratingsBeh);
        rKnowledgeTV = v.findViewById(R.id.ratingsKn);
        rAppearanceTV = v.findViewById(R.id.ratingsAp);
        rConsistencyTV = v.findViewById(R.id.ratingsCo);
        rBehaviorETV = v.findViewById(R.id.ratingsBehE);
        rEnvironmentTV = v.findViewById(R.id.ratingsApE);
        rConsistencyETV = v.findViewById(R.id.ratingsCoE);

        rBehavior = v.findViewById(R.id.ratingBarBeh);
        rKnowledge = v.findViewById(R.id.ratingBarKn);
        rAppearance = v.findViewById(R.id.ratingBarAp);
        rConsistency = v.findViewById(R.id.ratingBarCo);
        rBehaviorEG = v.findViewById(R.id.ratingBarBehE);
        rEnvironmentG = v.findViewById(R.id.ratingBarApE);
        rConsistencyEG = v.findViewById(R.id.ratingBarCoE);
        vFRating = v.findViewById(R.id.vFRating);
        pb = view.findViewById(R.id.pB2);
        pb.setVisibility(View.GONE);
        allJobs = new ArrayList<>();
        targetPubSize = (long) 10;
        red = (long) 1;
        db = FirebaseFirestore.getInstance();
        listView = view.findViewById(R.id.myPubsList);
        vf = view.findViewById(R.id.vFMyPubs);
        user = ((List) getActivity()).getUser();
        allMyJobs = user.getInterestedJobs();
        if (allMyJobs == null || allMyJobs.size() <= 0) {
            vf.setDisplayedChild(1);
        } else {
            listSize = allMyJobs.size();
            vf.setDisplayedChild(0);
            pb.setVisibility(View.VISIBLE);
            if (listSize < 9) {
                targetPubSize = (long) listSize;
            }
            listAdapter = new CustomAdapterIntPubs(getContext(), allJobs);
            listView.setAdapter(listAdapter);
            listView.addFooterView(footer);
            counter = 0;
            getJobs();
        }
        cl = Calendar.getInstance();
        flag = false;
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        db = FirebaseFirestore.getInstance();
        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listSize - red < 0) {
                    Toast.makeText(getActivity(), "No More Publications Found", Toast.LENGTH_SHORT).show();
                } else if (listSize - red < 10) {
                    if (counter == targetPubSize) {
                        targetPubSize = (long) allMyJobs.size() - (red - 1);
                        counter = 0;
                        pb.setVisibility(View.VISIBLE);
                        getJobs();
                    }
                } else {
                    if (counter == targetPubSize) {
                        targetPubSize = (long) 10;
                        counter = 0;
                        pb.setVisibility(View.VISIBLE);
                        getJobs();
                    }
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final JobAd tempJob = allJobs.get(i);
                pubName.setText(tempJob.getPubName());
                jobPos.setText(jobPoss[tempJob.getType()]);
                if (!tempJob.getEmployer()){
                    v.findViewById(R.id.divider1).setVisibility(View.VISIBLE);
                    jobField.setVisibility(View.VISIBLE);
                    jobField.setText(tempJob.getJobField());
                }else{
                    v.findViewById(R.id.divider1).setVisibility(View.GONE);
                    jobField.setVisibility(View.GONE);
                }
                if (tempJob.getPayment() != null) {
                    payment.setText(tempJob.getPayment() + " €");
                } else {
                    payment.setText("- €");
                }
                if (!tempJob.getExtra().equals(null) && !tempJob.getExtra().equals("")) {
                    extra.setText(tempJob.getExtra());
                } else {
                    extra.setText("-");
                }
                if (!tempJob.getState()){
                    verificationCode.setVisibility(View.VISIBLE);
                    complete.setVisibility(View.VISIBLE);
                }else{
                    verificationCode.setVisibility(View.GONE);
                    complete.setVisibility(View.GONE);
                }
                vFRating.setDisplayedChild(1);
                tempJob.getPublisherId().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if (document.getLong("employeeRatingsNum")>0 || document.getLong("employerRatingsNum")>0){
                                vFRating.setDisplayedChild(0);
                                rBehavior.setRating(Float.valueOf(document.getDouble("behaviorRating").toString()));
                                rKnowledge.setRating(Float.valueOf(document.getDouble("knowledgeRating").toString()));
                                rAppearance.setRating(Float.valueOf(document.getDouble("appearanceRating").toString()));
                                rConsistency.setRating(Float.valueOf(document.getDouble("ConsistencyRating").toString()));
                                rBehaviorEG.setRating(Float.valueOf(document.getDouble("behaviorERating").toString()));
                                rEnvironmentG.setRating(Float.valueOf(document.getDouble("workFieldRating").toString()));
                                rConsistencyEG.setRating(Float.valueOf(document.getDouble("efficiencyRating").toString()));

                                rBehaviorTV.setText("(" + document.getLong("employeeRatingsNum") + ")");
                                rKnowledgeTV.setText("(" + document.getLong("employeeRatingsNum") + ")");
                                rAppearanceTV.setText("(" + document.getLong("employeeRatingsNum") + ")");
                                rConsistencyTV.setText("(" + document.getLong("employeeRatingsNum") + ")");
                                rBehaviorETV.setText("(" + document.getLong("employerRatingsNum") + ")");
                                rEnvironmentTV.setText("(" + document.getLong("employerRatingsNum") + ")");
                                rConsistencyETV.setText("(" + document.getLong("employerRatingsNum") + ")");
                            }
                        }
                    }
                });
                dateTime = tempJob.getDateTime();
                cl.setTime(dateTime);
                mYear = cl.get(Calendar.YEAR);
                mMonth = cl.get(Calendar.MONTH);
                mDay = cl.get(Calendar.DAY_OF_MONTH);
                mHour = cl.get(Calendar.HOUR_OF_DAY);
                mMinute = cl.get(Calendar.MINUTE);
                date.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);
                hour.setText(mHour + ":" + mMinute);
                popupWindow.showAtLocation(getView().getRootView(),Gravity.CENTER, 0, 0);
                View popupBlur = (View) popupWindow.getContentView().getRootView();
                WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
                WindowManager.LayoutParams p = (WindowManager.LayoutParams) popupBlur.getLayoutParams();
                p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                p.dimAmount = 0.35f;
                wm.updateViewLayout(popupBlur, p);
                popDis = false;
                complete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (verify(tempJob) && !flag){
                            flag = true;
                            final DocumentReference tempJobDocRef = FirebaseFirestore.getInstance().collection("jobs").document(tempJob.getUid());
                            tempJob.getPublisherId().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()){
                                        ArrayList<DocumentReference> tempDocs = (ArrayList<DocumentReference>) task.getResult().get("myJobs");
                                        ArrayList<DocumentReference> tempTDocs = (ArrayList<DocumentReference>) task.getResult().get("transactions");
                                         tempDocs.remove(tempJobDocRef);
                                         if (tempTDocs == null) {
                                             tempTDocs = new ArrayList<>();
                                         }
                                        tempTDocs.add(tempJobDocRef);
                                        final Map<String, Object> changes = new HashMap<>();
                                        changes.put("myJobs", tempDocs);
                                        changes.put("transactions", tempTDocs);
                                        tempJob.getPublisherId().update(changes).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                final DocumentReference userDocRefTemp = FirebaseFirestore.getInstance().collection("users").document(user.getUid());
                                                userDocRefTemp.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()){
                                                            ArrayList<DocumentReference> tempDocs = user.getInterestedJobs();
                                                            tempDocs.remove(tempJobDocRef);
                                                            user.setInterestedJobs(tempDocs);
                                                            if (user.getPastJobs()!=null) {
                                                                tempDocs = user.getPastJobs();
                                                            }else{
                                                                tempDocs = new ArrayList<DocumentReference>();
                                                            }
                                                            tempDocs.add(tempJobDocRef);
                                                            user.setPastJobs(tempDocs);
                                                            if (user.getTransactions() != null){
                                                                tempDocs = user.getTransactions();
                                                            }else{
                                                                tempDocs = new ArrayList<DocumentReference>();
                                                            }
                                                            tempDocs.add(tempJobDocRef);
                                                            user.setTransactions(tempDocs);
                                                            final Map<String, Object> changes2 = new HashMap<>();
                                                            changes2.put("interestedJobs", user.getInterestedJobs());
                                                            changes2.put("pastJobs", user.getPastJobs());
                                                            changes2.put("transactions", user.getTransactions());
                                                            userDocRefTemp.update(changes2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if(!task.isSuccessful()){
                                                                        Toast.makeText(getActivity(), "Failed to complete transaction", Toast.LENGTH_SHORT).show();
                                                                    }else{
                                                                        Rate(tempJob.getPublisherId());
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    }
                                                });
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }
                });
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                        popDis = true;
                    }
                });
                verificationCode.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            hideKeyboard(v);
                        }
                    }
                });
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (rated){
                    final Map<String, Object> changes3 = new HashMap<>();
                    changes3.put("behaviorERating", rBehaviorED);
                    changes3.put("workFieldRating", rEnvironmentD);
                    changes3.put("efficiencyRating", rConsistencyED);
                    changes3.put("employerRatingsNum",user.getRatingsNumE()+1);
                    FirebaseFirestore.getInstance().collection("users").document(user.getUid()).update(changes3);
                    rated = false;
                }
                popupWindow.setContentView(v);
                popDis = true;
                flag = false;
            }
        });
    }

    private void Rate(DocumentReference pubRef){
        if (popDis){
            popupWindow.setContentView(vi);
            popupWindow.showAtLocation(getView().getRootView(),Gravity.CENTER, 0, 0);
            View popupBlur = (View) popupWindow.getContentView().getRootView();
            WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
            WindowManager.LayoutParams p = (WindowManager.LayoutParams) popupBlur.getLayoutParams();
            p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            p.dimAmount = 0.35f;
            wm.updateViewLayout(popupBlur, p);
            popDis = false;
        }else{
            popupWindow.dismiss();
            Rate(pubRef);
        }
        rVF.setDisplayedChild(1);
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rBehaviorED = (user.getrBehaviorE()*user.getRatingsNumE()+rBehaviorE.getRating())/(user.getRatingsNumE()+1);
                rEnvironmentD = (user.getrEnvironment()*user.getRatingsNumE()+rEnvironment.getRating())/(user.getRatingsNumE()+1);
                rConsistencyED = (user.getrConsistencyE()*user.getRatingsNumE()+rConsistencyE.getRating())/(user.getRatingsNumE()+1);
                popupWindow.dismiss();
                rated = true;
            }
        });
    }

    private Boolean verify(JobAd tempJob){
        if (verificationCode.getEditText().getText().toString().trim().isEmpty()){
            verificationCode.setErrorEnabled(true);
            verificationCode.setError("Field can't be empty");
            flag = false;
            return false;
        }else if (verificationCode.getEditText().getText().toString().trim().equals(tempJob.getUid().substring(0,6))) {
            verificationCode.setError(null);
            verificationCode.setErrorEnabled(false);
            return true;
        }else{
            verificationCode.setError(null);
            verificationCode.setErrorEnabled(false);
            Toast.makeText(getActivity(), "Wrong verification code ", Toast.LENGTH_SHORT).show();
            flag = false;
            return false;
        }
    }

    public void getJobs() {
        if (counter == targetPubSize || listSize - (counter + red) < 0) {
            red += counter;
            listAdapter.notifyDataSetChanged();
            pb.setVisibility(View.GONE);
            return;
        } else {
            allMyJobs.get((int) (listSize - (counter + red))).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            allJobs.add(new JobAd(document.getDate("uploadDate"), document.getString("pubName"), document.getString("jobField"),
                                    document.getString("extra"), Integer.parseInt(document.getLong("type").toString()), document.getDate("dateTime"), document.getBoolean("state"),
                                    document.getString("region"), document.getDocumentReference("publisherId"), (ArrayList<DocumentReference>) document.get("interestedIds"),
                                    document.getDouble("payment"), document.getBoolean("employer"), document.getId()));
                            counter++;
                            getJobs();
                        }
                    }
                }
            });
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
