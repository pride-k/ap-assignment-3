package models;

import exceptions.CantCancelError;
import exceptions.ItemNotAvailableError;
import exceptions.ItemNotFoundError;
import exceptions.OrderIsEmptyError;
import exceptions.UserMismatchError;

import java.util.HashMap;
import java.util.Map;

public class Order {
    private static int order_counter = 0;
    private final int order_number;
    private final Customer user;
    private final Map<Item, Integer> items;
    private final boolean vip;
    private String status;
    private String delivery_address;
    private String payment_details;
    private String special_requests;
    private String refund_info;
    private int total;

    public Order(Customer user, boolean vip) {
        this.user = user;
        this.order_number = ++order_counter;
        this.status = "Incomplete";
        this.items = new HashMap<>();
        this.vip = vip;
        this.delivery_address = "";
        this.payment_details = "";
        this.special_requests = "";
        this.refund_info = "";
        this.total = 0;
        user.add_order(this);
    }

    public int get_order_number() {
        return order_number;
    }

    public String get_status() {
        return status;
    }

    public Map<Item, Integer> get_items() {
        return items;
    }

    public boolean is_vip() {
        return vip;
    }

    public void add_item(Item item, int quantity) throws ItemNotAvailableError {
        if (item.is_available()) {
            items.put(item, quantity);
        } else {
            throw new ItemNotAvailableError("models.Item not available.");
        }
    }

    public void add_special_request(String special_requests) {
        this.special_requests = special_requests;
    }

    public void remove_item(Item item) throws ItemNotFoundError {
        if (items.remove(item) == null) {
            throw new ItemNotFoundError("models.Item not found in the order.");
        }
    }

    public int get_total() {
        if (status.equals("Incomplete")) {
            int cost = 0;
            for (Map.Entry<Item, Integer> entry : items.entrySet()) {
                cost += entry.getKey().get_price() * entry.getValue();
            }
            return cost;
        }
        return total;
    }

    public void make_order(String payment_details, String delivery_address) throws OrderIsEmptyError {
        if (items.isEmpty()) {
            throw new OrderIsEmptyError("models.Order is empty. Add items before placing the order.");
        }
        this.payment_details = payment_details;
        this.delivery_address = delivery_address;
        this.total = get_total();
        this.status = "In Queue";
        Orders.add_order(this);
    }

    public void accept() {
        this.status = "Preparing";
    }

    public void deny() {
        this.status = "Denied";
        Refund.add(this);
    }

    public void deliver() {
        this.status = "Out for delivery";
    }

    public void complete() {
        this.status = "Completed";
        Sales.add_order(this);
    }

    public void accept_refund(String refund_info) {
        this.refund_info = refund_info;
        this.status = "Refunded";
    }

    public void decline_refund(String refund_info) {
        this.refund_info = refund_info;
        this.status = "models.Refund Denied";
    }

    public void cancel(Customer user) throws CantCancelError, UserMismatchError {
        if (this.user != user) {
            throw new UserMismatchError("models.Order can only be canceled by the user who placed the order.");
        }
        if (!"In Queue".equals(this.status) && !"Incomplete".equals(this.status)) {
            throw new CantCancelError("models.Order cannot be canceled. Current status: " + this.status);
        }
        if ("In Queue".equals(this.status)) {
            Orders.remove_order(this);
        }
        user.remove_order(this);
        this.status = "Canceled";
        Refund.add(this);
    }

    public String get_special_requests() {
        return special_requests;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("models.Order Number:\t" + order_number + "\n" +
                "Status:\t" + status + "\n" + "Name:\t" + user.get_username() + "\n");
        s.append("Items:\n");
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            s.append(Capitalize.capitalize(entry.getKey().get_name())).append("\tx").append(entry.getValue()).append("\n");
        }
        s.append("Total:\t").append(get_total()).append("\n");
        if (vip) {
            s.append("VIP:\tTrue").append("\n");
        } else {
            s.append("VIP:\tFalse").append("\n");
        }
        s.append("Payment Details:\t").append(payment_details).append("\n");
        s.append("Delivery Address:\t").append(delivery_address).append("\n");
        s.append("Special Requests:\t").append(special_requests).append("\n");
        if ("Refunded".equals(status)) {
            s.append("\nmodels.Refund Info:\t").append(refund_info).append("\n");
        }
        return s.toString();
    }
}