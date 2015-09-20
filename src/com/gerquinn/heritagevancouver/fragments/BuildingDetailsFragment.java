package com.gerquinn.heritagevancouver.fragments;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.gerquinn.heritagevancouver.MapActivity;
import com.gerquinn.heritagevancouver.R;
import com.gerquinn.heritagevancouver.helpers.AppController;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class BuildingDetailsFragment extends Fragment{
	
	public String buildingName, address, description, shortImageUrl, imageUrl = " ";
	public double latitude,longitude = 0.0;
	public ArrayList<String> building_array =  new ArrayList<String>();
	public URL url;
	
	public File imgFile;
	public ScrollView Sc1;
	public TextView T1, T2, T3;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	
	public Button but1;
	
	//@Override
	public View onCreatView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//Inflate the layout for this fragment
		LinearLayout lLayout = (LinearLayout) inflater.inflate(R.layout.activity_building_description, container, false);
		
		BuildingDetails();
		
		//TableLayout Table1 = (TableLayout) findViewById(R.id.descriptionTable);
		
		if(imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();
		NetworkImageView image = (NetworkImageView) lLayout.findViewById(R.id.image1);
		
		// Loading image with placeholder and error image
		imageLoader.get(imageUrl, ImageLoader.getImageListener(
				image, R.drawable.img_placeholder, R.drawable.img_placeholder));
		
		image.setImageUrl(imageUrl, imageLoader);
		
		
		T1 = (TextView) lLayout.findViewById(R.id.description1);
		T2 = (TextView) lLayout.findViewById(R.id.description2);
		T3 = (TextView) lLayout.findViewById(R.id.description3);
		but1 = (Button) lLayout.findViewById(R.id.button1);
		
		T1.setText(buildingName);
		T2.setText(address);
		T3.setText(description);
		
		but1.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
				Intent i = new Intent(getActivity(), MapActivity.class);
				i.putExtra("Array Boolean", false);
				i.putExtra("Building Name", buildingName);
				i.putExtra("Building Address", address);
				i.putExtra("Latitude", latitude);
				i.putExtra("Longitude", longitude);
				startActivity(i);
				
				//finish();
			}
		});
		
		return lLayout;
	}
	
	public void BuildingDetails(){
		buildingName = getActivity().getIntent().getExtras().getString("buildingName");
    	address = getActivity().getIntent().getExtras().getString("address");
    	latitude = getActivity().getIntent().getExtras().getDouble("latitude");
    	longitude = getActivity().getIntent().getExtras().getDouble("longitude");
    	description = getActivity().getIntent().getExtras().getString("description");
    	imageUrl = getActivity().getIntent().getExtras().getString("imageUrl");
		
	}
	
	/**
     * Create a new instance of DetailsFragment, initialized to
     * show the text at 'index'.
     */
    public static BuildingDetailsFragment newInstance(int index) {
    	BuildingDetailsFragment f = new BuildingDetailsFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);

        return f;
    }

    public int getShownIndex() {
        return getArguments().getInt("index", 0);
    }
	

}
