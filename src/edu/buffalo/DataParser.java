package edu.buffalo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

public class DataParser {
	private static int APP_VERSION = 1;
	private static boolean DEBUG = false;
	//values for this mobile device
	private String mCountry = "usa";
	private String mLanguage = "english";
	private String mDeviceModel = "nexus_5";
	private String mOsVersion = "5.1";
	private String mAppVersion = "1";

	// 
	private static String PROPERTY_COUNTRY = "country";
	private static String PROPERTY_LANGUAGE = "lang";
	private static String PROPERTY_DEVICE_MODEL = "model";
	private static String PROPERTY_OS = "os_version";
	private static String PROPERTY_APP_VERSION = "app_version";
	private static String PROPERTY_OP = "op";
	private static String PROPERTY_EXCLUDE = "exclude";
	private static String OP_NOOP = "noop";
	private static String OP_AND = "and";
	private static String OP_OR = "or";


	/* Data format 
	 * Use JSON to encode the data. Each value accompanied with an op code
	 * [name:{value:"x",op:"and", exclude:"true"},name:{value:"x2",op:"or",exclude:"false"}}
	 * The jsonString representing the data is sent over the network
	 */

	public void runTest(){
		JSONArray data = getData();
		if (DEBUG) System.out.println("Data:"+data.toString());

		boolean result = parseData(data.toString());
		System.out.println("Result:"+result);



	}

	public JSONArray getData(){
		//country == usa OR language == eng AND model == nexus_5 AND NOT(osVersion == 2.0) AND NOT(appVersion == 2)
		JSONArray data1 = new JSONArray();
		JSONObject countryObj = new JSONObject();
		JSONObject languageObj = new JSONObject();
		JSONObject modelObj = new JSONObject();
		JSONObject osVersionObj = new JSONObject();
		JSONObject appVersionObj = new JSONObject();
		try {
			countryObj.put(PROPERTY_COUNTRY, "usa");
			countryObj.put(PROPERTY_OP, OP_NOOP);
			countryObj.put(PROPERTY_EXCLUDE, false);
			data1.put(countryObj);

			languageObj.put(PROPERTY_LANGUAGE, "english");
			languageObj.put(PROPERTY_OP, OP_OR);
			languageObj.put(PROPERTY_EXCLUDE, false);
			data1.put(languageObj);

			modelObj.put(PROPERTY_DEVICE_MODEL, "nexus_5" );
			modelObj.put(PROPERTY_OP, OP_AND);
			modelObj.put(PROPERTY_EXCLUDE, false);
			data1.put(modelObj);

			osVersionObj.put(PROPERTY_OS,"2.0");
			osVersionObj.put(PROPERTY_OP, OP_AND);
			osVersionObj.put(PROPERTY_EXCLUDE, true);
			data1.put(osVersionObj);

			appVersionObj.put(PROPERTY_APP_VERSION, "2");
			appVersionObj.put(PROPERTY_OP, OP_AND);
			appVersionObj.put(PROPERTY_EXCLUDE, true);
			data1.put(appVersionObj);


		} catch (JSONException e) {

			e.printStackTrace();
		}
		return data1;
	}





	public boolean parseData(String data){
		boolean result = false;
		if(!isValidJSONString(data)){
			result = false;
			if (DEBUG) System.out.println("Result:"+result);
			return result;
		}
		result = true;
		if (DEBUG) System.out.println("Result:"+result);


		JSONArray jsonData = null;
		try {
			jsonData = new JSONArray(data);

			for(int i = 0; i < jsonData.length();i++){
				JSONObject propertyObj = jsonData.getJSONObject(i);

				//country
				if(propertyObj.has(PROPERTY_COUNTRY)){
					String value = propertyObj.getString(PROPERTY_COUNTRY);
					if(OP_NOOP.equals(propertyObj.getString(PROPERTY_OP))){
						result = mCountry.equals(value);
						if(propertyObj.getBoolean(PROPERTY_EXCLUDE)){
							result = !result;
						}

					}else if(OP_AND.equals(propertyObj.getString(PROPERTY_OP))){
						


						if(propertyObj.getBoolean(PROPERTY_EXCLUDE)){
							result = result && !mCountry.equals(value);
						}else{
							result = result && mCountry.equals(value);
						}

					}else{
						


						if(propertyObj.getBoolean(PROPERTY_EXCLUDE)){
							result = result || !mCountry.equals(value);
						}else{
							result = result || mCountry.equals(value);
						}

					}
				}

				//language
				if(propertyObj.has(PROPERTY_LANGUAGE)){
					String value = propertyObj.getString(PROPERTY_LANGUAGE);
					if(OP_NOOP.equals(propertyObj.getString(PROPERTY_OP))){
						result = mLanguage.equals(value);
						if(propertyObj.getBoolean(PROPERTY_EXCLUDE)){
							result = !result;
						}

					}else if(OP_AND.equals(propertyObj.getString(PROPERTY_OP))){
						


						if(propertyObj.getBoolean(PROPERTY_EXCLUDE)){
							result = result && !mLanguage.equals(value);
						}else{
							result = result && mLanguage.equals(value);
						}

					}else{
						


						if(propertyObj.getBoolean(PROPERTY_EXCLUDE)){
							result = result || !mLanguage.equals(value);
						}else{
							result = result || mLanguage.equals(value);
						}

					}
				}
				
				// device model
				if(propertyObj.has(PROPERTY_DEVICE_MODEL)){
					String value = propertyObj.getString(PROPERTY_DEVICE_MODEL);
					if(OP_NOOP.equals(propertyObj.getString(PROPERTY_OP))){
						result = mDeviceModel.equals(value);
						if(propertyObj.getBoolean(PROPERTY_EXCLUDE)){
							result = !result;
						}

					}else if(OP_AND.equals(propertyObj.getString(PROPERTY_OP))){
						


						if(propertyObj.getBoolean(PROPERTY_EXCLUDE)){
							result = result && !mDeviceModel.equals(value);
						}else{
							result = result && mDeviceModel.equals(value);
						}

					}else{
						


						if(propertyObj.getBoolean(PROPERTY_EXCLUDE)){
							result = result || !mDeviceModel.equals(value);
						}else{
							result = result || mDeviceModel.equals(value);
						}

					}
				}
				// os 
				
				if(propertyObj.has(PROPERTY_OS)){
					String value = propertyObj.getString(PROPERTY_OS);
					if(OP_NOOP.equals(propertyObj.getString(PROPERTY_OP))){
						result = mOsVersion.equals(value);
						if(propertyObj.getBoolean(PROPERTY_EXCLUDE)){
							result = !result;
						}

					}else if(OP_AND.equals(propertyObj.getString(PROPERTY_OP))){
						


						if(propertyObj.getBoolean(PROPERTY_EXCLUDE)){
							result = result && !mOsVersion.equals(value);
						}else{
							result = result && mOsVersion.equals(value);
						}

					}else{
						


						if(propertyObj.getBoolean(PROPERTY_EXCLUDE)){
							result = result || !mOsVersion.equals(value);
						}else{
							result = result || mOsVersion.equals(value);
						}

					}
				}
				
				//app
				
				if(propertyObj.has(PROPERTY_APP_VERSION)){
					String value = propertyObj.getString(PROPERTY_APP_VERSION);
					if(OP_NOOP.equals(propertyObj.getString(PROPERTY_OP))){
						result = mAppVersion.equals(value);
						if(propertyObj.getBoolean(PROPERTY_EXCLUDE)){
							result = !result;
						}

					}else if(OP_AND.equals(propertyObj.getString(PROPERTY_OP))){
						


						if(propertyObj.getBoolean(PROPERTY_EXCLUDE)){
							result = result && !mAppVersion.equals(value);
						}else{
							result = result && mAppVersion.equals(value);
						}

					}else{
						result = result || mAppVersion.equals(value);


						if(propertyObj.getBoolean(PROPERTY_EXCLUDE)){
							result = result || !mAppVersion.equals(value);
						}else{
							result = result || mAppVersion.equals(value);
						}

					}
				}
				
				



			}



		} catch (JSONException e) {

			e.printStackTrace();
		}

		return result;
	}

	public DataParser() {
		JSONObject obj = new JSONObject();
	}


	public boolean isValidJSONString(String jsonString){
		try {
			new JSONObject(jsonString);
		} catch (JSONException e) {

			try {
				new JSONArray(jsonString);
			} catch (JSONException e1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param args
	 */
	 public static void main(String[] args) {
		 new DataParser().runTest();

	 }

}
