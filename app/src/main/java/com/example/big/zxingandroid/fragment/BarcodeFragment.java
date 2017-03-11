package com.example.big.zxingandroid.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.big.zxingandroid.MainActivity;
import com.example.big.zxingandroid.R;
import com.example.big.zxingandroid.activities.ScanActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;

public class BarcodeFragment extends Fragment implements BarcodeFragmentInterface {
  protected MainActivity mActivity;
  //public BarcodeFragment(Activity activity) {
  //  // Required empty public constructor
  //  mActivity = (MainActivity) activity;
  //}
  @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_barcode, container, false);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    int position = FragmentPagerItem.getPosition(getArguments());
    if (mActivity.getCurrenPageActive() == 2) {
      //initEvent();
    }
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    mActivity = (MainActivity) activity;
  }

  private void initEvent() {
    IntentIntegrator intentIntegrator = new IntentIntegrator(mActivity);
    intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        .setPrompt("将二维码/条码放入框内，即可自动扫描")
        .setOrientationLocked(false)
        .setCaptureActivity(ScanActivity.class)
        .initiateScan(); // 初始化扫描
  }

  @Override public void startBarCode(Activity activity) {
    if(activity != null){

    IntentIntegrator intentIntegrator =new IntentIntegrator(activity);
    intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        .setPrompt("将二维码/条码放入框内，即可自动扫描")
        .setOrientationLocked(false)
        .setCaptureActivity(ScanActivity.class)
        .initiateScan(); // 初始化扫描
    }
  }
}
