package com.farmagro.tomapedido;

import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;

public class MenuDinamicoAdapter implements Unbinder{
    private MenuDinamico target;



    public void unbind(){
        MenuDinamico target2 = this.target;
    }
}
