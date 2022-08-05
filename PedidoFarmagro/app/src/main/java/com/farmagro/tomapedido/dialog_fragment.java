package com.farmagro.tomapedido;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.farmagro.tomapedido.application.SQLibraryApp;
import com.farmagro.tomapedido.modelo.Usuario;

public class dialog_fragment  extends DialogFragment {
    protected SQLibraryApp sqLibraryApp;
    Usuario bean;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        sqLibraryApp = ((SQLibraryApp) getActivity().getApplication());
        bean = sqLibraryApp.getDataManager().getUsuario();
            switch (Integer.parseInt(TAG)) {
                case 0:
                    return new AlertDialog.Builder(getActivity())
                            .setIcon(R.drawable.ic_alert_dialog).setTitle(R.string.lblMensaje).setMessage(R.string.lblErrorEnvio)
                            .setPositiveButton(R.string.lblAceptar, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    return;
                                   /*Intent intent = new Intent(getActivity(), MenuPrincipal.class);
                                    intent.putExtra("page", 1);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    getActivity().startActivity(intent);
                                    getActivity().finish();*/
                                }
                            }).create();


                case 1:
                    return new AlertDialog.Builder(getActivity()).setIcon(R.drawable.ic_alert_dialog).setTitle(R.string.lblMensaje).setMessage(R.string.lblSuccessEnvio).setPositiveButton(R.string.lblAceptar, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Intent intent = new Intent(getActivity(), MenuAlterno.class);
                            intent.putExtra("page", 0);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            getActivity().startActivity(intent);
                            getActivity().finish();
                        }
                    }).create();
                case 2:
                    return new AlertDialog.Builder(requireContext())
                            .setMessage(TAG)
                            .setPositiveButton("OK", (dialog, which) -> {} )
                            .create();

                case 3:
                    return new AlertDialog.Builder(getActivity())
                            .setIcon(R.drawable.ic_alert_dialog)
                            .setTitle(R.string.lblMensaje)
                            .setMessage("Hola "+ bean.getNombreusuario() +", hemos detectado que no tienes señal ó tu plan de datos no se encuentra activo. La aplicación sólo te permitirá generar los pedidos como pendientes.")
                            .setPositiveButton(R.string.lblAceptar, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                }
                            }).create();
                default:
                    return null;
            }



    }

    public static String TAG = "PurchaseConfirmationDialog";

}