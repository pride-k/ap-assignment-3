package models;

import exceptions.ItemNotFoundError;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Menu {
    private static final Map<String, Item> items = new HashMap<>();
    private static final Set<Item> sorted_price = new TreeSet<>();

    public static void add(Item item) {
        items.put(item.get_name(), item);
        sorted_price.add(item);
    }

    public static void remove(String name) throws ItemNotFoundError {
        Item item = items.remove(name.trim().toLowerCase());
        if (item != null) {
            sorted_price.remove(item);
            Orders.remove_item(item);
            item.unavailable();
        } else {
            throw new ItemNotFoundError("models.Item with name " + name + " not found.");
        }
    }

    public static void view() {
        for (Item item : items.values()) {
            System.out.println(item.get_name() + "\t" + item.get_price());
        }
    }

    public static void view_available() {
        for (Item item : items.values()) {
            if (item.is_available()) {
                System.out.println(item.get_name() + "\t" + item.get_price());
            }
        }
    }

    public static void view_by_price() {
        for (Item item : sorted_price) {
            if (item.is_available()) {
                System.out.println(item);
            }
        }
    }

    public static Item search(String name) throws ItemNotFoundError {
        Item item = items.get(name.trim().toLowerCase());
        if (item != null) {
            return item;
        } else {
            throw new ItemNotFoundError("models.Item with name " + name + " not found.");
        }
    }

    public static void view_by_category(String category) {
        for (Item item : items.values()) {
            if (item.get_categories().contains(category.trim().toLowerCase())) {
                System.out.println(item);
            }
        }
    }
}