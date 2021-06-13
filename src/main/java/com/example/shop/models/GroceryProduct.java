package com.example.shop.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("grocery_product")
public class GroceryProduct implements java.io.Serializable, Comparable<GroceryProduct>{
	@Id
	private long id;
	private String title;
	private String brand;
	@Field("pack_size")
	private String packSize;
	private int mrp;
	private int price;
	private String offers;
	@Field("stock_availibilty")
	private boolean stockAvailibility;
	@Field("image_url")
	private String imageUrl;
	private long seller;
	private String ingredients;

	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getPackSize() {
		return packSize;
	}
	public void setPackSize(String packSize) {
		this.packSize = packSize;
	}
	public int getMrp() {
		return mrp;
	}
	public void setMrp(int mrp) {
		this.mrp = mrp;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getOffers() {
		return offers;
	}
	public void setOffers(String offers) {
		this.offers = offers;
	}
	public boolean isStockAvailibility() {
		return stockAvailibility;
	}
	public void setStockAvailibility(boolean stockAvailibility) {
		this.stockAvailibility = stockAvailibility;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public long getSeller() {
		return seller;
	}
	public void setSeller(long seller) {
		this.seller = seller;
	}
	public String getIngredients() {
		return ingredients;
	}
	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}
	@Override
	public int compareTo(GroceryProduct other) {
		return (int) (this.id - other.id);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((brand == null) ? 0 : brand.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((imageUrl == null) ? 0 : imageUrl.hashCode());
		result = prime * result + ((ingredients == null) ? 0 : ingredients.hashCode());
		result = prime * result + mrp;
		result = prime * result + ((offers == null) ? 0 : offers.hashCode());
		result = prime * result + ((packSize == null) ? 0 : packSize.hashCode());
		result = prime * result + price;
		result = prime * result + (int) (seller ^ (seller >>> 32));
		result = prime * result + (stockAvailibility ? 1231 : 1237);
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GroceryProduct other = (GroceryProduct) obj;
		if (brand == null) {
			if (other.brand != null)
				return false;
		} else if (!brand.equals(other.brand))
			return false;
		if (id != other.id)
			return false;
		if (imageUrl == null) {
			if (other.imageUrl != null)
				return false;
		} else if (!imageUrl.equals(other.imageUrl))
			return false;
		if (ingredients == null) {
			if (other.ingredients != null)
				return false;
		} else if (!ingredients.equals(other.ingredients))
			return false;
		if (mrp != other.mrp)
			return false;
		if (offers == null) {
			if (other.offers != null)
				return false;
		} else if (!offers.equals(other.offers))
			return false;
		if (packSize == null) {
			if (other.packSize != null)
				return false;
		} else if (!packSize.equals(other.packSize))
			return false;
		if (price != other.price)
			return false;
		if (seller != other.seller)
			return false;
		if (stockAvailibility != other.stockAvailibility)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "GroceryProduct [id=" + id + ", title=" + title + ", brand=" + brand + ", packSize=" + packSize
				+ ", mrp=" + mrp + ", price=" + price + ", offers=" + offers + ", stockAvailibility="
				+ stockAvailibility + ", imageUrl=" + imageUrl + ", seller=" + seller + ", ingredients=" + ingredients
				+ "]";
	}
	
	
	
	
	
}
