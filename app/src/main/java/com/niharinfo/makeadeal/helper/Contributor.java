package com.niharinfo.makeadeal.helper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chaitanya on 27/5/15.
 */
public class Contributor {
        @SerializedName("product_id")
        private String productId;
        @SerializedName("cat_id")
        private String catId;
        @SerializedName("subcat_id")
        private String subcatId;
        @Expose
        private String title;
        @Expose
        private String alias;
        @Expose
        private String brand;
        @SerializedName("product_desc")
        private String productDesc;
        @SerializedName("meta_title")
        private String metaTitle;
        @SerializedName("meta_key")
        private String metaKey;
        @SerializedName("meta_desc")
        private String metaDesc;
        @Expose
        private String created;
        @Expose
        private String updated;
        @Expose
        private String id;
        @SerializedName("seller_id")
        private String sellerId;
        @SerializedName("product_code")
        private String productCode;
        @Expose
        private String price;
        @Expose
        private String discount;
        @SerializedName("delivery_date")
        private String deliveryDate;
        @Expose
        private String url;
        @Expose
        private String thumb;
        @Expose
        private String stock;
        @SerializedName("is_seller_deal")
        private String isSellerDeal;
        @Expose
        private String deal;
        @SerializedName("created_at")
        private String createdAt;
        @SerializedName("updated_at")
        private String updatedAt;
        @SerializedName("view_counter")
        private Float viewCounter;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getCatId() {
            return catId;
        }

        public void setCatId(String catId) {
            this.catId = catId;
        }

        public String getSubcatId() {
            return subcatId;
        }

        public void setSubcatId(String subcatId) {
            this.subcatId = subcatId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getProductDesc() {
            return productDesc;
        }

        public void setProductDesc(String productDesc) {
            this.productDesc = productDesc;
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

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSellerId() {
            return sellerId;
        }

        public void setSellerId(String sellerId) {
            this.sellerId = sellerId;
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getDeliveryDate() {
            return deliveryDate;
        }

        public void setDeliveryDate(String deliveryDate) {
            this.deliveryDate = deliveryDate;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public String getIsSellerDeal() {
            return isSellerDeal;
        }

        public void setIsSellerDeal(String isSellerDeal) {
            this.isSellerDeal = isSellerDeal;
        }

        public String getDeal() {
            return deal;
        }

        public void setDeal(String deal) {
            this.deal = deal;
        }

        public String getCreatedAt() {
            return createdAt;
        }
        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }
        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Float getViewCounter() {
            return viewCounter;
        }
        public void setViewCounter(Float viewCounter) {
            this.viewCounter = viewCounter;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }
    public Contributor(String title,String price,String discount, String thumb) {
        this.title = title;
        this.discount = discount;
        this.price=price;
        this.thumb=thumb;
    }

}
