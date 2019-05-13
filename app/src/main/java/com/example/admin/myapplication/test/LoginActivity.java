package com.example.admin.myapplication.test;
import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.admin.myapplication.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private static int REQUEST_CODE_TAKE_PICTURE = 0;
    private ImageView imageView;
    private TextView textView;
    private static final String[] PERMISSION_STARTWELCOMEGUIDEACTIVITY = new
            String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = new Intent(this, TestService.class);
        startService(intent);
        imageView = findViewById(R.id.iv_show_image);
        textView = findViewById(R.id.tv_show_name);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getCurrentPosition();
            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //启动相机的关键
//                if (openCameraIntent.resolveActivity(getPackageManager()) != null) {
//                    startActivityForResult(openCameraIntent, REQUEST_CODE_TAKE_PICTURE);
//                }
            }
        });
    }

    private void getCurrentPosition() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // 返回所有已知的位置提供者的名称列表，包括未获准访问或调用活动目前已停用的。
        List<String> lp = lm.getAllProviders();
        for (String item : lp) {
            Log.i("8023", "可用位置服务：" + item);
        }
        Criteria criteria = new Criteria();
        criteria.setCostAllowed(false);
        //设置位置服务免费
        criteria.setAccuracy(Criteria.ACCURACY_COARSE); //设置水平位置精度
        //getBestProvider 只有允许访问调用活动的位置供应商将被返回
        String providerName = lm.getBestProvider(criteria, true);
        Log.i("8023", "------位置服务：" + providerName);
//        if (providerName != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSION_STARTWELCOMEGUIDEACTIVITY, 100);
            }
            Location location = lm.getLastKnownLocation(providerName);
            Log.i("8023", "-------"+location);
            //获取维度信息
            double latitude = location.getLatitude();
            //获取经度信息
            double longitude = location.getLongitude();
            textView.setText("定位方式： "+providerName+"  维度："+latitude+"  经度："+longitude);
//        }
//        else
//        {
//            Toast.makeText(this, "1.请检查网络连接 \n2.请打开我的位置", Toast.LENGTH_SHORT).show();
//        }

        Intent fullScreenIntent = new Intent(this, TestActivity.class);
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(this, 0,
                fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);
         Notification notificationBuilder = new NotificationCompat.Builder(this, "")
                 .setContentTitle("是否启动应用")
                .setContentText("后台有应用需要唤醒")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_CALL)
                .setFullScreenIntent(fullScreenPendingIntent, true)
                .build();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if ( resultCode == RESULT_OK) {
                    Bitmap bm = (Bitmap) data.getExtras().get("data");
                    imageView.setImageBitmap(bm);
                } else {
                    Log.e("test", "failed");
                }
                break;
        }
    }

    public static void savePhotoToSDCard(String path, String photoName, Bitmap photoBitmap) {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File dir = new File(path);
            if(!dir.exists()) {
                dir.mkdirs();
            }
            File photoFile = new File(path, photoName);
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream  =new FileOutputStream(photoName);
                if(photoBitmap != null) {
                    if(photoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)) {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }
    }

}
