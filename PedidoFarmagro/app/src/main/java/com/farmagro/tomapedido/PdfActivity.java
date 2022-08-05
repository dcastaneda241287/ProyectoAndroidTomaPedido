package com.farmagro.tomapedido;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import com.github.barteksc.pdfviewer.PDFView;

public class PdfActivity extends AppCompatActivity {
    private Context context;
    private PDFView pdfView;
    private ProgressDialog progressDialog;
    private byte[] mbyte ;

    public void onLoader(boolean isLoading) {
        if (isLoading) {
            this.progressDialog = ProgressDialog.show(this.context, "", getString(R.string.cargando), true, false);
            return;
        }
        ProgressDialog progressDialog2 = this.progressDialog;
        if (progressDialog2 != null) {
            progressDialog2.dismiss();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        this.pdfView = (PDFView) findViewById(R.id.pdfView);
        mbyte = getIntent().getByteArrayExtra("pdf");

        if (mbyte != null) {
            this.pdfView.fromBytes(mbyte).load();
        }
    }
}