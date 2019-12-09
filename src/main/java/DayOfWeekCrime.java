import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public enum DayOfWeekCrime implements Comparable<DayOfWeekCrime>{
    SUNDAY(1,"Sunday"),
    MONDAY(2,"Monday"),
    TUESDAY(3,"Tuesday"),
    WEDNESDAY(4,"Wednesday"),
    THURSDAY(5,"Thursday"),
    FRIDAY(6,"Friday"),
    SATURDAY(7,"Saturday");

    private final int dayNumber;
    private final String dayName;

    DayOfWeekCrime(int n, String dayName) {
        this.dayNumber = n;
        this.dayName = dayName;
    }

    public static DayOfWeekCrime of(int num) {
        for(DayOfWeekCrime d : DayOfWeekCrime.values()){
            if(d.dayNumber == num)
                return d;
        }
        throw new IllegalArgumentException ("No such a number");
    }

    public static List<String> stringValues() {
        return Arrays.stream(DayOfWeekCrime.values())
                .map(dw -> dw.dayName)
                .collect(toList());
    }

    @Override
    public String toString() {
        return this.dayName;
    }
}
