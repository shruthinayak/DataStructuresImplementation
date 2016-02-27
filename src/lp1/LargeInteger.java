package lp1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class LargeInteger {
    final static int B = 10;
    final int pow = 1;
    boolean sign;

    List<Long> number;//= new ArrayList<>();

    LargeInteger() {
        this.number = new ArrayList<>();
    }
    LargeInteger(String s) {
        this.number = new ArrayList<>();
        int len = s.length() - pow;
        int i;
        for (i = len; i > -1; i -= pow) {
            String temp = s.substring(i, i + pow);
            number.add(Long.parseLong(temp));
        }
        if (!s.isEmpty() && i + pow != 0)
            number.add(Long.parseLong(s.substring(0, i + pow)));
    }

    LargeInteger(LargeInteger x, int start, int end) {
//        this.number = new ArrayList<>();
        this.number = x.number.subList(start, end);
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
        LargeInteger result = new LargeInteger();
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
        if (carry > 0) {
            result.number.add(carry);
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

    static LargeInteger multiply(LargeInteger a, LargeInteger b) {
        if (a.number.size() == 1 && b.number.size() == 1) {
            return new LargeInteger(a.number.get(0) * b.number.get(0));
        } else {
            boolean flag = a.number.size() == 1 ? true : false;
            LargeInteger result = new LargeInteger();
            if (flag) {
                multiply(a, b, result);
            } else {
                multiply(b, a, result);
            }
            return result;
        }

    }

    private static void multiply(LargeInteger a, LargeInteger b, LargeInteger result) {
        Long num1 = a.number.get(0);
        long carry = 0;
        for (Long num2 : b.number) {
            Long pro = num1 * num2 + carry;
            //if product is greater than base, take carry
            long temp = pro % B;
            carry = pro / B;
            result.number.add(temp);
            /*if (pro > a.B) {
                LargeInteger temp = new LargeInteger(pro.toString());
                result.number.addAll(temp.number);
            } else {
                result.number.add(pro);
            }*/
        }
        if (carry != 0) {
            result.number.add(carry);
        }
    }

    static LargeInteger product(LargeInteger a, LargeInteger b) {
        //Assuming the lists are of even size and the equal.
//        if (a.number.size() != b.number.size()) {
//            int n = Math.max(a.number.size(), b.number.size());
//            while (a.number.size() != n) {
//                a.number.add((long) 0);
//            }
//            while (b.number.size() != n) {
//                b.number.add((long) 0);
//            }
//        }
        if (a.number.size() == 1 || b.number.size() == 1) {
            return multiply(a, b);
        }
        //number of digits in max(a,b)
        int n = Math.min(a.number.size(), b.number.size());

        LargeInteger al = new LargeInteger(a, 0, n / 2);
        LargeInteger ar = new LargeInteger(a, n / 2, a.number.size());
        LargeInteger bl = new LargeInteger(b, 0, n / 2);
        LargeInteger br = new LargeInteger(b, n / 2, b.number.size());
        LargeInteger xl = product(al, bl);
        LargeInteger xr = product(ar, br);
        LargeInteger aSum = add(al, ar);
        LargeInteger bSum = add(bl, br);
        LargeInteger xm = product(aSum, bSum);

        LargeInteger middle = subtract(subtract(xm, xl), xr);
        for (int i = 0; i < n / 2; i++) {
            middle.number.add(0, (long) 0);
        }
        if (n % 2 != 0) n--;
        for (int i = 0; i < n; i++) {
            xr.number.add(0, (long) 0);
        }
        LargeInteger result = add(add(xl, middle), xr);
        return result;

    }

    static LargeInteger power(LargeInteger a, long n) {
        if (n == 0) return new LargeInteger((long) 1);
        if (n == 1) return a;
        LargeInteger result = power(a, n / 2);
        result = product(result, result);
        if (n % 2 != 0) {
            result = product(result, a);
        }
        return result;
    }


    static LargeInteger power(LargeInteger a, LargeInteger n) {
        if (n.number.size() == 1) return power(a, n.number.get(0));
        else {
            long a0 = n.number.get(0);
//            LargeInteger n1= new LargeInteger();
//            n1.number=n.number.subList(1,n.number.size());
            n.number.remove(0);
            LargeInteger xtos = power(a, n);
            return product(power(xtos, B), power(a, a0));
        }
    }

    @Override
    public String toString() {
        int len = number.size() - 1;
        StringBuilder sb = new StringBuilder();
        for (int i = len; i >= 0; i--) {
            Long aLong = number.get(i);
            if (i != len)
                    sb.append(leadingZeroes(aLong.toString(), pow));
                else
                    sb.append(aLong.toString());
        }
        return sb.toString();
    }

    void printList() {
        System.out.println(toString());
    }
}
