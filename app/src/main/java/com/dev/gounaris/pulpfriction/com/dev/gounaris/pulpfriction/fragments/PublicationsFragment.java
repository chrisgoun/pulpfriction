package com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.dev.gounaris.pulpfriction.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PublicationsFragment extends Fragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_publications, container, false);
        if (((com.dev.gounaris.pulpfriction.List)getActivity()).getIndex() == 11){
            getFragmentManager().beginTransaction().replace(R.id.fragment_cont,
                    new MyPublicationsFragment()).commit();
            ((com.dev.gounaris.pulpfriction.List)getActivity()).setIndex(13);
        }else {
            getFragmentManager().beginTransaction().replace(R.id.fragment_cont,
                    new PublicationsfragmentFragment()).commit();
        }
        return view;
    }
}
