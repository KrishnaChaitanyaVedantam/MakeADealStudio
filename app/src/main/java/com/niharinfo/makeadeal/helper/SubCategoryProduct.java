package com.niharinfo.makeadeal.helper;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.niharinfo.makeadeal.RetrofitHelpers.SubCategory;

import java.io.Serializable;

/**
 * Created by chaitanya on 22/5/15.
 */
public class SubCategoryProduct implements Parcelable{

    public SubCategoryProduct(){}

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getProductDeal() {
        return productDeal;
    }

    public void setProductDeal(String productDeal) {
        this.productDeal = productDeal;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(String productDiscount) {
        this.productDiscount = productDiscount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProductThumb() {
        return productThumb;
    }

    public void setProductThumb(String productThumb) {
        this.productThumb = productThumb;
    }



    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getSubCategoryID() {
        return subCategoryID;
    }

    public void setSubCategoryID(String subCategoryID) {
        this.subCategoryID = subCategoryID;
    }

    public String getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle;
    }

    public String getMetaKey() {
        return metaKey;
    }

    public void setMetaKey(String metaKey) {
        this.metaKey = metaKey;
    }

    public String getMetaDesc() {
        return metaDesc;
    }

    public void setMetaDesc(String metaDesc) {
        this.metaDesc = metaDesc;
    }

    public String getSellerID() {
        return sellerID;
    }

    public void setSellerID(String sellerID) {
        this.sellerID = sellerID;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getStockAvailable() {
        return stockAvailable;
    }

    public void setStockAvailable(String stockAvailable) {
        this.stockAvailable = stockAvailable;
    }

    public String getIsSellerDeal() {
        return isSellerDeal;
    }

    public void setIsSellerDeal(String isSellerDeal) {
        this.isSellerDeal = isSellerDeal;
    }

    public String getViewCounter() {
        return viewCounter;
    }

    public void setViewCounter(String viewCounter) {
        this.viewCounter = viewCounter;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    public SubCategoryProduct(String title,String price,String discount, String thumb) {
        this.productTitle = title;
        this.productDiscount = discount;
        this.productPrice=price;
        this.productThumb=thumb;
    }

    public SubCategoryProduct(String productID,String productTitle,String categoryID,String createdAt,String aliasName,
                              String productDeal,String productBrand,String productPrice,String productDiscount,String imageUrl,String productThumb,
                              String productDescription,String updatedAt,String subCategoryID,String metaTitle,String metaKey,
                              String metaDesc,String pid,String sellerID,String productCode,String deliveryDate,String stockAvailable,
                              String isSellerDeal,String viewCounter,String rating,String totalRatings){
        this.productID = productID;
        this.productTitle = productTitle;
        this.categoryID = categoryID;
        this.createdAt = createdAt;
        this.aliasName = aliasName;
        this.productDeal = productDeal;
        this.productBrand = productBrand;
        this.productPrice = productPrice;
        this.productDiscount = productDiscount;
        this.imageUrl = imageUrl;
        this.productThumb = productThumb;
        this.productDescription = productDescription;
        this.updatedAt = updatedAt;
        this.subCategoryID = subCategoryID;
        this.metaTitle = metaTitle;
        this.metaKey = metaKey;
        this.metaDesc = metaDesc;
        this.pid = pid;
        this.sellerID = sellerID;
        this.productCode = productCode;
        this.deliveryDate = deliveryDate;
        this.stockAvailable = stockAvailable;
        this.isSellerDeal = isSellerDeal;
        this.viewCounter = viewCounter;
        this.rating = rating;
        this.totalRatings = totalRatings;
    }

    private SubCategoryProduct(Parcel in){
        this.productID = in.readString();
        this.productTitle = in.readString();
        this.categoryID = in.readString();
        this.createdAt = in.readString();
        this.aliasName = in.readString();
        this.productDeal = in.readString();
        this.productBrand = in.readString();
        this.productPrice = in.readString();
        this.productDiscount = in.readString();
        this.imageUrl = in.readString();
        this.productThumb = in.readString();
        this.productDescription = in.readString();
        this.updatedAt = in.readString();
        this.subCategoryID = in.readString();
        this.metaTitle = in.readString();
        this.metaKey = in.readString();
        this.metaDesc = in.readString();
        this.pid = in.readString();
        this.sellerID = in.readString();
        this.productCode = in.readString();
        this.deliveryDate = in.readString();
        this.stockAvailable = in.readString();
        this.isSellerDeal = in.readString();
        this.viewCounter = in.readString();
        this.rating = in.readString();
        this.totalRatings = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productID);
        dest.writeString(productTitle);
        dest.writeString(categoryID);
        dest.writeString(createdAt);
        dest.writeString(aliasName);
        dest.writeString(productDeal);
        dest.writeString(productBrand);
        dest.writeString(productPrice);
        dest.writeString(productDiscount);
        dest.writeString(imageUrl);
        dest.writeString(productThumb);
        dest.writeString(productDescription);
        dest.writeString(updatedAt);
        dest.writeString(subCategoryID);
        dest.writeString(metaTitle);
        dest.writeString(metaKey);
        dest.writeString(metaDesc);
        dest.writeString(pid);
        dest.writeString(sellerID);
        dest.writeString(productCode);
        dest.writeString(deliveryDate);
        dest.writeString(stockAvailable);
        dest.writeString(isSellerDeal);
        dest.writeString(viewCounter);
        dest.writeString(rating);
        dest.writeString(totalRatings);
    }

    public static final Parcelable.Creator<SubCategoryProduct> CREATOR = new Parcelable.Creator<SubCategoryProduct>(){
        @Override
        public SubCategoryProduct createFromParcel(Parcel source) {
            return new SubCategoryProduct(source);
        }

        @Override
        public SubCategoryProduct[] newArray(int size) {
            return new SubCategoryProduct[size];
        }
    };

    @SerializedName("product_id")
    private String productID;
    @SerializedName("title")
    private String productTitle;
    @SerializedName("cat_id")
    private String categoryID;
    @SerializedName("created")
    private String createdAt;
    @SerializedName("alias")
    private String aliasName;
    @SerializedName("deal")
    private String productDeal;
    @SerializedName("brand")
    private String productBrand;
    @SerializedName("price")
    private String productPrice;
    @SerializedName("discount")
    private String productDiscount;
    @SerializedName("url")
    private String imageUrl;
    @SerializedName("thumb")
    private String productThumb;
    @SerializedName("product_desc")
    private String productDescription;
    @SerializedName("updated")
    private String updatedAt;
    @SerializedName("subcat_id")
    private String subCategoryID;
    @SerializedName("meta_title")
    private String metaTitle;
    @SerializedName("meta_key")
    private String metaKey;
    @SerializedName("meta_desc")
    private String metaDesc;
    @SerializedName("id")
    private String pid;
    @SerializedName("seller_id")
    private String sellerID;
    @SerializedName("product_code")
    private String productCode;
    @SerializedName("delivery_date")
    private String deliveryDate;
    @SerializedName("stock")
    private String stockAvailable;
    @SerializedName("is_seller_deal")
    private String isSellerDeal;
    @SerializedName("view_counter")
    private String viewCounter;

    public String getTotalRatings() {
        return totalRatings;
    }

    public void setTotalRatings(String totalRatings) {
        this.totalRatings = totalRatings;
    }

    @SerializedName("tot_ratings")
    private String totalRatings;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Expose
    private String rating;
}
