package com.farmagro.tomapedido.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Base64;
import android.util.Xml;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class Util {
    public static String getDateTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public static String getTime() {
        return new SimpleDateFormat("HH:mm").format(new Date());
    }

    public static String getDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public static String getToday() {
        return new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    }
    public static float formatearDecimales(float numero, Integer numeroDecimales) {
        return (float) (Math.round(numero * Math.pow(10, numeroDecimales)) / Math.pow(10, numeroDecimales));
    }
    public static String obtieneDosDecimales(float valor){
        DecimalFormat format = new DecimalFormat();
        format.setMaximumFractionDigits(2); //Define 2 decimales.
        return format.format(valor);
    }
    public static String obtieneDosDecimalesDouble(Double valor){
        DecimalFormat format = new DecimalFormat();
        format.setMaximumFractionDigits(2); //Define 2 decimales.
        return format.format(valor);
    }

    public static String mostrarNumero(float d) {
        return (d == (long) d) ? String.format("%d",(long)d):String.format("%s",d);
    }
    public static String formatDecimal(String valor) {
        if (valor.compareTo("") == 0 || valor.compareTo(Constante.NO) == 0) {
            return "0.00";
        }
        String respuesta = Double.toString(((double) ((int) ((0.005d + Double.parseDouble(valor)) * 100.0d))) / 100.0d);
        //String respuesta = Double.toString(((double) ((int) ((Double.parseDouble(valor)) * 100.0d))) / 100.0d);
        if (split(respuesta, ".")[1].length() == 1) {
            return String.valueOf(respuesta) + Constante.NO;
        }
        return respuesta;
    }

    public static String getTwoDecimals(float value){
        DecimalFormat df = new DecimalFormat("###,###.##");
        return df.format(value);
    }

    public static String esEntero(Float numero){
        if (numero - Math.floor(numero) == 0) {
            return "Entero";
        } else {
            return "Decimal";
        }
    }
    public static String esEnteroDouble(Double numero){
        if (numero - Math.floor(numero) == 0) {
            return "Entero";
        } else {
            return "Decimal";
        }
    }
    public static String formatDecimal2(String valor) {
        if (valor.compareTo("") == 0 || valor.compareTo(Constante.NO) == 0) {
            return "0.00";
        }
        String respuesta = Double.toString(((double) ((int) ((Double.parseDouble(valor)) * 100.0d))) / 100.0d);
        //String respuesta = Double.toString(((double) ((int) ((Double.parseDouble(valor)) * 100.0d))) / 100.0d);
        if (split(respuesta, ".")[1].length() == 1) {
            return String.valueOf(respuesta) + Constante.NO;
        }
        return respuesta;
    }

    public static String[] split(String original, String separador) {
        Vector<String> nodes = new Vector<>();
        int index = original.indexOf(separador);
        while (index >= 0) {
            nodes.addElement(original.substring(0, index));
            original = original.substring(separador.length() + index);
            index = original.indexOf(separador);
        }
        nodes.addElement(original);
        String[] result = new String[nodes.size()];
        if (nodes.size() > 0) {
            for (int loop = 0; loop < nodes.size(); loop++) {
                result[loop] = nodes.elementAt(loop);
            }
        }
        return result;
    }

    public static String getApplicationPath() {
        return String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath()) + "/farmagro/";
    }

    public static boolean createDirIfNotExists(String path) {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), path);
        if (file.exists()) {
            return true;
        }
        if (!file.mkdirs()) {
            return false;
        }
        new File(String.valueOf(getApplicationPath()) + "tmp").mkdirs();
        return true;
    }
    public static String getFileToByte(String filePath){
        Bitmap bmp = null;
        ByteArrayOutputStream bos = null;
        byte[] bt = null;
        String encodeString = null;
        try{
            bmp = BitmapFactory.decodeFile(filePath);
            bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bt = bos.toByteArray();
            encodeString = Base64.encodeToString(bt, Base64.DEFAULT);
        }catch (Exception e){
            e.printStackTrace();
        }
        return encodeString;
    }

    public static byte[] getFile(java.lang.String r11) {
         /*
            r5 = 0
            r0 = 0
            java.io.FileInputStream r6 = new java.io.FileInputStream     // Catch:{ IOException -> 0x009f }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x009f }
            java.lang.String r9 = getApplicationPath()     // Catch:{ IOException -> 0x009f }
            java.lang.String r9 = java.lang.String.valueOf(r9)     // Catch:{ IOException -> 0x009f }
            r8.<init>(r9)     // Catch:{ IOException -> 0x009f }
            java.lang.StringBuilder r8 = r8.append(r11)     // Catch:{ IOException -> 0x009f }
            java.lang.String r8 = r8.toString()     // Catch:{ IOException -> 0x009f }
            r6.<init>(r8)     // Catch:{ IOException -> 0x009f }
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x00a1, all -> 0x0098 }
            r1.<init>()     // Catch:{ IOException -> 0x00a1, all -> 0x0098 }
            r8 = 8192(0x2000, float:1.14794E-41)
            byte[] r2 = new byte[r8]     // Catch:{ IOException -> 0x0044, all -> 0x009b }
        L_0x0025:
            int r7 = r6.read(r2)     // Catch:{ IOException -> 0x0044, all -> 0x009b }
            r8 = -1
            if (r7 != r8) goto L_0x003f
            if (r6 == 0) goto L_0x0031
            r6.close()     // Catch:{ IOException -> 0x008a }
        L_0x0031:
            if (r1 == 0) goto L_0x0093
            r1.close()     // Catch:{ IOException -> 0x008f }
            r0 = r1
            r5 = r6
        L_0x0038:
            if (r0 == 0) goto L_0x0096
            byte[] r8 = r0.toByteArray()
        L_0x003e:
            return r8
        L_0x003f:
            r8 = 0
            r1.write(r2, r8, r7)     // Catch:{ IOException -> 0x0044, all -> 0x009b }
            goto L_0x0025
        L_0x0044:
            r4 = move-exception
            r0 = r1
            r5 = r6
        L_0x0047:
            java.lang.String r8 = "CLARO"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x0074 }
            java.lang.String r10 = "IOException:"
            r9.<init>(r10)     // Catch:{ all -> 0x0074 }
            java.lang.String r10 = r4.toString()     // Catch:{ all -> 0x0074 }
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ all -> 0x0074 }
            java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x0074 }
            android.util.Log.d(r8, r9)     // Catch:{ all -> 0x0074 }
            if (r5 == 0) goto L_0x0064
            r5.close()     // Catch:{ IOException -> 0x006f }
        L_0x0064:
            if (r0 == 0) goto L_0x0038
            r0.close()     // Catch:{ IOException -> 0x006a }
            goto L_0x0038
        L_0x006a:
            r3 = move-exception
            r3.printStackTrace()
            goto L_0x0038
        L_0x006f:
            r3 = move-exception
            r3.printStackTrace()
            goto L_0x0064
        L_0x0074:
            r8 = move-exception
        L_0x0075:
            if (r5 == 0) goto L_0x007a
            r5.close()     // Catch:{ IOException -> 0x0080 }
        L_0x007a:
            if (r0 == 0) goto L_0x007f
            r0.close()     // Catch:{ IOException -> 0x0085 }
        L_0x007f:
            throw r8
        L_0x0080:
            r3 = move-exception
            r3.printStackTrace()
            goto L_0x007a
        L_0x0085:
            r3 = move-exception
            r3.printStackTrace()
            goto L_0x007f
        L_0x008a:
            r3 = move-exception
            r3.printStackTrace()
            goto L_0x0031
        L_0x008f:
            r3 = move-exception
            r3.printStackTrace()
        L_0x0093:
            r0 = r1
            r5 = r6
            goto L_0x0038
        L_0x0096:
            r8 = 0
            goto L_0x003e
        L_0x0098:
            r8 = move-exception
            r5 = r6
            goto L_0x0075
        L_0x009b:
            r8 = move-exception
            r0 = r1
            r5 = r6
            goto L_0x0075
        L_0x009f:
            r4 = move-exception
            goto L_0x0047
        L_0x00a1:
            r4 = move-exception
            r5 = r6
            goto L_0x0047
        */
        throw new UnsupportedOperationException("Method not decompiled: com.claro.farmagro.util.Util.getFile(java.lang.String):byte[]");
    }

    public static String getFileContents(final String sRuta) throws IOException {
        final InputStream inputStream = new FileInputStream(sRuta);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        final StringBuilder stringBuilder = new StringBuilder();

        boolean done = false;

        while (!done) {
            final String line = reader.readLine();
            done = (line == null);

            if (line != null) {
                stringBuilder.append(line);
            }
        }

        reader.close();
        inputStream.close();

        return stringBuilder.toString();
    }
}
