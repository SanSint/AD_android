package com.san.logicuniversity_ad.ui.store;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.san.logicuniversity_ad.AsyncToServer;
import com.san.logicuniversity_ad.BuildConfig;
import com.san.logicuniversity_ad.Command;
import com.san.logicuniversity_ad.R;
import com.san.logicuniversity_ad.utils.adaptors.StocktakeItemAdaptor;
import com.san.logicuniversity_ad.modals.StocktakeItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Store_StockChecking extends AppCompatActivity implements AsyncToServer.IServerResponse {

    private final String GET_STOCKTAKE_URL = BuildConfig.API_BASE_URL + "/store/check-history/Id";

    RecyclerView rvStocktakeItem;
    String selectedStocktakeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_stock_checking);


        rvStocktakeItem = findViewById(R.id.rv_stocktake_items);
        rvStocktakeItem.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        rvStocktakeItem.setLayoutManager(llm);

        Bundle extra = getIntent().getExtras();
        selectedStocktakeId = extra.getString("stocktakeId");

        requestStocktake();
    }

    private void requestStocktake() {
        Command cmd = new Command(this, "getStocktake", GET_STOCKTAKE_URL + "/" + selectedStocktakeId, null);
        new AsyncToServer().execute(cmd);
    }

    @Override
    public void onServerResponse(JSONObject jsonObj) {
        if (jsonObj == null) {
            return;
        }
        try {
            String context = (String) jsonObj.get("context");

            if (context.compareTo("getStocktake") == 0) {
                onGetStocktake(jsonObj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onGetStocktake(JSONObject jsonObj) {
        try {
            JSONObject stocktake = jsonObj.getJSONObject("result");

            ArrayList<StocktakeItem> stItemAL = new ArrayList<>();
            JSONArray diArr = (JSONArray) stocktake.get("itemList");

            for (int i = 0, count = diArr.length(); i < count; i++) {
                JSONObject riJson = diArr.getJSONObject(i);
                StocktakeItem si = new StocktakeItem (
                        riJson.getString("itemNumber"),
                        riJson.getString("category"),
                        riJson.getString("description"),
                        riJson.getString("unitOfMeasure"),
                        riJson.getInt("qty"),
                        riJson.getInt("qty"));

                stItemAL.add(si);
            }

            StocktakeItemAdaptor sia = new StocktakeItemAdaptor(stItemAL);
            rvStocktakeItem.setAdapter(sia);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
