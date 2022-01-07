package com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.customListViewAdapters;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.gounaris.pulpfriction.R;
import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.objects.JobAd;
import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.objects.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CustomAdapterAllPubs extends ArrayAdapter<JobAd> {

    public CustomAdapterAllPubs(@NonNull Context context, ArrayList<JobAd> pub) {
        super(context, R.layout.list_pubs_layout,pub);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.list_pubs_layout,parent,false);

        String[] jobPoss = customView.getResources().getStringArray(R.array.jobPos);
        Calendar cl = Calendar.getInstance();
        JobAd pub = getItem(position);
        TextView tVDate = customView.findViewById(R.id.tVDate);
        TextView tVJobPos = customView.findViewById(R.id.tVJobPos);
        TextView tVPubName = customView.findViewById(R.id.tVPubName);
        ImageView inter = customView.findViewById(R.id.inter);
        TextView tVPrice = customView.findViewById(R.id.tVPrice);

        Date dateTime = pub.getDateTime();
        cl.setTime(dateTime);
        String date = cl.get(Calendar.DAY_OF_MONTH)+"/"+(cl.get(Calendar.MONTH)+1)+"/"+cl.get(Calendar.YEAR);
        String jobPos = jobPoss[pub.getType()];
        String pubName = pub.getPubName();
        Boolean interested = false;
        if (pub.getInterestedIds() == null){
            interested = false;
        }else {
            interested = pub.getInterestedIds().contains(FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()));
        }
        String price = pub.getPayment() + " €";

        tVDate.setText(date);
        tVJobPos.setText(jobPos);
        tVPubName.setText(pubName);
        tVPrice.setText(price);
        if (interested){
            inter.setVisibility(View.VISIBLE);
        }else {
            inter.setVisibility(View.GONE);
        }

        return customView;
    }
}
