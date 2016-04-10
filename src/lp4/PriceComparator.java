package lp4;

import java.util.Comparator;

/**
 * Created by shruthi on 10/4/16.
 */
public class PriceComparator implements Comparator<Item> {

    @Override
    public int compare(Item o1, Item o2) {
        return o1.getPrice() < o2.getPrice() ? -1 : 1;
    }
}
