package com.example.pat.aapkatrade.general.progressbar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.animation.ProgressBarAnimation;

/**
 * Created by PPC21 on 01-Feb-17.
 */

public class ProgressDialogHandler
{

    private ProgressBar mProgressBar;
    private Context mContext;
    ProgressDialog prog;



    public ProgressDialogHandler(Context context)
    {
        mContext = context;
        prog= new ProgressDialog(context,R.style.AppCompatAlertDialogStyle);

        prog.setTitle(mContext.getString(R.string.pleaseWait));
        prog.setMessage(mContext.getString(R.string.webpage_being_loaded));
        prog.setCancelable(false);
        prog.setIndeterminate(true);


        prog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        hide();
    }

    public void show() {
        prog.show();
    }

    public void hide() {

        prog.hide();

    }


}
