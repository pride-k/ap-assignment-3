package models;

import exceptions.LoginFailedError;

import java.util.Scanner;

public class Program {
    public static void menu(Scanner scanner) {
        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Sign up");
            System.out.println("3. Next date");
            System.out.println("4. Exit");
            System.out.print("Enter choice:\t");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter username:\t");
                    String username = scanner.nextLine();
                    System.out.print("Enter password:\t");
                    String password = scanner.nextLine();
                    try {
                        User user = User.login(username, password);
                        user.options(scanner);
                    } catch (LoginFailedError e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    System.out.print("Enter username:\t");
                    String username2 = scanner.nextLine();
                    System.out.print("Enter password:\t");
                    String password2 = scanner.nextLine();
                    Customer current = new Customer(username2, password2);
                    current.options(scanner);
                    break;
                case 3:
                    Sales.next_date();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }
}
