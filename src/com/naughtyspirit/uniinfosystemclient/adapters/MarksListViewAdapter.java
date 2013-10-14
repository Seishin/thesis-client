package com.naughtyspirit.uniinfosystemclient.adapters;

import java.util.ArrayList;

import com.naughtyspirit.uniinfosystemclient.R;
import com.naughtyspirit.uniinfosystemclient.models.Mark;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MarksListViewAdapter extends BaseAdapter {

	private Context ctx;
	private ArrayList<Mark> marks;

	// Views
	private View view;

	static class ViewHolder {
		public TextView date;
		public TextView title;
		public TextView mark;
		public TextView year;
		public TextView semester;
	}

	public MarksListViewAdapter(Context ctx, ArrayList<Mark> marks) {
		this.ctx = ctx;
		this.marks = marks;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		view = convertView;
		
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.layout_mark, parent, false);

			ViewHolder viewHolder = new ViewHolder();
			
			viewHolder.title = (TextView) view.findViewById(R.id.title);
			viewHolder.mark = (TextView) view.findViewById(R.id.mark);
			viewHolder.date = (TextView) view.findViewById(R.id.date);
			viewHolder.year = (TextView) view.findViewById(R.id.year);
			viewHolder.semester = (TextView) view.findViewById(R.id.semester);
			
			view.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) view.getTag();
		
		holder.title.setText(marks.get(position).getTitle());
		holder.mark.setText(marks.get(position).getMark());
		holder.date.setText(marks.get(position).getDate());
		holder.year.setText(String.valueOf(marks.get(position).getYear()));
		holder.semester.setText(String.valueOf(marks.get(position).getSemester()));
		
		return view;
	}

	@Override
	public int getCount() {
		return marks.size();
	}

	@Override
	public Object getItem(int position) {
		return marks.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
}
