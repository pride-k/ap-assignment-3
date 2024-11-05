package models;

import exceptions.OrderNotFoundError;

import java.util.ArrayList;
import java.util.List;

public class Refund {
    private static final List<Order> refunding = new ArrayList<>();

    public static List<Order> all() {
        return refunding;
    }

    public static void add(Order order) {
        refunding.add(order);
    }

    public static void show() {
        for (Order order : refunding) {
            System.out.println(order);
        }
    }

    public static void accept(String refund_info, Order order) throws OrderNotFoundError {
        if (!refunding.contains(order)) {
            throw new OrderNotFoundError("models.Order with number " + order.get_order_number() + " not found.");
        }
        refunding.remove(order);
        order.accept_refund(refund_info);

    }

    public static void decline(String refund_info, Order order) throws OrderNotFoundError {
        if (!refunding.contains(order)) {
            throw new OrderNotFoundError("models.Order with number " + order.get_order_number() + " not found.");
        }
        refunding.remove(order);
        order.decline_refund(refund_info);

    }
}
