package com.example.big.zxingandroid;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.big.zxingandroid.activities.ScanActivity;
import com.example.big.zxingandroid.fragment.BarcodeFragment;
import com.example.big.zxingandroid.fragment.PageFragment;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class MainActivity extends AppCompatActivity {
  private Button btnClick;
  private TextView tvResult;

  //CustomSwipableViewPager viewPager;
  FragmentPagerItemAdapter adapter;
  SmartTabLayout viewpagerTab;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //initView();
    generateHeadMenu();
    //initEvent();
    //        new IntentIntegrator(this).initiateScan();
  }

  public void strapScan(View view) {
    new IntentIntegrator(this).setCaptureActivity(ScanActivity.class)
        .setBeepEnabled(true)
        .initiateScan();
  }

  //private void initView() {
  //  //viewPager = (CustomSwipableViewPager) findViewById(R.id.viewpager);
  //  viewpagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
  //}

  private void initEvent() {
    btnClick.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        //假如你要用的是fragment进行界面的跳转
        //IntentIntegrator intentIntegrator = IntentIntegrator.forSupportFragment(ShopFragment.this).setCaptureActivity(CustomScanAct.class);
        IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
            .setPrompt("将二维码/条码放入框内，即可自动扫描")//写那句提示的话
            .setOrientationLocked(false)//扫描方向固定
            .setCaptureActivity(ScanActivity.class) // 设置自定义的activity是CustomActivity
            .initiateScan(); // 初始化扫描
      }
    });
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
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

  private void generateHeadMenu() {
    //Load Header Menu

    FragmentPagerItems pages = FragmentPagerItems.with(this)
        .add("Page 1", PageFragment.class)
        .add("Page 2", PageFragment.class)
        .add("Page 3", BarcodeFragment.class)
        .add("Page 4", PageFragment.class)
        .create();
    adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);

    CustomSwipableViewPager viewPager = (CustomSwipableViewPager) findViewById(R.id.viewpager);
    final SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);

    viewPager.setOnSetCurrentItem(new CustomSwipableViewPager.OnSetCurrentItem() {
      @Override public void setCurrentItem(int currentItem) {
        Log.e("Tom testing", "setCurrentItem: " + currentItem);
      }
    });

    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.e("Tom testing", "onPageScrolled: "
            + position
            + " positionOffset:"
            + positionOffset
            + " positionOffsetPixels:"
            + positionOffsetPixels);
      }

      @Override public void onPageSelected(int position) {
        adapter.getItem(position);
        //slidingupArrowPanel.setVisibility(View.VISIBLE);
      }

      @Override public void onPageScrollStateChanged(int state) {
        Log.e("Tom testing", "onPageScrollStateChanged: " + state);
      }
    });

    final LayoutInflater inflater = LayoutInflater.from(this);
    final Resources res = getResources();

    viewPagerTab.setCustomTabView(new SmartTabLayout.TabProvider() {
      @Override public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
        final View itemView = inflater.inflate(R.layout.custom_header_tab, container, false);
        TextView text = (TextView) itemView.findViewById(R.id.customHeaderTabTitle);
        text.setText(adapter.getPageTitle(position));
        final ImageView icon = (ImageView) itemView.findViewById(R.id.customHeaderTabIcon);

        switch (position) {
          case 0:
            icon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_topup_old, null));
            break;
          case 1:
            icon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_transfer_old, null));
            break;
          case 2:
            icon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_scan, null));
            break;
          case 3:
            icon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_pay_old, null));
            break;
          default:
            throw new IllegalStateException("Invalid position: " + position);
        }

        return itemView;
      }
    });

    viewPager.setAdapter(adapter);
    viewPager.setPagingEnabled(true);
    viewPagerTab.setViewPager(viewPager);

    viewPagerTab.post(new Runnable() {
      @Override public void run() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int intScreenHeight = displayMetrics.heightPixels;
        int intTabHeight = viewPagerTab.getMeasuredHeight();

        int intStatusHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
          intStatusHeight = getResources().getDimensionPixelSize(resourceId);
        }
        int intSlidingHeight = intScreenHeight - intTabHeight - intStatusHeight;
        float fRatio = intSlidingHeight / (float) (intScreenHeight);
      }
    });
  }
}
