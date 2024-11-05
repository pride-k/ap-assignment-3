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
        new Item("Pizza", 200, true, List.of("fast food", "mains"));

        Scanner scanner = new Scanner(System.in);
        try {
            new Admin("admin", "admin");
        } catch (LoginFailedError e) {
            System.out.println(e.getMessage());
        }
        new Customer("a", "a");

        Program.menu(scanner);
    }
}