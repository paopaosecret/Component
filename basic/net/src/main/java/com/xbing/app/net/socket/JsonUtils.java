package com.xbing.app.net.socket;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhaobing04 on 2018/5/28.
 */

public class JsonUtils {

    public static void testJson(){
        String json = "{\n" +
                "\t\"header\": \"this is header\",\n" +
                "\t\"body\": [{\n" +
                "\t\t\"name\": \"body1\",\n" +
                "\t\t\"num\": \"1\"\n" +
                "\t}, {\n" +
                "\t\t\"name\": \"body2\",\n" +
                "\t\t\"num\": \"2\"\n" +
                "\t}]\n" +
                "}";

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            JSONArray arrayBody = jsonObject.getJSONArray("body");
            for(int i = 0 ; i < arrayBody.length(); i++){
                JSONObject jsonObject1 = arrayBody.getJSONObject(i);
                Log.e("jsonUtils","num:" + jsonObject1.getInt("num") +",name："+ jsonObject1.getString("name"));
            }
            Log.e("jsonUtils","json:" + jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
