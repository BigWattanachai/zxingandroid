package com.example.big.zxingandroid;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by BiG on 3/11/2017 AD.
 */

public class CustomSwipeAbleViewPager extends ViewPager {

  private boolean enabled;

  public CustomSwipeAbleViewPager.OnSetCurrentItem onSetCurrentItem;

  public interface OnSetCurrentItem{
    void setCurrentItem(int currentItem);
  }

  public void setOnSetCurrentItem(CustomSwipeAbleViewPager.OnSetCurrentItem onSetCurrentItem){
    this.onSetCurrentItem = onSetCurrentItem;
  }

  public CustomSwipeAbleViewPager(Context context) {
    super(context);
  }

  public CustomSwipeAbleViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent event) {

    if (this.enabled) {
      return super.onInterceptTouchEvent(event);
    }

    //return false for nonSwipeableViewPager
    return false;
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {

    if (this.enabled) {
      return super.onTouchEvent(event);
    }

    //return false for nonSwipeableViewPager
    return false;
  }


  @Override
  public void setCurrentItem(int item) {
    super.setCurrentItem(item);

    if(onSetCurrentItem != null){
      onSetCurrentItem.setCurrentItem(item);
    }
  }

  public void setPagingEnabled(boolean enabled) {
    this.enabled = enabled;
  }
}
