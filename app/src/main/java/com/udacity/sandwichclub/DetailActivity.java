package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    ImageView ingredientsIv;
    TextView originalNameTv;
    TextView knownAsTv;
    TextView placeTv;
    TextView descriptionTv;
    TextView ingredientsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());

        originalNameTv.setText(sandwich.getMainName().equals("") ? "-" : sandwich.getMainName());

        List<String> knowASList = sandwich.getAlsoKnownAs();
        int knownSize = knowASList.size();
        if(knownSize == 0)
            knownAsTv.append("-");

        for(int i=0; i < knownSize; i++)
        {
            String knownAS = knowASList.get(i);

            if(i == knownSize - 1)
                knownAsTv.append(knownAS);
            else
                knownAsTv.append(knownAS + ", ");
        }
        placeTv.setText(sandwich.getPlaceOfOrigin().equals("") ? "-" : sandwich.getPlaceOfOrigin());
        descriptionTv.setText(sandwich.getDescription().equals("") ? "-" : sandwich.getDescription());

        List<String> ingredientsList = sandwich.getIngredients();
        int ingredientSize = ingredientsList.size();
        if(ingredientSize == 0)
            ingredientsTv.append("-");
        for(int i=0; i < ingredientSize; i++)
        {
            String ingredient = ingredientsList.get(i);

            if(i == ingredientSize - 1)
                ingredientsTv.append(ingredient + ".");
            else
                ingredientsTv.append(ingredient + ", ");
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        ingredientsIv = findViewById(R.id.image_iv);
        originalNameTv = findViewById(R.id.origin_tv);
        knownAsTv = findViewById(R.id.also_known_tv);
        placeTv = findViewById(R.id.place_tv);
        descriptionTv = findViewById(R.id.description_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);

    }
}
