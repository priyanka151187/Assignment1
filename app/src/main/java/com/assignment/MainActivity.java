package com.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.assignment.adapter.MainAdapter;
import com.assignment.api.APIClient;
import com.assignment.api.APIInterface;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    APIInterface apiInterface;
    private static String TAG = "MainActivity";
    public static ArrayList<LocationModel> resturlist = new ArrayList<LocationModel>();
    private ArrayList<LocationModel> filterList = new ArrayList<LocationModel>();
    RecyclerView recycleView;
    MainAdapter mainAdapter;
    AutoCompleteTextView edit_search;
    TextView nodata_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycleView = (RecyclerView)findViewById(R.id.recycleView);
        edit_search = (AutoCompleteTextView)findViewById(R.id.edit_search);
        nodata_txt = (TextView)findViewById(R.id.nodata_txt);
        getRestList();
        setEdittextListner();
    }

    private void setEdittextListner() {
        edit_search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                if (edit_search.getText().toString().length() == 0) {
                    getRestList();
                }

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                if (edit_search.getText().length() > 0) {
                    String search = "" + edit_search.getText().toString().trim().toLowerCase();
                    filterList.clear();
                    for (int j = 0; j < resturlist.size(); j++) {
                        String searchingtext = "" + resturlist.get(j).getName().toString().toLowerCase();
                        if (searchingtext.contains(search)) {
                            filterList.add(resturlist.get(j));
                            recycleView.setVisibility(View.VISIBLE);
                            setdataInList(filterList);
                        }
                    }
                    mainAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    //Call Api for getting all the data
    public void getRestList() {
        Call<JsonObject> call;
        apiInterface = APIClient.getClient().create(APIInterface.class);
        call = apiInterface.RestList();
        Log.d(TAG, "Call Main " + call.request().url());
        call.enqueue(new retrofit2.Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d(TAG, "listOfMyMonth " + response.body());
                JsonObject js = response.body();

                if (js.get("results") != null) {
                    resturlist.clear();
                    if (js.get("results").getAsJsonArray() != null) {
                        JsonArray data = js.get("results").getAsJsonArray();
                        for (int i = 0; i < data.size(); i++) {
                            LocationModel locationmodel = new LocationModel();
                            JsonObject js1 = data.get(i).getAsJsonObject();

                            locationmodel.setIcon(js1.get("icon").getAsString());
                            locationmodel.setName(js1.get("name").getAsString());
//                            locationmodel.setPrice_level(js1.get("price_level").getAsString());
                            locationmodel.setRating(js1.get("rating").getAsString());

//                            JsonObject js2 = js1.get("opening_hours").getAsJsonObject();
//                            String open_now = js2.get("open_now").getAsString();

//                            JsonObject js3 = js1.get("plus_code").getAsJsonObject();
//                            String compound_code = js3.get("compound_code").getAsString();
//                            String global_code = js3.get("global_code").getAsString();
//
//                            JsonArray js4 = js1.get("types").getAsJsonArray();

                            resturlist.add(locationmodel);

                        }

                        Log.d("resturlist", "" + resturlist.size());
                    }
                }
               setdataInList(resturlist);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(TAG, "onFailure " + call.toString() + t);
            }
        });

    }


    public void setdataInList(ArrayList<LocationModel> resrlist) {
        if(resrlist.size() > 0) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            recycleView.setLayoutManager(linearLayoutManager);
            mainAdapter = new MainAdapter(resrlist);
            recycleView.setAdapter(mainAdapter);
            nodata_txt.setVisibility(View.GONE);
        }else {
            nodata_txt.setVisibility(View.VISIBLE);
        }
    }
}