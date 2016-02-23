package common;

/**
 * Created by tejas on 2/23/2016.
 */
public class Timer {
    private static int size = 2000000;
    private static int trials = 2000000;
    private static int phase = 0;
    private static long startTime, endTime, elapsedTime;

    public void timer() {
        if (phase == 0) {
            startTime = System.currentTimeMillis();
            phase = 1;
        } else {
            endTime = System.currentTimeMillis();
            elapsedTime = endTime - startTime;
            System.out.println("Time: " + elapsedTime + " msec.");
            memory();
            phase = 0;
        }
    }

    public void memory() {
        long memAvailable = Runtime.getRuntime().totalMemory();
        long memUsed = memAvailable - Runtime.getRuntime().freeMemory();
        System.out.println("Memory: " + memUsed / 1000000 + " MB / " + memAvailable / 1000000 + " MB.");
    }

}
