package lp4;

import java.math.BigInteger;
import java.util.*;

public class MDS {
    final long MAX = -1L;
    final long MIN = -2L;
    TreeMap<Long, Item> idItem = new TreeMap<>();
    Map<Long, List<Item>> descItem = new HashMap<>();

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
                List<Item> temp = new LinkedList<>();
                descItem.put(description[i], temp);
            }
            descItem.get(description[i]).add(item);
        }

        return retValue;
    }

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
            List<Item> list = descItem.get(des);
            Collections.sort(list, new PriceComparator());
            return list.get(0).getPrice();
        }
        return 0;
    }

    double findMaxPrice(long des) {
        if (descItem.containsKey(des)) {
            List<Item> list = descItem.get(des);
            Collections.sort(list, new PriceComparator());
            return list.get(list.size() - 1).getPrice();
        }
        return 0;
    }

    int findPriceRange(long des, double lowPrice, double highPrice) {
        int count = 0;
        if (descItem.containsKey(des)) {
            for (Item i : descItem.get(des))
                if (i.price >= lowPrice && i.price <= highPrice)
                    count++;
        }
        return count;
    }

    double priceHike(long minid, long maxid, double rate) {
        double netIncrease = 0;
        //TODO: rearrange max min
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

    void print() {
        for (Long i : idItem.keySet()) {
            System.out.println(idItem.get(i).toString());
        }
        for (Long i : descItem.keySet()) {
            System.out.print(i + "- ");
            for (Item key : descItem.get(i)) {
                System.out.print(key + ":" + key.price + ", ");
            }
            System.out.println();
        }
        System.out.println();
    }
}