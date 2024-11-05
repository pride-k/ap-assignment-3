package models;

import exceptions.OrderNotFoundError;
import exceptions.QueueEmptyError;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Orders {
    private static final Queue<Order> vip_queue = new LinkedList<>();
    private static final Queue<Order> normal_queue = new LinkedList<>();
    private static final List<Order> prep = new ArrayList<>();
    private static final List<Order> delivering = new ArrayList<>();

    public static void add_order(Order order) {
        if (order.is_vip()) {
            vip_queue.add(order);
        } else {
            normal_queue.add(order);
        }
    }

    public static Order view_next() throws QueueEmptyError {
        if (vip_queue.isEmpty() && normal_queue.isEmpty()) {
            throw new QueueEmptyError("No order to prepare");
        }
        if (!vip_queue.isEmpty()) {
            return vip_queue.peek();
        } else {
            return normal_queue.peek();
        }
    }

    public static void accept_order() throws QueueEmptyError {
        if (vip_queue.isEmpty() && normal_queue.isEmpty()) {
            throw new QueueEmptyError("No order to prepare");
        }
        Order next_order;
        if (!vip_queue.isEmpty()) {
            next_order = vip_queue.poll();
        } else {
            next_order = normal_queue.poll();
        }
        prep.add(next_order);
        next_order.accept();
    }

    public static void deny_order() throws QueueEmptyError {
        if (vip_queue.isEmpty() && normal_queue.isEmpty()) {
            throw new QueueEmptyError("No order to deny");
        }
        Order next_order;
        if (!vip_queue.isEmpty()) {
            next_order = vip_queue.poll();
        } else {
            next_order = normal_queue.poll();
        }
        next_order.deny();
    }

    public static void deliver(int order_number) throws OrderNotFoundError {
        Order order_to_deliver = null;
        for (Order order : prep) {
            if (order.get_order_number() == order_number) {
                order_to_deliver = order;
                break;
            }
        }
        if (order_to_deliver != null) {
            prep.remove(order_to_deliver);
            delivering.add(order_to_deliver);
            order_to_deliver.deliver();
        } else {
            throw new OrderNotFoundError("models.Order with number " + order_number + " not found in preparation list.");
        }
    }

    public static void complete(int order_number) throws OrderNotFoundError {
        Order order_to_complete = null;
        for (Order order : delivering) {
            if (order.get_order_number() == order_number) {
                order_to_complete = order;
                break;
            }
        }
        if (order_to_complete != null) {
            delivering.remove(order_to_complete);
            order_to_complete.complete();
        } else {
            throw new OrderNotFoundError("models.Order with number " + order_number + " not found in delivering list.");
        }
    }

    public static void view_prep() {
        System.out.println("models.Orders in preparation:");
        for (Order order : prep) {
            System.out.println(order);
        }
    }

    public static void view_delivering() {
        System.out.println("models.Orders out for delivery:");
        for (Order order : delivering) {
            System.out.println(order);
        }
    }

    public static void remove_order(Order order) {
        if (order.is_vip()) {
            vip_queue.remove(order);
        } else {
            normal_queue.remove(order);
        }
    }

    public static void remove_item(Item item) {
        List<Order> to_deny_vip = new ArrayList<>();
        for (Order order : vip_queue) {
            if (order.get_items().containsKey(item)) {
                to_deny_vip.add(order);
            }
        }
        for (Order order : to_deny_vip) {
            vip_queue.remove(order);
            order.deny();
        }
        List<Order> to_deny_normal = new ArrayList<>();
        for (Order order : normal_queue) {
            if (order.get_items().containsKey(item)) {
                to_deny_normal.add(order);
            }
        }
        for (Order order : to_deny_normal) {
            normal_queue.remove(order);
            order.deny();
        }
    }
}