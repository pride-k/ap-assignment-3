package models;

import java.time.LocalDate;
import java.util.TreeMap;

public class Sales {
    private static final TreeMap<LocalDate, Daily_Sales> sales = new TreeMap<>();
    private static LocalDate current_date = LocalDate.of(1999, 12, 31);

    public static void add_vip(String sale) {
        sales.get(current_date).add_vip(sale);
    }

    public static void next_date() {
        current_date = current_date.plusDays(1);
        System.out.println("Date changed to " + current_date);
        Daily_Sales new_day = new Daily_Sales();
        sales.put(current_date, new_day);
    }

    public static LocalDate today() {
        return current_date;
    }

    public static void add_order(Order order) {
        sales.get(current_date).add_order(order);
    }

    public static void get_sales(LocalDate date) {
        System.out.println(sales.get(date));
    }
}
