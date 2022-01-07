package com.dev.gounaris.pulpfriction;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.media.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.fragments.AboutUsFragment;
import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.fragments.CalendarFragment;
import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.fragments.HomeFragment;
import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.fragments.NotificationsFragment;
import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.fragments.PaymentsFragment;
import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.fragments.ProfileFragment;
import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.fragments.PublicationsFragment;
import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.fragments.PublicationsfragmentFragment;
import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.fragments.SettingsFragment;
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
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;


public class List extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private User user;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private TextView name;
    private TextView email;
    private Integer index;
    private NavigationView nView;
    private String nameTx;
    private String emailTx;
    private FirebaseFirestore db;
    private DocumentReference docRef;
    private SharedPreferences sharedPreferences;
    private String uid;
    private FirebaseStorage storage;
    private ImageView profPic;
    private JobAd job;
    private PopupWindow popupWindow;
    private View v;
    private DisplayMetrics dm;
    private RatingBar rBehavior, rAppearance, rConsistency, rKnowledge;
    private ViewFlipper rVF;
    private Button rate;
    private Button back;
    private Double rBehaviorD, rAppearanceD, rConsistencyD, rKnowledgeD;
    android.support.v4.app.NotificationCompat.Builder notification;
    private static final int uniqueID = 68746;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String menuFragment = getIntent().getStringExtra("menuFragment");
        if (menuFragment != null) {
            // Here we can decide what do to -- perhaps load other parameters from the intent extras such as IDs, etc

        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.main_listpage);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        nView = findViewById(R.id.nav_view);
        nView.setNavigationItemSelectedListener(this);
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();
        name = nView.getHeaderView(0).findViewById(R.id.profName);
        email = nView.getHeaderView(0).findViewById(R.id.profEmail);
        profPic = nView.getHeaderView(0).findViewById(R.id.iVProfilePhoto);
        uid = String.valueOf(mAuth.getCurrentUser().getUid());
        docRef = db.collection("users").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        user = new User(document.getLong("amka"), document.getBoolean("employer"), document.getString("job"), document.getString("name"),
                                document.getString("lastName"), document.getString("companyName"), document.getLong("companyAfm"), document.getString("companyJob"),
                                document.getBoolean("male"), document.getLong("bDay"), document.getLong("bMonth"), document.getLong("bYear"), mAuth.getCurrentUser().getEmail(),
                                (ArrayList<DocumentReference>) document.get("interestedJobs"), (ArrayList<DocumentReference>) document.get("myJobs"), (ArrayList<DocumentReference>) document.get("transactions"),
                                uid, document.getString("profPic"), document.getDouble("knowledgeRating"), document.getLong("employeeRatingsNum"), storage.getReference().child(document.getString("profPicRef")),
                                (ArrayList<DocumentReference>) document.get("pastJobs"), document.getString("extra"), document.getString("experience"), document.getDouble("behaviorRating"), document.getDouble("appearanceRating"),
                                document.getDouble("ConsistencyRating"), document.getDouble("behaviorERating"), document.getDouble("workFieldRating"), document.getDouble("efficiencyRating"), document.getLong("employerRatingsNum"));
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new HomeFragment()).commit();
                        nameTx = user.getName() + " " + user.getLastName();
                        emailTx = user.getEmail();
                        name.setText(nameTx);
                        email.setText(emailTx);
                        Picasso.with(getApplicationContext()).load(user.getProfilePicUrl()).fit().placeholder(R.mipmap.ic_launcher_round).centerCrop().into(profPic);
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        nView.setCheckedItem(R.id.nav_home);
        index = 1;
        notification = new android.support.v4.app.NotificationCompat.Builder(this);
        notification.setAutoCancel(true);
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Double height = 1300.0;
        Double width = dm.widthPixels*0.9;
        v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.rating_popup,null,false);
        popupWindow = new PopupWindow(v , width.intValue(), height.intValue(), true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0xff61a635));
        popupWindow.setAnimationStyle(R.style.popup_window_animation);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(15);
        }
        rVF = v.findViewById(R.id.rVF);
        rBehavior = v.findViewById(R.id.ratingBarBeh);
        rAppearance = v.findViewById(R.id.ratingBarAp);
        rConsistency = v.findViewById(R.id.ratingBarCo);
        rKnowledge = v.findViewById(R.id.ratingBarKn);
        rate = v.findViewById(R.id.rate);
        back = v.findViewById(R.id.back);
    }

    @Override
    protected void onStart() {
        super.onStart();

        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot document, @Nullable FirebaseFirestoreException e) {
                if (e==null && document !=null && document.exists() && user!=null && user.getMyJobs()!=null){
                    if (!user.getMyJobs().equals(document.get("myJobs"))){
                        user.setMyJobs((ArrayList<DocumentReference>) document.get("myJobs"));
                        user.setTransactions((ArrayList<DocumentReference>) document.get("transactions"));
                        rBehavior.setRating(0);
                        rAppearance.setRating(0);
                        rConsistency.setRating(0);
                        rKnowledge.setRating(0);
                        rVF.setDisplayedChild(0);
                        popupWindow.showAtLocation(getCurrentFocus(),Gravity.CENTER, 0, 0);
                        View popupBlur = (View) popupWindow.getContentView().getRootView();
                        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
                        WindowManager.LayoutParams p = (WindowManager.LayoutParams) popupBlur.getLayoutParams();
                        p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                        p.dimAmount = 0.35f;
                        wm.updateViewLayout(popupBlur, p);
                        rate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                rBehaviorD = ((user.getrBehavior()*user.getRatingsNum()+rBehavior.getRating())/(user.getRatingsNum()+1));
                                rAppearanceD = ((user.getrAppearance()*user.getRatingsNum()+rAppearance.getRating())/(user.getRatingsNum()+1));
                                rConsistencyD = ((user.getrConsistency()*user.getRatingsNum()+rConsistency.getRating())/(user.getRatingsNum()+1));
                                rKnowledgeD = ((user.getrKnowledge()*user.getRatingsNum()+rKnowledge.getRating())/(user.getRatingsNum()+1));
                                popupWindow.dismiss();
                                final Map<String, Object> changes4 = new HashMap<>();
                                changes4.put("behaviorRating", rBehaviorD);
                                changes4.put("appearanceRating", rAppearanceD);
                                changes4.put("ConsistencyRating", rConsistencyD);
                                changes4.put("knowledgeRating", rKnowledgeD);
                                changes4.put("employeeRatingsNum", (user.getRatingsNum()+1));
                                docRef.update(changes4);
                            }
                        });
                        back.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupWindow.dismiss();
                            }
                        });
                    }else{
                        if (!user.getProfilePicUrl().equals(document.getString("profPic"))){
                            user = new User(document.getLong("amka"), document.getBoolean("employer"), document.getString("job"), document.getString("name"),
                                    document.getString("lastName"), document.getString("companyName"), document.getLong("companyAfm"), document.getString("companyJob"),
                                    document.getBoolean("male"), document.getLong("bDay"), document.getLong("bMonth"), document.getLong("bYear"), mAuth.getCurrentUser().getEmail(),
                                    (ArrayList<DocumentReference>) document.get("interestedJobs"), (ArrayList<DocumentReference>) document.get("myJobs"), (ArrayList<DocumentReference>) document.get("transactions"),
                                    uid, document.getString("profPic"), document.getDouble("knowledgeRating"), document.getLong("employeeRatingsNum"), storage.getReference().child(document.getString("profPicRef")),
                                    (ArrayList<DocumentReference>) document.get("pastJobs"), document.getString("extra"), document.getString("experience"), document.getDouble("behaviorRating"), document.getDouble("appearanceRating"),
                                    document.getDouble("ConsistencyRating"), document.getDouble("behaviorERating"), document.getDouble("workFieldRating"), document.getDouble("efficiencyRating"), document.getLong("employerRatingsNum"));
                            nameTx = user.getName() + " " + user.getLastName();
                            emailTx = user.getEmail();
                            name.setText(nameTx);
                            email.setText(emailTx);
                        }else{
                            user = new User(document.getLong("amka"), document.getBoolean("employer"), document.getString("job"), document.getString("name"),
                                    document.getString("lastName"), document.getString("companyName"), document.getLong("companyAfm"), document.getString("companyJob"),
                                    document.getBoolean("male"), document.getLong("bDay"), document.getLong("bMonth"), document.getLong("bYear"), mAuth.getCurrentUser().getEmail(),
                                    (ArrayList<DocumentReference>) document.get("interestedJobs"), (ArrayList<DocumentReference>) document.get("myJobs"), (ArrayList<DocumentReference>) document.get("transactions"),
                                    uid, document.getString("profPic"), document.getDouble("knowledgeRating"), document.getLong("employeeRatingsNum"), storage.getReference().child(document.getString("profPicRef")),
                                    (ArrayList<DocumentReference>) document.get("pastJobs"), document.getString("extra"), document.getString("experience"), document.getDouble("behaviorRating"), document.getDouble("appearanceRating"),
                                    document.getDouble("ConsistencyRating"), document.getDouble("behaviorERating"), document.getDouble("workFieldRating"), document.getDouble("efficiencyRating"), document.getLong("employerRatingsNum"));
                            nameTx = user.getName() + " " + user.getLastName();
                            emailTx = user.getEmail();
                            name.setText(nameTx);
                            email.setText(emailTx);
                            Picasso.with(getApplicationContext()).load(user.getProfilePicUrl()).fit().placeholder(R.mipmap.ic_launcher_round).centerCrop().into(profPic);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if (index == 13){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new PublicationsFragment()).commit();
            index = 3;
        }else if (index == 11){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new PublicationsFragment()).commit();
        }else if (index != 1){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            nView.setCheckedItem(R.id.nav_home);
            index = 1;
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                index = 1;
                break;
            case R.id.nav_publications:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new PublicationsFragment()).commit();
                index = 3;
                break;
            case R.id.nav_payment_hist:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new PaymentsFragment()).commit();
                index = 4;
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SettingsFragment()).commit();
                index = 5;
                break;
            case R.id.nav_about_us:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AboutUsFragment()).commit();
                index = 6;
                break;
            case R.id.nav_log_out:
                mAuth.signOut();
                SharedPreferences.Editor editorUser = sharedPreferences.edit();
                editorUser.putString("myPrefsEmail", "").apply();
                editorUser.putString("myPrefsPass", "").apply();
                Intent intent = new Intent(this, loginPage.class);
                startActivity(intent);
                break;
            case R.id.nav_calendar:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new CalendarFragment()).commit();
                index = 8;
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
                index = 9;
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setIndex(Integer index){
        this.index = index;
        return;
    }
    public Integer getIndex(){
        return index;
    }

    public User getUser(){
        return user;
    }

    public JobAd getJob(){
        return job;
    }

    public void setJob(JobAd job){
        this.job = job;
    }

    public void uploadMyPubs(DocumentReference newPubRef){
        ArrayList<DocumentReference> add;
        if (user.getMyJobs() != null){
            add = user.getMyJobs();
        }else {
            add = new ArrayList<>();
        }
        add.add(newPubRef);
        user.setMyJobs(add);
        docRef.update("myJobs", add);
    }
    public void uploadSelectedUser(User user){
        ArrayList<DocumentReference> arrayList = new ArrayList<>();
        arrayList.add(FirebaseFirestore.getInstance().collection("users").document(user.getUid()));
        Map<String, Object> updates = new HashMap<>();
        updates.put("interestedIds", arrayList);
        updates.put("state", false);
        FirebaseFirestore.getInstance().collection("jobs").document(job.getUid()).update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "User booked", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }else {
                    Toast.makeText(getApplicationContext(), "Failed to book user", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notification_badge,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.not_bt){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new NotificationsFragment()).commit();
            index = 7;
        }
        return super.onOptionsItemSelected(item);
    }
    public void createNot(){
        notification.setSmallIcon(R.drawable.logo1);
        notification.setTicker("A user is interested about your publication");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("New interested user");
        notification.setContentText("A user is interested about your publication");
        Intent intent = new Intent(this, List.class);
        intent.putExtra("menuFragment", "notification");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqueID, notification.build());
    }
}