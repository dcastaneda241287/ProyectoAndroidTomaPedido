package com.farmagro.tomapedido;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.farmagro.tomapedido.application.SQLibraryApp;
import com.farmagro.tomapedido.modelo.CabPedido;
import com.farmagro.tomapedido.modelo.Cliente;
import com.farmagro.tomapedido.modelo.DetPedido;
import com.farmagro.tomapedido.modelo.Respuesta;
import com.farmagro.tomapedido.modelo.TmpDetalle;
import com.farmagro.tomapedido.modelo.Usuario;
import com.farmagro.tomapedido.modelo.Zona;
import com.farmagro.tomapedido.service.SSentPedido;
import com.farmagro.tomapedido.util.Constante;
import com.farmagro.tomapedido.util.ScalingUtilities;
import com.farmagro.tomapedido.util.Util;

import org.apache.http.HttpStatus;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.farmagro.tomapedido.dialog_fragment;

public class ResumenActivity extends Fragment {
    private static final int ERROR_ENVIO = 0;
    private static final int SUCCESS_ENVIO = 1;
    private static final int TAKE_IMAGE = 1;
    private Button btnAceptar;
    /* access modifiers changed from: private */
    public CabPedido currentPedido;
    private Zona currentZona;
    private Cliente currentCliente;
    private String fileFrente;
    /* access modifiers changed from: private */
    public long idPedido;
    private Uri imageUri;
    private ImageView imgFoto;
    private int newHeight = HttpStatus.SC_INTERNAL_SERVER_ERROR;
    private int newWidth = HttpStatus.SC_INTERNAL_SERVER_ERROR;
    /* access modifiers changed from: private */
    public ProgressDialog progressSave;
    private File tmpFile;
    private TextView txtMontoFlete;
    private TextView txtTotalFacturar;
    private TextView txtTotalIgv;
    private TextView txtTotalVenta;
    private String mCurrentPhotoPath;
    private String scodalmacen;
    private Respuesta rpta;
    private String rutaImagen;
    protected SQLibraryApp sqLibraryApp;
    /* access modifiers changed from: protected */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.resumen, container, false);
        sqLibraryApp = ((SQLibraryApp) getActivity().getApplication());
        return rootView;
    }

    @Override
    public void onViewCreated(View view,
                              Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        this.getActivity().setTitle((CharSequence) getString(R.string.title_bar_resumen));
        this.txtMontoFlete = view.findViewById(R.id.txtMontoFlete);
        this.txtTotalVenta = view.findViewById(R.id.txtTotalVenta);
        this.txtTotalIgv = view.findViewById(R.id.txtTotalIgv);
        this.txtTotalFacturar = view.findViewById(R.id.txtTotalFacturar);
        this.btnAceptar = view.findViewById(R.id.btnAceptar);
        this.imgFoto = view.findViewById(R.id.imgFoto);
        this.currentPedido = (CabPedido) getArguments().get("currentPedido");
        this.currentZona = (Zona) getArguments().get("currentZona");
        this.currentCliente = (Cliente) getArguments().get("currentCliente");
        float total_venta = 0.0f;
        float igv_venta = 0.0f;
        for (TmpDetalle beanDet : this.sqLibraryApp.getDataManager().getTmpDetalleList()) {
            total_venta += beanDet.getTotal().floatValue();
            igv_venta += beanDet.getIgv().floatValue();
        }
        float total_venta2 = 0.0f;
        float total_igv = 0.0f;
        float monFlete = 0.0f;

        if (this.currentPedido.getMonflete() != ""){
            monFlete = Float.parseFloat(this.currentPedido.getMonflete());
            total_venta2 = total_venta + monFlete;
            total_igv = (igv_venta + (monFlete * 0.18f));
        }else{
            total_igv = igv_venta;
            total_venta2 = total_venta;
        }


        float total_final = total_venta2 + total_igv;
        this.txtMontoFlete.setText(Util.getTwoDecimals(monFlete));
        this.txtTotalVenta.setText(Util.getTwoDecimals(total_venta2));
        this.txtTotalIgv.setText(Util.getTwoDecimals(total_igv));
        this.txtTotalFacturar.setText(Util.getTwoDecimals(total_final));
        this.currentPedido.setTotalmercaderia(String.valueOf(total_venta2));
        this.currentPedido.setTotaligv(String.valueOf(total_igv));
        this.currentPedido.setTotalfacturar(String.valueOf(total_final));
        this.btnAceptar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                dialogPregunta("Elija una de las opciones");
                //dialogEventos("Se guardó satisfactoriamente",true);
                /*new AlertDialog.Builder(getActivity()).
                        setIcon(R.drawable.ic_alert_dialog).
                        setTitle(R.string.lblMensaje).
                        setMessage("Desea enviar el pedido?").
                        setPositiveButton(R.string.lblAceptar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        ResumenActivity.this.grabarPedido();
                        new EnviarPedidoTask().execute(new Void[0]);
                    }
                }).setNegativeButton(R.string.lblCancelar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).setNeutralButton(R.string.lblGuardar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        ResumenActivity.this.grabarPedido();
                        Intent intent = new Intent(getActivity(), MenuAlterno.class);
                        intent.putExtra("page", 0);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        ResumenActivity.this.startActivity(intent);
                        getActivity().finish();
                    }
                }).create().show();*/
            }
        });
    }

    /* access modifiers changed from: private */
    public void grabarPedido() {
        String binaryImage = null;
        //byte[] file = Util.getFile("tmp/" + this.fileFrente);
        byte[] file = getFileToByteN(rutaImagen);

        if (file != null) {
            binaryImage = Base64.encodeToString(file, 2);
        }
        File imgFile = new File("tmp/" + this.fileFrente);
        if (imgFile.exists() && imgFile.length() > 0) {
            Bitmap bm = BitmapFactory.decodeFile("tmp/" + this.fileFrente);
            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bOut);
            binaryImage = Base64.encodeToString(bOut.toByteArray(), Base64.DEFAULT);
        }

        this.currentPedido.setEstado("N");
        this.currentPedido.setTotalunidad(String.valueOf(this.sqLibraryApp.getDataManager().getTmpDetalleList().size()));
        if (binaryImage != null) {
            this.currentPedido.setFoto(binaryImage);
        } else {
            this.currentPedido.setFoto("");
        }
        for (TmpDetalle beanTmp : this.sqLibraryApp.getDataManager().getTmpDetalleList()) {
            scodalmacen = beanTmp.getVcodalmacen().toString();
        }
        currentPedido.setAlmacen(scodalmacen);
        this.idPedido = this.sqLibraryApp.getDataManager().saveCabPedido(this.currentPedido);
        for (TmpDetalle beanTmp : this.sqLibraryApp.getDataManager().getTmpDetalleList()) {
            DetPedido beanDetalle = new DetPedido();
            beanDetalle.setIdPedido(Long.valueOf(this.idPedido));
            beanDetalle.setCodproducto(beanTmp.getCodproducto());
            beanDetalle.setDesproducto(beanTmp.getDesproducto());
            beanDetalle.setPrecio(beanTmp.getPrecio());
            beanDetalle.setCantidad(beanTmp.getCantidad());
            beanDetalle.setDescuento(beanTmp.getDescuento());
            beanDetalle.setTotal(beanTmp.getTotal());
            beanDetalle.setUndventa(beanTmp.getUndventa());
            beanDetalle.setIgv(beanTmp.getIgv());
            beanDetalle.setCodbodega(beanTmp.getVcodalmacen());
            scodalmacen = beanTmp.getVcodalmacen().toString();
            this.sqLibraryApp.getDataManager().saveDetPedido(beanDetalle);
        }
        this.sqLibraryApp.getDataManager().deleteTmpDetalle();
    }

    public static byte[] getFileToByteN(String filePath){
        Bitmap bmp = null;
        ByteArrayOutputStream bos = null;
        byte[] bt = null;
        String encodeString = null;
        try{
            bmp = BitmapFactory.decodeFile(filePath);
            bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bt = bos.toByteArray();
            //encodeString = Base64.encodeToString(bt, Base64.DEFAULT);
        }catch (Exception e){
            e.printStackTrace();
        }
        return bt;
    }

    private byte[] convertToBytes(Uri path) {
        Bitmap.CompressFormat format = Build.VERSION.SDK_INT >= 30 ?
                Bitmap.CompressFormat.WEBP_LOSSLESS : Bitmap.CompressFormat.WEBP;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            InputStream inputStream = getContext().getContentResolver().openInputStream(path);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            bitmap.compress(format, 100, outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }

    public class EnviarPedidoTask extends AsyncTask<Void, String, Integer> {
        public EnviarPedidoTask() {
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            ResumenActivity.this.progressSave = ProgressDialog.show(getActivity(), "Pedido", "Grabando...", true);
            ResumenActivity.this.progressSave.show();
        }

        /* access modifiers changed from: protected */
        public Integer doInBackground(Void... arg0) {
            Usuario beanUsuario = ResumenActivity.this.sqLibraryApp.getDataManager().getUsuario();
            List<DetPedido> lstbeanDet = ResumenActivity.this.sqLibraryApp.getDataManager().getDetPedidoByIdList(ResumenActivity.this.idPedido);

            try {
                String resp = new SSentPedido(getActivity().getApplication(), beanUsuario,currentPedido, lstbeanDet).doRequest();
                JSONObject jo = new JSONObject(resp);
                rpta = new Respuesta();
                rpta.setiCodRespuesta(Integer.parseInt(jo.getString("iCodRespuesta")));
                rpta.setsRespuesta(jo.getString("sRespuesta"));

                ResumenActivity.this.sqLibraryApp.getDataManager().deletePedidoById(ResumenActivity.this.idPedido);
                return 1;
            } catch (Exception e) {
                return -1;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Integer result) {
            if (ResumenActivity.this.progressSave.isShowing()) {
                ResumenActivity.this.progressSave.dismiss();
            }
            switch (result.intValue()) {
                case -1:
                    //getActivity().showDialog(0);

                    dialogEventos(getString(R.string.lblErrorEnvio),false);
                    return;
                    /*dialog_fragment ra = new dialog_fragment();
                    ra.TAG = "0";
                    new dialog_fragment().show(
                            getChildFragmentManager(), ra.TAG);*/

                case 1:
                    dialogEventos(getString(R.string.lblSuccessEnvio),true);
                    return;
                    /*dialog_fragment ra1 = new dialog_fragment();
                    ra1.TAG = "1";
                    new dialog_fragment().show(
                            getChildFragmentManager(), ra1.TAG);*/

                default:
                    return;
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            try {
                Bitmap imgBitmap = BitmapFactory.decodeFile(rutaImagen);
                imgFoto.setImageBitmap(imgBitmap);

                /*
                // Creating file
                File photoFile = null;

                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {

                }
                InputStream inputStream = this.getContentResolver().openInputStream(data.getData());
                FileOutputStream fileOutputStream = new FileOutputStream(photoFile);
                // Copying
                copyStream(inputStream, fileOutputStream);
                fileOutputStream.close();
                inputStream.close();*/

            } catch (Exception e) {
                Log.d("Error", "onActivityResult: " + e.toString());
            }
        }


        if (requestCode == 1 && resultCode == -1) {
            try {
                try {
                    Bitmap bitmap = BitmapFactory.decodeFile(rutaImagen);//MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.fromFile(this.tmpFile));
                    Bitmap scaledBitmap = ScalingUtilities.createScaledBitmap(bitmap, this.newWidth, this.newHeight, ScalingUtilities.ScalingLogic.FIT);
                    bitmap.recycle();
                    Bitmap resizedBitmap = Bitmap.createBitmap(scaledBitmap);
                    OutputStream outStream = new FileOutputStream(new File(rutaImagen));//new FileOutputStream(new File(String.valueOf(Util.getApplicationPath()) + "tmp", this.tmpFile.getName()));
                    resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                    outStream.flush();
                    outStream.close();
                    this.imgFoto.setImageBitmap(resizedBitmap);
                } catch (Exception e) {
                    Log.d(Constante.TAG_CLARO, e.toString());
                }
            } catch (Exception e2) {
                Toast.makeText(getActivity(), "Error:" + e2.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_resumen, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.opcBackResumen) {
            salirDetalle();
        }
        if (item.getItemId() == R.id.opcPhoto) {
            openCamra();
            /*final int REQUEST_ACTION_CAMERA = 9;
            Intent cameraImgIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            this.fileFrente = "IMG_" + System.currentTimeMillis() + ".jpg";
            File myDirectory = new File(String.valueOf(Util.getApplicationPath()) + "tmp");
            myDirectory.mkdirs();
            this.tmpFile = new File(myDirectory, this.fileFrente);
            this.imageUri = FileProvider.getUriForFile(getActivity(), "com.farmagro.tomapedido", tmpFile); //Uri.fromFile(this.tmpFile);
            cameraImgIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
            getActivity().startActivityForResult(cameraImgIntent, 1);*/
        }
        return true;
    }
    private File crearImagen() throws IOException{
        String nombreimagen = "IMG_" + System.currentTimeMillis();
        File myDirectory = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);;//new File(String.valueOf(Util.getApplicationPath()) + "tmp");
        this.tmpFile = File.createTempFile(nombreimagen, "jpg", myDirectory);
        rutaImagen = tmpFile.getAbsolutePath();
        return tmpFile;
    }

    final int REQUEST_ACTION_CAMERA = 9;

    void openCamra() {
        Intent cameraImgIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File imagenArchivo = null;

        try{
            imagenArchivo = crearImagen();
        }catch (IOException ex){
            Log.e("Error ", ex.toString());
        }

        if(imagenArchivo != null){
            Uri fotoUri = FileProvider.getUriForFile(getActivity(),"com.farmagro.tomapedido.fileprovider", imagenArchivo);
            cameraImgIntent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
            startActivityForResult(cameraImgIntent, 1);
        }
    }

    private void salirDetalle() {
        Bundle bundle2 = new Bundle();
        bundle2.putSerializable("currentPedido", currentPedido);
        bundle2.putSerializable("currentZona", currentZona);
        bundle2.putSerializable("currentCliente", currentCliente);

        AdicionalActivity fragmentAdicional = new AdicionalActivity();
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentAdicional.setArguments(bundle2);
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentAdicional).commit();
        return;

        //getActivity().getFragmentManager().popBackStack();
        //getActivity().finish();

    }

    public void dialogPregunta(String message) {
        TextView aceptar, guardar, informacion;
        TextView cancel;
        View alertCustomdialog = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_pregunta, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setView(alertCustomdialog);
        aceptar = (TextView) alertCustomdialog.findViewById(R.id.tv_aceptar_button);
        guardar = (TextView) alertCustomdialog.findViewById(R.id.tv_guardar_button);
        cancel  = (TextView) alertCustomdialog.findViewById(R.id.tv_cancel_button);
        informacion = (TextView) alertCustomdialog.findViewById(R.id.tv_information);
        final AlertDialog dialog = alert.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();
        informacion.setText(message);
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResumenActivity.this.grabarPedido();
                new EnviarPedidoTask().execute(new Void[0]);
            }
        });
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResumenActivity.this.grabarPedido();
                //dialog.dismiss();
                Intent intent = new Intent(getActivity(), MenuAlterno.class);
                intent.putExtra("page", 0);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ResumenActivity.this.startActivity(intent);
                //getActivity().finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void dialogEventos(String message, boolean correct) {
        ImageView ivPopup;
        TextView tvContinueButton;
        TextView continuar,informacion;
        View alertCustomdialog = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_popup, null);
        ivPopup = alertCustomdialog.findViewById(R.id.iv_popup);
        tvContinueButton = alertCustomdialog.findViewById(R.id.tv_continue_button);
        if (!correct) {
            ivPopup.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.background_circle_shape_red));
            ivPopup.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_error_popup));
            tvContinueButton.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorError));
            tvContinueButton.setText(R.string.popup_tv_click_close);
        }
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setView(alertCustomdialog);
        continuar = (TextView) alertCustomdialog.findViewById(R.id.tv_continue_button);
        informacion = (TextView) alertCustomdialog.findViewById(R.id.tv_information);

        final AlertDialog dialog = alert.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        informacion.setText(message);
        if(!correct){
            continuar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }else {
            continuar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    Intent intent = new Intent(getActivity(), MenuAlterno.class);
                    intent.putExtra("page", 0);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                }
            });
        }

    }
    /*@Override
    public void onDestroy(){
        super.onDestroy();
        if ( progressDialog!=null && progressDialog.isShowing() ){
            progressDialog.cancel();
        }
    }*/

}
