package com.farmagro.tomapedido;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.glance.layout.Alignment;

import org.w3c.dom.Text;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PopupDialogFragment extends DialogFragment {
    private boolean isCorrect;
    protected ImageView ivPopup;
    private String message;
    protected TextView tvContinueButton;
    protected TextView tvMessage;
    private Unbinder unbinder;
    private Activity actividad;

    public PopupDialogFragment(){ }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        return CreaDialogoGestion();
    }

    private AlertDialog CreaDialogoGestion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_fragment_popup, null);
        //inflater.inflate(R.layout.dialog_fragment_popup, container, false);
        ivPopup = v.findViewById(R.id.iv_popup);
        tvContinueButton = v.findViewById(R.id.tv_continue_button);

        if (!this.isCorrect) {
            this.ivPopup.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.background_circle_shape_red));
            this.ivPopup.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_error_popup));
            this.tvContinueButton.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorError));
            this.tvContinueButton.setText(R.string.popup_tv_click_close);
        }
        builder.setView(v);
        tvContinueButton = v.findViewById(R.id.tv_continue_button);
        tvMessage = v.findViewById(R.id.tv_information);
        eventosBotones();

        return builder.create();
    }

    private void eventosBotones(){

        tvMessage.setText(message);
        tvContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof Activity){
            this.actividad = (Activity) context;
        }else{
            throw new RuntimeException(context.toString()
                    + "most implement OnFragmentInteractionListener");
        }
    }

    /*public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.tvMessage = view.findViewById(R.id.tv_information);
        this.tvMessage.setText(this.message);
        if (!this.isCorrect) {
            this.ivPopup.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.background_circle_shape_red));
            this.ivPopup.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_error_popup));
            this.tvContinueButton.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorError));
            this.tvContinueButton.setText(R.string.popup_tv_click_close);
        }
    }

    public void onClickContinue() {
    }*/

    public void setMessage(String message2) {
        this.message = message2;
    }

    public void setCorrect(boolean correct) {
        this.isCorrect = correct;
    }


    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null) {
            return;
        }
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //int width = getResources().getDimensionPixelSize(R.dimen.popup_width); int height = getResources().getDimensionPixelSize(R.dimen.popup_height); getDialog().getWindow().setLayout(width, height);



        //getDialog().getWindow().setGravity(8388615);

        //layout_marginBottom
        //getDialog().getWindow().setGravity(80);

        // ... other stuff you want to do in your onStart() method
    }
}

