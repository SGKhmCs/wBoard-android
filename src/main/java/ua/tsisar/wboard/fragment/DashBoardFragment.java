package ua.tsisar.wboard.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.tsisar.wboard.R;

public class DashBoardFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dash_board,  container, false);
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }
}
