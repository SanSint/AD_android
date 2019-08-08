package com.san.logicuniversity_ad.ui.store;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.san.logicuniversity_ad.AsyncToServer;
import com.san.logicuniversity_ad.BuildConfig;
import com.san.logicuniversity_ad.Command;
import com.san.logicuniversity_ad.R;
import com.san.logicuniversity_ad.utils.adaptors.StocktakeAdaptor;
import com.san.logicuniversity_ad.modals.Stocktake;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Store_StockCheckList extends AppCompatActivity implements AsyncToServer.IServerResponse {

    private final String GET_STOCKTAKE_LIST_URL = BuildConfig.API_BASE_URL + "/store/check-history";

    RecyclerView rvStocktake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_stock_check_list);

        rvStocktake = findViewById(R.id.rv_stock_check);
        rvStocktake.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        rvStocktake.setLayoutManager(llm);

        requestStocktakeList();
    }

    private void requestStocktakeList() {
        Command cmd = new Command(this, "getStocktakes", GET_STOCKTAKE_LIST_URL , null);
        new AsyncToServer().execute(cmd);
    }

    @Override
    public void onServerResponse(JSONObject jsonObj) {

        if (jsonObj == null) {
            return;
        }
        try {
            String context = (String) jsonObj.get("context");

            if (context.compareTo("getStocktakes") == 0) {
                onGetStocktakeList(jsonObj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void onGetStocktakeList(JSONObject jsonObj) {
        try {
            ArrayList<Stocktake> stocktakeArrayList= new ArrayList<>();
            JSONArray riArr = (JSONArray) jsonObj.get("result");

            for (int i = 0, count = riArr.length(); i < count; i++) {
                JSONObject riJson = riArr.getJSONObject(i);
                Stocktake d = new Stocktake(
                        riJson.getString("stocktakeId"),
                        riJson.getString("doneBy"),
                        riJson.getString("month"));

                stocktakeArrayList.add(d);
            }

            StocktakeAdaptor da = new StocktakeAdaptor(stocktakeArrayList);
            rvStocktake.setAdapter(da);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
