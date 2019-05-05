package util;



import java.time.LocalDate;

public class HelperUtil {

    public static boolean isDatesOverLapped(LocalDate startDate1, LocalDate endDate1,
                                            LocalDate startDate2, LocalDate endDate2)
            throws NullPointerException {
        return startDate1.isBefore(endDate2) && startDate2.isBefore(endDate1);
    }

}