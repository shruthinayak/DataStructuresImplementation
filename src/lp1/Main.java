package lp1;

public class Main {
    public static void main(String[] args) {
        String x = "9876540000987659";
        String y = "10785621877891059";
        System.out.println(x);
        LargeInteger l = new LargeInteger(x);
        LargeInteger m = new LargeInteger(y);
        l.printList();
        System.out.println(y);
        m.printList();
        l.add(l, m).printList();

        l.subtract(l, m).printList();
    }

}
