package com.gerquinn.heritagevancouver.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.gerquinn.heritagevancouver.R;
import com.gerquinn.heritagevancouver.database.WalkingTourItems;
import com.gerquinn.heritagevancouver.helpers.AppController;

public class CustomListAdapter extends BaseAdapter{
	
	private Activity activity;
	private LayoutInflater inflater;
	private List<WalkingTourItems> walkingTourItems;
	ImageView imageView;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	
	public CustomListAdapter(Activity activity, List<WalkingTourItems> walkingTourItems) {
		this.activity = activity;
		this.walkingTourItems = walkingTourItems;
	}
	
	public CustomListAdapter(String[] values) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		return walkingTourItems.size();
	}

	@Override
	public Object getItem(int position) {
		return walkingTourItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		
		WalkingTourItems wti = walkingTourItems.get(position);
		
		if(inflater == null)
			inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(convertView == null)
			convertView = inflater.inflate(R.layout.building_list_layout, null);
		
		if(imageLoader == null){
			imageLoader = AppController.getInstance().getImageLoader();	
		}
		NetworkImageView thumbnail = (NetworkImageView) convertView.findViewById(R.id.thumbnail);
		TextView header = (TextView)convertView.findViewById(R.id.firstLine);
		TextView yearLabel = (TextView)convertView.findViewById(R.id.secondLine);
		TextView addressLabel = (TextView)convertView.findViewById(R.id.thirdLine);
		
		header.setText(wti.getBuildingName());
		yearLabel.setText(String.valueOf(wti.getYear()));
		addressLabel.setText(wti.getAddress());
		
		//Thumnail Image
		thumbnail.setImageUrl(wti.getThumbUrl(), imageLoader);
		
		return convertView;
	}

}
