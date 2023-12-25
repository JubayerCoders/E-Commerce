package com.codersteam.codersshop;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import org.json.JSONArray;

import java.util.ArrayList;

public class Details_Page extends AppCompatActivity {

    

    TextView dt_product_name, dt_brand_name, dt_rating, dt_description, dt_stock, dt_main_price, dt_old_price;

    ImageSlider imageSlider;

    ImageView btn_plus, btn_minus;
    TextView net_price;
    TextView tv_counter;
    public static String imageSliderArray = "";

    public static String PD_NAME = "";
    public static String DESCRIPTION = "";
    public static String BRAND = "";
    public static String RATING = "";
    public static String STOCK = "";
    public static String MAIN_PRICE = "";
    public static String OLD_PRICE = "";
    public static Bitmap MY_BIPMAP = null;
    int count = 1;






    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);




        dt_product_name = findViewById(R.id.dt_product_name);
        dt_brand_name = findViewById(R.id.dt_brand_name);
        dt_rating = findViewById(R.id.dt_rating);
        dt_description = findViewById(R.id.dt_description);
        dt_stock = findViewById(R.id.dt_stock);
        dt_main_price = findViewById(R.id.dt_main_price);
        dt_old_price = findViewById(R.id.dt_old_price);
        imageSlider = findViewById(R.id.image_slider);
        net_price = findViewById(R.id.net_price);
        btn_plus = findViewById(R.id.btn_plus);
        btn_minus = findViewById(R.id.btn_minus);
        tv_counter = findViewById(R.id.tv_counter);

        dt_old_price.setPaintFlags(dt_old_price.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);

        //======================Product Image SLider========================//
        ArrayList<SlideModel> imageList = new ArrayList<>();

        try {

            JSONArray jsonArray = new JSONArray(imageSliderArray);
            if (jsonArray.length() > 0) {
                JSONArray imagerLinkArray = jsonArray.getJSONArray(0);


                for (int i =0; i<imagerLinkArray.length(); i++) {

                    String imageLink = imagerLinkArray.getString(i);
                    imageList.add(new SlideModel(imageLink, null));

                }
            }

        } catch (Exception e) {

        }
        imageSlider.setImageList(imageList);
        //======================Product Image SLider========================//

        dt_product_name.setText(PD_NAME);
        dt_brand_name.setText(BRAND);
        dt_rating.setText(RATING);
        dt_description.setText(DESCRIPTION);
        dt_stock.setText(STOCK + " Stock Available");
        dt_main_price.setText("$"+MAIN_PRICE);
        dt_old_price.setText("$"+OLD_PRICE);
        net_price.setText("$"+MAIN_PRICE);


        //---------------Quality Function Work----------------//

        btn_plus.setOnClickListener(v -> {



            if (count < 5) {

                count++;
                tv_counter.setText(""+count);

                if (count==5) {
                    btn_plus.setBackgroundColor(getResources().getColor(R.color.off_white));
                }

                if (count>1){

                    btn_minus.setBackgroundColor(getResources().getColor(R.color.normal_black));

                }


            } else {

                Toast.makeText(Details_Page.this, "Maximum  limit reached.", Toast.LENGTH_SHORT).show();


            }


        });

        btn_minus.setOnClickListener(v -> {

            if (count>1) {

                count--;
                tv_counter.setText(""+count);

                if (count==1) {
                    btn_minus.setBackgroundColor(getResources().getColor(R.color.off_white));
                }


                if (count<5){

                    btn_minus.setBackgroundColor(getResources().getColor(R.color.normal_black));

                }
            }



        });








        //---------------Quality Function Work----------------//











    }
}