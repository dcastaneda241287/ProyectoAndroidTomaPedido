package com.farmagro.tomapedido;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.farmagro.tomapedido.util.ResizeAnimation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.ButterKnife;

public class MenuAnimado extends AppCompatActivity {

    private Menu menu;
    private FloatingActionButton floatingActionButton;
    private Toolbar toolbar;

    private ImageButton ibHome;
    LinearLayout llSectionOption;
    private boolean isOpen = true;
    private boolean isOpenMenu = false;
    ImageButton ibVectorDrawable;
    RelativeLayout tabBar1;
    RelativeLayout tabBar2;
    ImageButton ibOpenMoreOptionMenu;
    private View lastView = null;
    private Fragment fragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        setContentView(R.layout.activity_menu_animado);
        setTitle("Anim Items");
        ButterKnife.bind((Activity) this);
        llSectionOption = findViewById(R.id.ll_section_option);
        ibHome = findViewById(R.id.ib_home);
        ibVectorDrawable = findViewById(R.id.ib_dim);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        setSupportActionBar(toolbar);
        //onCreateOptionsMenu(menu);
/*
        this.tabBar2.post(new Runnable() {
            public void run() {
                MenuAnimado.this.llSectionOption.setTranslationY((float) MenuAnimado.this.tabBar2.getHeight());
            }
        });*/
        ibVectorDrawable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainMenu();
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (menu != null) {
                    for (int i = 0; i < menu.size(); i++) {
                        Drawable drawable = menu.getItem(i).getIcon();
                        if (drawable instanceof Animatable) {
                            ((Animatable) drawable).start();
                        }
                    }
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_nuevo, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Drawable drawable = item.getIcon();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
        return super.onOptionsItemSelected(item);
    }

    public void openMainMenu() {
        if (this.isOpen) {
            this.isOpen = false;
            expandAnimation();
            this.isOpenMenu = true;
            return;
        }
        this.isOpen = true;
        this.isOpenMenu = false;
        reduceAnimation();
    }

    private void expandAnimation() {
        this.ibVectorDrawable.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_close_on));
        RelativeLayout relativeLayout = this.tabBar1;
        ResizeAnimation resizeAnimation = new ResizeAnimation(relativeLayout, (float) relativeLayout.getWidth(), (float) this.tabBar1.getHeight(), (float) this.tabBar2.getWidth(), (float) (this.tabBar2.getHeight() * 2));
        resizeAnimation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
                MenuAnimado.this.tabBar1.setAlpha(1.0f);
                ObjectAnimator mover = ObjectAnimator.ofFloat(MenuAnimado.this.llSectionOption, "translationY", new float[]{0.0f});
                mover.setDuration(250);
                mover.start();
            }

            public void onAnimationEnd(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
        this.tabBar1.startAnimation(resizeAnimation);
    }

    private void reduceAnimation() {
        this.ibVectorDrawable.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_more_on));
        RelativeLayout relativeLayout = this.tabBar1;
        ResizeAnimation resizeAnimation = new ResizeAnimation(relativeLayout, (float) relativeLayout.getWidth(), (float) this.tabBar1.getHeight(), (float) this.ibOpenMoreOptionMenu.getWidth(), (float) this.ibOpenMoreOptionMenu.getHeight());
        resizeAnimation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
                ObjectAnimator mover = ObjectAnimator.ofFloat(MenuAnimado.this.llSectionOption, "translationY", new float[]{(float) MenuAnimado.this.tabBar2.getHeight()});
                mover.setDuration(250);
                ObjectAnimator fade = ObjectAnimator.ofFloat(MenuAnimado.this.tabBar1, "alpha", new float[]{1.0f, 0.0f});
                fade.setDuration(50);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(mover).before(fade);
                animatorSet.start();
            }

            public void onAnimationEnd(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
        this.tabBar1.startAnimation(resizeAnimation);
    }

    private void animationVectorDrawable(int imageVectorDrawable, int imageVectorDrawableClose) {
        if (Build.VERSION.SDK_INT > 20) {
            this.ibVectorDrawable.setBackground(ContextCompat.getDrawable(this, imageVectorDrawable));
            Drawable drawable = this.ibVectorDrawable.getBackground();
            if (drawable instanceof Animatable) {
                ((Animatable) drawable).start();
                return;
            }
            return;
        }
        this.ibVectorDrawable.setBackground(ContextCompat.getDrawable(this, imageVectorDrawableClose));
    }
    private void selectedFragment(View v) {
        /*
        this.fragment = getFragmentManager().findFragmentById(R.id.fl_content);
        switch (v.getId()) {
            case R.id.ib_home:
                if (!(this.fragment instanceof HomeFragment)) {
                    this.fragment = HomeFragment.newInstance(this);
                    if (PexApplication.account != null) {
                        this.tvClientName.setText(PexApplication.account.getFullName());
                    }
                    this.tvSubTitle.setText(C1347R.string.main_text_welcome_home);
                    this.tvSubTitle.setVisibility(View.GONE);
                    break;
                }
                break;
            case R.id.ib_info:
                if (!(this.fragment instanceof FaqFragment)) {
                    this.fragment = FaqFragment.newInstance(this);
                    this.tvClientName.setText(R.string.faq_title_toolbar);
                    this.tvSubTitle.setVisibility(View.GONE);
                    break;
                }
                break;
            case R.id.ib_map:
                if (!(this.fragment instanceof MapFragment)) {
                    this.fragment = MapFragment.newInstance(this);
                    this.tvSubTitle.setVisibility(View.GONE);
                    this.tvSubTitle.setText(C1347R.string.map_fragment_title);
                    this.tvClientName.setText(C1347R.string.map_fragment_subtitle);
                    break;
                }
                break;
            case R.id.ib_present:
                if (!(this.fragment instanceof OffersFragment)) {
                    this.fragment = OffersFragment.newInstance(this);
                    this.tvSubTitle.setVisibility(View.INVISIBLE);
                    this.tvClientName.setText(C1347R.string.offers_title_toolbar);
                    break;
                }
                break;
            case R.id.ib_setting:
                if (!(this.fragment instanceof ConfigurationFragment)) {
                    this.fragment = ConfigurationFragment.newInstance(this);
                    this.tvSubTitle.setVisibility(View.INVISIBLE);
                    this.tvClientName.setText(C1347R.string.configuration_title);
                    break;
                }
                break;
            case R.id.ib_shape:
                if (!(this.fragment instanceof ShapeFragment)) {
                    this.fragment = ShapeFragment.newInstance(this);
                    this.tvClientName.setText(R.string.shape_title_toolbar);
                    this.tvSubTitle.setVisibility(View.INVISIBLE);
                    break;
                }
                break;
            case R.id.ib_shipping:
                if (!(this.fragment instanceof RechargeFragment)) {
                    this.fragment = RechargeFragment.newInstance(this, new Bundle(), true);
                    ((RechargeFragment) this.fragment).setOnRechargeFragmentSuccessNextAction(this);
                    this.tvClientName.setText(R.string.recharge_title_toolbar);
                    this.tvSubTitle.setVisibility(View.INVISIBLE);
                    break;
                }
                break;
            case R.id.ib_shop:
                if (!(this.fragment instanceof ShoppingAndRechargesFragment)) {
                    this.fragment = ShoppingAndRechargesFragment.newInstance(this);
                    this.tvSubTitle.setVisibility(View.INVISIBLE);
                    this.tvClientName.setText(C1347R.string.purchases_title);
                    break;
                }
                break;
        }
        if (this.fragment != null) {
            if (this.isOpenMenu) {
                this.isOpenMenu = false;
                this.isOpen = true;
                reduceAnimation();
            }
            fragmentAnimator(this.fragment);
        }
        */
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
        selectedFragment(v);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == -1) {
            onClickSection(this.ibHome);
        }
    }

}
