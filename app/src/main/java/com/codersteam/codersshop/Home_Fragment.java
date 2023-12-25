package com.codersteam.codersshop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Home_Fragment extends Fragment {

    GridView grid_view;

    ProgressBar progressBar;
    ArrayList <HashMap <String,String>> arrayList = new ArrayList<>();
    HashMap <String, String> hashMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View myView = inflater.inflate(R.layout.fragment_home, container, false);



        grid_view = myView.findViewById(R.id.grid_view);
        progressBar = myView.findViewById(R.id.progressBar);


        String url = "https://dummyjson.com/products";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {



                try {
                    JSONArray jsonArray = response.getJSONArray("products");

                    for (int x=0; x<jsonArray.length(); x++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(x);
                        String title = jsonObject.getString("title");
                        String price = jsonObject.getString("price");
                        String discount = jsonObject.getString("discountPercentage");
                        String rating = jsonObject.getString("rating");
                        String stock = jsonObject.getString("stock");
                        String thumbnail = jsonObject.getString("thumbnail");
                        String brand = jsonObject.getString("brand");
                        String description = jsonObject.getString("description");

                        String[] images = new String[] {jsonObject.getString("images")};
                        String imageArray = Arrays.toString(images);


                        hashMap = new HashMap<>();
                        hashMap.put("title",title);
                        hashMap.put("price",price);
                        hashMap.put("discountPercentage",discount);
                        hashMap.put("thumbnail",thumbnail);
                        hashMap.put("rating",rating);
                        hashMap.put("stock",stock);
                        hashMap.put("brand", brand);
                        hashMap.put("description", description);
                        hashMap.put("images", imageArray);
                        arrayList.add(hashMap);



                    }





                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                progressBar.setVisibility(View.GONE);




            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), "can't load", Toast.LENGTH_SHORT).show();


            }
        });

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(jsonObjectRequest);



        myAdaptar myAdaptar = new myAdaptar();
        grid_view.setAdapter(myAdaptar);



        return myView;




    }

    public class myAdaptar extends BaseAdapter{


        @Override
        public int getCount() {
            return

                    arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View mview = layoutInflater.inflate(R.layout.prodduct_degsin, null);

            TextView discount, product_name, stock, main_price, old_price, rating;
            ImageView thumb_image;

            CardView btn_prodduct = mview.findViewById(R.id.btn_prodduct);


            discount = mview.findViewById(R.id.discount);
            product_name = mview.findViewById(R.id.product_name);
            stock = mview.findViewById(R.id.stock);
            main_price = mview.findViewById(R.id.main_price);
            old_price = mview.findViewById(R.id.old_price);
            rating = mview.findViewById(R.id.rating);
            thumb_image = mview.findViewById(R.id.thumb_image);

            old_price.setPaintFlags(old_price.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);



            hashMap = arrayList.get(position);
            String discount1 = hashMap.get("discountPercentage");
            String product_name1 = hashMap.get("title");
            String main_price1 = hashMap.get("price");
            String rating1 = hashMap.get("rating");
            String stock1 = hashMap.get("stock");
            String thumb_image1 = hashMap.get("thumbnail");
            String brand1 = hashMap.get("brand");
            String images = hashMap.get("images");
            String description = hashMap.get("description");

            //============Discount Calculation===========//
            int original_price = Integer.parseInt(main_price1);
            float discountPercentage = Float.parseFloat(discount1);

            int discountAmount = (int) (original_price * (discountPercentage / 100.0));
            int actualprice = original_price - discountAmount;
            main_price.setText("$"+actualprice);
            //============Discount Calculation===========//


            Picasso.get().load(thumb_image1).placeholder(R.drawable.img_3).
                    resize(410,400).centerCrop().into(thumb_image);


            discount.append("-"+discount1+"%");
            product_name.append(product_name1);
            old_price.setText("$"+main_price1);
            rating.append(rating1);
            stock.append(stock1+" Available");






            //old_price.setPaintFlags(old_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


            btn_prodduct.setOnClickListener(v -> {

                Details_Page.PD_NAME = product_name1;
                Details_Page.BRAND = brand1;
                Details_Page.RATING = rating1;
                Details_Page.DESCRIPTION = description;
                Details_Page.STOCK = stock1;
                Details_Page.MAIN_PRICE = String.valueOf(actualprice);
                Details_Page.OLD_PRICE = main_price1;
                Details_Page.imageSliderArray = images;

                Bitmap bitmap = ((BitmapDrawable) thumb_image.getDrawable() ).getBitmap();
                Details_Page.MY_BIPMAP = bitmap;

                Toast.makeText(getActivity(), "This is " +product_name1, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), Details_Page.class);
                startActivity(intent);





            });



            return mview;
        }
    }


}