package lp1;

public class Solution {
    public static void main(String[] args) {
        LargeInteger a = new LargeInteger("13456789000");
        LargeInteger b = new LargeInteger("206789");
        LargeInteger.mod(a, b).printList();
        System.out.println(LargeInteger.mod(a, b).toString());
        LargeInteger.factorial(new LargeInteger(1L)).printList();
//        LargeInteger.subtract(new LargeInteger(13456789000L), new LargeInteger(13456380597L)).printList();

    }
}

