package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json)
    {
        Sandwich objSandwich = new Sandwich();

        try
        {
            JSONObject mainSandwichObject = new JSONObject(json);

            JSONObject childSandwichObject = mainSandwichObject.getJSONObject("name");
            String mainName = childSandwichObject.getString("mainName");

            JSONArray nameArray = childSandwichObject.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = new ArrayList<>();

            for (int i=0; i < nameArray.length(); i++) {
                String name = nameArray.getString(i);
                alsoKnownAs.add(name);
            }

            String placeOfOrigin = mainSandwichObject.getString("placeOfOrigin");
            String description = mainSandwichObject.getString("description");;
            String image = mainSandwichObject.getString("image");

            JSONArray ingredientsArray = mainSandwichObject.getJSONArray("ingredients");
            List<String> ingredients = new ArrayList<>();

            for (int i=0; i < ingredientsArray.length(); i++) {
                String ingredient = ingredientsArray.getString(i);
                ingredients.add(ingredient);
            }

            objSandwich = new Sandwich(mainName,alsoKnownAs,placeOfOrigin,description,image,ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return objSandwich;
    }
}
