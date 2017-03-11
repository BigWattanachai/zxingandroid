package com.example.big.zxingandroid.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import com.example.big.zxingandroid.MainActivity;
import com.example.big.zxingandroid.R;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.liuguangqiang.swipeback.SwipeBackLayout;
import com.liuguangqiang.swipeback.SwipeBackLayout.DragEdge;
import java.util.HashMap;

public class ScanActivity extends Activity {
  private CaptureManager captureManager;
  private DecoratedBarcodeView decoratedBarcodeView;
  SwipeBackLayout swipeBackLayoutContent;
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_scan);

    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

    decoratedBarcodeView = (DecoratedBarcodeView) findViewById(R.id.zxing_barcode_scanner);
    swipeBackLayoutContent = (SwipeBackLayout)findViewById(R.id.swipeback_content_barcode);
    captureManager = new CaptureManager(this, decoratedBarcodeView);
    captureManager.initializeFromIntent(getIntent(), savedInstanceState);
    captureManager.decode();
    decoratedBarcodeView.setStatusText("");
    setSwipeableActivities();
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

  private  void setSwipeableActivities() {
    HashMap<DragEdge, Class<?>> defineDragEdge = new HashMap<>();
    defineDragEdge.put(SwipeBackLayout.DragEdge.LEFT, MainActivity.class);
    defineDragEdge.put(SwipeBackLayout.DragEdge.RIGHT, MainActivity.class);
    swipeBackLayoutContent.setDefinedDragEdge(defineDragEdge);
    swipeBackLayoutContent.setEnableFlingBack(true);
  }
}
