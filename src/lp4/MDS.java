package lp4;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MDS {
    final long MAX = -1l;
    final long MIN = -2l;
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

            if (!descItem.containsKey(description[i])) { //for a particular description number
                TreeMap<Long, Item> s = new TreeMap<>();
                s.put(MAX, item);
                s.put(MIN, item);
                s.put(item.getItemId(), item);
                descItem.put(description[i], s);
            } else {
                TreeMap<Long, Item> longItemTreeMap = descItem.get(description[i]);
                if (item.getPrice() > longItemTreeMap.get(MAX).getPrice()) {
                    longItemTreeMap.put(MAX, item);
                }
                if (item.getPrice() < longItemTreeMap.get(MIN).getPrice()) {
                    longItemTreeMap.put(MIN, item);
                }
                longItemTreeMap.put(item.getItemId(), item);
            }
        }

        return retValue;
    }

    private long removeItemFromDescriptionMap(Item item) {
        long sum = 0;
        for (int i = 0; i < item.size; i++) {
            sum += item.desc[i];
            TreeMap<Long, Item> longItemTreeMap = descItem.get(item.desc[i]);
            longItemTreeMap.remove(item.getItemId());

            //if no other items left, delete the description number
            if (longItemTreeMap.size() == 2)
                descItem.remove(item.desc[i]);
            else {
                //else check if the deleted item was minimum of any of description list.
                if (item.getItemId() == longItemTreeMap.get(MAX).getItemId() || item.getItemId() == longItemTreeMap.get(MIN).getItemId()) {
                    rearrangeMaxMin(item, i);
                }
            }

        }
        return sum;
    }

    private void rearrangeMaxMin(Item item, int i) {

        TreeMap<Long, Item> longItemTreeMap = descItem.get(item.desc[i]);
        Item max = new Item();
        Item min = new Item();
        max.setPrice(Double.MIN_VALUE);
        min.setPrice(Double.MAX_VALUE);
        longItemTreeMap.put(MAX, max);
        longItemTreeMap.put(MIN, min);
        for (Long key : longItemTreeMap.keySet()) {
            if (key != MAX && key != MIN) {
                if (longItemTreeMap.get(key).getPrice() > max.getPrice()) {
                    longItemTreeMap.put(MAX, longItemTreeMap.get(key));
                }
                if (longItemTreeMap.get(key).getPrice() < min.getPrice()) {
                    longItemTreeMap.put(MIN, longItemTreeMap.get(key));
                }
            }
        }
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
        if (descItem.containsKey(des)) {
            return descItem.get(des).get(MIN).getPrice();
        }
        return 0;
    }

    double findMaxPrice(long des) {
        if (descItem.containsKey(des)) {
            return descItem.get(des).get(MAX).getPrice();
        }
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
            System.out.print(i + "- ");
            for (Long key : descItem.get(i).keySet()) {
                System.out.print(key + ":" + descItem.get(i).get(key).price + ", ");
            }
            System.out.println();
        }
        System.out.println();
    }
}