package lp1;

public class Solution {
    public static void main(String[] args) {
        long a1 = 1010000000;
        long b1 = 1001000000;
        LargeInteger a = new LargeInteger(a1);
        LargeInteger b = new LargeInteger(b1);
        LargeInteger c = LargeInteger.subtract(a, b);
        LargeInteger d = LargeInteger.add(a, b);
        System.out.println("Sub");
        System.out.print((a1 - b1) + " : ");
        c.printList();
        System.out.println("Add");
        System.out.print((a1 + b1) + " : ");
        d.printList();

    }
}

