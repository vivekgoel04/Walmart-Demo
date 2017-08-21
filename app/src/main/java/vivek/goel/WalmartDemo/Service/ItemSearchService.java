package vivek.goel.WalmartDemo.Service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vivek.goel.WalmartDemo.ServerConnection.APIClient;
import vivek.goel.WalmartDemo.ServerConnection.APIInterface;
import vivek.goel.WalmartDemo.models.DataModel.ItemFeed;

/**
 * Created by vivekgoel on 8/18/17.
 */

public class ItemSearchService extends IntentService {
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);;
    String searchQuery;
    String nextPage;
    String apiKey = "tkkr6sg36f2v49xmpa5u7zfn";
    ItemFeed itemFeed;

    public ItemSearchService() {
        super("ItemSearchService");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if(intent.getBundleExtra("bundle") != null) {
            Bundle bundle = intent.getBundleExtra("bundle");
            searchQuery = bundle.getString("Query");
            nextPage = bundle.getString("nextPage");
            Log.d("#############", "service**"+nextPage);

        }

        /**
         GET List Items
         **/

        if(searchQuery != null && (nextPage.equals("") || nextPage == null)) {
            Map<String, String> map = new HashMap<>();
            map.put("format","json");
            map.put("brand",searchQuery);
            map.put("apiKey",apiKey);
            Call<ItemFeed> call3 = apiInterface.doGetBrandItems(map);
            call3.enqueue(new Callback<ItemFeed>() {
                @Override
                public void onResponse(Call<ItemFeed> call, Response<ItemFeed> response) {
                    itemFeed = response.body();
                    publishResults(itemFeed);
                }

                @Override
                public void onFailure(Call<ItemFeed> call, Throwable t) {
                    call.cancel();
                }
            });
        }
        else {
            if(nextPage.equals("") || nextPage == null) {
                Log.d("****Inside call 1 *****", "" + searchQuery + " " + nextPage);
                Call<ItemFeed> call1 = apiInterface.doGetFirstPageItemDetails();
                call1.enqueue(new Callback<ItemFeed>() {
                    @Override
                    public void onResponse(Call<ItemFeed> call, Response<ItemFeed> response) {
                        itemFeed = response.body();
                        publishResults(itemFeed);
                    }
                    @Override
                    public void onFailure(Call<ItemFeed> call, Throwable t) {
                        call.cancel();
                    }
                });
            }
            else {
                Log.d("****Inside call 2 *****", "" + searchQuery + " " + nextPage);
                Call<ItemFeed> call2 = apiInterface.doGetItemDetails(nextPage);
                call2.enqueue(new Callback<ItemFeed>() {
                    @Override
                    public void onResponse(Call<ItemFeed> call, Response<ItemFeed> response) {
                        itemFeed = response.body();
                        publishResults(itemFeed);
                    }

                    @Override
                    public void onFailure(Call<ItemFeed> call, Throwable t) {
                        call.cancel();
                    }
                });
            }
        }
    }

    private void publishResults(ItemFeed itemFeed) {
        Intent newIntent = new Intent();
        newIntent.setAction("vivek.goel.DATA_LOADED");
        newIntent.putExtra("itemFeed",itemFeed);
        sendBroadcast(newIntent);
    }
}
