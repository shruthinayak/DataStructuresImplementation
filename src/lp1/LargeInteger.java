package lp1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class LargeInteger {
    final static int B = 1000;
    final int pow = 3;
    List<Long> number = new ArrayList<>();

    LargeInteger(String s) {
        int len = s.length() - pow;
        int i;
        for (i = len; i > -1; i -= pow) {
            String temp = s.substring(i, i + pow);
            number.add(Long.parseLong(temp));
        }
        if (!s.isEmpty() && i + pow != 0)
            number.add(Long.parseLong(s.substring(0, i + pow)));
    }

    LargeInteger(Long num) {
        this(String.valueOf(num));
    }

    static String leadingZeroes(String s, int pow) {
        return String.format(String.format("%%0%dd", pow), Long.parseLong(s));
    }

    static LargeInteger add(LargeInteger a, LargeInteger b) {
        Iterator aI = a.number.iterator();
        Iterator bI = b.number.iterator();
        LargeInteger result = new LargeInteger("");
        long carry = 0;
        while (aI.hasNext() || bI.hasNext()) {
            long x = 0;
            if (aI.hasNext())
                x = Long.parseLong(aI.next().toString());
            long y = 0;
            if (bI.hasNext())
                y = Long.parseLong(bI.next().toString());

            long sum = x + y + carry;
            result.number.add(sum % B);
            carry = sum / B;
        }

        return result;
    }

    static LargeInteger subtract(LargeInteger a, LargeInteger b) {
        boolean reverse = false;
        if (b.number.size() > a.number.size()) {
            reverse = true;
        } else if (b.number.size() == a.number.size() && b.number.get(b.number.size() - 1) > a.number.get(a.number.size() - 1)) {
            reverse = true;
        }
        Iterator aI = a.number.iterator();
        Iterator bI = b.number.iterator();
        LargeInteger result = new LargeInteger("");
        long borrow = 0;
        while (aI.hasNext() || bI.hasNext()) {
            long x = 0;
            if (aI.hasNext())
                x = Long.parseLong(aI.next().toString());
            long y = 0;
            if (bI.hasNext())
                y = Long.parseLong(bI.next().toString());
            if (reverse) {
                long temp = x;
                x = y;
                y = temp;
            }
            long diff = x - y - borrow;
            if (diff < 0) {
                diff = (x + B) - y - borrow;
                borrow = 1;
            } else
                borrow = 0;
            result.number.add(diff);
        }
        if (reverse) {
            int index = result.number.size() - 1;
            result.number.add(index, 0 - result.number.get(index));
        }

        return result;
    }

    @Override
    public String toString() {
        int len = number.size() - 1;
        StringBuilder sb = new StringBuilder();
        for (int i = len; i >= 0; i--) {
            Long aLong = number.get(i);
            if (aLong != 0)
                if (i != len)
                    sb.append(leadingZeroes(aLong.toString(), pow));
                else
                    sb.append(aLong.toString());
        }
        return sb.toString();
    }

    LargeInteger product(LargeInteger a, LargeInteger b) {
        return null;
    }

    LargeInteger power(LargeInteger a, long n) {
        return null;
    }

    void printList() {
        System.out.println(toString());
    }
}
