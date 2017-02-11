package com.gerquinn.heritagevancouver.helpers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RemotePictureSearch {
	
	private String picNameToSearch;
	private static String picsUrl = "http://quinntechssential.com/heritage_vancouver/user_img_upload";
	public static void main(String[] args){
		
		RemotePictureSearch picSearch = new RemotePictureSearch();
		
		//Try different Directory and Filename
		picSearch.searchDirectory(new File(picsUrl), "post.php");
		
		int count = picSearch.getResult().size();
		if(count == 0){
			System.out.println("No photos availible, please check connection");
		}else{
			for(String matched : picSearch.getResult()){
				System.out.println("Found : " + matched);
			}
		}
	}
	
	private List<String> result = new ArrayList<String>();
	
	public RemotePictureSearch() {
		
	}
	
	public String getPicNameToSearch(){
		return picNameToSearch;
	}
	
	public List<String> getResult(){
		return result;
	}
	
	private void search(File file){
		
		if(file.isDirectory()){
			if(file.canRead()){
				for(File temp : file.listFiles()){
					if(temp.isDirectory()){
						search(temp);
					}else{
						if(getPicNameToSearch().equals(temp.getName())){
							result.add(temp.getAbsolutePath().toString());
						}
					}
				}
			}
		}
	}
	
	public void searchDirectory(File directory, String picNameToSearch){
		
		setPicNameToSearch(picNameToSearch);
		
		if(directory.isDirectory()){
			search(directory);
		}else{
			System.out.println(directory.getAbsoluteFile() + " is not a directory");
		}
	}
	
	public void setPicNameToSearch(String picNameToSearch){
		this.picNameToSearch = picNameToSearch;
	}

}
