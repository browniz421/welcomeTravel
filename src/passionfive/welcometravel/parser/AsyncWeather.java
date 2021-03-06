package passionfive.welcometravel.parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;

public class AsyncWeather extends AsyncBase
{
	public AsyncWeather(Context context) {
		super(context);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Integer doInBackground(String... params) {
		strUrl = params[0];
		System.setProperty("http.keepAlive", "false");
		HttpURLConnection urlConnection=null;

		try {
			URL url=new URL(strUrl);
			urlConnection=(HttpURLConnection)url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoInput(true);
			urlConnection.setReadTimeout(TIME);
			urlConnection.setConnectTimeout(TIME);
			urlConnection.setInstanceFollowRedirects(false);
			urlConnection. connect();

			int responseCode=urlConnection.getResponseCode();

			if(responseCode==HttpURLConnection.HTTP_OK) {
				XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
				factory.setNamespaceAware(true);
				XmlPullParser xpp=factory.newPullParser();

				InputStream in=urlConnection.getInputStream();
				
				xpp.setInput(in, "UTF-8");

				mApplicationSample.clearWeatherData();

				int count = 0;
				
				String tag = null;

				String curCondition = null;
				String curConditionCode = null;
				String curTemp = null;
				String curDate = null;

				String todayDay = null;
				String todayDate = null;
				String todayLow = null;
				String todayHigh = null;
				String todayCondition = null;
				String todayConditionCode = null;

				String tomorrowDay = null;
				String tomorrowDate = null;
				String tomorrowLow = null;
				String tomorrowHigh = null;
				String tomorrowCondition = null;
				String tomorrowConditionCode = null;

				int eventType = xpp.getEventType();

				while (eventType != XmlPullParser.END_DOCUMENT) {
					switch (eventType) {
					case XmlPullParser.START_DOCUMENT:
						break;
						
					case XmlPullParser.END_DOCUMENT:
						break;
						
					case XmlPullParser.START_TAG:
						tag = xpp.getName();
						
//						setString(str) 메소드 이용하면 html 태그 정리
						
						if(tag.equals("condition")) {
							curCondition = xpp.getAttributeValue(null, "text");
							curConditionCode = xpp.getAttributeValue(null, "code");
							curTemp = xpp.getAttributeValue(null, "temp");
							curDate = xpp.getAttributeValue(null, "date");
						}
						
						if(tag.equals("forecast")) {
							if(count==0){
								todayDay = xpp.getAttributeValue(null, "day");
								todayDate = xpp.getAttributeValue(null, "date");
								todayLow = xpp.getAttributeValue(null, "low");
								todayHigh = xpp.getAttributeValue(null, "high");
								todayCondition = xpp.getAttributeValue(null, "text");
								todayConditionCode = xpp.getAttributeValue(null, "code");
							}
							else {
								tomorrowDay = xpp.getAttributeValue(null, "day");
								tomorrowDate = xpp.getAttributeValue(null, "date");
								tomorrowLow = xpp.getAttributeValue(null, "low");
								tomorrowHigh = xpp.getAttributeValue(null, "high");
								tomorrowCondition =  xpp.getAttributeValue(null, "text");
								tomorrowConditionCode = xpp.getAttributeValue(null, "code");
							}
							count++;
						}
						break;
						
					case XmlPullParser.END_TAG:
						break;
						
					case XmlPullParser.TEXT:
						break;
						
					default:
						break;
					}
					eventType = xpp.next();
				}

				mApplicationSample.hashmapWeather.put("curCondition", curCondition);
				mApplicationSample.hashmapWeather.put("curConditionCode", curConditionCode);
				mApplicationSample.hashmapWeather.put("curTemp", curTemp);
				mApplicationSample.hashmapWeather.put("curDate", curDate);

				mApplicationSample.hashmapWeather.put("todayDay", todayDay);
				mApplicationSample.hashmapWeather.put("todayDate", todayDate);
				mApplicationSample.hashmapWeather.put("todayLow", todayLow);
				mApplicationSample.hashmapWeather.put("todayHigh", todayHigh);
				mApplicationSample.hashmapWeather.put("todayCondition", todayCondition);
				mApplicationSample.hashmapWeather.put("todayConditionCode", todayConditionCode);

				mApplicationSample.hashmapWeather.put("tomorrowDay", tomorrowDay);
				mApplicationSample.hashmapWeather.put("tomorrowDate", tomorrowDate);
				mApplicationSample.hashmapWeather.put("tomorrowLow", tomorrowLow);
				mApplicationSample.hashmapWeather.put("tomorrowHigh", tomorrowHigh);
				mApplicationSample.hashmapWeather.put("tomorrowCondition", tomorrowCondition);
				mApplicationSample.hashmapWeather.put("tomorrowConditionCode", tomorrowConditionCode);

				curCondition = null;
				curConditionCode = null;
				curTemp = null;
				curDate = null;

				todayDay = null;
				todayDate = null;
				todayLow = null;
				todayHigh = null;
				todayCondition = null;
				todayConditionCode = null;

				tomorrowDay = null;
				tomorrowDate = null;
				tomorrowLow = null;
				tomorrowHigh = null;
				tomorrowCondition = null;
				tomorrowConditionCode = null;

				if(in!=null) {
					in.close();
					in=null;
				}
			}
		}
		catch (MalformedURLException e) {
			return ERROR;
		}
		catch (ProtocolException e) {
			return ERROR;
		}
		catch (IOException e) {
			return ERROR;
		}
		catch (XmlPullParserException e) {
			return ERROR;
		}
		catch (Exception e) {
			return ERROR;
		}
		finally {
			if(urlConnection!=null) {
				urlConnection.disconnect();
				urlConnection=null;
			}
		}

		return NO_ERROR;
	}

	@Override
	protected void onPostExecute(Integer result) {
//		추가로 해야할 부분 이쪽에
		if(result == ERROR) {
			
		}
		else if(result == NO_ERROR) {
			
		}
		
		super.onPostExecute(result);
	}
}
