package com.example.big.zxingandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
    private Button btnClick;
    private TextView tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
//        new IntentIntegrator(this).initiateScan();
    }

    public void strapScan(View view){
        new IntentIntegrator(this).setCaptureActivity(ScanActivity.class).setBeepEnabled(true).initiateScan();
    }

    private void initView() {
        btnClick = (Button) findViewById(R.id.btn_click);
        tvResult = (TextView) findViewById(R.id.tv_result);
    }

    private void initEvent() {
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //假如你要用的是fragment进行界面的跳转
                //IntentIntegrator intentIntegrator = IntentIntegrator.forSupportFragment(ShopFragment.this).setCaptureActivity(CustomScanAct.class);
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                intentIntegrator
                        .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
                        .setPrompt("将二维码/条码放入框内，即可自动扫描")//写那句提示的话
                        .setOrientationLocked(false)//扫描方向固定
                        .setCaptureActivity(ScanActivity.class) // 设置自定义的activity是CustomActivity
                        .initiateScan(); // 初始化扫描
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {

            } else {
                // ScanResult 为获取到的字符串
                String ScanResult = intentResult.getContents();
                tvResult.setText(ScanResult);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
