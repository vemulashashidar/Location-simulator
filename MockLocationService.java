package com.example.locationsimulator;

import android.app.*; import android.content.*; import android.location.*; import android.os.*; import java.util.*;

public class MockLocationService extends Service {
    LocationManager lm; Handler h; Runnable r; double lat,lon; long interval; final String provider="gps"; final int ID=42;
    @Override public int onStartCommand(Intent in,int flags,int id){ lat=in.getDoubleExtra("lat",0); lon=in.getDoubleExtra("lon",0); interval=in.getLongExtra("interval",1000); startForeground(ID,notification()); setup(); return START_STICKY; }
    Notification notification(){ String ch="mock_location"; NotificationManager nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE); if(Build.VERSION.SDK_INT>=26) nm.createNotificationChannel(new NotificationChannel(ch,"Mock Location",NotificationManager.IMPORTANCE_LOW)); return new Notification.Builder(this,ch).setContentTitle("Location Simulator").setContentText("Mock GPS active: "+lat+", "+lon).setSmallIcon(android.R.drawable.ic_menu_mylocation).setOngoing(true).build(); }
    void setup(){ lm=(LocationManager)getSystemService(LOCATION_SERVICE); try { if(lm.getProvider(provider)==null || !lm.getProvider(provider).getName().equals(provider)){} } catch(Exception ignored){} try { lm.removeTestProvider(provider); } catch(Exception ignored){} try { lm.addTestProvider(provider,true,true,false,false,true,true,true,0,5); } catch(IllegalArgumentException ignored) {} catch(SecurityException e){ stopSelf(); return; }
        try { lm.setTestProviderEnabled(provider,true); } catch(Exception ignored){}
        h=new Handler(Looper.getMainLooper()); r=()->{ push(); h.postDelayed(r,interval); }; h.post(r);
    }
    void push(){ try { Location x=new Location(provider); x.setLatitude(lat); x.setLongitude(lon); x.setAccuracy(1f); x.setAltitude(0); x.setSpeed(0); x.setBearing(0); x.setTime(System.currentTimeMillis()); x.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos()); lm.setTestProviderLocation(provider,x); } catch(Exception ignored){} }
    @Override public void onDestroy(){ if(h!=null&&r!=null)h.removeCallbacks(r); try{lm.setTestProviderEnabled(provider,false);lm.removeTestProvider(provider);}catch(Exception ignored){} stopForeground(true); super.onDestroy(); }
    @Override public android.os.IBinder onBind(Intent i){return null;}
}
