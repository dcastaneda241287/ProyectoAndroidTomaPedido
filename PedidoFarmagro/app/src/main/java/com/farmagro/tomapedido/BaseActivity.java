package com.farmagro.tomapedido;

import android.os.Bundle;

//import com.actionbarsherlock.app.ActionBar;
//import com.actionbarsherlock.app.SherlockActivity;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.farmagro.tomapedido.application.SQLibraryApp;


public class BaseActivity extends AppCompatActivity {//extends SherlockActivity {
    protected static final int BACK_FROM_GPS_ACT = 1;
    protected static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 0;
    protected static final long MINIMUM_TIME_BETWEEN_UPDATES = 0;
    protected ActionBar ab;
    protected SQLibraryApp sqLibraryApp;

    //access modifiers changed from: protected
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.ab = getSupportActionBar();
        this.ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME );
        //this.ab.setIcon((int) R.drawable.ic_header);
        this.sqLibraryApp = (SQLibraryApp) getApplication();
    }


}
