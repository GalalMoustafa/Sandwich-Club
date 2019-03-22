package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView origin_tv = findViewById(R.id.origin_tv);
        if (!sandwich.getPlaceOfOrigin().equals("")){
            origin_tv.setText(sandwich.getPlaceOfOrigin());
        }
        TextView alsoKnownAs_tv = findViewById(R.id.also_known_tv);
        if(!sandwich.getAlsoKnownAs().isEmpty()){
            alsoKnownAs_tv.setText("");
            for (int i = 0; i< sandwich.getAlsoKnownAs().size(); i++){
                if (i != 0){
                    alsoKnownAs_tv.setText(alsoKnownAs_tv.getText() + "\n");
                }
                alsoKnownAs_tv.setText(alsoKnownAs_tv.getText() + sandwich.getAlsoKnownAs().get(i));
            }
        }
        TextView description_tv = findViewById(R.id.description_tv);
        if (sandwich.getDescription() != null){
            description_tv.setText(sandwich.getDescription());
        }
        TextView ingredients_tv = findViewById(R.id.ingredients_tv);
        if (!sandwich.getIngredients().isEmpty()) {
            ingredients_tv.setText("");
            for (int i = 0; i < sandwich.getIngredients().size(); i++) {
                if (i != 0) {
                    ingredients_tv.setText(ingredients_tv.getText() + "\n");
                }
                ingredients_tv.setText(ingredients_tv.getText() + sandwich.getIngredients().get(i));
            }
        }
    }
}
