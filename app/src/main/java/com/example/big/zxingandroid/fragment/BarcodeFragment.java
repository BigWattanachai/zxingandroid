package com.example.big.zxingandroid.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.big.zxingandroid.R;
import com.example.big.zxingandroid.activities.ScanActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;

public class BarcodeFragment extends Fragment {
  @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_barcode, container, false);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initEvent();
    int position = FragmentPagerItem.getPosition(getArguments());
  }

  private void initEvent() {
    IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
    intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        .setPrompt("将二维码/条码放入框内，即可自动扫描")
        .setOrientationLocked(false)
        .setCaptureActivity(ScanActivity.class)
        .initiateScan(); // 初始化扫描
  }
}
