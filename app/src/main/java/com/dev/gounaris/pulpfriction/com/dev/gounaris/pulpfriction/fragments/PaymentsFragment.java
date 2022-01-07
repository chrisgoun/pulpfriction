package com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.fragments;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.dev.gounaris.pulpfriction.List;
import com.dev.gounaris.pulpfriction.R;
import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.customListViewAdapters.CustomAdapterAllPubs;
import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.objects.JobAd;
import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class PaymentsFragment extends Fragment {

    View view;
    private View vi, vii;
    private DisplayMetrics dm;
    private PopupWindow popupWindow;
    private Button back, backd;
    private ViewFlipper vFRatingd;
    private TextView pubName;
    private TextView jobPos;
    private TextView jobField;
    private TextView dated;
    private TextView hour;
    private TextView payment;
    private TextView extrad;
    private String[] jobPoss;
    private RatingBar rBehaviord, rKnowledged, rAppearanced, rConsistencyd, rBehaviorEG, rEnvironmentG, rConsistencyEG;
    private TextView rBehaviorTVd, rKnowledgeTVd, rAppearanceTVd, rConsistencyTVd, rBehaviorETVd, rEnvironmentTVd, rConsistencyETVd;
    private ArrayAdapter listUAdapter;
    private ArrayList<JobAd> allJobs;
    private ArrayList<DocumentReference> allMyJobs;
    private ProgressBar pB;
    private ListView prevJobsListView;
    private ViewFlipper vFprevJobs;
    private User user;
    private int counterU;
    private Date dateTimed;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private Calendar c;
    private Calendar cl;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_payments, container, false);
        user = ((List) getActivity()).getUser();
        vii = LayoutInflater.from(getContext()).inflate(R.layout.interested_pubs_about_layout,null,false);
        dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        Double height = dm.heightPixels*0.8;
        Double width = dm.widthPixels*0.9;
        cl = Calendar.getInstance();
        jobPoss = getResources().getStringArray(R.array.jobPos);
        prevJobsListView = view.findViewById(R.id.myPubsList);
        vFRatingd = vii.findViewById(R.id.vFRating);
        jobField = vii.findViewById(R.id.vFJobFieldTV);
        backd = vii.findViewById(R.id.back);
        pubName = vii.findViewById(R.id.vFNameTV);
        jobPos = vii.findViewById(R.id.vFJobPosTV);
        payment = vii.findViewById(R.id.vFPaymentTV);
        extrad = vii.findViewById(R.id.vFExtraTV);
        dated = vii.findViewById(R.id.Date);
        hour = vii.findViewById(R.id.Hour);
        vFprevJobs = view.findViewById(R.id.vFtrans);

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
        vFprevJobs.setDisplayedChild(0);

        popupWindow = new PopupWindow(vii , width.intValue(), height.intValue(), true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0xff61a635));
        popupWindow.setAnimationStyle(R.style.popup_window_animation);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(15);
        }
        pB = view.findViewById(R.id.pB2);
        lists();
        return view;
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
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            if (document.get("publisherId").equals((FirebaseFirestore.getInstance().collection("users").document(user.getUid())).toString())){
                                allJobs.add(new JobAd(document.getDate("uploadDate"), document.getString("pubName"), document.getString("jobField"),
                                        document.getString("extra"), Integer.parseInt(document.getLong("type").toString()), document.getDate("dateTime"), false,
                                        document.getString("region"), document.getDocumentReference("publisherId"), (ArrayList<DocumentReference>) document.get("interestedIds"),
                                        (document.getDouble("payment")*(-1.0)), document.getBoolean("employer"), document.getId()));
                                listUAdapter.notifyDataSetChanged();
                            }else {
                                allJobs.add(new JobAd(document.getDate("uploadDate"), document.getString("pubName"), document.getString("jobField"),
                                        document.getString("extra"), Integer.parseInt(document.getLong("type").toString()), document.getDate("dateTime"), false,
                                        document.getString("region"), document.getDocumentReference("publisherId"), (ArrayList<DocumentReference>) document.get("interestedIds"),
                                        document.getDouble("payment"), document.getBoolean("employer"), document.getId()));
                                listUAdapter.notifyDataSetChanged();
                            }
                            getJobs();
                        }
                    }
                }
            });
        }
    }

    private void lists(){
        if (user!=null && user.getTransactions()!=null){
            vFprevJobs.setDisplayedChild(0);
            pB.setVisibility(View.VISIBLE);
            allMyJobs = user.getTransactions();
            counterU = allMyJobs.size()-1;
            allJobs = new ArrayList<>();
            listUAdapter = new CustomAdapterAllPubs(getContext(), allJobs);
            prevJobsListView.setAdapter(listUAdapter);
            getJobs();
            prevJobsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
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
                    if (tempJob.getPublisherId().equals(FirebaseFirestore.getInstance().collection("users"))){
                        tempJob.getInterestedIds().get(0).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful() && task.getResult() != null) {
                                    DocumentSnapshot document = task.getResult();
                                    if ((document.getLong("employeeRatingsNum") > 0) || (document.getLong("employerRatingsNum") > 0)) {
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
                    }else {
                        tempJob.getPublisherId().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful() && task.getResult() != null) {
                                    DocumentSnapshot document = task.getResult();
                                    if ((document.getLong("employeeRatingsNum") > 0) || (document.getLong("employerRatingsNum") > 0)) {
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
                    }
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
        }else if (user != null && user.getTransactions()==null){
            vFprevJobs.setDisplayedChild(1);
        }else if (user != null && user.getTransactions().size()==0){
            vFprevJobs.setDisplayedChild(1);
        }
    }

}
