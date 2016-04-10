package lp4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;

/**
 * Sample driver code for Project LP4.  Modify as needed.
 * Do not change input/output formats.
 *
 * @author rbk
 */
public class LP4Driver {
    static final int DLENGTH = 100000;
    static long[] description;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in;
//        args[0] = "lp4-data/lp4-1.txt";
//        args[0] = "lp4-data/lp4-2.txt";
//        args[0] = "lp4-data/lp4-3-1k.txt"; //52252.36
//        args[0] = "lp4-data/lp4-4-5k.txt"; //490409.01
        args[0] = "lp4-data/lp4-5-ck.txt"; //173819092858.24
        if (args.length > 0) {
            in = new Scanner(new File(args[0]));
        } else {
            in = new Scanner(System.in);
        }
        String s;
        double rv = 0;
        description = new long[DLENGTH];
        double temp = 0;
        Timer timer = new Timer();
        MDS mds = new MDS();
        int line = 0;
        while (in.hasNext()) {
            s = in.next();
            if (s.charAt(0) == '#') {
                s = in.nextLine();
                continue;
            }
            if (s.equals("Insert")) {
                long id = in.nextLong();
                double price = in.nextDouble();
                long des = in.nextLong();
                int index = 0;
                while (des != 0) {
                    description[index++] = des;
                    des = in.nextInt();
                }
                description[index] = 0;
                temp = mds.insert(id, price, description, index);
                rv += temp;
            } else if (s.equals("Find")) {
                long id = in.nextLong();
                temp = mds.find(id);
                rv += temp;
            } else if (s.equals("Delete")) {
                long id = in.nextLong();
                temp = mds.delete(id);
                rv += temp;
            } else if (s.equals("FindMinPrice")) {
                long des = in.nextLong();
                temp = mds.findMinPrice(des);
                rv += temp;
            } else if (s.equals("FindMaxPrice")) {
                long des = in.nextLong();
                temp = mds.findMaxPrice(des);
                rv += temp;
            } else if (s.equals("FindPriceRange")) {
                long des = in.nextLong();
                double lowPrice = in.nextDouble();
                double highPrice = in.nextDouble();
                temp = mds.findPriceRange(des, lowPrice, highPrice);
                rv += temp;
            } else if (s.equals("PriceHike")) {
                long minid = in.nextLong();
                long maxid = in.nextLong();
                double rate = in.nextDouble();
                temp = mds.priceHike(minid, maxid, rate);
                rv += temp;
            } else if (s.equals("Range")) {
                double lowPrice = in.nextDouble();
                double highPrice = in.nextDouble();
                temp = mds.range(lowPrice, highPrice);
                rv += temp;
            } else if (s.equals("SameSame")) {
                temp = mds.samesame();
                rv += temp;
            } else if (s.equals("End")) {
                break;
            } else {
                System.out.println("Houston, we have a problem.\nUnexpected line in input: " + s);
                System.exit(0);
            }
            line++;
//            System.out.print(line + ": " + s + ": ");
//            temp= Math.floor(temp*100)/100;
            String a = temp + "";
            if (a.endsWith(".0")) {
                a = a.replace(".0", "");
                //    System.out.println(a);
            } else {
                //   System.out.printf("%.2f\n", temp);
            }
            /*if(temp==1){
                System.out.println(1);
            }else{
            System.out.printf("%.2f\n", temp);}*/
        }
//        mds.print();
        System.out.printf("%.2f\n", rv);
        System.out.println(timer.end());
    }
}