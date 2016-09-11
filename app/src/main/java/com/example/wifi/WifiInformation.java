package com.example.wifi;

import java.util.List;
import android.net.wifi.ScanResult;
import com.example.accesspoint.*;;

public class WifiInformation {
	int NUM_OF_AP;
	AverageFilter[] signalLevelFilter;
	List<ScanResult> currentScanList;
	String[] wifiString;
	ListOfLevel myLevels;
	
	WifiInformation()
	{
		NUM_OF_AP=4;
		signalLevelFilter=new AverageFilter[NUM_OF_AP];
		wifiString=new String[1];
		wifiString[0]="Starting";
		currentScanList=null;
		for(int i=0;i<NUM_OF_AP;i++)
		{
			signalLevelFilter[i]=new AverageFilter();
		}
	}
	
	public void updateInformation(List<ScanResult> wifiScanList)
	{
		
		currentScanList=wifiScanList;
		wifiString=new String[wifiScanList.size()];
		float[] level=new float[4];
		int j=0;
		
		/*for(int i = 0; i < wifiScanList.size(); i++)
   	    {
   	       wifiString[i] = ("BSSID: "+currentScanList.get(i).BSSID
   	            		+"\nSSID: "+currentScanList.get(i).SSID
   	            		+"\nlevel: "+currentScanList.get(i).level);
   	    }*/
		
		level[0]=-100f;
   		level[1]=-100f;
   		level[2]=-100f;
   		level[3]=-100f;
   		
		for(int i = 0; i < wifiScanList.size(); i++)
   	    {
   			if(BSSIDcode(currentScanList.get(i).BSSID)!=(NUM_OF_AP+1))//Is a designated AP
   			{
   				int currentBSSIDcode=BSSIDcode(currentScanList.get(i).BSSID);
   				int signalLevel=currentScanList.get(i).level;
   				
   				level[currentBSSIDcode]=signalLevel;
   				
   				//Averaging filter processing
   				//signalLevelFilter[currentBSSIDcode].addReading(signalLevel);
   				
   				//myLevels=new ListOfLevel(currentScanList,signalLevelFilter);
   				
   			//Creating wifi information string
   				wifiString[j++] = ("BSSID: "+currentScanList.get(i).BSSID
   	            		+"\nSSID: "+currentScanList.get(i).SSID
   	            		+"\nFiltering Result: "+signalLevelFilter[currentBSSIDcode].getReading());
   				
   			}
   	    }
		
		for(int i=0;i<4;i++)
		{
			//Averaging filter processing
			signalLevelFilter[i].addReading(level[i]);
		}
		
		myLevels=new ListOfLevel(currentScanList,signalLevelFilter);
		
		
	}
	
	public String[] getStringArray()
	{
		return wifiString;
	}
	
	public String[] getLocationInfo()
	{
		String[] LocationInfoString=new String[4];
		LocationInfoString[0]="Is foyer ladder?: "+myLevels.isLocation1()+
				              "\nIs pusat fotokopi?: "+myLevels.isLocation2()+
		                      "\nIs DK2?: "+myLevels.isLocation3()+
		                      "\nIs DK1?: "+myLevels.isLocation4();
		return LocationInfoString;
	}
	
	public boolean needToSpeak()
	{
		int numberOfTrues=0;
		if(myLevels.isLocation1())
		{	numberOfTrues++;	}
		if(myLevels.isLocation2())
		{	numberOfTrues++;	}
		if(myLevels.isLocation3())
		{	numberOfTrues++;	}
		if(myLevels.isLocation4())
		{	numberOfTrues++;	}
		
		if(numberOfTrues==1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public String speakString()
	{
		if(myLevels.isLocation1())
		{	return "foyer ladder";	}
		if(myLevels.isLocation2())
		{	return "photocopy centre";	}
		if(myLevels.isLocation3())
		{	return "lecture hall two";	}
		if(myLevels.isLocation4())
		{	return "lecture hall one";	}
		
		return "";
	}
	
	public int BSSIDcode(String s)
   	{
   		String theBSSID[]=new String[NUM_OF_AP];
   		theBSSID[0]="00:12:a9:53:63:80";
   		theBSSID[1]="00:1f:41:6e:b7:f8";
   		theBSSID[2]="00:1f:41:2e:b7:f8";
   		theBSSID[3]="00:12:a9:53:63:82";
   		
   		int i;
   		
   		for(i=0;i<NUM_OF_AP;i++)
   		{
   			if(s.compareTo(theBSSID[i])==0)
   			{
   				return i;
   			}
   		}
   		
   		return (NUM_OF_AP+1);
   	}
}
