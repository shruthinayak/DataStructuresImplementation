package lp1;

public class Solution {
    public static void main(String[] args) {
//        LargeInteger a = new LargeInteger("1234567890123456789012345678901234567890");
        LargeInteger a = new LargeInteger("712820");
        LargeInteger b = new LargeInteger("1");
        LargeInteger c = LargeInteger.add(a, b);
        LargeInteger d = LargeInteger.subtract(c, a);
        LargeInteger e = LargeInteger.product(a, b);
//        c.printList();
        e.printList();
//                LargeInteger zero = new LargeInteger(0);
//                LargeInteger f = LargeInteger.product(a, zero);
//                LargeInteger two = new LargeInteger(2);
//                LargeInteger g = LargeInteger.power(two, 1025);
//        System.out.println("a = " + a);
//        System.out.println("b = " + b);
//        System.out.println("c=a+b = " + c);
//        System.out.println("a+b-a = " + d);
//                System.out.println("a*c = " + e);
//                System.out.println("a*0 = " + f);
//                System.out.println("2^1025 = " + g);
//                System.out.println("Internal representation:");
//                g.printList();
    }
}

