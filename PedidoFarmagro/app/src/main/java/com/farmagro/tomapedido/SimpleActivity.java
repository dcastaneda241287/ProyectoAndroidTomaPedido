package com.farmagro.tomapedido;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnLongClick;
import butterknife.Action;


public class SimpleActivity extends Activity {
    private static final butterknife.Action ALPHA_FADE = new Action()
    {

        @Override public void apply(@NonNull View view, int index)
        {
            AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
            alphaAnimation.setFillBefore(true);
            alphaAnimation.setDuration(500);
            alphaAnimation.setStartOffset(index * 100);
            view.startAnimation(alphaAnimation);
        }

    };

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.subtitle)
    TextView subtitle;
    @BindView(R.id.hello)
    Button hello;
    @BindView(R.id.list_of_things)
    ListView listOfThings;
    @BindView(R.id.footer)
    TextView footer;

    @BindViews({ R.id.title, R.id.subtitle, R.id.hello })
    List<View> headerViews;

    private SimpleAdapter adapter;

    @OnClick(R.id.hello) void sayHello() {
        Toast.makeText(this, "Hello, views!", Toast.LENGTH_SHORT).show();
        //ButterKnife.apply(headerViews, ALPHA_FADE);
        butterknife.ViewCollections.run(headerViews , ALPHA_FADE );
    }

    @OnLongClick(R.id.hello) boolean sayGetOffMe() {
        Toast.makeText(this, "Let go of me!", Toast.LENGTH_SHORT).show();
        return true;
    }

    @OnItemClick(R.id.list_of_things) void onItemClick(int position) {
        Toast.makeText(this, "You clicked: " + adapter.getItem(position), Toast.LENGTH_SHORT).show();
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        ButterKnife.bind(this);

        // Contrived code to use the bound fields.
        title.setText("Butter Knife");
        subtitle.setText("Field and method binding for Android views.");
        footer.setText("by Jake Wharton");
        hello.setText("Say Hello");

        adapter = new SimpleAdapter(this);
        listOfThings.setAdapter((ListAdapter) adapter);
    }
}