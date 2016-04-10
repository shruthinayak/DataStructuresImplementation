package lp4;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MDS {
    TreeMap<Long, Item> idItem = new TreeMap<>();
    Map<Long, TreeMap<Long, Item>> descItem = new HashMap<>();

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
            if (size > 0) {
                removeItemFromDescriptionMap(item);
                item.setDescription(description, size);
            }
            item.setPrice(price);
        }

        for (int i = 0; i < size; i++) {
            if (!descItem.containsKey(description[i])) {
                TreeMap<Long, Item> s = new TreeMap<>();
                s.put(item.getItemId(), item);
                descItem.put(description[i], s);
            } else {
                descItem.get(description[i]).put(item.getItemId(), item);
            }
        }

        return retValue;
    }

    private long removeItemFromDescriptionMap(Item item) {
        long sum = 0;
        for (int i = 0; i < item.size; i++) {
            sum += item.desc[i];
            descItem.get(item.desc[i]).remove(item.getItemId());
            if (descItem.get(item.desc[i]).size() == 0)
                descItem.remove(item.desc[i]);
        }
        return sum;
    }

    double find(long id) {
        if (idItem.containsKey(id)) {
            return idItem.get(id).price;
        }
        return 0;
    }

    long delete(long id) {
        long sum = 0;
        if (idItem.containsKey(id)) {
            Item item = idItem.get(id);
            sum = removeItemFromDescriptionMap(item);
            idItem.remove(item.itemId);
        }
        return sum;
    }

    double findMinPrice(long des) {
        return 0;
    }

    double findMaxPrice(long des) {
        return 0;
    }

    int findPriceRange(long des, double lowPrice, double highPrice) {
        return 0;
    }

    double priceHike(long minid, long maxid, double rate) {
        return 0;
    }

    int range(double lowPrice, double highPrice) {
        return 0;
    }

    int samesame() {
        return 0;
    }

    void print() {
        for (Long i : idItem.keySet()) {
            System.out.println(idItem.get(i).toString());
        }
        for (Long i : descItem.keySet()) {
            System.out.print(i + ": ");
            for (Long key : descItem.get(i).keySet()) {
                System.out.print(descItem.get(i).get(key).itemId + ", ");
            }
            System.out.println();
        }
        System.out.println();
    }
}