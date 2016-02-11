package sp0pq;

import java.util.Comparator;

/**
 * Created by shruthi on 10/2/16.
 */
public class Utility {

    public static Comparator getComparator(boolean isAscending) {
        return new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                int a = (int) o1;
                int b = (int) o2;
                if (a > b)
                    if (isAscending)
                        return -1;
                    else return 1;
                else if (a < b)
                    if (isAscending)
                        return 1;
                    else return -1;
                else return 0;
            }
        };
    }
}


