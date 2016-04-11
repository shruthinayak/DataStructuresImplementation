package lp4;

import java.util.Comparator;

/**
 * G21:
 * Shruthi Ramesh, Nayak: sxn145130
 * Tejasvee Bolisetty: txb140830
 */

public class PriceComparator implements Comparator<Item> {

    @Override
    public int compare(Item o1, Item o2) {
        return o1.getPrice() < o2.getPrice() ? -1 : 1;
    }
}
