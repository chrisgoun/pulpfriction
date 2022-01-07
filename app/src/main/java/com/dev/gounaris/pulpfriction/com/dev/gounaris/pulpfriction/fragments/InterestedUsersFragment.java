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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.dev.gounaris.pulpfriction.List;
import com.dev.gounaris.pulpfriction.R;
import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.customListViewAdapters.CustomAdapterAllPubs;
import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.customListViewAdapters.CustomAdapterMyPubs;
import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.customListViewAdapters.CustomAdapterUsers;
import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.objects.JobAd;
import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

public class InterestedUsersFragment extends Fragment {

    private View view;
    private ListView listView;
    private FirebaseFirestore db;
    private ArrayAdapter listAdapter, listUAdapter;
    private ArrayList<User> allUsers;
    private Long red;
    private Long targetPubSize;
    private JobAd job;
    private ArrayList<DocumentReference> allIntUsers;
    private ViewFlipper vf;
    private int listSize;
    private int counter;
    private ProgressBar pb;
    private View footer;
    private View v;
    private Boolean flag;
    private DisplayMetrics dm;
    private PopupWindow popupWindow;
    private FirebaseStorage storage;
    private Button back;
    private Button book;
    private ImageView profPic;
    private TextView name;
    private TextView age;
    private TextView experience;
    private ViewFlipper vFprevJobs;
    private ListView prevJobsListView;
    private ViewFlipper vFRating;
    private TextView rBehaviorTV, rKnowledgeTV, rAppearanceTV, rConsistencyTV, rBehaviorETV, rEnvironmentTV, rConsistencyETV;
    private TextView extra;
    private ArrayList<DocumentReference> allMyJobs;
    private ArrayList<JobAd> allJobs;
    private int counterU;
    private RatingBar rBehavior, rKnowledge, rAppearance, rConsistency, rBehaviorE, rEnvironment, rConsistencyE;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_interested_users, container, false);

        footer = LayoutInflater.from(getContext()).inflate(R.layout.footer, null);
        dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        Double height = dm.heightPixels*0.8;
        Double width = dm.widthPixels*0.9;
        v = LayoutInflater.from(getContext()).inflate(R.layout.users_about_layout,null,false);
        popupWindow = new PopupWindow(v , width.intValue(), height.intValue(), true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0xff61a635));
        popupWindow.setAnimationStyle(R.style.popup_window_animation);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(15);
        }

        back = v.findViewById(R.id.back);
        book = v.findViewById(R.id.book);
        profPic = v.findViewById(R.id.profPic);
        name = v.findViewById(R.id.name);
        age = v.findViewById(R.id.age);
        experience = v.findViewById(R.id.experience);
        vFprevJobs = v.findViewById(R.id.vFprevJobs);
        prevJobsListView = v.findViewById(R.id.prevJobs);
        vFRating = v.findViewById(R.id.vFRating);

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
        rBehaviorE = v.findViewById(R.id.ratingBarBehE);
        rEnvironment = v.findViewById(R.id.ratingBarApE);
        rConsistencyE = v.findViewById(R.id.ratingBarCoE);

        extra = v.findViewById(R.id.extra);

        pb = view.findViewById(R.id.pB2);
        pb.setVisibility(View.GONE);
        allUsers = new ArrayList<>();
        targetPubSize = (long) 10;
        red = (long) 1;
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        listView = view.findViewById(R.id.intUsersList);
        vf = view.findViewById(R.id.vFIntUsers);
        job = ((List) getActivity()).getJob();
        allIntUsers = job.getInterestedIds();
        if (allIntUsers == null || allIntUsers.size() <= 0) {
            vf.setDisplayedChild(1);
        } else {
            listSize = allIntUsers.size();
            vf.setDisplayedChild(0);
            pb.setVisibility(View.VISIBLE);
            if (listSize < 9) {
                targetPubSize = (long) listSize;
            }
            listAdapter = new CustomAdapterUsers(getContext(), allUsers);
            listView.setAdapter(listAdapter);
            listView.addFooterView(footer);
            counter = 0;
            getUsers();
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listSize - red < 0) {
                    Toast.makeText(getActivity(), "No More Interested Users Found", Toast.LENGTH_SHORT).show();
                } else if (listSize - red < 10) {
                    if (counter == targetPubSize) {
                        targetPubSize = (long) allIntUsers.size() - (red - 1);
                        counter = 0;
                        pb.setVisibility(View.VISIBLE);
                        getUsers();
                    }
                } else {
                    if (counter == targetPubSize) {
                        targetPubSize = (long) 10;
                        counter = 0;
                        pb.setVisibility(View.VISIBLE);
                        getUsers();
                    }
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final User tempUser = allUsers.get(i);
                Picasso.with(getActivity()).load(tempUser.getProfilePicUrl()).fit().placeholder(R.mipmap.ic_launcher_round).centerCrop().into(profPic);
                name.setText(tempUser.getName() + " " + tempUser.getLastName());
                Calendar cl = Calendar.getInstance();
                Calendar c = Calendar.getInstance();
                cl.set(Integer.valueOf(tempUser.getbYear().toString()), Integer.valueOf(tempUser.getbMonth().toString()), Integer.valueOf(tempUser.getbDay().toString()));
                Integer ageNum = c.get(Calendar.YEAR) - cl.get(Calendar.YEAR);
                if (cl.get(Calendar.DAY_OF_YEAR)<c.get(Calendar.DAY_OF_YEAR)){
                    ageNum--;
                }
                age.setText(ageNum.toString());
                experience.setText(tempUser.getExperience());
                if (tempUser.getRatingsNum()>0 || tempUser.getRatingsNumE()>0) {
                    vFRating.setDisplayedChild(0);
                    rBehavior.setRating(Float.valueOf(tempUser.getrBehavior().toString()));
                    rKnowledge.setRating(Float.valueOf(tempUser.getrKnowledge().toString()));
                    rAppearance.setRating(Float.valueOf(tempUser.getrAppearance().toString()));
                    rConsistency.setRating(Float.valueOf(tempUser.getrConsistency().toString()));
                    rBehaviorE.setRating(Float.valueOf(tempUser.getrBehaviorE().toString()));
                    rEnvironment.setRating(Float.valueOf(tempUser.getrEnvironment().toString()));
                    rConsistencyE.setRating(Float.valueOf(tempUser.getrConsistencyE().toString()));

                    rBehaviorTV.setText("(" + tempUser.getRatingsNum() + ")");
                    rKnowledgeTV.setText("(" + tempUser.getRatingsNum() + ")");
                    rAppearanceTV.setText("(" + tempUser.getRatingsNum() + ")");
                    rConsistencyTV.setText("(" + tempUser.getRatingsNum() + ")");
                    rBehaviorETV.setText("(" + tempUser.getRatingsNumE() + ")");
                    rEnvironmentTV.setText("(" + tempUser.getRatingsNumE() + ")");
                    rConsistencyETV.setText("(" + tempUser.getRatingsNumE() + ")");
                }else{
                    vFRating.setDisplayedChild(1);
                }
                if (tempUser.getExtra()!=null && !tempUser.getExtra().equals("")){
                    extra.setText(tempUser.getExtra());
                }
                if (tempUser.getPastJobs() != null && tempUser.getPastJobs().size()>0){
                    vFprevJobs.setDisplayedChild(0);
                    pb.setVisibility(View.VISIBLE);
                    allMyJobs = tempUser.getPastJobs();
                    counterU = allMyJobs.size()-1;
                    allJobs = new ArrayList<>();
                    listUAdapter = new CustomAdapterAllPubs(getContext(), allJobs);
                    prevJobsListView.setAdapter(listUAdapter);
                    getJobs();
                }else{
                    vFprevJobs.setDisplayedChild(1);
                }
                popupWindow.showAtLocation(getView().getRootView(),Gravity.CENTER, 0, 0);
                View popupBlur = (View) popupWindow.getContentView().getRootView();
                WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
                WindowManager.LayoutParams p = (WindowManager.LayoutParams) popupBlur.getLayoutParams();
                p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                p.dimAmount = 0.3f;
                wm.updateViewLayout(popupBlur, p);
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
                book.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((List) getActivity()).uploadSelectedUser(tempUser);
                        popupWindow.dismiss();
                    }
                });
            }
        });

    }

    private void getUsers(){
        if (counter == targetPubSize || listSize - (counter + red) < 0) {
            red += counter;

            pb.setVisibility(View.GONE);
            return;
        } else {
            allIntUsers.get((int) (listSize - (counter + red))).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    counter++;
                    getUsers();
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            allUsers.add(new User(document.getLong("amka"), document.getBoolean("employer"), document.getString("job"), document.getString("name"),
                                    document.getString("lastName"), document.getString("companyName"), document.getLong("companyAfm"), document.getString("companyJob"),
                                    document.getBoolean("male"), document.getLong("bDay"), document.getLong("bMonth"), document.getLong("bYear"), "",
                                    (ArrayList<DocumentReference>) document.get("interestedJobs"), (ArrayList<DocumentReference>) document.get("myJobs"), (ArrayList<DocumentReference>) document.get("transactions"),
                                    document.getId(), document.getString("profPic"), document.getDouble("knowledgeRating"), document.getLong("employeeRatingsNum"), storage.getReference().child(document.getString("profPicRef")),
                                    (ArrayList<DocumentReference>) document.get("pastJobs"), document.getString("extra"), document.getString("experience"), document.getDouble("behaviorRating"), document.getDouble("appearanceRating"),
                                    document.getDouble("ConsistencyRating"), document.getDouble("behaviorERating"), document.getDouble("workFieldRating"), document.getDouble("efficiencyRating"), document.getLong("employerRatingsNum")));
                            listAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }
    public void getJobs() {
        if (counterU < 0) {
            listUAdapter.notifyDataSetChanged();
            pb.setVisibility(View.GONE);
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
                                    document.getString("extra"), Integer.parseInt(document.getLong("type").toString()), document.getDate("dateTime"), document.getBoolean("state"),
                                    document.getString("region"), document.getDocumentReference("publisherId"), (ArrayList<DocumentReference>) document.get("interestedIds"),
                                    document.getDouble("payment"), document.getBoolean("employer")));
                        }
                    }
                }
            });
        }
    }

}
