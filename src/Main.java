import exceptions.LoginFailedError;

import models.Admin;
import models.Customer;
import models.Item;
import models.Program;
import models.Sales;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Sales.next_date();
        new Item("Burger", 100, true, List.of("fast food", "mains"));
        new Item("Pizza", 200, false, List.of("fast food", "mains"));
        new Item("Pasta", 150, true, List.of("fast food", "sides"));
        new Item("Coke", 50, true, List.of("drinks"));
        new Item("Paratha", 50, true, List.of("mains"));
        new Item("Biryani", 200, false, List.of("mains"));
        new Item("Toast", 50, false, List.of("breakfast"));
        new Item("Tea", 50, true, List.of("drinks"));
        new Item("Coffee", 50, true, List.of("drinks"));

        Scanner scanner = new Scanner(System.in);
        try {
            new Admin("admin", "admin");
        } catch (LoginFailedError e) {
            System.out.println(e.getMessage());
        }
        new Customer ("Aman", "aman@1234");
        new Customer("Ravi", "ravi@1234");
        new Customer("Riya", "riya@1234");
        new Customer("Urvi", "urvi@1234");

        new Customer("a", "a");
        Program.menu(scanner);
    }
}