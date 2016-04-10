package lp4;

/**
 * Created by shruthi on 9/4/16.
 */
public class Item {
    long itemId;
    long[] description;
    double price;

    public Item(long itemId, long[] description, double price) {
        this.itemId = itemId;
        this.description = description;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long[] getDescription() {
        return description;
    }

    public void setDescription(long[] description) {
        this.description = description;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return "(" + itemId + ", " + price + ")";
    }
}

