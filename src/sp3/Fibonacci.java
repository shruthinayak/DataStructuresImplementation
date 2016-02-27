package sp3;

/**
 * Created by tejas on 2/25/2016.
 */
public class Fibonacci {
    /**
     * calculates the nth fibonacci to the mod p in O(n)
     *
     * @param n fibonacci
     * @param p
     * @return nth fibonacci mod p
     */
    public static long linearFibonacci(long n, long p) {
        long fib[][] = {{1, 1}, {1, 0}};
        long fib2[][] = {{1, 1}, {1, 0}};
        if (n == 0) return 0;
        for (long i = 2; i < n; i++) {
            fib = getProduct(fib, fib2, p);
        }

        return fib[0][0];
    }

    /**
     * finds the multiplication product of two matrices
     *
     * @param fib  matrix 1
     * @param fib2 matrix 2
     * @param p
     * @return result matrix of multiplication
     */
    public static long[][] getProduct(long[][] fib, long[][] fib2, long p) {
        long a = fib[0][0] * fib2[0][0] % p;
        long b = fib[0][1] * fib2[1][0] % p;
        long c = fib[0][0] * fib2[0][1] % p;
        long d = fib[0][1] * fib2[1][1] % p;
        long e = fib[1][0] * fib2[0][0] % p;
        long f = fib[1][1] * fib2[1][0] % p;
        long g = fib[1][0] * fib2[0][1] % p;
        long h = fib[1][1] * fib2[1][1] % p;
        fib[0][0] = (a + b) % p;
        fib[0][1] = (c + d) % p;
        fib[1][0] = (e + f) % p;
        fib[1][1] = (g + h) % p;
        return fib;
    }

    /**
     * calculates nth fibonacci mod p in O(logn) time
     *
     * @param n
     * @param p
     * @return nth fibonacci mod p
     */
    public static long logFibonacci(long n, long p) {
        if (n == 0) return 0;
        long fib[][] = {{1, 1}, {1, 0}};
        fib = power(fib, n - 1, p);
        return fib[0][0];
    }

    /**
     * calculates matrix to the power of n
     *
     * @param fib
     * @param n
     * @param p
     * @return result of the matrix power
     */
    public static long[][] power(long[][] fib, long n, long p) {
        if (n == 0 || n == 1) return fib;
        long fib2[][] = {{1, 1}, {1, 0}};
        fib = power(fib, n / 2, p);
        fib = getProduct(fib, fib, p);
        if (n % 2 != 0) return getProduct(fib, fib2, p);
        else return fib;
    }

}
