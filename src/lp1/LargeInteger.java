package lp1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class LargeInteger {
    final static int B = 100; //Base
    final int pow = 2;
    boolean positive = true; //Sign. By default, the number is assumed to be positive

    List<Long> number; //List in which we store the number in the reverse way.

    LargeInteger() {
        this.number = new ArrayList<>();
    }

    LargeInteger(String s) {
        if (s.startsWith("-")) {
            positive = false;
            s = s.substring(1);
        }

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
        this.number = x.number.subList(start, end);
    }

    LargeInteger(LargeInteger x, int start, int end, boolean sign) {
        this.number = x.number.subList(start, end);
        this.positive = sign;
    }

    LargeInteger(Long num) {
        this(String.valueOf(num));
    }

    /**
     * Utility function that appends zeroes to a certain number
     *
     * @param s   the string that has to be appended with zeroes
     * @param pow the width of the string
     * @return string with leading zeroes
     */
    static String leadingZeroes(String s, int pow) {
        return String.format(String.format("%%0%dd", pow), Long.parseLong(s));
    }

    static LargeInteger add(LargeInteger a, LargeInteger b) {
        return decideAddSubtract(a, b);
    }

    /**
     * Returns the sum of the 2 LargeIntegers
     *
     * @param a LargeInteger List
     * @param b LargeInteger List
     * @return LargeInteger that returns the sum of a and b
     */
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
        b.positive = !b.positive;//314, -625
        LargeInteger i = decideAddSubtract(a, b);
        b.positive = !b.positive;
        return i;
    }

    /**
     * Function that decides whether to add or subtract based on the signs.
     *
     * @param a
     * @param b
     * @return LargeInteger accordingly.
     */
    private static LargeInteger decideAddSubtract(LargeInteger a, LargeInteger b) {
        LargeInteger result;
        if (a.positive == b.positive) {
            result = add(a.number, b.number);
            result.positive = a.positive;
        } else {
            result = subtract(a.number, b.number);
            result.positive = getSign(a, b);
        }
        return result;
    }

    /**
     * Subtract logic for 2 LargeIntegers
     *
     * @param a
     * @param b
     * @return LargeInteger that contains the difference of a and b.
     */
    static LargeInteger subtract(List<Long> a, List<Long> b) {
        boolean reverse = false; //boolean to represent whether a-b or b-a
        int zero = 0;
        int curr = 0, count = 0;
        if (b.size() > a.size()) {
            reverse = true;
        } else if (b.size() == a.size()) {
            int len = b.size();
            do {
                if (b.get(len - 1) > a.get(len - 1)) {
                    reverse = true;
                    break;
                } else if (b.get(len - 1) < a.get(len - 1)) {
                    break;
                }
                len--;
            } while (len > 0);
        }
        Iterator aI = a.iterator();
        Iterator bI = b.iterator();
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
            if (diff > 0) {
                curr = count;
            }
            count++;
            result.number.add(diff);
        }

        //Remove if any leading zeroes.
        while (curr < count - 1) {
            result.number.remove(curr + 1);
            count--;
        }

        return result;
    }

    /**
     * Gets the sign for the LargeInteger that contains the difference of a and b.
     *
     * @param a
     * @param b
     * @return true - indicates positive sign. false - indicates negative sign
     */
    static boolean getSign(LargeInteger a, LargeInteger b) {
        //is b>a?

        if (b.number.size() > a.number.size()) {
            return b.positive;
        } else if (b.number.size() == a.number.size()) {
            int len = b.number.size();
            do {
                if (b.number.get(len - 1) > a.number.get(len - 1)) {
                    return b.positive;
                } else if (b.number.get(len - 1) < a.number.get(len - 1)) {
                    return a.positive;
                }
                len--;
            } while (len > 0);
        }
        return a.positive;

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
            //if product1 is greater than base, take carry
            long temp = pro % B;
            carry = pro / B;
            result.number.add(temp);
        }
        if (carry != 0) {
            result.number.add(carry);
        }
    }

    static LargeInteger product(LargeInteger a, LargeInteger b) {
        boolean aSign = a.positive;
        boolean bSign = b.positive;
        boolean pos = a.positive == b.positive;
        a.positive = true;
        b.positive = true;
        LargeInteger result = product1(a, b);
        result.positive = pos;
        a.positive = aSign;
        b.positive = bSign;

        return result;
    }

    /**
     * Returns the product of a and b.
     *
     * @param a
     * @param b
     * @return LargeInteger that contains the product of a and b.
     */
    static LargeInteger product1(LargeInteger a, LargeInteger b) {

        if (a.number.size() == 1 || b.number.size() == 1) {
            return multiply(a, b);
        }
        //number of digits in max(a,b)
        int n = Math.min(a.number.size(), b.number.size());

        LargeInteger al = new LargeInteger(a, 0, n / 2);
        LargeInteger ar = new LargeInteger(a, n / 2, a.number.size());
        LargeInteger bl = new LargeInteger(b, 0, n / 2);
        LargeInteger br = new LargeInteger(b, n / 2, b.number.size());
        LargeInteger xl = product1(al, bl);
        LargeInteger xr = product1(ar, br);
        LargeInteger aSum = add(al, ar);
        LargeInteger bSum = add(bl, br);
        LargeInteger xm = product1(aSum, bSum);

        LargeInteger middle = subtract(subtract(xm, xl), xr);
        for (int i = 0; i < n / 2; i++) {
            middle.number.add(0, (long) 0);
        }
        if (n % 2 != 0) n--;
        for (int i = 0; i < n; i++) {
            xr.number.add(0, (long) 0);
        }
        LargeInteger result = add(add(xl, middle), xr);

        while (result.number.get(result.number.size() - 1) == 0 && result.number.size() > 1) {
            result.number.remove(result.number.size() - 1);

        }

        return result;

    }

    /**
     * Computes the power
     *
     * @param a the number
     * @param n the exponent
     * @return LargeInteger that represents the number to the power exponent.
     */
    static LargeInteger power(LargeInteger a, long n) {
        if (n == 0) return new LargeInteger((long) 1);
        if (n == 1) return a;
        LargeInteger result = power(a, n / 2);
        result = product1(result, result);
        if (n % 2 != 0) {
            result = product1(result, a);
        }
        return result;
    }

    /**
     * Computes the power when the exponent is a LargeInteger
     *
     * @param a
     * @param n
     * @return LargeInteger that represents the power when the exponent is a LargeInteger
     */
    static LargeInteger power(LargeInteger a, LargeInteger n) {
        if (n.number.size() == 1) return power(a, n.number.get(0));
        else {
            long a0 = n.number.get(0);
            n.number.remove(0);
            LargeInteger xtos = power(a, n);
            return product1(power(xtos, B), power(a, a0));
        }
    }

    static LargeInteger divide(LargeInteger a, LargeInteger b) {
        return divideMod(a, b, true);
    }

    /**
     * Computes Remainder or Quotient.
     *
     * @param a      the numerator
     * @param b      the denominator
     * @param divide boolean that decides whether quotient is required or remainder
     * @return Remainder or Quotient depending on the boolean
     */
    static LargeInteger divideMod(LargeInteger a, LargeInteger b, boolean divide) {
        boolean aSign = a.positive;
        boolean bSign = b.positive;
        if (b.toString().equals("0")) {
            throw new IllegalArgumentException();
        }
        boolean pos = a.positive == b.positive;
        LargeInteger low = new LargeInteger((long) 1);
        LargeInteger high = a;
        a.positive = true;
        b.positive = true;

        if (compare(a, b) == -1) {
            a.positive = aSign;
            b.positive = bSign;
            return new LargeInteger(0L);
        }
        if (b.toString().equals("1") || b.toString().equals("-1")) {
            a.positive = aSign;
            b.positive = bSign;
            return new LargeInteger(a, 0, a.number.size(), pos);
        }
        LargeInteger prevPowMid = null;
        LargeInteger powMid = null;
        while (compare(add(low, new LargeInteger(1L)), high) == -1) {
            LargeInteger mid = divideByN(add(low, high));
            prevPowMid = powMid;
            powMid = product1(mid, b);
            if (compare(powMid, a) == 1) {
                high = mid;
            } else if (compare(powMid, a) == -1) {
                low = mid;
            } else {
                mid.positive = pos;
                a.positive = aSign;
                b.positive = bSign;
                //powMid.printList();
                if (divide) return mid;
                else return subtract(powMid, a);
            }
        }
        if (compare(powMid, a) == -1) {
            prevPowMid = powMid;
        }
        a.positive = aSign;
        b.positive = bSign;
        low.positive = pos;
        if (divide) return low;
        else return subtract(a, prevPowMid);
    }

    /**
     * Utility function that computes a/2
     * @param a LargeInteger
     * @return LargeInteger that represents a/2
     */
    static LargeInteger divideByN(LargeInteger a) {
        LargeInteger result = new LargeInteger();
        long carry = 0;
        long n;
        for (int i = a.number.size() - 1; i >= 0; i--) {
            n = ((carry * B) + a.number.get(i)) / 2;
            carry = a.number.get(i) % 2;
            result.number.add(0, n);
        }
        while (result.number.get(result.number.size() - 1) == 0) {
            result.number.remove(result.number.size() - 1);
        }
        return result;
    }

    /**
     * Returns if a>b
     * @param a
     * @param b
     * @return integer 0 representing they are equal, 1 if greater, -1 if lower
     */
    public static int compare(LargeInteger a, LargeInteger b) {

        LargeInteger temp = subtract(a, b);
        // have to compare the number greater than 0
        if (temp.toString().equals("0"))
            return 0;
        if (temp.positive) {
            return 1;
        } else {
            return -1;
        }

    }

    static LargeInteger squareRoot(LargeInteger a) {

        LargeInteger low = new LargeInteger((long) 1);
        LargeInteger high = a;
        if (a.toString().equals("0")) {
            return a;
        }

        while (compare(add(low, new LargeInteger(1L)), high) == -1) {
            LargeInteger mid = divide(add(low, high), new LargeInteger(2L));
            LargeInteger powMid = power(mid, 2);
            if (compare(powMid, a) == 1) {
                high = mid;
            } else if (compare(powMid, a) == -1) {
                low = mid;
            } else return mid;
        }
        return low;
    }

    static LargeInteger mod(LargeInteger a, LargeInteger b) {
        if (a.positive && b.positive) {
            return divideMod(a, b, false);
        } else {
            throw new IllegalArgumentException();
        }
    }

    static LargeInteger factorial(LargeInteger a) {
        LargeInteger x = new LargeInteger(2L);
        LargeInteger result = new LargeInteger(a, 0, a.number.size());
        while (compare(x, a) < 0) {
            result = product(result, x);
            x = add(x, new LargeInteger(1L));
        }

        return result;
    }

    @Override
    public String toString() {
        int len = number.size() - 1;
        StringBuilder sb = new StringBuilder();
        if (!positive) {
            sb.append("-");
        }
        boolean zero = true; //Never add leading zeroes at the beginning. Hence always "True" for the first time.
        for (int i = len; i >= 0; i--) {
            Long aLong = number.get(i);
            if (!zero)
                sb.append(leadingZeroes(aLong.toString(), pow));
            else {
                sb.append(aLong.toString());
                zero = false;
            }


        }
        if (sb.toString().length() == 0) {
            sb.append("0");
        }

        return sb.toString();
    }


    void printList() {
        /*System.out.print(B + ":");
        for (long a : this.number) {
            System.out.print(a + " ");
        }
        System.out.println();*/
        System.out.println(toString());
    }

}
