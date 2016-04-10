package lp4;

import java.util.*;

public class MDS {
    TreeMap<Long, Item> idItem = new TreeMap<>();
    Map<Long, List<Item>> descItem = new HashMap<>();

    int insert(long id, double price, long[] description, int size) {
        Item item;
        int retValue = 1;
        //If an entry with the same id already exists, its description and price are replaced by the new values
        if (!idItem.containsKey(id)) {
            item = new Item(id, description, price);
            idItem.put(id, item);
        } else {
            retValue = 0;
            item = idItem.get(id);
            //If description is empty, then just the price is updated.
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    descItem.get(item.description[i]).remove(item);
                }
                item.description = description;
            }
            item.price = price;
        }

        for (int i = 0; i < size; i++) {
            if (!descItem.containsKey(description[i])) {
                List<Item> s = new ArrayList<>();
                s.add(item);
                descItem.put(description[i], s);
            } else {
                descItem.get(description[i]).add(item);
            }
        }

        return retValue;
    }

    double find(long id) {
        return 0;
    }

    long delete(long id) {
        return 0;
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
            for (Item j : descItem.get(i))
                System.out.print(j.itemId + ", ");
            System.out.println();
        }
        System.out.println();
    }
}