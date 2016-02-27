package sp0h;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Created by shruthi on 26/2/16.
 */
public class Support {
    int a;
    int b;
    String name;

    Support(int a, int b, String name) {
        this.a = a;
        this.b = b;
        this.name = name;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(3, 5).
                append(name).append(a).append(b).
                toHashCode();
    }

    @Override
    public boolean equals(Object c) {
        if (c instanceof Support) {
            Support x = (Support) c;
            return x.a == a && x.b == b && x.name.equals(name);
        }
        return false;
    }

    @Override
    public String toString() {
        return a + ":" + b + ":" + name;
    }
}
