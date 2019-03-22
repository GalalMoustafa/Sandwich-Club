package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            Log.d("json",json);
            JSONObject sandwichDetailsJson = new JSONObject(json);
            JSONObject name = sandwichDetailsJson.getJSONObject("name");

            String mainName = name.getString("mainName");

            List<String> alsoKnownAsStrings = null;
            if (!name.isNull("alsoKnownAs")){
                JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");
                alsoKnownAsStrings = new ArrayList<>();
                for (int i = 0; i < alsoKnownAs.length(); i++){
                    alsoKnownAsStrings.add(alsoKnownAs.getString(i));
                }
            }
            

            String placeOfOrigin = sandwichDetailsJson.getString("placeOfOrigin");
            String description = sandwichDetailsJson.getString("description");
            String imageLink = sandwichDetailsJson.getString("image");

            List<String> ingredientsStrings = null;
            if (!sandwichDetailsJson.isNull("ingredients")){
                JSONArray ingredients = sandwichDetailsJson.getJSONArray("ingredients");
                ingredientsStrings = new ArrayList<>();
                for (int i = 0; i < ingredients.length(); i++){
                    ingredientsStrings.add(ingredients.getString(i));
                }
            }
            
            return new Sandwich(mainName, alsoKnownAsStrings, placeOfOrigin, description, imageLink, ingredientsStrings);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
