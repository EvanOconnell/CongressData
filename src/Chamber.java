/**
 * Enumeration class Chamber - chamber of congress (house or senate)
 * 
 * @author Tang
 * @version 2013.02.02
 */
public enum Chamber
{
    HOUSE("house"), 
    SENATE("senate");

    private final String name;

    Chamber(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
