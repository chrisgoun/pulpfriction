package com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.dev.gounaris.pulpfriction.List;
import com.dev.gounaris.pulpfriction.MainActivity;
import com.dev.gounaris.pulpfriction.R;
import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.customListViewAdapters.CustomAdapterMyPubs;
import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.objects.JobAd;
import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyPublicationsFragment extends Fragment {

    private View view;

    private ListView listView;
    private FirebaseFirestore db;
    private ArrayAdapter listAdapter;
    private ArrayList<JobAd> allJobs;
    private Long red;
    private Long targetPubSize;
    private User user;
    private ArrayList<DocumentReference> allMyJobs;
    private ViewFlipper vf;
    private int listSize;
    private int counter;
    private ProgressBar pb;
    private View footer;
    private View v;
    private TextView jobPos;
    private Date dateTime;
    private Boolean flag;
    private Calendar cl,c;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private int Year, Month, Day, Hour, Minute;
    private DisplayMetrics dm;
    private PopupWindow popupWindow;
    String[] jobPoss;

    private TextView payment;
    private TextView extra;

    private Button intUsers;
    private Button delete;
    private Button back;
    private TextView date;
    private TextView hour;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_publications, container, false);
        footer = LayoutInflater.from(getContext()).inflate(R.layout.footer, null);
        dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        Double height = dm.heightPixels*0.8;
        Double width = dm.widthPixels*0.9;
        v = LayoutInflater.from(getContext()).inflate(R.layout.my_pubs_about_layout,null,false);
        popupWindow = new PopupWindow(v , width.intValue(), height.intValue(), true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0xff61a635));
        popupWindow.setAnimationStyle(R.style.popup_window_animation);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(15);
        }
        jobPoss = getResources().getStringArray(R.array.jobPos);
        jobPos = v.findViewById(R.id.vFJobPosTV);

        payment = v.findViewById(R.id.vFPaymentTV);

        extra = v.findViewById(R.id.vFExtraTV);

        intUsers = v.findViewById(R.id.intUsers);
        delete = v.findViewById(R.id.delete);
        back = v.findViewById(R.id.back);

        date = v.findViewById(R.id.Date);
        hour = v.findViewById(R.id.Hour);

        pb = view.findViewById(R.id.pB2);
        pb.setVisibility(View.GONE);
        allJobs = new ArrayList<>();
        targetPubSize = (long) 10;
        red = (long) 1;
        db = FirebaseFirestore.getInstance();
        listView = view.findViewById(R.id.myPubsList);
        vf = view.findViewById(R.id.vFMyPubs);
        user = ((List) getActivity()).getUser();
        allMyJobs = user.getMyJobs();
        if (allMyJobs == null || allMyJobs.size() <= 0) {
            vf.setDisplayedChild(1);
        } else {
            listSize = allMyJobs.size();
            vf.setDisplayedChild(0);
            pb.setVisibility(View.VISIBLE);
            if (listSize < 9) {
                targetPubSize = (long) listSize;
            }
            listAdapter = new CustomAdapterMyPubs(getContext(), allJobs);
            listView.setAdapter(listAdapter);
            listView.addFooterView(footer);
            counter = 0;
            getJobs();
        }
        cl = Calendar.getInstance();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        db = FirebaseFirestore.getInstance();
        db.collection("users").document(user.getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if(e != null && documentSnapshot != null && documentSnapshot.exists() && !documentSnapshot.get("myJobs").equals(allMyJobs)){
                    user.setMyJobs((ArrayList<DocumentReference>) documentSnapshot.get("myJobs"));
                    allMyJobs = user.getMyJobs();
                    listSize = allMyJobs.size();
                    if (listSize <= 0) {
                        vf.setDisplayedChild(1);
                    } else {
                        vf.setDisplayedChild(0);
                        pb.setVisibility(View.VISIBLE);
                        if (listSize < 9) {
                            targetPubSize = (long) listSize;
                        }
                        counter = 0;
                        getJobs();
                    }
                }
            }
        });
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
            public void onItemClick(AdapterView<?> adapterView, View view1, final int i, long l) {
                final JobAd tempJob = allJobs.get(i);
                jobPos.setText(jobPoss[tempJob.getType()]);
                flag = true;
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
                if (!tempJob.getState()) {
                    intUsers.setText(R.string.intUsersBut);
                }else{
                    intUsers.setText(R.string.interested_users);
                }
                dateTime = tempJob.getDateTime();
                cl.setTime(dateTime);
                mYear = cl.get(Calendar.YEAR);
                mMonth = cl.get(Calendar.MONTH);
                mDay = cl.get(Calendar.DAY_OF_MONTH);
                mHour = cl.get(Calendar.HOUR_OF_DAY);
                mMinute = cl.get(Calendar.MINUTE);
                date.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);
                hour.setText(mHour + ":" + mMinute);
                TextView tV20 = v.findViewById(R.id.tV20);
                TextView verCode = v.findViewById(R.id.verCode);
                if (!tempJob.getState()){
                    tV20.setVisibility(View.VISIBLE);
                    verCode.setVisibility(View.VISIBLE);
                    verCode.setText(tempJob.getUid().substring(0,6));
                }else{
                    tV20.setVisibility(View.GONE);
                    verCode.setVisibility(View.GONE);
                }
                popupWindow.showAtLocation(getView().getRootView(),Gravity.CENTER, 0, 0);
                View popupBlur = (View) popupWindow.getContentView().getRootView();
                WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
                WindowManager.LayoutParams p = (WindowManager.LayoutParams) popupBlur.getLayoutParams();
                p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                p.dimAmount = 0.35f;
                wm.updateViewLayout(popupBlur, p);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (flag) {
                            flag = false;
                            if (tempJob.getState()){
                                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                                alertDialog.setTitle("Delete");
                                alertDialog.setMessage("Delete this publication permanently?");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Confirm",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                allMyJobs.get(i).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            allMyJobs.remove(i);
                                                            user.setMyJobs(allMyJobs);
                                                            db = FirebaseFirestore.getInstance();
                                                            db.collection("users").document(user.getUid()).update("myJobs", allMyJobs);
                                                            Toast.makeText(getActivity(), "Publication Deleted Successfully", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(getActivity(), "Failed To Delete Publication", Toast.LENGTH_SHORT).show();
                                                        }
                                                        flag = true;
                                                        popupWindow.dismiss();
                                                    }
                                                });
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                flag = true;
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
                            }else{
                                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                                alertDialog.setTitle("Delete");
                                alertDialog.setMessage("Delete this publication permanently? Charges may apply");
                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Confirm",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                allMyJobs.get(i).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            allMyJobs.remove(i);
                                                            user.setMyJobs(allMyJobs);
                                                            db = FirebaseFirestore.getInstance();
                                                            db.collection("users").document(user.getUid()).update("myJobs", allMyJobs);
                                                            Toast.makeText(getActivity(), "Publication Deleted Successfully", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(getActivity(), "Failed To Delete Publication", Toast.LENGTH_SHORT).show();
                                                        }
                                                        flag = true;
                                                        popupWindow.dismiss();
                                                    }
                                                });
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                flag = true;
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
                            }
                        }
                    }
                });
                if (tempJob.getState()) {
                    intUsers.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ((List) getActivity()).setJob(tempJob);
                            popupWindow.dismiss();
                            ((List) getActivity()).setIndex(11);
                            getFragmentManager().beginTransaction().replace(R.id.fragment_cont, new InterestedUsersFragment()).commit();
                        }
                    });
                }
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
            }
        });
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
                    counter++;
                    getJobs();
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            allJobs.add(new JobAd(document.getDate("uploadDate"), document.getString("pubName"), document.getString("jobField"),
                                    document.getString("extra"), Integer.parseInt(document.getLong("type").toString()), document.getDate("dateTime"), document.getBoolean("state"),
                                    document.getString("region"), document.getDocumentReference("publisherId"), (ArrayList<DocumentReference>) document.get("interestedIds"),
                                    document.getDouble("payment"), document.getBoolean("employer"), document.getId()));
                        }
                    }
                }
            });
        }
    }

}
