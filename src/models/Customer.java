package models;

import exceptions.CantCancelError;
import exceptions.ItemNotAvailableError;
import exceptions.ItemNotFoundError;
import exceptions.OrderIsEmptyError;
import exceptions.UserMismatchError;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Customer extends User {
    private final List<Order> orders;
    private boolean vip;

    public Customer(String username, String password) {
        super(username, password);
        vip = false;
        orders = new ArrayList<>();
    }

    @Override
    public void options(Scanner scanner) {
        while (true) {
            System.out.println("1. Create new order");
            System.out.println("2. View past orders");
            System.out.println("3. View order status");
            System.out.println("4. Add review");
            System.out.println("5. Become a VIP");
            System.out.println("6. Reorder past order");
            System.out.println("7. Exit");
            System.out.print("Enter your choice:\t");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    create_order(scanner);
                    break;
                case 2:
                    view_past_orders();
                    break;
                case 3:
                    order_status(scanner);
                    break;
                case 4:
                    add_review(scanner);
                    break;
                case 5:
                    make_vip(scanner);
                    break;
                case 6:
                    reorder_past_order(scanner);
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void create_order(Scanner scanner) {
        Order order = new Order(this, false);
        while (true) {
            System.out.println("1. View menu");
            System.out.println("2. View items by price");
            System.out.println("3. View items by category");
            System.out.println("4. Add item to order");
            System.out.println("5. Remove item from order");
            System.out.println("6. View order");
            System.out.println("7. View reviews");
            System.out.println("8. Add special requests");
            System.out.println("9. Checkout");
            System.out.println("10. Cancel order");
            System.out.print("Enter your choice:\t");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    view_all_available();
                    break;
                case 2:
                    view_items_price();
                    break;
                case 3:
                    view_items_by_category(scanner);
                    break;
                case 4:
                    add_item_to_order(scanner, order);
                    break;
                case 5:
                    remove_item_from_order(scanner, order);
                    break;
                case 6:
                    view_order(order);
                    break;
                case 7:
                    view_reviews(scanner);
                    break;
                case 8:
                    add_special_requests(scanner, order);
                    break;
                case 9:
                    checkout(scanner, order);
                    return;
                case 10:
                    cancel_order(order);
                    System.out.println("models.Order cancelled.");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void view_past_orders() {
        for (Order order : orders) {
            System.out.println(order);
        }
    }

    private void reorder_past_order(Scanner scanner) {
        if (orders.isEmpty()) {
            System.out.println("You have no past orders to reorder.");
            return;
        }

        System.out.println("Select an order to reorder:");
        for (Order order : orders) {
            System.out.println(order);
        }
        int order_number = scanner.nextInt();
        scanner.nextLine();
        Order order_to_reorder = null;
        for (Order order : orders) {
            if (order.get_order_number() == order_number) {
                order_to_reorder = order;
                break;
            }
        }
        if (order_to_reorder == null) {
            System.out.println("models.Order not found.");
            return;
        }

        Order new_order = new Order(this, order_to_reorder.is_vip());
        for (Map.Entry<Item, Integer> entry : order_to_reorder.get_items().entrySet()) {
            try {
                new_order.add_item(entry.getKey(), entry.getValue());
            } catch (ItemNotAvailableError e) {
                System.out.println(e.getMessage());
            }
        }
        new_order.add_special_request(order_to_reorder.get_special_requests());
        System.out.println(new_order);
        System.out.print("Enter 1 to proceed to payment:\t");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice != 1) {
            return;
        }
        System.out.print("Enter payment details:\t");
        String payment_details = scanner.nextLine();
        System.out.print("Enter delivery address:\t");
        String delivery_address = scanner.nextLine();
        try {
            new_order.make_order(payment_details, delivery_address);
            System.out.println("models.Order placed successfully.");
        } catch (OrderIsEmptyError e) {
            System.out.println(e.getMessage());
        }
    }

    private void order_status(Scanner scanner) {
        for (Order order : orders) {
            if (order.get_status().equals("In Queue") || order.get_status().equals("Preparing") || order.get_status().equals("Out for delivery")) {
                System.out.println(order);
            }
        }
        System.out.print("Enter order number to cancel:\t(Enter -1 to go back)\t");
        int order_number = scanner.nextInt();
        scanner.nextLine();
        if (order_number == -1) {
            return;
        }
        Order order_to_cancel = null;
        for (Order order : orders) {
            if (order.get_order_number() == order_number) {
                order_to_cancel = order;
                break;
            }
        }
        if (order_to_cancel != null) {
            cancel_order(order_to_cancel);
        } else {
            System.out.println("models.Order not found.");
        }
    }

    private void add_review(Scanner scanner) {
        if (orders.isEmpty()) {
            System.out.println("You have no past orders to review.");
            return;
        }

        System.out.println("Select an order to review:");
        for (Order order : orders) {
            if (order.get_status().equals("Completed")) {
                System.out.println(order);
            }
        }
        int order_number = scanner.nextInt();
        scanner.nextLine();
        Order order_to_review = null;
        for (Order order : orders) {
            if (order.get_status().equals("Completed") && order.get_order_number() == order_number) {
                order_to_review = order;
                break;
            }
        }
        if (order_to_review == null) {
            System.out.println("models.Order not found.");
            return;
        }

        System.out.println("Select an item to review:");
        for (Item item : order_to_review.get_items().keySet()) {
            System.out.println(item);
        }
        String item_name = scanner.nextLine();
        Item item_to_review = null;
        for (Item item : order_to_review.get_items().keySet()) {
            if (item.get_name().equals(item_name)) {
                item_to_review = item;
                break;
            }
        }
        if (item_to_review == null) {
            System.out.println("models.Item not found.");
            return;
        }

        System.out.print("Enter your review:\t");
        String review = scanner.nextLine();
        item_to_review.add_review(this, review);
        System.out.println("Review added");
    }

    private void make_vip(Scanner scanner) {
        if (vip) {
            System.out.println("You are already a VIP.");
            return;
        }
        System.out.println("To become a VIP, you need to make a one-time payment of 100.");
        System.out.print("Enter 1 to proceed to payment:\t");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice != 1) {
            return;
        }
        System.out.print("Enter payment details:\t");
        String payment_details = scanner.nextLine();
        Sales.add_vip(payment_details);
        vip = true;
        System.out.println("You are now a VIP.");
    }

    private void view_all_available() {
        Menu.view_available();
    }

    private void view_items_price() {
        Menu.view_by_price();
    }

    private void view_items_by_category(Scanner scanner) {
        System.out.print("Enter category:\t");
        String category = scanner.nextLine();
        Menu.view_by_category(category);
    }

    private void add_item_to_order(Scanner scanner, Order order) {
        System.out.print("Enter item name:\t");
        String name = scanner.nextLine();
        try {
            Item item = Menu.search(name);
            System.out.print("Enter quantity:\t");
            int quantity = scanner.nextInt();
            scanner.nextLine();
            order.add_item(item, quantity);
            System.out.println("models.Item added to order.");
        } catch (ItemNotFoundError | ItemNotAvailableError e) {
            System.out.println(e.getMessage());
        }
    }

    private void remove_item_from_order(Scanner scanner, Order order) {
        System.out.print("Enter item name to remove:\t");
        String name = scanner.nextLine();
        try {
            Item item = Menu.search(name);
            order.remove_item(item);
            System.out.println("models.Item removed from order.");
        } catch (ItemNotFoundError e) {
            System.out.println(e.getMessage());
        }
    }

    private void view_order(Order order) {
        System.out.println(order);
    }

    private void add_special_requests(Scanner scanner, Order order) {
        System.out.print("Enter special requests:\t");
        String special_requests = scanner.nextLine();
        order.add_special_request(special_requests);
        System.out.println("Special requests added.");
    }

    private void checkout(Scanner scanner, Order order) {
        System.out.print("Enter payment details:\t");
        String payment_details = scanner.nextLine();
        System.out.print("Enter delivery address:\t");
        String delivery_address = scanner.nextLine();
        try {
            order.make_order(payment_details, delivery_address);
            System.out.println("models.Order placed successfully.");
        } catch (OrderIsEmptyError e) {
            System.out.println(e.getMessage());
        }
    }

    private void view_reviews(Scanner scanner) {
        System.out.println("Select an item to view reviews:");
        String item_name = scanner.nextLine();
        try {
            Item item = Menu.search(item_name);
            System.out.println(item);
            item.view_reviews();
        } catch (ItemNotFoundError e) {
            System.out.println(e.getMessage());
        }
    }

    private void cancel_order(Order order) {
        try {
            order.cancel(this);
            System.out.println("models.Order cancelled successfully.");
        } catch (CantCancelError | UserMismatchError e) {
            System.out.println(e.getMessage());
        }
    }

    public void add_order(Order order) {
        orders.add(order);
    }

    public void remove_order(Order order) {
        orders.remove(order);
    }
}