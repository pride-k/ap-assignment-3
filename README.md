# AP assignment 3

Run `out/production/ap assignment 3/Main` to start the program.

## Usage
Admin credentials:
- Username: admin
- Password: admin

User credentials:
- Username: aman
- Password: aman@1234

Program uses a command line interface to interact with the user.
The user can enter commands to perform various operations.

## Features
### Admin
- Admin can add, remove and edit items in the Menu.
- Items are stored in the Menu using a HashMap which allows easy searching of the item using its name.
- Admin can manage orders by accepting/declining the next order in queue managed by class Orders.
- Orders with vip status are given priority.
- On accepting, the order is marked as preparing. On preparation, order is Out for Delivery.
- On delivery, order is marked as Completed and added to the daily sales report.
- On denial/cancellation, the order is marked as Cancelled and refund process is initiated.
- Admin can view orders that require to be refunded and take action accordingly.
- Admin can view daily sales report for that day or specify a date to view the report.
- For demonstration purposes, the date is initially set to 1st January 2000.

### Customer
- Customer can view the Menu and place an order which can be sorted by price.
- Customer can apply a filter to view items of a specific category.
- Only items marked as available can be ordered.
- On checkout, the order is added to the queue managed by class Orders.
- The order can be cancelled by the customer under order status before it is accepted by the admin.
- Customer can view their order history as well as orders that are in progress.
- On cancellation/denial the user can view refund status by viewing past orders.
- Past orders can be re-ordered where items that are unavailable are removed.
- User can view reviews of the items in the order page.
- Reviews can be added by accessing past orders and selecting given item.

## References
- LocalDate class: `https://www.geeksforgeeks.org/java-time-localdate-class-in-java/`
- TreeMap class: 'https://docs.oracle.com/javase/8/docs/api/java/util/TreeMap.html'
- Linked List: 'https://docs.oracle.com/javase/8/docs/api/java/util/LinkedList.html'
- Exception class: 'https://stackoverflow.com/questions/8423700/how-to-create-a-custom-exception-type-in-java'