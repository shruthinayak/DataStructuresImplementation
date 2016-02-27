package lp1;

import common.Timer;

public class Solution {
    public static void main(String[] args) {
//        LargeInteger a = new LargeInteger("1234567890123456789012345678901234567890");
        LargeInteger a = new LargeInteger("100"); //105211212344//90569784495866770974195656280275310090138980613960953881501965823101
        LargeInteger b = new LargeInteger("120");//1541232356477
//        LargeInteger c = LargeInteger.add(a, b);
//        LargeInteger d = LargeInteger.subtract(c, a);
        Timer time = new Timer();
        time.timer();
        LargeInteger f = LargeInteger.power(a, 10000);

//        LargeInteger e = LargeInteger.product(a, b);
        time.timer();
//        LargeInteger g= LargeInteger.power(a,b); //00000000000000000000000000000000000000000000162889462677744140625
//        c.printList();
//        e.printList();
        f.printList();
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

