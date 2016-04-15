package com.coolweather.app.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.coolweather.app.activity.WeatherActivity;

import android.os.Message;

public class HttpUtil {
	
	public static void sendHttpRequest(final String address,
			final HttpCallbackListener listener){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				HttpURLConnection connection=null;
				try {
					URL url=new URL(address);
					connection=(HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
//					connection.setRequestProperty("Charsert", "UTF-8");  
//					connection.setRequestProperty("contentType", "UTF-8"); 
					
					
//					connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
					
					connection.setRequestProperty("Content-type", "application/x-java-serialized-object");
					
					InputStream in=connection.getInputStream();
					BufferedReader reader=new BufferedReader(new InputStreamReader(in));
					StringBuilder response=new StringBuilder();
					String line;
					while((line=reader.readLine())!=null){
						response.append(line);
					}
					reader.close();
					if(listener!=null){
						//回调onFinish方法
						listener.onFinish(response.toString());
					}
				} catch (Exception e) {
					if(listener!=null){
						listener.onError(e);
					}
				}finally{
					if(connection!=null){
						
						connection.disconnect();
					}
				}
			}
		}).start();
	}
	
}
