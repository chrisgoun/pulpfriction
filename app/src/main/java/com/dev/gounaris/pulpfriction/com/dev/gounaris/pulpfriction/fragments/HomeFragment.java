package com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.dev.gounaris.pulpfriction.List;
import com.dev.gounaris.pulpfriction.R;
import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.customListViewAdapters.CustomAdapterAllPubs;
import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.customListViewAdapters.CustomAdapterIntPubs;
import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.objects.JobAd;
import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    GestureDetectorCompat gestureDetectorCompat;

    private View view;
    private View v;
    private Spinner spJob;
    private Spinner spPrice;
    private Spinner spsort;
    private Button dateTime;
    private PopupWindow popupWindow;
    private DisplayMetrics dm;
    private FloatingActionButton filter;
    private Calendar c, cl;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private Date date;
    private Button back, backd;
    private int job, price, sort;
    private View vi, vii,viii;
    private com.craftman.cardform.CardForm cardForm;
    private Boolean[] filters;
    private Query query;
    private Boolean filterPay;
    private TextView tV;
    private Button pay;
    private ProgressBar pb, p;
    private ListView listView;
    private User user;
    private ArrayList<JobAd> allJobs;
    private ArrayAdapter listAdapter;
    private Double rBehaviorED, rEnvironmentD, rConsistencyED;
    private RatingBar rBehavior, rKnowledge, rAppearance, rConsistency, rBehaviorEG, rEnvironmentG, rConsistencyEG;
    private TextView rBehaviorTV, rKnowledgeTV, rAppearanceTV, rConsistencyTV, rBehaviorETV, rEnvironmentTV, rConsistencyETV;
    private ViewFlipper vFRating, vFRatingd, vFNews;
    private TextView pubName;
    private TextView jobPos;
    private TextView jobField;
    private TextView dated;
    private TextView hour;
    private TextView payment;
    private TextView extra;
    private String[] jobPoss;
    private Date dateTimed;
    private EditText cardNumber;
    private EditText cvc;
    private Boolean flag;
    private Boolean wantsPrice, wantsCal;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        filter = view.findViewById(R.id.filters);
        flag = true;
        wantsPrice = false;
        wantsCal = false;
        viii = LayoutInflater.from(getContext()).inflate(R.layout.pr_bar, null);
        p = viii.findViewById(R.id.pB3);
        jobPoss = getResources().getStringArray(R.array.jobPos);
        filterPay = false;
        filters = new Boolean[3];
        dm = new DisplayMetrics();
        listView = view.findViewById(R.id.pubsList);
        vFNews = view.findViewById(R.id.vFNews);
        vFNews.setDisplayedChild(0);
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        Double height = dm.heightPixels*0.8;
        Double width = dm.widthPixels*0.9;
        v = LayoutInflater.from(getContext()).inflate(R.layout.filters,null,false);
        vi = LayoutInflater.from(getContext()).inflate(R.layout.credit_card_input,null,false);
        vii = LayoutInflater.from(getContext()).inflate(R.layout.interested_pubs_about_layout,null,false);
        jobField = vii.findViewById(R.id.vFJobFieldTV);
        backd = vii.findViewById(R.id.back);
        pubName = vii.findViewById(R.id.vFNameTV);
        jobPos = vii.findViewById(R.id.vFJobPosTV);
        payment = vii.findViewById(R.id.vFPaymentTV);
        extra = vii.findViewById(R.id.vFExtraTV);
        dated = vii.findViewById(R.id.Date);
        hour = vii.findViewById(R.id.Hour);
        rBehaviorTV = vii.findViewById(R.id.ratingsBeh);
        rKnowledgeTV = vii.findViewById(R.id.ratingsKn);
        rAppearanceTV = vii.findViewById(R.id.ratingsAp);
        rConsistencyTV = vii.findViewById(R.id.ratingsCo);
        rBehaviorETV = vii.findViewById(R.id.ratingsBehE);
        rEnvironmentTV = vii.findViewById(R.id.ratingsApE);
        rConsistencyETV = vii.findViewById(R.id.ratingsCoE);
        cardNumber = vi.findViewById(com.craftman.cardform.R.id.card_number);
        cvc = vi.findViewById(com.craftman.cardform.R.id.cvc);

        rBehavior = vii.findViewById(R.id.ratingBarBeh);
        rKnowledge = vii.findViewById(R.id.ratingBarKn);
        rAppearance = vii.findViewById(R.id.ratingBarAp);
        rConsistency = vii.findViewById(R.id.ratingBarCo);
        rBehaviorEG = vii.findViewById(R.id.ratingBarBehE);
        rEnvironmentG = vii.findViewById(R.id.ratingBarApE);
        rConsistencyEG = vii.findViewById(R.id.ratingBarCoE);
        vFRating = v.findViewById(R.id.vFRating);
        vFRatingd = vii.findViewById(R.id.vFRating);
        popupWindow = new PopupWindow(v , width.intValue(), height.intValue(), true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0xff61a635));
        popupWindow.setAnimationStyle(R.style.popup_window_animation);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(15);
        }
        spJob = v.findViewById(R.id.spJobPos);
        spPrice = v.findViewById(R.id.spPricePoint);
        dateTime = v.findViewById(R.id.date);
        spsort = v.findViewById(R.id.sortBy);
        back = v.findViewById(R.id.back);
        cardForm = vi.findViewById(R.id.cardform);
        job = 0;
        price = 0;
        sort = 2;
        tV = vi.findViewById(com.craftman.cardform.R.id.payment_amount);
        pay = vi.findViewById(com.craftman.cardform.R.id.btn_pay);
        user = ((List) getActivity()).getUser();
        pb = view.findViewById(R.id.pB2);
        pb.setVisibility(View.GONE);
        p.setVisibility(View.GONE);
        allJobs = new ArrayList<>();
        listAdapter = new CustomAdapterAllPubs(getContext(), allJobs);
        listView.setAdapter(listAdapter);
        listView.addFooterView(viii);
        spsort.setSelection(2);
        cl = Calendar.getInstance();
        p.setVisibility(View.VISIBLE);
        if (user!=null) {
            getAllJobs(false, false, false);
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.setContentView(v);
                popupWindow.showAtLocation(getView().getRootView(),Gravity.CENTER, 0, 0);
                View popupBlur = (View) popupWindow.getContentView().getRootView();
                WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
                WindowManager.LayoutParams p = (WindowManager.LayoutParams) popupBlur.getLayoutParams();
                p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                p.dimAmount = 0.35f;
                wm.updateViewLayout(popupBlur, p);
                dateTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        c = Calendar.getInstance();
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
                                        mDay = dayOfMonth;
                                        mMonth = monthOfYear;
                                        mYear = year;
                                        String str = mDay + "/" + (mMonth + 1) + "/" + mYear;
                                        cl = Calendar.getInstance();
                                        cl.set(mYear, mMonth, mDay);
                                        date = cl.getTime();
                                        dateTime.setText(str);

                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                    }
                });
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
            }
        });
        spJob.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                job = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spPrice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                price = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spsort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sort = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //GET FILTERS AND SORT
                if (popupWindow.getContentView().equals(v)) {
                    if (job > 0) {
                        filters[0] = true;
                    } else {
                        filters[0] = false;
                    }
                    if (price > 0) {
                        filters[1] = true;
                    } else {
                        filters[1] = false;
                    }
                    if (date != null) {
                        filters[2] = true;
                    } else {
                        filters[2] = false;
                    }
                    if (user != null) {
                        allJobs.clear();
                        listAdapter.notifyDataSetChanged();
                        getAllJobs(filters[0], filters[1], filters[2]);
                    }
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                    extra.setText(tempJob.getExtra());
                } else {
                    extra.setText("-");
                }
                vFRatingd.setDisplayedChild(1);
                tempJob.getPublisherId().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if (document.getLong("employeeRatingsNum")>0 || document.getLong("employerRatingsNum")>0){
                                vFRatingd.setDisplayedChild(0);
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
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                popupWindow.setContentView(vi);
                final JobAd tempJob = allJobs.get(i);
                tV.setText(0 + " €");
                pay.setText("Pay " + tV.getText());
                popupWindow.showAtLocation(getView().getRootView(),Gravity.CENTER, 0, 0);
                View popupBlur = (View) popupWindow.getContentView().getRootView();
                WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
                WindowManager.LayoutParams p = (WindowManager.LayoutParams) popupBlur.getLayoutParams();
                p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                p.dimAmount = 0.35f;
                wm.updateViewLayout(popupBlur, p);
                pay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (cardNumber.getText().toString().length()==19 && cvc.getText().toString().length()==3) {
                            popupWindow.dismiss();
                            final ArrayList<DocumentReference> intUsers;
                            if (tempJob.getInterestedIds() != null){
                                intUsers = tempJob.getInterestedIds();
                            }else{
                                intUsers = new ArrayList<>();
                            }
                            intUsers.add(FirebaseFirestore.getInstance().collection("users").document(user.getUid()));
                            (FirebaseFirestore.getInstance().collection("jobs").document(tempJob.getUid())).update("interestedIds", intUsers).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        ArrayList<DocumentReference> intDocs;
                                        if (user.getInterestedJobs() != null){
                                            intDocs = user.getInterestedJobs();
                                        }else{
                                            intDocs = new ArrayList<>();
                                        }
                                        intDocs.add(FirebaseFirestore.getInstance().collection("jobs").document(tempJob.getUid()));
                                        user.setInterestedJobs(intDocs);
                                        FirebaseFirestore.getInstance().collection("users").document(user.getUid()).update("interestedJobs", user.getInterestedJobs()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    tempJob.setInterestedIds(intUsers);
                                                    listAdapter.notifyDataSetChanged();
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }
                });
                return false;
            }
        });
    }

    private void getAllJobs(Boolean jobB, Boolean priceB, Boolean dateB){
        p.setVisibility(View.VISIBLE);
        vFNews.setDisplayedChild(0);
        allJobs.clear();
        listAdapter.notifyDataSetChanged();
        //switch 0 for pos
        //switch 1 for price
        //switch 2 for date
            filterPay = false;
            query = FirebaseFirestore.getInstance().collection("jobs");
            if (jobB && dateB && priceB) {
                wantsCal = true;
                query = query.whereEqualTo("type", job - 1);
                switch (sort) {
                    case 0:
                        wantsPrice = true;
                        break;
                    case 1:
                        wantsPrice = true;
                        query = query.orderBy("payment", Query.Direction.DESCENDING);
                        break;
                    case 2:
                        wantsPrice = true;
                        break;
                }
            } else if (jobB && dateB) {
                wantsCal = true;
                if (sort == 1) {
                    query = query.whereEqualTo("type", job - 1).orderBy("payment", Query.Direction.DESCENDING);
                } else {
                    query = query.whereEqualTo("type", job - 1);
                }
            } else if (dateB) {
                wantsCal = true;
                switch (sort) {
                    case 0:
                        query = query.orderBy("type");
                        if (priceB) {
                            filterPay = true;
                        }
                        break;
                    case 1:
                            query = query.orderBy("payment", Query.Direction.DESCENDING);
                            wantsPrice = true;
                        break;
                    case 2:
                        break;
                }
            } else if (jobB && priceB) {
                query = query.whereEqualTo("type", job - 1);
                switch (sort) {
                    case 0:
                        wantsPrice = true;
                        break;
                    case 1:
                        wantsPrice = true;
                        query = query.orderBy("payment", Query.Direction.DESCENDING);
                        break;
                    case 2:
                        filterPay = true;
                        query = query.orderBy("dateTime");
                        break;
                }
            } else if (jobB) {
                query = query.whereEqualTo("type", job - 1);
                switch (sort) {
                    case 0:
                        break;
                    case 1:
                        query = query.orderBy("payment", Query.Direction.DESCENDING);
                        break;
                    case 2:
                        query = query.orderBy("dateTime");
                        break;
                }
            } else {
                switch (sort) {
                    case 0:
                        query = query.orderBy("type");
                        break;
                    case 1:
                        query = query.orderBy("payment", Query.Direction.DESCENDING);
                        break;
                    case 2:
                        query = query.orderBy("onlyDate");
                        break;
                }
            }
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                        java.util.List<DocumentSnapshot> docs = task.getResult().getDocuments();
                        DocumentReference myDocRef = FirebaseFirestore.getInstance().collection("users").document(user.getUid());
                        if (filterPay) {
                            switch (price) {
                                case 1:
                                    for (int i = 0; i < docs.size(); i++) {
                                        DocumentSnapshot document = docs.get(i);
                                        if (document.exists() && (document.getDouble("payment") <= 20 && document.getBoolean("state"))) {
                                            createNewJobAd(document);
                                        }
                                    }
                                    break;
                                case 2:
                                    for (int i = 0; i < docs.size(); i++) {
                                        DocumentSnapshot document = docs.get(i);
                                        if (document.exists() && (document.getDouble("payment") <= 50 && document.getBoolean("state") && (document.getDouble("payment") > 20))) {
                                            createNewJobAd(document);
                                        }
                                    }
                                    break;
                                case 3:
                                    for (int i = 0; i < docs.size(); i++) {
                                        DocumentSnapshot document = docs.get(i);
                                        if (document.exists() && (document.getDouble("payment") <= 100 && document.getBoolean("state") && (document.getDouble("payment") > 50))) {
                                            createNewJobAd(document);
                                        }
                                    }
                                    break;
                                case 4:
                                    for (int i = 0; i < docs.size(); i++) {
                                        DocumentSnapshot document = docs.get(i);
                                        if (document.exists() && (document.getDouble("payment") <= 150 && (document.getDouble("payment") > 100 && document.getBoolean("state")))) {
                                            createNewJobAd(document);
                                        }
                                    }
                                    break;
                                case 5:
                                    for (int i = 0; i < docs.size(); i++) {
                                        DocumentSnapshot document = docs.get(i);
                                        if (document.exists() && document.getBoolean("state") && (document.getDouble("payment") > 150)) {
                                            createNewJobAd(document);

                                        }
                                    }
                                    break;
                            }
                        } else {
                            for (int i = 0; i < docs.size(); i++) {
                                DocumentSnapshot document = docs.get(i);
                                if (document.exists() && document.getBoolean("state")) {
                                    createNewJobAd(document);

                                }
                            }
                        }
                        p.setVisibility(View.GONE);
                    } else {
                        vFNews.setDisplayedChild(1);
                        p.setVisibility(View.GONE);
                    }
                }
            });
    }

    private void createNewJobAd (DocumentSnapshot document){
        ArrayList<DocumentReference> intUsersId = (ArrayList<DocumentReference>) document.get("interestedIds");
        if (!(document.get("publisherId").equals((FirebaseFirestore.getInstance().collection("users").document(user.getUid()))))){
            if (intUsersId != null) {
                if (intUsersId.contains(FirebaseFirestore.getInstance().collection("users").document(user.getUid()))) {
                } else {
                    if (wantsPrice && wantsCal) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(document.getDate("dateTime"));
                        if (cl.get(Calendar.YEAR) == cal.get(Calendar.YEAR) && cl.get(Calendar.MONTH) == cal.get(Calendar.MONTH) && cl.get(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH)) {
                            switch (price) {
                                case 1:
                                    if (document.getDouble("payment") <= 20 && document.getBoolean("state")) {
                                        allJobs.add(new JobAd(document.getDate("uploadDate"), document.getString("pubName"), document.getString("jobField"),
                                                document.getString("extra"), Integer.parseInt(document.getLong("type").toString()), document.getDate("dateTime"), document.getBoolean("state"),
                                                document.getString("region"), document.getDocumentReference("publisherId"), (ArrayList<DocumentReference>) document.get("interestedIds"),
                                                document.getDouble("payment"), document.getBoolean("employer"), document.getId()));
                                        listAdapter.notifyDataSetChanged();
                                    }
                                    break;
                                case 2:
                                    if (document.getDouble("payment") > 20 && document.getDouble("payment") <= 50 && document.getBoolean("state")) {
                                        allJobs.add(new JobAd(document.getDate("uploadDate"), document.getString("pubName"), document.getString("jobField"),
                                                document.getString("extra"), Integer.parseInt(document.getLong("type").toString()), document.getDate("dateTime"), document.getBoolean("state"),
                                                document.getString("region"), document.getDocumentReference("publisherId"), (ArrayList<DocumentReference>) document.get("interestedIds"),
                                                document.getDouble("payment"), document.getBoolean("employer"), document.getId()));
                                        listAdapter.notifyDataSetChanged();
                                    }
                                    break;
                                case 3:
                                    if (document.getDouble("payment") > 50 && document.getDouble("payment") <= 100 && document.getBoolean("state")) {
                                        allJobs.add(new JobAd(document.getDate("uploadDate"), document.getString("pubName"), document.getString("jobField"),
                                                document.getString("extra"), Integer.parseInt(document.getLong("type").toString()), document.getDate("dateTime"), document.getBoolean("state"),
                                                document.getString("region"), document.getDocumentReference("publisherId"), (ArrayList<DocumentReference>) document.get("interestedIds"),
                                                document.getDouble("payment"), document.getBoolean("employer"), document.getId()));
                                        listAdapter.notifyDataSetChanged();
                                    }
                                    break;
                                case 4:
                                    if (document.getDouble("payment") > 100 && document.getDouble("payment") <= 150 && document.getBoolean("state")) {
                                        allJobs.add(new JobAd(document.getDate("uploadDate"), document.getString("pubName"), document.getString("jobField"),
                                                document.getString("extra"), Integer.parseInt(document.getLong("type").toString()), document.getDate("dateTime"), document.getBoolean("state"),
                                                document.getString("region"), document.getDocumentReference("publisherId"), (ArrayList<DocumentReference>) document.get("interestedIds"),
                                                document.getDouble("payment"), document.getBoolean("employer"), document.getId()));
                                        listAdapter.notifyDataSetChanged();
                                    }
                                    break;
                                case 5:
                                    if (document.getDouble("payment") > 150 && document.getBoolean("state")) {
                                        allJobs.add(new JobAd(document.getDate("uploadDate"), document.getString("pubName"), document.getString("jobField"),
                                                document.getString("extra"), Integer.parseInt(document.getLong("type").toString()), document.getDate("dateTime"), document.getBoolean("state"),
                                                document.getString("region"), document.getDocumentReference("publisherId"), (ArrayList<DocumentReference>) document.get("interestedIds"),
                                                document.getDouble("payment"), document.getBoolean("employer"), document.getId()));
                                        listAdapter.notifyDataSetChanged();
                                    }
                                    break;
                            }
                        }
                    } else if (wantsPrice) {
                        switch (price) {
                            case 1:
                                if (document.getDouble("payment") <= 20 && document.getBoolean("state")) {
                                    allJobs.add(new JobAd(document.getDate("uploadDate"), document.getString("pubName"), document.getString("jobField"),
                                            document.getString("extra"), Integer.parseInt(document.getLong("type").toString()), document.getDate("dateTime"), document.getBoolean("state"),
                                            document.getString("region"), document.getDocumentReference("publisherId"), (ArrayList<DocumentReference>) document.get("interestedIds"),
                                            document.getDouble("payment"), document.getBoolean("employer"), document.getId()));
                                    listAdapter.notifyDataSetChanged();
                                }
                                break;
                            case 2:
                                if (document.getDouble("payment") > 20 && document.getDouble("payment") <= 50 && document.getBoolean("state")) {
                                    allJobs.add(new JobAd(document.getDate("uploadDate"), document.getString("pubName"), document.getString("jobField"),
                                            document.getString("extra"), Integer.parseInt(document.getLong("type").toString()), document.getDate("dateTime"), document.getBoolean("state"),
                                            document.getString("region"), document.getDocumentReference("publisherId"), (ArrayList<DocumentReference>) document.get("interestedIds"),
                                            document.getDouble("payment"), document.getBoolean("employer"), document.getId()));
                                    listAdapter.notifyDataSetChanged();
                                }
                                break;
                            case 3:
                                if (document.getDouble("payment") > 50 && document.getDouble("payment") <= 100 && document.getBoolean("state")) {
                                    allJobs.add(new JobAd(document.getDate("uploadDate"), document.getString("pubName"), document.getString("jobField"),
                                            document.getString("extra"), Integer.parseInt(document.getLong("type").toString()), document.getDate("dateTime"), document.getBoolean("state"),
                                            document.getString("region"), document.getDocumentReference("publisherId"), (ArrayList<DocumentReference>) document.get("interestedIds"),
                                            document.getDouble("payment"), document.getBoolean("employer"), document.getId()));
                                    listAdapter.notifyDataSetChanged();
                                }
                                break;
                            case 4:
                                if (document.getDouble("payment") > 100 && document.getDouble("payment") <= 150 && document.getBoolean("state")) {
                                    allJobs.add(new JobAd(document.getDate("uploadDate"), document.getString("pubName"), document.getString("jobField"),
                                            document.getString("extra"), Integer.parseInt(document.getLong("type").toString()), document.getDate("dateTime"), document.getBoolean("state"),
                                            document.getString("region"), document.getDocumentReference("publisherId"), (ArrayList<DocumentReference>) document.get("interestedIds"),
                                            document.getDouble("payment"), document.getBoolean("employer"), document.getId()));
                                    listAdapter.notifyDataSetChanged();
                                }
                                break;
                            case 5:
                                if (document.getDouble("payment") > 150 && document.getBoolean("state")) {
                                    allJobs.add(new JobAd(document.getDate("uploadDate"), document.getString("pubName"), document.getString("jobField"),
                                            document.getString("extra"), Integer.parseInt(document.getLong("type").toString()), document.getDate("dateTime"), document.getBoolean("state"),
                                            document.getString("region"), document.getDocumentReference("publisherId"), (ArrayList<DocumentReference>) document.get("interestedIds"),
                                            document.getDouble("payment"), document.getBoolean("employer"), document.getId()));
                                    listAdapter.notifyDataSetChanged();
                                }
                                break;
                        }
                    } else if (wantsCal) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(document.getDate("dateTime"));
                        if (cl.get(Calendar.YEAR) == cal.get(Calendar.YEAR) && cl.get(Calendar.MONTH) == cal.get(Calendar.MONTH) && cl.get(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH)) {
                            allJobs.add(new JobAd(document.getDate("uploadDate"), document.getString("pubName"), document.getString("jobField"),
                                    document.getString("extra"), Integer.parseInt(document.getLong("type").toString()), document.getDate("dateTime"), document.getBoolean("state"),
                                    document.getString("region"), document.getDocumentReference("publisherId"), (ArrayList<DocumentReference>) document.get("interestedIds"),
                                    document.getDouble("payment"), document.getBoolean("employer"), document.getId()));
                            listAdapter.notifyDataSetChanged();
                        }
                    } else {
                        allJobs.add(new JobAd(document.getDate("uploadDate"), document.getString("pubName"), document.getString("jobField"),
                                document.getString("extra"), Integer.parseInt(document.getLong("type").toString()), document.getDate("dateTime"), document.getBoolean("state"),
                                document.getString("region"), document.getDocumentReference("publisherId"), (ArrayList<DocumentReference>) document.get("interestedIds"),
                                document.getDouble("payment"), document.getBoolean("employer"), document.getId()));
                        listAdapter.notifyDataSetChanged();
                    }
                }
            } else {
                if (wantsPrice && wantsCal) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(document.getDate("dateTime"));
                    if (cl.get(Calendar.YEAR) == cal.get(Calendar.YEAR) && cl.get(Calendar.MONTH) == cal.get(Calendar.MONTH) && cl.get(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH)) {
                        switch (price) {
                            case 1:
                                if (document.getDouble("payment") <= 20 && document.getBoolean("state")) {
                                    allJobs.add(new JobAd(document.getDate("uploadDate"), document.getString("pubName"), document.getString("jobField"),
                                            document.getString("extra"), Integer.parseInt(document.getLong("type").toString()), document.getDate("dateTime"), document.getBoolean("state"),
                                            document.getString("region"), document.getDocumentReference("publisherId"), (ArrayList<DocumentReference>) document.get("interestedIds"),
                                            document.getDouble("payment"), document.getBoolean("employer"), document.getId()));
                                    listAdapter.notifyDataSetChanged();
                                }
                                break;
                            case 2:
                                if (document.getDouble("payment") > 20 && document.getDouble("payment") <= 50 && document.getBoolean("state")) {
                                    allJobs.add(new JobAd(document.getDate("uploadDate"), document.getString("pubName"), document.getString("jobField"),
                                            document.getString("extra"), Integer.parseInt(document.getLong("type").toString()), document.getDate("dateTime"), document.getBoolean("state"),
                                            document.getString("region"), document.getDocumentReference("publisherId"), (ArrayList<DocumentReference>) document.get("interestedIds"),
                                            document.getDouble("payment"), document.getBoolean("employer"), document.getId()));
                                    listAdapter.notifyDataSetChanged();
                                }
                                break;
                            case 3:
                                if (document.getDouble("payment") > 50 && document.getDouble("payment") <= 100 && document.getBoolean("state")) {
                                    allJobs.add(new JobAd(document.getDate("uploadDate"), document.getString("pubName"), document.getString("jobField"),
                                            document.getString("extra"), Integer.parseInt(document.getLong("type").toString()), document.getDate("dateTime"), document.getBoolean("state"),
                                            document.getString("region"), document.getDocumentReference("publisherId"), (ArrayList<DocumentReference>) document.get("interestedIds"),
                                            document.getDouble("payment"), document.getBoolean("employer"), document.getId()));
                                    listAdapter.notifyDataSetChanged();
                                }
                                break;
                            case 4:
                                if (document.getDouble("payment") > 100 && document.getDouble("payment") <= 150 && document.getBoolean("state")) {
                                    allJobs.add(new JobAd(document.getDate("uploadDate"), document.getString("pubName"), document.getString("jobField"),
                                            document.getString("extra"), Integer.parseInt(document.getLong("type").toString()), document.getDate("dateTime"), document.getBoolean("state"),
                                            document.getString("region"), document.getDocumentReference("publisherId"), (ArrayList<DocumentReference>) document.get("interestedIds"),
                                            document.getDouble("payment"), document.getBoolean("employer"), document.getId()));
                                    listAdapter.notifyDataSetChanged();
                                }
                                break;
                            case 5:
                                if (document.getDouble("payment") > 150 && document.getBoolean("state")) {
                                    allJobs.add(new JobAd(document.getDate("uploadDate"), document.getString("pubName"), document.getString("jobField"),
                                            document.getString("extra"), Integer.parseInt(document.getLong("type").toString()), document.getDate("dateTime"), document.getBoolean("state"),
                                            document.getString("region"), document.getDocumentReference("publisherId"), (ArrayList<DocumentReference>) document.get("interestedIds"),
                                            document.getDouble("payment"), document.getBoolean("employer"), document.getId()));
                                    listAdapter.notifyDataSetChanged();
                                }
                                break;
                        }
                    }
                } else if (wantsPrice) {
                    switch (price) {
                        case 1:
                            if (document.getDouble("payment") <= 20 && document.getBoolean("state")) {
                                allJobs.add(new JobAd(document.getDate("uploadDate"), document.getString("pubName"), document.getString("jobField"),
                                        document.getString("extra"), Integer.parseInt(document.getLong("type").toString()), document.getDate("dateTime"), document.getBoolean("state"),
                                        document.getString("region"), document.getDocumentReference("publisherId"), (ArrayList<DocumentReference>) document.get("interestedIds"),
                                        document.getDouble("payment"), document.getBoolean("employer"), document.getId()));
                                listAdapter.notifyDataSetChanged();
                            }
                            break;
                        case 2:
                            if (document.getDouble("payment") > 20 && document.getDouble("payment") <= 50 && document.getBoolean("state")) {
                                allJobs.add(new JobAd(document.getDate("uploadDate"), document.getString("pubName"), document.getString("jobField"),
                                        document.getString("extra"), Integer.parseInt(document.getLong("type").toString()), document.getDate("dateTime"), document.getBoolean("state"),
                                        document.getString("region"), document.getDocumentReference("publisherId"), (ArrayList<DocumentReference>) document.get("interestedIds"),
                                        document.getDouble("payment"), document.getBoolean("employer"), document.getId()));
                                listAdapter.notifyDataSetChanged();
                            }
                            break;
                        case 3:
                            if (document.getDouble("payment") > 50 && document.getDouble("payment") <= 100 && document.getBoolean("state")) {
                                allJobs.add(new JobAd(document.getDate("uploadDate"), document.getString("pubName"), document.getString("jobField"),
                                        document.getString("extra"), Integer.parseInt(document.getLong("type").toString()), document.getDate("dateTime"), document.getBoolean("state"),
                                        document.getString("region"), document.getDocumentReference("publisherId"), (ArrayList<DocumentReference>) document.get("interestedIds"),
                                        document.getDouble("payment"), document.getBoolean("employer"), document.getId()));
                                listAdapter.notifyDataSetChanged();
                            }
                            break;
                        case 4:
                            if (document.getDouble("payment") > 100 && document.getDouble("payment") <= 150 && document.getBoolean("state")) {
                                allJobs.add(new JobAd(document.getDate("uploadDate"), document.getString("pubName"), document.getString("jobField"),
                                        document.getString("extra"), Integer.parseInt(document.getLong("type").toString()), document.getDate("dateTime"), document.getBoolean("state"),
                                        document.getString("region"), document.getDocumentReference("publisherId"), (ArrayList<DocumentReference>) document.get("interestedIds"),
                                        document.getDouble("payment"), document.getBoolean("employer"), document.getId()));
                                listAdapter.notifyDataSetChanged();
                            }
                            break;
                        case 5:
                            if (document.getDouble("payment") > 150 && document.getBoolean("state")) {
                                allJobs.add(new JobAd(document.getDate("uploadDate"), document.getString("pubName"), document.getString("jobField"),
                                        document.getString("extra"), Integer.parseInt(document.getLong("type").toString()), document.getDate("dateTime"), document.getBoolean("state"),
                                        document.getString("region"), document.getDocumentReference("publisherId"), (ArrayList<DocumentReference>) document.get("interestedIds"),
                                        document.getDouble("payment"), document.getBoolean("employer"), document.getId()));
                                listAdapter.notifyDataSetChanged();
                            }
                            break;
                    }
                } else if (wantsCal) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(document.getDate("dateTime"));
                    if (cl.get(Calendar.YEAR) == cal.get(Calendar.YEAR) && cl.get(Calendar.MONTH) == cal.get(Calendar.MONTH) && cl.get(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH)) {
                        allJobs.add(new JobAd(document.getDate("uploadDate"), document.getString("pubName"), document.getString("jobField"),
                                document.getString("extra"), Integer.parseInt(document.getLong("type").toString()), document.getDate("dateTime"), document.getBoolean("state"),
                                document.getString("region"), document.getDocumentReference("publisherId"), (ArrayList<DocumentReference>) document.get("interestedIds"),
                                document.getDouble("payment"), document.getBoolean("employer"), document.getId()));
                        listAdapter.notifyDataSetChanged();
                    }
                } else {
                    allJobs.add(new JobAd(document.getDate("uploadDate"), document.getString("pubName"), document.getString("jobField"),
                            document.getString("extra"), Integer.parseInt(document.getLong("type").toString()), document.getDate("dateTime"), document.getBoolean("state"),
                            document.getString("region"), document.getDocumentReference("publisherId"), (ArrayList<DocumentReference>) document.get("interestedIds"),
                            document.getDouble("payment"), document.getBoolean("employer"), document.getId()));
                    listAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}
