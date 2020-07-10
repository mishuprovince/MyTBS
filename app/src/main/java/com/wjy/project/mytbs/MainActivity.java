package com.wjy.project.mytbs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_pdf,btn_excel;
    private String pdfUrl = "http://www.blueerdos.top/dwz/Public/upload/123.pdf";
    private String excelUrl = "http://www.blueerdos.top/dwz/Public/upload/345.xlsx";
    private String wordUrl = "http://www.blueerdos.top/dwz/Public/upload/567.pptx";
    private String pptUrl = "http://www.blueerdos.top/dwz/Public/upload/789.pptx";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shouquan();
        initView();
    }

    private void initView(){
        btn_pdf = findViewById(R.id.btn_pdf);
        btn_pdf.setOnClickListener(this);
        btn_excel = findViewById(R.id.btn_excel);
        btn_excel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_pdf:
                Intent intent = new Intent(MainActivity.this,FileDisplayActivity.class);
                intent.putExtra("filePath",pdfUrl);
                startActivity(intent);
                break;
            case R.id.btn_excel:
                Intent intent2 = new Intent(MainActivity.this,FileDisplayActivity.class);
                intent2.putExtra("filePath",excelUrl);
                startActivity(intent2);
                break;
            case R.id.btn_word:
                Intent intent3 = new Intent(MainActivity.this,FileDisplayActivity.class);
                intent3.putExtra("filePath",wordUrl);
                startActivity(intent3);
                break;
            case R.id.btn_ppt:
                Intent intent4 = new Intent(MainActivity.this,FileDisplayActivity.class);
                intent4.putExtra("filePath",pptUrl);
                startActivity(intent4);
                break;

        }
    }

    private void shouquan() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//如果 API level 是大于等于 23(Android 6.0) 时  
            //判断是否具有权限  
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //判断是否需要向用户解释为什么需要申请该权限  
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    Toast.makeText(MainActivity.this, "自Android 6.0开始需要打开位置权限", Toast.LENGTH_SHORT).show();
                }
                //请求权限  
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.INTERNET,
                                Manifest.permission.ACCESS_NETWORK_STATE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.ACCESS_WIFI_STATE,
                                Manifest.permission.CHANGE_WIFI_STATE,
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        2);
            }
        }
    }
}
