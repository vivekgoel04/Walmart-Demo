package vivek.goel.WalmartDemo.models.DataModel;



import java.io.Serializable;

public class Items implements Serializable{

    private int itemId;
    public String name;
    private float salePrice;
    public String shortDescription;
    public String thumbnailImage;
    public String mediumImage;
    private String largeImage;
    private String size;
    private String color;
    private String sellerInfo;
    public String stock;
    public String gender;
    public boolean availableOnline;

    public int getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public float getSalePrice() {
        return salePrice;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public String getMediumImage() {
        return mediumImage;
    }

    public String getStock() {
        return stock;
    }

    public String getGender() {
        return gender;
    }

    public boolean isAvailableOnline() {
        return availableOnline;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public String getSellerInfo() {
        return sellerInfo;
    }

    public String getLargeImage() {
        return largeImage;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//
//        dest.writeInt(itemId); 
//        dest.writeString(name); 
//        dest.writeFloat(salePrice); 
//        dest.writeString(shortDescription); 
//        dest.writeString(thumbnailImage); 
//        dest.writeString(stock); 
//        dest.writeString(gender); 
//        dest.writeByte((byte) (availableOnline ? 1 : 0)); 
//    }

}
