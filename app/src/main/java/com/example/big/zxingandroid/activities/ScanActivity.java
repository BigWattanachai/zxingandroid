package com.example.big.zxingandroid.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import com.example.big.zxingandroid.R;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

public class ScanActivity extends Activity {
  private CaptureManager captureManager;
  private DecoratedBarcodeView decoratedBarcodeView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_scan);

    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

    decoratedBarcodeView = (DecoratedBarcodeView) findViewById(R.id.zxing_barcode_scanner);

    captureManager = new CaptureManager(this, decoratedBarcodeView);
    captureManager.initializeFromIntent(getIntent(), savedInstanceState);
    captureManager.decode();
    decoratedBarcodeView.setStatusText("");
  }

  @Override protected void onResume() {
    super.onResume();
    captureManager.onResume();
  }

  @Override protected void onPause() {
    super.onPause();
    captureManager.onPause();
    decoratedBarcodeView.setTorchOff();
    overridePendingTransition(0, 0);
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    captureManager.onSaveInstanceState(outState);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    captureManager.onDestroy();
  }

  @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
    return decoratedBarcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
  }
}
