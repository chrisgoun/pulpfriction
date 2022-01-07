package com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.dev.gounaris.pulpfriction.List;
import com.dev.gounaris.pulpfriction.R;

public class NotificationsFragment extends Fragment {
    private View view;
    private ListView lV;
    private ArrayAdapter lA;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notifications, container, false);
        return view;
    }
}
