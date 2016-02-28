package lp1;

public class Solution {
    public static void main(String[] args) {
//        long a1 = ;
//        long b1 = 10;
//        long b1 = 209187566;
//        long a1 = 23456879;
        LargeInteger b = new LargeInteger("10");
        LargeInteger a = new LargeInteger("10");
//        LargeInteger c = LargeInteger.subtract(a, b);
//        LargeInteger d = LargeInteger.add(a, b);
        LargeInteger.divide(b, a).printList();
        /*LargeInteger.subtract(a, b).printList();
        LargeInteger.subtract(a,b).printList();
        System.out.println("Sub");
        System.out.print((a1 - b1) + " : ");
        c.printList();
        System.out.println("Add");
        System.out.print((a1 + b1) + " : ");
        d.printList();*/

    }
}

