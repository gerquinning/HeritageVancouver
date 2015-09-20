package com.gerquinn.heritagevancouver.adapters;

import java.util.ArrayList;
import java.util.List;

import com.gerquinn.heritagevancouver.R;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class CheckListAdapter extends ArrayAdapter<String>{
	
	List <PackageInfo> packageList;
	ArrayList <String> addressArray;
	Activity context;
	boolean[] itemChecked;
	TextView addressName;
	CheckBox ck1;
	Button btnUpload;
	
	public CheckListAdapter(Activity context, ArrayList <String> addressArray){
		super(context, android.R.layout.simple_list_item_1, addressArray);
		this.context = context;
		this.addressArray = addressArray;
		itemChecked = new boolean[addressArray.size()];
	}
	
	private class ViewHolder{
		TextView addressName;
		CheckBox ck1;
	}
	
	@Override
	public int getCount(){
		return addressArray.size();
	}
	
	@Override
	public String getItem(int position){
		return addressArray.get(position);
	}
	
	@Override
	public long getItemId(int position){
		return 0;
	}
	
	@Override
	public View getView(final int position, View convertView, final ViewGroup parent){
		
		final ViewHolder holder;
		
		LayoutInflater inflater = context.getLayoutInflater();
		
		if(convertView == null){
			convertView = inflater.inflate(R.layout.upload_checklist, null);
			holder = new ViewHolder();
			
			addressName = (TextView)convertView.findViewById(R.id.uploadAddress);
			ck1 = (CheckBox)convertView.findViewById(R.id.checkBox1);
			
			//convertView.setTag(holder);
		}else{
			//holder = (ViewHolder)convertView.getTag();
		}
		
		addressName.setText(addressArray.get(position));
		ck1.setChecked(false);
		
		btnUpload = (Button)convertView.findViewById(R.id.btnUpload);
		
		/*btnUpload.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});*/
		
		return convertView;
	}
	
	//Sets itemsChecked Array to false
	public void removeAllChecks(int num ){
	    for(int i = 0; i < itemChecked.length - 1; i++){
	        itemChecked[i] = false;
	    }
	    itemChecked[num] = true;

	}
}


