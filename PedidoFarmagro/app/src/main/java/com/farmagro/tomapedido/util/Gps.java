package com.farmagro.tomapedido.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.apache.http.HttpHeaders;

public class Gps {
    public static int TEN_SECONDS = 10000;
    public static Context context;
    static LocationListener a = new LocationListener() {
        public void onStatusChanged(String paramString, int paramInt, Bundle paramBundle) {
        }

        public void onProviderEnabled(String paramString) {
        }

        public void onProviderDisabled(String paramString) {
        }

        public void onLocationChanged(Location paramLocation) {
            Log.d(HttpHeaders.LOCATION, "Location Received: " + paramLocation.getLatitude() + "; " + paramLocation.getLongitude());
            try {
                if (Gps.isBetterLocation(paramLocation, Gps.currentBestLocation)) {
                    Gps.currentBestLocation = paramLocation;
                }
            } catch (Exception localException) {
                Log.e(HttpHeaders.LOCATION, "onLocationChanged", localException);
            }
        }
    };
    private static LocationManager b;
    private static boolean c = false;
    public static Location currentBestLocation;
    private static boolean d = false;
    private static boolean e = false;
    private static LocationResult f;
    public static String mensaje;

    public static abstract class LocationResult {
        public abstract void getLocation(Location location);
    }

    public static boolean isCanGetLocation() {
        return e;
    }

    public static void setCanGetLocation(boolean paramBoolean) {
        e = paramBoolean;
    }

    public static void setLocationResult(LocationResult paramLocationResult) {
        f = paramLocationResult;
    }

    public static boolean startListening(Context paramContext) {
        context = paramContext;
        if (b == null) {
            b = (LocationManager) paramContext.getSystemService(Context.LOCATION_SERVICE);
        }
        System.out.println(b.getAllProviders().toString());
        try {
            c = b.isProviderEnabled("gps");
        } catch (Exception e2) {
        }
        try {
            d = b.isProviderEnabled("network");
        } catch (Exception e3) {
        }
        if (c || d) {
            if (c) {
                setCanGetLocation(true);
                /*
                int permiso = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
                if(permiso ==  PackageManager.PERMISSION_DENIED){
                    if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_COARSE_LOCATION)){

                    }else{
                        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    }
                }
                */
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.

                }else{
                    b.requestLocationUpdates("gps", (long) TEN_SECONDS, 10.0f, a);
                    System.out.println("GPS Registered");
                    mensaje = "GPS Registered";
                }
            }
            if (d) {
                setCanGetLocation(true);
                b.requestLocationUpdates("network", (long) TEN_SECONDS, 10.0f, a);
                System.out.println("Network Registered");
                mensaje = "Network Registered";
            }
            return true;
        }
        setCanGetLocation(false);
        return false;
    }

    public static boolean startListening(Context paramContext, LocationResult paramLocationResult) {
        setLocationResult(paramLocationResult);
        return startListening(paramContext);
    }

    public static Location getLocation() throws Exception {
        if (currentBestLocation == null) {
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(YourService.this, "First enable LOCATION ACCESS in settings.", Toast.LENGTH_LONG).show();
                //return;
            }
            //if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

                Location localLocation1 = b.getLastKnownLocation("gps");
                Location localLocation2 = b.getLastKnownLocation("network");
                if (c && d) {
                    if (isBetterLocation(localLocation1, localLocation2)) {
                        currentBestLocation = localLocation1;
                        mensaje = "GPS Registered";
                    } else {
                        currentBestLocation = localLocation2;
                        mensaje = "Network Registered";
                    }
                } else if (c) {
                    mensaje = "GPS Registered";
                    currentBestLocation = localLocation1;
                } else if (d) {
                    mensaje = "Network Registered";
                    currentBestLocation = localLocation2;
                }

        }
        return currentBestLocation;
    }

    public static String getMensaje() {
        return mensaje;
    }

    public static void stopListening() {
        try {
            b.removeUpdates(a);
            f = null;
        } catch (NullPointerException e2) {
        }
    }

    public static String getLocationState() {
        StringBuilder localStringBuilder = new StringBuilder("network: ");
        if (d) {
            localStringBuilder.append("ON");
        } else {
            localStringBuilder.append("OFF");
        }
        localStringBuilder.append("\ngps: ");
        if (c) {
            localStringBuilder.append("ON");
        } else {
            localStringBuilder.append("OFF");
        }
        return localStringBuilder.toString();
    }

    protected static boolean isBetterLocation(Location paramLocation1, Location paramLocation2) throws Exception {
        if (paramLocation2 == null) {
            return true;
        }
        long l = paramLocation1.getTime() - paramLocation2.getTime();
        int i = l > 120000 ? 1 : 0;
        int j = l < -120000 ? 1 : 0;
        int k = l > 0 ? 1 : 0;
        if (i != 0) {
            return true;
        }
        if (j != 0) {
            return false;
        }
        int m = (int) (paramLocation1.getAccuracy() - paramLocation2.getAccuracy());
        int n = m > 0 ? 1 : 0;
        int i1 = m < 0 ? 1 : 0;
        int i2 = m > 200 ? 1 : 0;
        boolean bool = a(paramLocation1.getProvider(), paramLocation2.getProvider());
        if (i1 != 0) {
            return true;
        }
        if (k == 0 || n != 0) {
            return k != 0 && i2 == 0 && bool;
        }
        return true;
    }

    private static boolean a(String paramString1, String paramString2) {
        if (paramString1 == null) {
            return paramString2 == null;
        }
        return paramString1.equals(paramString2);
    }
}

