package com.fish.app;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fish.connection.ConnectionCallBack;
import com.fish.connection.MyHttpGet;
import com.fish.dao.PHDao;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class PHApp {
	
	private static final Logger log = Logger.getLogger(TemperatureApp.class);
	
	private PHDao phDao;
	
	public PHApp() {
		ApplicationContext context = 
	    		new ClassPathXmlApplicationContext("DataSource.xml");
		DataSource dataSource = (DataSource)context.getBean("dataSource");
		phDao = new PHDao(dataSource);
	}
	
	public void connection(){
		String url = "https://api.spark.io/v1/devices/54ff6f066678574919490867/PH?access_token=47ff72f4daca42c4bc5e7f406d7a46ae3fbcb81f";
		Map<String, String> headMap = new HashMap<String, String>();
		MyHttpGet con = new MyHttpGet(url, new ConnectionCallBack() {
			
			public void callback(CloseableHttpResponse response) {
				// TODO Auto-generated method stub
				
			}
			
			public void callback(String str) {
				JsonParser jsonParser = new JsonParser();
				JsonElement element = jsonParser.parse(str);
				String result = element.getAsJsonObject().get("result").toString();
				float r = Float.valueOf(result);
				log.info("save to DB.");
				phDao.save(r);
			}
		}, headMap);
		
		con.connection();
	}
	
	public static void main(String[] args) {
		PHApp app = new PHApp();
		while(true){
			log.info("connect to spark cloud.");
			app.connection();
			log.info("wait fro 1 min...");
			try {
				Thread.sleep(1000 * 60);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
