package models;

import exceptions.ItemNotFoundError;
import exceptions.LoginFailedError;
import exceptions.OrderNotFoundError;
import exceptions.QueueEmptyError;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Admin extends User {
    public Admin(String username, String password) throws LoginFailedError {
        super(username, validatePassword(password));
    }

    private static String validatePassword(String password) throws LoginFailedError {
        if (!"admin".equals(password)) {
            throw new LoginFailedError("Login failed: Invalid username or password.");
        }
        return password;
    }

    @Override
    public void options(Scanner scanner) {
        while (true) {
            System.out.println("1. Manage menu");
            System.out.println("2. Manage orders");
            System.out.println("3. Manage refunds");
            System.out.println("4. Get sales report");
            System.out.println("5. Exit");
            System.out.print("Enter your choice:\t");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    manage_menu(scanner);
                    break;
                case 2:
                    manage_orders(scanner);
                    break;
                case 3:
                    manage_refunds(scanner);
                    break;
                case 4:
                    get_sales_report(scanner);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void manage_menu(Scanner scanner) {
        while (true) {
            System.out.println("1. Add item");
            System.out.println("2. Update item");
            System.out.println("3. Delete item");
            System.out.println("4. View all items");
            System.out.println("5. View items by price");
            System.out.println("6. View items by category");
            System.out.println("7: View all available items");
            System.out.println("8. Back to main menu");
            System.out.print("Enter your choice:\t");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    add_item(scanner);
                    break;
                case 2:
                    update_item(scanner);
                    break;
                case 3:
                    delete_item(scanner);
                    break;
                case 4:
                    view_all_items();
                    break;
                case 5:
                    view_items_price();
                    break;
                case 6:
                    view_items_by_category(scanner);
                    break;
                case 7:
                    view_all_available();
                    break;
                case 8:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void manage_orders(Scanner scanner) {
        while (true) {
            System.out.println("1. View next order");
            System.out.println("2. Accept order");
            System.out.println("3. Deny order");
            System.out.println("4. View preparing list");
            System.out.println("5. Deliver order");
            System.out.println("6. View delivering list");
            System.out.println("7. Complete order");
            System.out.println("8. Back to main menu");
            System.out.print("Enter your choice:\t");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    view_next_order();
                    break;
                case 2:
                    accept_order();
                    break;
                case 3:
                    deny_order();
                    break;
                case 4:
                    view_prep();
                    break;
                case 5:
                    deliver_order(scanner);
                    break;
                case 6:
                    view_del();
                    break;
                case 7:
                    complete_order(scanner);
                    break;
                case 8:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void manage_refunds(Scanner scanner) {
        while (true) {
            System.out.println("1. View refund requests");
            System.out.println("2. Manage refund");
            System.out.println("3. Back to main menu");
            System.out.print("Enter your choice:\t");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    view_refund_requests();
                    break;
                case 2:
                    manage_refund(scanner);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void manage_refund(Scanner scanner) {
        System.out.print("Enter order number to manage refund:\t");
        int order_number = scanner.nextInt();
        scanner.nextLine();
        Order current = null;
        for (Order order : Refund.all()) {
            if (order.get_order_number() == order_number) {
                current = order;
                break;
            }
        }
        if (current == null) {
            System.out.println("models.Order not found.");
            return;
        }
        System.out.println(current);
        System.out.print("Accept or Deny refund (A/D):\t");
        String decision = scanner.nextLine();
        System.out.print("Enter refund info:\t");
        String refund_info = scanner.nextLine();
        try {
            if ("A".equalsIgnoreCase(decision)) {
                Refund.accept(refund_info, current);
                System.out.println("models.Refund accepted.");
            } else if ("D".equalsIgnoreCase(decision)) {
                Refund.decline(refund_info, current);
                System.out.println("models.Refund declined.");
            } else {
                System.out.println("Invalid decision.");
            }
        } catch (OrderNotFoundError e) {
            System.out.println(e.getMessage());
        }
    }

    private void get_sales_report(Scanner scanner) {
        System.out.println("1. Get today's sales");
        System.out.println("2. Get sales by date");
        System.out.print("Enter your choice:\t");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                get_today_sales();
                break;
            case 2:
                get_sales_by_date(scanner);
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    private void get_today_sales() {
        Sales.get_sales(Sales.today());
    }

    private void get_sales_by_date(Scanner scanner) {
        System.out.print("Enter day\t");
        int day = scanner.nextInt();
        System.out.print("Enter month\t");
        int month = scanner.nextInt();
        System.out.print("Enter year\t");
        int year = scanner.nextInt();
        scanner.nextLine();
        try {
            Sales.get_sales(LocalDate.of(year, month, day));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void view_refund_requests() {
        Refund.show();
    }

    private void view_prep() {
        Orders.view_prep();
    }

    private void view_del() {
        Orders.view_delivering();
    }

    private void view_items_by_category(Scanner scanner) {
        System.out.print("Enter category:\t");
        String category = scanner.nextLine();
        Menu.view_by_category(category);
    }

    private void add_item(Scanner scanner) {
        System.out.print("Enter item name:\t");
        String name = scanner.nextLine();
        System.out.print("Enter item price:\t");
        int price = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Is the item available (true/false):\t");
        boolean available = scanner.nextBoolean();
        scanner.nextLine();
        System.out.print("Enter item categories (comma separated):\t");
        String categories_input = scanner.nextLine();
        List<String> categories = List.of(categories_input.split(","));
        Item item = new Item(name, price, available, categories);
        Menu.add(item);
        System.out.println("models.Item added successfully.");
    }

    private void update_item(Scanner scanner) {
        System.out.print("Enter item name to update:\t");
        String name = scanner.nextLine();
        try {
            Item item = Menu.search(name);
            System.out.print("Enter new price:\t");
            int price = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Is the item available (true/false):\t");
            boolean available = scanner.nextBoolean();
            scanner.nextLine();
            System.out.print("Enter new categories (comma separated):\t");
            String categories_input = scanner.nextLine();
            List<String> categories = List.of(categories_input.split(","));
            item.update_price(price);
            item.update_availability(available);
            item.update_categories(categories);
            System.out.println("models.Item updated successfully.");
        } catch (ItemNotFoundError e) {
            System.out.println(e.getMessage());
        }
    }

    private void delete_item(Scanner scanner) {
        System.out.print("Enter item name to delete:\t");
        String name = scanner.nextLine();
        try {
            Menu.remove(name);
            System.out.println("models.Item deleted successfully.");
        } catch (ItemNotFoundError e) {
            System.out.println(e.getMessage());
        }
    }

    private void view_all_items() {
        Menu.view();
    }

    private void view_all_available() {
        Menu.view_available();
    }

    private void view_items_price() {
        Menu.view_by_price();
    }

    private void view_next_order() {
        try {
            Order next_order = Orders.view_next();
            System.out.println(next_order);
        } catch (QueueEmptyError e) {
            System.out.println(e.getMessage());
        }
    }

    private void accept_order() {
        try {
            Orders.accept_order();
            System.out.println("models.Order accepted.");
        } catch (QueueEmptyError e) {
            System.out.println(e.getMessage());
        }
    }

    private void deny_order() {
        try {
            Orders.deny_order();
            System.out.println("models.Order denied.");
        } catch (QueueEmptyError e) {
            System.out.println(e.getMessage());
        }
    }

    private void deliver_order(Scanner scanner) {
        System.out.print("Enter order number to deliver:\t");
        int order_number = scanner.nextInt();
        scanner.nextLine();
        try {
            Orders.deliver(order_number);
            System.out.println("models.Order out for delivery.");
        } catch (OrderNotFoundError e) {
            System.out.println(e.getMessage());
        }
    }

    private void complete_order(Scanner scanner) {
        System.out.print("Enter order number to complete:\t");
        int order_number = scanner.nextInt();
        scanner.nextLine();
        try {
            Orders.complete(order_number);
            System.out.println("models.Order completed.");
        } catch (OrderNotFoundError e) {
            System.out.println(e.getMessage());
        }
    }
}