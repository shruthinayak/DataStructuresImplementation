package lp4;

/**
 * Created by shruthi on 9/4/16.
 */
public class Item {
    long itemId;
    long[] desc;
    double price;
    int size;

    public Item(long itemId, long[] description, double price, int size) {
        this.itemId = itemId;
        desc = new long[size];
        for (int i = 0; i < size; i++) {
            desc[i] = description[i];
        }
        this.price = price;
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long[] getDesc() {
        return desc;
    }

    public void setDescription(long[] description, int size) {
        desc = new long[size];
        for (int i = 0; i < size; i++) {
            desc[i] = description[i];
        }
        this.size = size;
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

