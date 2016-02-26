package lp1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class LargeInteger {
    final static int B = 100;
    final int pow = 2;
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
        return add(a.number, b.number);
    }

    static LargeInteger add(List<Long> a, List<Long> b) {
        Iterator aI = a.iterator();
        Iterator bI = b.iterator();
        LargeInteger result = new LargeInteger("");
        long carry = 0;
        while (aI.hasNext() || bI.hasNext()) {
            long x = 0;
            if (aI.hasNext())
                x = (long) aI.next();
            long y = 0;
            if (bI.hasNext())
                y = (long) bI.next();

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

    static LargeInteger product(LargeInteger a, LargeInteger b, int start, int end) {
        //Assuming the lists are of even size and the equal.
        /*if (start == end) {
            return new LargeInteger(multiply(a.number.get(start), b.number.get(start)));
        }
        int index = (int) Math.ceil((end - start) / 2);
        LargeInteger a1 = product(a, b, start, index);
        LargeInteger d1 = product(a, b, index + 1, end);
        LargeInteger addA = add(a, start, index, );
        LargeInteger addB = add(b.number.subList(start, index), b.number.subList(index + 1, end));
        LargeInteger prodAB = product(addA, addB, 0, addA.number.size() - 1);
        LargeInteger middle = subtract(subtract(prodAB, a1), d1);
        a1.printList();
        d1.printList();
        middle.printList();*/
        return null;
    }

    static long multiply(long a, long b) {
        return a * b;
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

    LargeInteger power(LargeInteger a, long n) {
        return null;
    }

    void printList() {
        System.out.println(toString());
    }
}
