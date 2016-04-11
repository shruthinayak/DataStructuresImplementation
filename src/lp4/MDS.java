package lp4;

import java.math.BigInteger;
import java.util.*;

/**
 * G21:
 * Shruthi Ramesh, Nayak: sxn145130
 * Tejasvee Bolisetty: txb140830
 */

public class MDS {
    /*
        idItem : A treemap of item-ids against item objects
        descItem<key, List<Item>>: A map that has description number against all the items that has <key> as a part of their description
    */
    TreeMap<Long, Item> idItem = new TreeMap<>();
    Map<Long, List<Item>> descItem = new HashMap<>();

    /**
     * Inserts an item.
     *
     * @param id           : Item id
     * @param price:       Item price
     * @param description: array of long ints
     * @param size:        size of the array
     * @return 1 if new object, 0 if old.
     */
    int insert(long id, double price, long[] description, int size) {
        Item item;
        int retValue = 1;
        //If an entry with the same id already exists, its desc and price are replaced by the new values
        if (!idItem.containsKey(id)) {
            item = new Item(id, description, price, size);
            idItem.put(id, item);
        } else {
            retValue = 0;
            item = idItem.get(id);
            //If desc is empty, then just the price is updated.
            item.setPrice(price);
            if (size > 0) {
                removeItemFromDescriptionMap(item);
                item.setDescription(description, size);
            }

        }

        for (int i = 0; i < size; i++) {
            if (!descItem.containsKey(description[i])) { //for a particular description number
                descItem.put(description[i], new LinkedList<>());
            }
            descItem.get(description[i]).add(item);
        }

        return retValue;
    }

    /**
     * Called when the item is deleted or the description is replaced.
     * @param item item to be removed
     * @return the sum of all the previous description ints
     */
    private long removeItemFromDescriptionMap(Item item) {
        long sum = 0;
        for (int i = 0; i < item.size; i++) {
            sum += item.desc[i];
            descItem.get(item.desc[i]).remove(item);
            if (descItem.get(item.desc[i]).isEmpty()) {
                descItem.remove(item.desc[i]);
            }
        }
        return sum;
    }

    /**
     * @param id Item id
     * @return price of item with given id (or 0, if not found).
     */
    double find(long id) {
        if (idItem.containsKey(id)) {
            return idItem.get(id).price;
        }
        return 0;
    }

    /**
     * delete item from storage
     * @param id Item id
     * @return Returns the sum of the long ints that are in the description of the item deleted (or 0, if such an id did not exist).
     */

    long delete(long id) {
        long sum = 0;
        if (idItem.containsKey(id)) {
            Item item = idItem.get(id);
            sum = removeItemFromDescriptionMap(item);
            idItem.remove(item.itemId);
        }
        return sum;
    }

    /**
     *
     * @param des long integer description number
     * @return find items whose description contains <des> and return lowest price of those items.
     */
    double findMinPrice(long des) {
        if (descItem.containsKey(des)) {
            List<Item> list = descItem.get(des);
            Collections.sort(list, new PriceComparator());
            return list.get(0).getPrice();
        }
        return 0;
    }

    /**
     *
     * @param des long integer description number
     * @return find items whose description contains <des> and return highest price of those items.
     */
    double findMaxPrice(long des) {
        if (descItem.containsKey(des)) {
            List<Item> list = descItem.get(des);
            Collections.sort(list, new PriceComparator());
            return list.get(list.size() - 1).getPrice();
        }
        return 0;
    }

    /**
     *
     * @param des Item id
     * @param lowPrice low
     * @param highPrice high
     * @return number of items whose description contains n, and their prices fall within the given range, [low, high]
     */
    int findPriceRange(long des, double lowPrice, double highPrice) {
        int count = 0;
        if (descItem.containsKey(des)) {
            for (Item i : descItem.get(des))
                if (i.price >= lowPrice && i.price <= highPrice)
                    count++;
        }
        return count;
    }

    /**
     * increase the price of every product, whose id is in the range [l,h], by r%
     * @param minid lower item id
     * @param maxid upper item id
     * @param rate r% by which the hike should occur
     * @return Returns the sum of the net increases of the prices.
     */

    double priceHike(long minid, long maxid, double rate) {
        double netIncrease = 0;
        if (rate > 0) {
            for (long key : idItem.tailMap(minid).keySet()) {
                if (key > maxid)
                    break;
                Item item = idItem.get(key);
                double hike = round(item.getPrice() * rate / 100.0);
                netIncrease += hike;
                item.setPrice(round(item.getPrice() + hike));
            }
        }
        return netIncrease;
    }

    private double round(double value) {
        return Math.floor((value + 0.000001) * 100) / 100;
    }

    /**
     *
     * @param lowPrice low
     * @param highPrice high
     * @return number of items whose price is at least "low" and at most "high".
     */
    int range(double lowPrice, double highPrice) {

        int count = 0;
        for (Map.Entry<Long, Item> itmEntry : idItem.entrySet()) {
            Item itm = itmEntry.getValue();
            if (itm.price >= lowPrice && itm.price <= highPrice) {
                count++;
            }
        }
        return count;
    }


    int samesame() {
        HashMap<String, ArrayList<Item>> same = new HashMap<>();
        for (Item itm : idItem.values()) {
            BigInteger sum = new BigInteger("0");
            if (itm.desc.length >= 8) {
                for (Long des : itm.desc) {
                    BigInteger temp = new BigInteger(des.toString());
                    sum = sum.add(temp.multiply(temp));
                }
                ArrayList<Item> val = new ArrayList<>();
                if (same.containsKey(sum.toString())) {
                    val = same.get(sum.toString());
                }
                val.add(itm);
                same.put(sum.toString(), val);
            }

        }
        int count = 0;
        for (ArrayList<Item> list : same.values()) {
            count += list.size() > 1 ? list.size() : 0;
        }
        return count;

    }
}