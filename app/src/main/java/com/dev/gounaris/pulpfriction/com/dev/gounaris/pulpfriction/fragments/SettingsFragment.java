package com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

import com.dev.gounaris.pulpfriction.List;
import com.dev.gounaris.pulpfriction.R;
import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.objects.User;

public class SettingsFragment extends Fragment {
    private View view;
    private Button rights;
    private Button help;
    private Button contact;
    private Switch not;
    private User user;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        not = view.findViewById(R.id.notifications);
        contact = view.findViewById(R.id.contact);
        help = view.findViewById(R.id.help);
        rights = view.findViewById(R.id.rights);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        user = ((List)getActivity()).getUser();
        rights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
