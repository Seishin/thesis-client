package com.naughtyspirit.uniinfosystemclient.fragments;

import java.util.concurrent.ExecutionException;

import com.naughtyspirit.uniinfosystemclient.R;
import com.naughtyspirit.uniinfosystemclient.helpers.JsonParserHelper;
import com.naughtyspirit.uniinfosystemclient.models.StudentInfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StudentInfoFragment extends Fragment {
	
	// UI components
	private View view;
	
	private TextView name;
	private TextView facultyNumber;
	private TextView faculty;
	private TextView major;
	private TextView degree;
	private TextView status;
	
	private TextView pin;
	private TextView birthDate;
	private TextView nationality;
	private TextView year;
	
	private StudentInfo profileInfo;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.fragment_student_info, container, false);
		
		try {
			profileInfo = JsonParserHelper.getInstance().getStudentInfo(getActivity());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		initUI();
		
		return view;
	}
	
	private void initUI() {
		
		getActivity().setTitle(getActivity().getString(R.string.profile_fragment_title));
		
		name = (TextView) view.findViewById(R.id.name);
		name.setText(profileInfo.getFirst_name() + " " + profileInfo.getLast_name());
		
		facultyNumber = (TextView) view.findViewById(R.id.faculty_number);
		facultyNumber.setText(profileInfo.getFaculty_number());
		
		faculty = (TextView) view.findViewById(R.id.faculty);
		faculty.setText(profileInfo.getFaculty());
		
		major = (TextView) view.findViewById(R.id.major);
		major.setText(profileInfo.getMajor());
		
		degree = (TextView) view.findViewById(R.id.degree);
		degree.setText(profileInfo.getDegree());
		
		status = (TextView) view.findViewById(R.id.status);
		status.setText(profileInfo.getStatus());
		
		pin = (TextView) view.findViewById(R.id.pin);
		pin.setText(profileInfo.getPin());
		
		birthDate = (TextView) view.findViewById(R.id.birth_date);
		birthDate.setText(profileInfo.getBirth_date());
		
		nationality = (TextView) view.findViewById(R.id.nationality);
		nationality.setText(profileInfo.getNationality());
		
		year = (TextView) view.findViewById(R.id.year);
		year.setText(String.valueOf(profileInfo.getYear()));
	}
 }
