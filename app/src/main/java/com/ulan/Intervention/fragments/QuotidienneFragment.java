package com.ulan.Intervention.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.ulan.Intervention.adapters.WeekAdapter;
import com.ulan.Intervention.utils.DbHelper;
import com.ulan.Intervention.R;
import com.ulan.Intervention.utils.FragmentHelper;

import java.time.Month;


public class QuotidienneFragment extends Fragment {

    public static final String KEY_FRAGMENT = "";
    private DbHelper db;
    private ListView listView;
    private WeekAdapter adapter;
    private ImageView popup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quotidienne, container, false);
        setupAdapter(view);
        setupListViewMultiSelect();

        return view;
    }

    private void setupAdapter(View view) {
        db = new DbHelper(getActivity());
        listView = view.findViewById(R.id.quotidiennelist);
        adapter = new WeekAdapter(getActivity(), listView, R.layout.listview_week_adapter, db.getWeek(KEY_FRAGMENT));
        listView.setAdapter(adapter);
    }

    private void setupListViewMultiSelect() {
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(FragmentHelper.setupListViewMultiSelect(getActivity(), listView, adapter, db));
    }
}