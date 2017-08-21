package vivek.goel.WalmartDemo.ServerConnection;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import vivek.goel.WalmartDemo.models.DataModel.ItemFeed;

/**
 * Created by vivekgoel on 05/01/17.
 */

public interface APIInterface {

    @GET("/v1/paginated/items?format=json&apiKey=tkkr6sg36f2v49xmpa5u7zfn")
    Call<ItemFeed> doGetFirstPageItemDetails();

    @GET
    Call<ItemFeed> doGetItemDetails(@Url String nextPage);

    @GET("/v1/paginated/items")
    Call<ItemFeed> doGetBrandItems(@QueryMap Map<String,String> options);


}
