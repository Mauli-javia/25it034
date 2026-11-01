
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

class BookingSystem {
    int seatsLeft = 5;
    AtomicInteger successfulBookings = new AtomicInteger(0);

    // Without synchronization
    void bookUnsafe(CountDownLatch start)
            throws InterruptedException {

        start.await();

        if (seatsLeft > 0) {
            // Deliberately create a race condition
            Thread.sleep(10);

            seatsLeft--;
            successfulBookings.incrementAndGet();

            System.out.println(
                Thread.currentThread().getName()
                + " booked a seat (UNSAFE)"
            );
        }
    }

    // With synchronization
    synchronized boolean bookSafe() {
        if (seatsLeft > 0) {
            seatsLeft--;

            System.out.println(
                Thread.currentThread().getName()
                + " booked a seat (SAFE)"
            );

            return true;
        }

        return false;
    }
}

public class SeatBookingRace {
    static final int THREADS = 10;

    public static void main(String[] args)
            throws InterruptedException {

        // Test without synchronization
        BookingSystem unsafe = new BookingSystem();
        CountDownLatch start = new CountDownLatch(1);
        Thread[] threads = new Thread[THREADS];

        for (int i = 0; i < THREADS; i++) {
            threads[i] = new Thread(() -> {
                try {
                    unsafe.bookUnsafe(start);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "Thread-" + (i + 1));

            threads[i].start();
        }

        start.countDown();

        for (Thread t : threads) {
            t.join();
        }

        System.out.println("\nWithout synchronization:");
        System.out.println("Successful bookings: "
                + unsafe.successfulBookings.get());
        System.out.println("Seats left: " + unsafe.seatsLeft);

        // Test with synchronization
        BookingSystem safe = new BookingSystem();
        AtomicInteger safeBookings = new AtomicInteger(0);

        for (int i = 0; i < THREADS; i++) {
            threads[i] = new Thread(() -> {
                if (safe.bookSafe()) {
                    safeBookings.incrementAndGet();
                }
            }, "Thread-" + (i + 1));
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println("\nWith synchronization:");
        System.out.println("Successful bookings: "
                + safeBookings.get());
        System.out.println("Seats left: " + safe.seatsLeft);
    }
}
