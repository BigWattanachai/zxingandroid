package com.example.big.zxingandroid.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.big.zxingandroid.R;
import com.example.big.zxingandroid.view.BarcodeActivity;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;

public class PageFragment extends Fragment {

  @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_page, container, false);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    int position = FragmentPagerItem.getPosition(getArguments());
    if (4 == position || 3 == position) {
      Intent intent = new Intent(getActivity(), BarcodeActivity.class);
      getActivity().startActivity(intent);
    }
    TextView title = (TextView) view.findViewById(R.id.item_title);
    title.setText(String.valueOf(position));
  }
}
