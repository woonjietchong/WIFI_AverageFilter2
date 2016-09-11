package com.example.wifi;

import java.util.TimerTask;
import android.net.wifi.WifiManager;


public class ScanRepeater extends TimerTask 
{
	WifiManager mainWifiObj;
	
	
	ScanRepeater(WifiManager passingWifiManager)
	{
		mainWifiObj=passingWifiManager;
	}
	
	@Override
 	 public void run() 
 	 {
		mainWifiObj.startScan();
 	 }
}
