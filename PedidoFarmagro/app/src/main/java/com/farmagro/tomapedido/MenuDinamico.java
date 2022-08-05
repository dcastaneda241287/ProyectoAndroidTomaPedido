package com.farmagro.tomapedido;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import butterknife.internal.Utils;

public class MenuDinamico extends AppCompatActivity {
    private boolean isOpen = true;
    private boolean isOpenMenu = false;
    private View lastView = null;
    LinearLayout llSectionOption;
    RelativeLayout tabBar1;
    RelativeLayout tabBar2;
    View ibHome;
    ImageButton ibOpenMoreOptionMenu;
    ImageButton ibVectorDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_dinamico);
        //overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        View view = Utils.findRequiredView(lastView, R.id.ib_home, "field 'ibHome' and method 'onClickSection'");
        /*this.tabBar2.post(new Runnable() {
            public void run() {
                MenuDinamico.this.llSectionOption.setTranslationY((float) MenuDinamico.this.tabBar2.getHeight());
            }
        });*/
    }

    private void viewSelected(View v) {
        View view = this.lastView;
        if (view != null) {
            view.setSelected(false);
        }
        v.setSelected(true);
        this.lastView = v;
    }



    public void onClickSection(View v) {
        viewSelected(v);
        //selectedFragment(v);
    }
}