package com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dev.gounaris.pulpfriction.List;
import com.dev.gounaris.pulpfriction.R;

public class PublicationsfragmentFragment extends Fragment {
    private View view;
    private Button interested;
    private Button my;
    private FloatingActionButton add;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fragment_publications, container, false);
        interested = view.findViewById(R.id.btInterestedPubs);
        my = view.findViewById(R.id.btMyPubs);
        add = view.findViewById(R.id.btAdd);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        interested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((List)getActivity()).setIndex(13);
                getFragmentManager().beginTransaction().replace(R.id.fragment_cont, new InterestedPublicationsFragment()).commit();
            }
        });
        my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((List)getActivity()).setIndex(13);
                getFragmentManager().beginTransaction().replace(R.id.fragment_cont, new MyPublicationsFragment()).commit();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((List)getActivity()).setIndex(13);
                getFragmentManager().beginTransaction().replace(R.id.fragment_cont, new NewPublicationFragment()).commit();
            }
        });
    }
}
