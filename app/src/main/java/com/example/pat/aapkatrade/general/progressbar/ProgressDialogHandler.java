package com.example.pat.aapkatrade.general.progressbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.ProgressBar;

import com.example.pat.aapkatrade.R;

/**
 * Created by PPC21 on 01-Feb-17.
 */

public class ProgressDialogHandler {
    private Context context;
    private ProgressDialog progressDialog;


    public ProgressDialogHandler(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context, R.style.AppCompatAlertDialogStyle);
        progressDialog.setTitle(this.context.getString(R.string.pleaseWait));
        progressDialog.setMessage(this.context.getString(R.string.webpage_being_loaded));
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        hide();
    }

    public void show() {
        if(progressDialog !=null)
        progressDialog.show();
    }

    public void hide() {
        if(progressDialog !=null)
        progressDialog.hide();
    }


}
