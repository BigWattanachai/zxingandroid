package com.example.big.zxingandroid.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.big.zxingandroid.MainActivity;
import com.example.big.zxingandroid.R;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;

public class PageFragment extends Fragment {

  private static final String TAG = "PageFragment";
  protected MainActivity mActivity;

  @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_page, container, false);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    int position = FragmentPagerItem.getPosition(getArguments());
    Log.d(TAG, "onViewCreated - position: " + position);

    TextView title = (TextView) view.findViewById(R.id.item_title);
    title.setText(String.valueOf(position));
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    mActivity = (MainActivity) activity;
  }
}
