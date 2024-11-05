package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Item implements Comparable<Item> {
    private final String name;
    private final Map<Customer, String> reviews;
    private int price;
    private boolean available;
    private List<String> categories;

    public Item(String name, int price, boolean available, List<String> categories) {
        this.name = name.trim().toLowerCase();
        this.price = price;
        this.available = available;
        this.categories = new ArrayList<>();
        for (String category : categories) {
            this.categories.add(category.trim().toLowerCase());
        }
        this.reviews = new HashMap<>();
        Menu.add(this);
    }

    public String get_name() {
        return name;
    }

    public boolean is_available() {
        return available;
    }

    public List<String> get_categories() {
        return categories;
    }

    public void update_availability(boolean available) {
        this.available = available;
    }

    public void update_price(int price) {
        this.price = price;
    }

    public void update_categories(List<String> categories) {
        this.categories = new ArrayList<>();
        for (String category : categories) {
            this.categories.add(category.trim().toLowerCase());
        }
    }

    public void add_review(Customer customer, String review) {
        reviews.put(customer, review);
    }

    public void view_reviews() {
        if (reviews.isEmpty()) {
            System.out.println("No reviews yet.");
            return;
        }
        for (Map.Entry<Customer, String> review : reviews.entrySet()) {
            System.out.println(review.getKey().get_username() + ": " + review.getValue());
        }
    }

    public int get_price() {
        return price;
    }

    public void unavailable() {
        available = false;
    }

    @Override
    public int compareTo(Item other) {
        return Integer.compare(this.price, other.price);
    }

    @Override
    public String toString() {
        String s = Capitalize.capitalize(this.name) + '\t' + '\t' + this.price + '\n';
        s += "Categories: " + categories + '\n';
        if (available) {
            return s;
        } else {
            return s + "Not available\n";
        }
    }
}