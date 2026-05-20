package Utility;

import java.time.LocalDate;

public class DateUtility {
    public static long daysBetween(LocalDate startDate, LocalDate endDate){
        return endDate.toEpochDay() - startDate.toEpochDay();
    }
}
