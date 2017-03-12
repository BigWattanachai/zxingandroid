package com.example.big.zxingandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.big.zxingandroid.fragment.BarcodeFragment;
import com.example.big.zxingandroid.fragment.BarcodeFragmentInterface;
import com.example.big.zxingandroid.fragment.PageFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class MainActivity extends AppCompatActivity {
  FragmentPagerItemAdapter adapter;
  BarcodeFragmentInterface barcodeFragmentInterface;
  int tabPosition;
  Boolean isFromBarCode = false;
  SmartTabLayout viewPagerTab;
  CustomSwipeAbleViewPager viewPager;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

    generateHeadMenu();
    if("android.intent.action.MAIN".equalsIgnoreCase(getIntent().getAction())){
      viewPager.setCurrentItem(0);
    }else if(viewPager != null) {
      viewPager.setCurrentItem(2);

    }
  }

  private void generateHeadMenu() {
    //Load Header Menu
    FragmentPagerItems pages = FragmentPagerItems.with(this)
        .add("Page 1", PageFragment.class)
        .add("Page 2", PageFragment.class)
        .add("Page 3", PageFragment.class)
        .add("Page 4", BarcodeFragment.class)
        .create();
    adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);

    viewPager = (CustomSwipeAbleViewPager) findViewById(R.id.viewpager);
    viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);

    viewPager.setOnSetCurrentItem(new CustomSwipeAbleViewPager.OnSetCurrentItem() {
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
        tabPosition = position;
        adapter.getItem(position);
        if (position == 3) {
          isFromBarCode = true;
          barcodeFragmentInterface = new BarcodeFragment();
          barcodeFragmentInterface.startBarCode(MainActivity.this);
        } else {
          isFromBarCode = false;
        }
      }

      @Override public void onPageScrollStateChanged(int state) {
        Log.e("Tom testing", "onPageScrollStateChanged: " + state);
      }
    });

    final LayoutInflater inflater = LayoutInflater.from(this);

    viewPagerTab.setCustomTabView(new SmartTabLayout.TabProvider() {
      @Override public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
        final View itemView = inflater.inflate(R.layout.custom_header_tab, container, false);
        TextView text = (TextView) itemView.findViewById(R.id.customHeaderTabTitle);
        text.setText(adapter.getPageTitle(position));
        final ImageView icon = (ImageView) itemView.findViewById(R.id.customHeaderTabIcon);

        switch (position) {
          case 0:
            icon.setImageDrawable(
                ResourcesCompat.getDrawable(getResources(), R.drawable.ic_topup_old, null));
            break;
          case 1:
            icon.setImageDrawable(
                ResourcesCompat.getDrawable(getResources(), R.drawable.ic_transfer_old, null));
            break;
          case 2:
            icon.setImageDrawable(
                ResourcesCompat.getDrawable(getResources(), R.drawable.ic_pay_old, null));
            break;
          case 3:
            icon.setImageDrawable(
                ResourcesCompat.getDrawable(getResources(), R.drawable.ic_scan_old, null));
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

  public int getCurrenPageActive() {
    return tabPosition;
  }
}
