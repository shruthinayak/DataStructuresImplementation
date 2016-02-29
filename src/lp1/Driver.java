package lp1;

/**
 * Created by tejas on 2/28/2016.
 */

import common.Timer;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * Driver program for LP1, level 1
 *
 * @author rbk
 *         Edit and replace "XYZ" by the name of your class
 */

public class Driver {
    public static void main(String[] args) throws FileNotFoundException {
        LargeInteger a = new LargeInteger("-01211");
        LargeInteger b = new LargeInteger("010");
        b.printList();
        Timer time = new Timer();
//        LargeInteger c = LargeInteger.product(a, b);
//        System.out.println("c= " + c);

        lp1_driver();
    }

    private static void lp1_driver() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("C:\\D\\Implementation\\projects\\DataStructuresImplementation\\lp1-data-s1\\lp1-data\\s1\\lp0-s1-in-5.txt"));
        String line = null;
        ArrayList<String> inputCommands = new ArrayList<>();
        HashMap<String, LargeInteger> vars = new HashMap<>();
        while (true) {
            try {
                line = sc.nextLine();
                if (Character.isDigit(line.charAt(0)))
                    inputCommands.add(line.substring(2));
            } catch (NoSuchElementException nse) {
                break;
            }
        }
        for (int i = 0; i < inputCommands.size(); i++) {
            String s = inputCommands.get(i);
            LargeInteger current = null;
            if (s.contains("=")) {
                String[] split = s.split("=");
                String var = split[0].trim();
                if (!vars.containsKey(var)) {
                    if (split[1].trim().matches("-?\\d+"))
                        vars.put(var, new LargeInteger(split[1].trim()));
                    else
                        vars.put(var, new LargeInteger());
                }
                current = vars.get(var);
                if (split[1].contains("+")) {
                    String[] values = split[1].trim().split("\\+");
                    LargeInteger var1 = vars.get(values[0].trim());
                    LargeInteger var2 = vars.get(values[1].trim());
                    current = LargeInteger.add(var1, var2);
                    vars.put(var, current);
                } else if (split[1].contains("-")) {
                    String[] values = split[1].trim().split("-");
                    LargeInteger var1 = vars.get(values[0].trim());
                    LargeInteger var2 = vars.get(values[1].trim());
                    current = LargeInteger.subtract(var1, var2);
                    vars.put(var, current);
                } else if (split[1].contains("*")) {
                    String[] values = split[1].trim().split("\\*");
                    LargeInteger var1 = vars.get(values[0].trim());
                    LargeInteger var2 = vars.get(values[1].trim());
                    current = LargeInteger.product(var1, var2);
                    vars.put(var, current);
                } else if (split[1].contains("/")) {
                    String[] values = split[1].trim().split("/");
                    LargeInteger var1 = vars.get(values[0].trim());
                    LargeInteger var2 = vars.get(values[1].trim());
                    current = LargeInteger.divide(var1, var2);
                    vars.put(var, current);
                } else if (split[1].contains("%")) {
                    String[] values = split[1].trim().split("%");
                    LargeInteger var1 = vars.get(values[0].trim());
                    LargeInteger var2 = vars.get(values[1].trim());
                    current = LargeInteger.mod(var1, var2);
                    vars.put(var, current);
                } else if (split[1].contains("^")) {
                    String[] values = split[1].trim().split("\\^");
                    LargeInteger var1 = vars.get(values[0].trim());
                    LargeInteger var2 = vars.get(values[1].trim());
                    current = LargeInteger.power(var1, var2);
                    vars.put(var, current);
                } else if (split[1].contains("!")) {
                    String[] values = split[1].trim().split("!");
                    LargeInteger var1 = vars.get(values[0].trim());
                    current = LargeInteger.factorial(var1);
                    vars.put(var, current);
                } else if (split[1].contains("~")) {
                    String[] values = split[1].trim().split("~");
                    LargeInteger var1 = vars.get(values[0].trim());
                    current = LargeInteger.squareRoot(var1);
                    vars.put(var, current);
                }
            } else if (s.contains("?")) {
                String[] split = s.split("\\?");
                current = vars.get(split[0].trim());
                String[] linenos = split[1].trim().split(":");
                int l1 = Integer.parseInt(linenos[0].trim());
                if (!current.toString().equals("0"))
                    i = l1 - 2;//because i++ will follow, and the list is 0 indexed
                else if (linenos.length > 2) {
                    i = Integer.parseInt(linenos[1].trim()) - 2;
                }
            } else if (s.contains(")")) {
                String[] split = s.split("\\)");
                current = vars.get(split[0].trim());
                current.printList();
            } else {
                current = vars.get(s.trim());
                System.out.println(current);
            }
        }
        sc.close();
    }
}
/*
Output:
a = 1234567890123456789012345678901234567890
b = 999
c=a+b = 1234567890123456789012345678901234568889
a+b-a = 999
a*c = 1524157875323883675049535156256668195733866777995869531010835238422208352374210
a*0 = 0
2^1025 = 359538626972463181545861038157804946723595395788461314546860162315465351611001926265416954644815072042240227759742786715317579537628833244985694861278948248755535786849730970552604439202492188238906165904170011537676301364684925762947826221081654474326701021369172596479894491876959432609670712659248448274432
Internal representation:
*****Depends on the base********
*/
