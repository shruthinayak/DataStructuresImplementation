package lp1;


import com.sun.jna.platform.win32.WinDef;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;
import java.util.stream.Collector;


public class Driver {
    public static void main(String[] args) throws FileNotFoundException {

        String pathname = "C:\\D\\Implementation\\projects\\DataStructuresImplementation\\lp1-data-s1\\lp1-data\\s1\\lp0-s1-in-5.txt";
//        String pathname = args[1];
        lp1_driver(pathname);
//        lp1_driver_level2(pathname);
    }

    private static void lp1_driver(String pathname) throws FileNotFoundException {

        Scanner read = new Scanner(new File(pathname));
        String line = null;
        ArrayList<String> inputCommands = new ArrayList<>();
        HashMap<String, LargeInteger> vars = new HashMap<>();
        while (read.hasNext()) {
            line = read.nextLine();
            if (Character.isDigit(line.charAt(0)))
                inputCommands.add(line.split(" ")[1]);//substring(2));

        }

        for (int i = 0; i < inputCommands.size(); i++) {
            String s = inputCommands.get(i);
            LargeInteger current = null;

            if (s.contains("=")) {
                String[] split = s.split("=");
                String var = split[0].trim();

                if (split[1].trim().matches("-?\\d+"))
                    vars.put(var, new LargeInteger(split[1].trim()));
                else if (!vars.containsKey(var))
                    vars.put(var, new LargeInteger());

                String sign = getSign(split[1]);
                if (sign.isEmpty()) continue;
                String[] values = split[1].trim().split(("\\" + sign));
                LargeInteger var1 = getLargeInteger(values[0].trim(), vars);
                LargeInteger var2 = getLargeInteger(values[1].trim(), vars);
                switch (sign) {
                    case "+":
                        current = LargeInteger.add(var1, var2);
                        break;
                    case "-":
                        current = LargeInteger.subtract(var1, var2);
                        break;
                    case "*":
                        current = LargeInteger.product(var1, var2);
                        break;
                    case "/":
                        current = LargeInteger.divide(var1, var2);
                        break;
                    case "%":
                        current = LargeInteger.mod(var1, var2);
                        break;
                    case "^":
                        current = LargeInteger.power(var1, var2);
                        break;
                    case "!":
                        current = LargeInteger.factorial(var1);
                        break;
                    case "~":
                        current = LargeInteger.squareRoot(var1);
                        break;

                }
                vars.put(var, current);

            } else if (s.contains("?")) {
                String[] split = s.split("\\?");
                current = getLargeInteger(split[0].trim(), vars);
                String[] linenos = split[1].trim().split(":");
                int l1 = Integer.parseInt(linenos[0].trim());
                if (!current.toString().equals("0"))
                    i = l1 - 2;
                else if (linenos.length > 2) {
                    i = Integer.parseInt(linenos[1].trim()) - 2;
                }
            } else if (s.contains(")")) {
                String[] split = s.split("\\)");
                current = getLargeInteger(split[0].trim(), vars); //vars.get(split[0].trim());
                current.printList();
            } else {
                current = getLargeInteger(s.trim(), vars);// vars.get(s.trim());
                System.out.println(current);
            }
        }
        read.close();
    }


    private static LargeInteger getLargeInteger(String variable, HashMap<String, LargeInteger> vars) {
        LargeInteger nVariable;
        if (variable.matches("-?\\d+"))
            nVariable = new LargeInteger(variable);
        else
            nVariable = vars.get(variable);
        return nVariable;
    }


    public static String getSign(String s) {
        String[] signs = {"+", "-", "*", "/", "^", "!", "~", "%"};
        for (String i : signs) {
            if (s.contains(i)) {
                return i;
            }
        }
        return "";
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
