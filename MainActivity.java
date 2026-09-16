package com.example.locationsimulator;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.*;
import android.view.View;

public class MainActivity extends Activity {
    EditText lat, lon, interval; TextView status;
    @Override public void onCreate(Bundle b) { super.onCreate(b); setContentView(R.layout.activity_main);
        lat=findViewById(R.id.latitude); lon=findViewById(R.id.longitude); interval=findViewById(R.id.interval); status=findViewById(R.id.mockStatus);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED) requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},10);
        findViewById(R.id.startButton).setOnClickListener(v -> startMock());
        findViewById(R.id.stopButton).setOnClickListener(v -> stopMock());
        findViewById(R.id.settingsButton).setOnClickListener(v -> { Intent i=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS); startActivity(i); });
    }
    void startMock(){ try { double a=Double.parseDouble(lat.getText().toString().trim()), o=Double.parseDouble(lon.getText().toString().trim()); int s=interval.getText().toString().trim().isEmpty()?1:Math.max(1,Integer.parseInt(interval.getText().toString().trim())); if(a<-90||a>90||o<-180||o>180) throw new Exception(); Intent i=new Intent(this,MockLocationService.class).putExtra("lat",a).putExtra("lon",o).putExtra("interval",s*1000L); startForegroundService(i); status.setText("Status: Running  •  "+a+", "+o); } catch(Exception e){ Toast.makeText(this,"Enter valid latitude, longitude and interval.",Toast.LENGTH_LONG).show(); } }
    void stopMock(){ stopService(new Intent(this,MockLocationService.class)); status.setText("Status: Stopped"); }
}
