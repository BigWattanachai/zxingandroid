package com.example.big.zxingandroid.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.big.zxingandroid.R;
import com.example.big.zxingandroid.activities.ScanActivity;
import com.google.zxing.integration.android.IntentIntegrator;

public class BarcodeActivity extends AppCompatActivity {
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_barcode);
    initEvent();
  }

  private void initEvent() {
    IntentIntegrator intentIntegrator = new IntentIntegrator(BarcodeActivity.this);
    intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        .setPrompt("将二维码/条码放入框内，即可自动扫描")
        .setOrientationLocked(false)
        .setCaptureActivity(ScanActivity.class)
        .initiateScan(); // 初始化扫描
  }
}
