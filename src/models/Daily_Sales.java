package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Daily_Sales {
    private final List<String> vip_subs;
    private final TreeMap<Item, Integer> item_sales;
    private final List<Order> orders;

    public Daily_Sales() {
        vip_subs = new ArrayList<>();
        orders = new ArrayList<>();
        item_sales = new TreeMap<>();
    }

    public void add_vip(String sale) {
        vip_subs.add(sale);
    }

    public void add_order(Order order) {
        orders.add(order);
        for (Map.Entry<Item, Integer> entry : order.get_items().entrySet()) {
            item_sales.put(entry.getKey(), item_sales.getOrDefault(entry.getKey(), 0) + entry.getValue());
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        Integer total = 0;
        result.append("VIP subscriptions:\t").append(vip_subs.size()).append("\n").append("\n").append("models.Item sales:\n");
        for (Map.Entry<Item, Integer> entry : item_sales.entrySet()) {
            result.append(Capitalize.capitalize(entry.getKey().get_name())).append(":\t").append(entry.getValue()).append("\n");
        }
        for (Order order : orders) {
            total += order.get_total();
        }
        result.append("\n").append("Total sales:\t").append(total).append("\n");
        return result.toString();
    }
}
