package lp1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


/**
 * Created by shruthi on 28/2/16.
 */
public class ShuntingYard {
    Queue<Character> q = new LinkedList<>();
    Stack<Operator> stack = new Stack<>();

    public static void main(String[] args) throws FileNotFoundException {
        String pathname = "";

        Scanner read = new Scanner(new File(pathname));
        String line = null;
        ArrayList<String> inputCommands = new ArrayList<>();
        HashMap<String, LargeInteger> vars = new HashMap<>();
        while (read.hasNext()) {
            line = read.nextLine();
            if (Character.isDigit(line.charAt(0)))
                inputCommands.add(line.substring(2));

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
                String postfix = evaluate(split[1]);

            }

        }
    }

    static String evaluate(String expression) {
        Queue<Character> q = new LinkedList<>();
        Stack<Operator> stack = new Stack<>();
        stack.push(Operator.NULL);
        char[] e = expression.split("=")[1].toCharArray();
        for (char i : e) {
            if (i == ' ')
                continue;
            if (isAtom(i)) {
                q.add(i);
            } else {
                Operator n = getOperator(i);
                if (n == Operator.CLOSE) {
                    while (stack.peek() != Operator.OPEN) {
                        q.add(stack.pop().sign);
                    }
                    stack.pop();
                } else if (n == Operator.OPEN) {
                    stack.push(Operator.OPEN);
                    continue;
                } else if (n.precedence > stack.peek().precedence) {
                    stack.push(n);
                } else {
                    while (n.precedence <= stack.peek().precedence && !n.left) {
                        q.add(stack.pop().sign);
                    }
                    stack.push(n);
                }

            }
        }
        while (!(stack.size() == 1))
            q.add(stack.pop().sign);
        StringBuilder sb = new StringBuilder();
        for (char c : q) {
            sb.append(c);
        }
        return sb.toString();
    }

    //a=a+3*4^2^3!

    static boolean isAtom(char ch) {
        return Character.isLetter(ch) || Character.isDigit(ch);
    }

    static Operator getOperator(char ch) {

        switch (ch) {
            case '!':
                return Operator.FACT;
            case '^':
                return Operator.POWER;
            case '*':
                return Operator.PRODUCT;
            case '/':
                return Operator.DIVIDE;
            case '%':
                return Operator.MOD;
            case '+':
                return Operator.SUM;
            case '-':
                return Operator.DIFFERENCE;
            case '(':
                return Operator.OPEN;
            case ')':
                return Operator.CLOSE;
        }
        return Operator.NULL;
    }

    enum Operator {
        FACT('!', 10),
        POWER('^', 10, true),
        PRODUCT('*', 9),
        DIVIDE('/', 9),
        MOD('%', 9),
        SUM('+', 8),
        DIFFERENCE('-', 8),
        OPEN('(', 0),
        CLOSE(')', 0),
        NULL(';', -1),;

        char sign;
        int precedence;
        boolean left;

        Operator(char sign, int precedence) {
            this(sign, precedence, false);
        }

        Operator(char sign, int precedence, boolean left) {
            this.sign = sign;
            this.precedence = precedence;
            this.left = left;
        }

    }


}
