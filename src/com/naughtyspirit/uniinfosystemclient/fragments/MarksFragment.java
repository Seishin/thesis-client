package com.naughtyspirit.uniinfosystemclient.fragments;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.naughtyspirit.uniinfosystemclient.R;
import com.naughtyspirit.uniinfosystemclient.adapters.MarksListViewAdapter;
import com.naughtyspirit.uniinfosystemclient.helpers.JsonParserHelper;
import com.naughtyspirit.uniinfosystemclient.models.Mark;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class MarksFragment extends Fragment {

	// UI components
	private View view;
	private ListView marksList;

	private ArrayList<Mark> marks = new ArrayList<Mark>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.fragment_marks, container, false);
		
		try {
			marks = JsonParserHelper.getInstance().getMarks(getActivity());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		initUI();
		
		return view;
	}
	
	private void initUI() {
		
		getActivity().setTitle(R.string.marks_title);
		
		marksList = (ListView) view.findViewById(R.id.marks);
		marksList.setAdapter(new MarksListViewAdapter(getActivity(), marks));
	}
}
