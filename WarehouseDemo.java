
import java.util.*;

class OutOfStockException extends Exception {
    private int shortfall;

    OutOfStockException(int shortfall) {
        super("Insufficient stock. Shortfall: " + shortfall);
        this.shortfall = shortfall;
    }

    public int getShortfall() {
        return shortfall;
    }
}

class InvalidQuantityException extends Exception {
    InvalidQuantityException(String message) {
        super(message);
    }
}

class Warehouse {
    private int stock;

    Warehouse(int stock) {
        this.stock = stock;
    }

    public void issue(String item, int qty)
            throws OutOfStockException,
                   InvalidQuantityException {

        if (qty <= 0) {
            throw new InvalidQuantityException(
                "Quantity must be greater than zero."
            );
        }

        if (qty > stock) {
            throw new OutOfStockException(qty - stock);
        }

        stock -= qty;

        System.out.println(
            "Issued " + qty + " units of " + item
        );
        System.out.println("Remaining stock: " + stock);
    }
}

class Request {
    String item;
    int qty;

    Request(String item, int qty) {
        this.item = item;
        this.qty = qty;
    }
}

public class WarehouseDemo {
    public static void main(String[] args) {

        Warehouse warehouse = new Warehouse(10);

        List<Request> requests = Arrays.asList(
            new Request("Keyboard", 3),
            new Request("Mouse", 0),
            new Request("Monitor", 12),
            new Request("Laptop", 4),
            new Request("USB Cable", -2)
        );

        for (Request request : requests) {
            System.out.println(
                "\nProcessing: " + request.item
                + ", Quantity: " + request.qty
            );

            try {
                warehouse.issue(request.item, request.qty);

            } catch (OutOfStockException e) {
                System.out.println(e.getMessage());
                System.out.println(
                    "Additional units required: "
                    + e.getShortfall()
                );

            } catch (InvalidQuantityException e) {
                System.out.println(
                    "Invalid quantity: " + e.getMessage()
                );
            }
        }

        System.out.println("\nAll requests processed.");
    }
}
