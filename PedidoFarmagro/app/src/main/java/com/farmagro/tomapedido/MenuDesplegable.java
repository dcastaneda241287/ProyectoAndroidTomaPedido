package com.farmagro.tomapedido;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.michaldrabik.tapbarmenulib.TapBarMenu;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuDesplegable extends AppCompatActivity {

    @BindView(R.id.tapBarMenu) TapBarMenu tapBarMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_desplegable);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tapBarMenu)
    public void onMenuButtonClick() {
        tapBarMenu.toggle();
    }

    @OnClick({ R.id.item1, R.id.item2, R.id.item3, R.id.item4 })
    public void onMenuItemClick(View view) {
        tapBarMenu.close();
        switch (view.getId()) {
            case R.id.item1:
                Log.i("TAG", "Item 1 selected");

                //dialog.dismiss();
                Intent intent = new Intent(this, MenuDinamico.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivity(intent);
                break;
            case R.id.item2:
                Log.i("TAG", "Item 2 selected");
                Intent intent2 = new Intent(this, MenuAnimado.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivity(intent2);
                break;
            case R.id.item3:
                Log.i("TAG", "Item 3 selected");
                break;
            case R.id.item4:
                Log.i("TAG", "Item 4 selected");
                break;
        }
    }
}
