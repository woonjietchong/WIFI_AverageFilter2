package com.example.wifi;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.net.wifi.WifiManager;

public class WifiScanReceiver extends BroadcastReceiver 
 {
	public static final int NUM_OF_AP=4;
	WifiManager mainWifiObj;
	Context myContext;
	String[] wifiString;
	String[] locString;
	ArrayAdapter<String> myArrayAdapter;
	ListView list;
	ListView list2;
	WifiInformation myWifiInfo;
	TextToSpeech ttobj;

	
	//Constructor
	WifiScanReceiver(WifiManager passingWifiManager,Context passingContext,ListView passingList1,ListView passingList2,WifiInformation passingInfo,TextToSpeech passingTTobj)
	{
		mainWifiObj=passingWifiManager;
		myContext=passingContext;
		list=passingList1;
		list2=passingList2;
		myWifiInfo=passingInfo;
		ttobj=passingTTobj;
	}
	
	public void updateTTS(TextToSpeech passingTTobj)
	{
		ttobj=passingTTobj;
	}
	   
	   
   	@SuppressLint("UseValueOf")
   	public void onReceive(Context c, Intent intent) 
   	{
   		myWifiInfo.updateInformation(mainWifiObj.getScanResults());
   		wifiString=myWifiInfo.getStringArray();
   		locString=myWifiInfo.getLocationInfo();
   		
   	    myArrayAdapter=new ArrayAdapter<String>(myContext,android.R.layout.simple_list_item_1,wifiString);
   	    list.setAdapter(myArrayAdapter);
   	    
   	    ArrayAdapter<String> myArrayAdapter2=new ArrayAdapter<String>(myContext,android.R.layout.simple_list_item_1,locString);
	    list2.setAdapter(myArrayAdapter2);
	    
	    if(myWifiInfo.needToSpeak())
	    {
	    	if(!ttobj.isSpeaking())
	    	{
	    		ttobj.speak(myWifiInfo.speakString(), TextToSpeech.QUEUE_FLUSH, null);
	    	}
	    }
   	}
   	
 }
   	
