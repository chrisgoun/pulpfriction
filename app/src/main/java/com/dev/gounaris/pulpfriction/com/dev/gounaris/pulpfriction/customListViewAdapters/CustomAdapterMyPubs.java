package com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.customListViewAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.dev.gounaris.pulpfriction.R;
import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.objects.JobAd;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class CustomAdapterMyPubs extends ArrayAdapter<JobAd> {

    public CustomAdapterMyPubs(@NonNull Context context, ArrayList<JobAd> pub) {
        super(context, R.layout.list_my_pubs_layout,pub);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.list_my_pubs_layout,parent,false);

        String[] jobPoss = customView.getResources().getStringArray(R.array.jobPos);
        Calendar cl = Calendar.getInstance();
        JobAd pub = getItem(position);
        TextView tVDate = customView.findViewById(R.id.tVDate);
        TextView tVJobPos = customView.findViewById(R.id.tVJobPos);
        CheckBox chState = customView.findViewById(R.id.jobState);
        Date dateTime = pub.getDateTime();
        cl.setTime(dateTime);
        String date = cl.get(Calendar.DAY_OF_MONTH)+"/"+(cl.get(Calendar.MONTH)+1)+"/"+cl.get(Calendar.YEAR);
        String jobPos = jobPoss[pub.getType()];
        Boolean state = pub.getState();

        tVDate.setText(date);
        tVJobPos.setText(jobPos);
        chState.setChecked(!state);

        return customView;
    }
}
