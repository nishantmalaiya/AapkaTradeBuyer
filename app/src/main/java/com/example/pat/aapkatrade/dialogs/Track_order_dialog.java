package com.example.pat.aapkatrade.dialogs;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pat.aapkatrade.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Track_order_dialog extends DialogFragment {


    public Track_order_dialog() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_track_order_dialog, container, false);
getDialog().getWindow().setBackgroundDrawableResource(R.color.transparent);





        return v;
    }

}
