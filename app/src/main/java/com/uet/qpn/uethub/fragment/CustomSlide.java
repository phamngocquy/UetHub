package com.uet.qpn.uethub.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.uet.qpn.uethub.R;

import agency.tango.materialintroscreen.SlideFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomSlide extends SlideFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_custom_slide, container, false);
        return view;
    }

    @Override
    public int backgroundColor() {
        return R.color.orange_salmon;
    }

    @Override
    public int buttonsColor() {
        return R.color.thumbColor;
    }

    @Override
    public boolean canMoveFurther() {
        return true;
    }

    @Override
    public String cantMoveFurtherErrorMessage() {
        return "bugggggggggg";
    }

}
