package vivek.goel.WalmartDemo.models.DataModel;

import java.io.Serializable;
import java.util.List;

public class ItemFeed implements Serializable {

    public String nextPage;
    public List<Items> items;


    public String getNextPage() {
        return nextPage;
    }

    public List<Items> getItems() {
        return items;
    }

}
