/* package com.gerquinn.heritagevancouver.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


@SuppressLint("NewApi")
public class MainToolbarFragment extends Fragment{

	private OnItemSelectedListener listener;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState){
		
		View view = new View(null);
		//View view = inflater.inflate(R.layout.toolbar, container, false);
		
		return view;
		
	}
	
	public interface OnItemSelectedListener{
		public void toolbarState(Boolean isSelected);
	}
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		if(activity instanceof OnItemSelectedListener){
			listener = (OnItemSelectedListener) activity;
		}else{
			throw new ClassCastException(activity.toString()
					+ " must implement Such and Such a Classes Method");
		}
	}
	
	@Override
	public void onDetach(){
		super.onDetach();
		listener = null;
	}
	
	public void updateDetail(){
	}
} */
