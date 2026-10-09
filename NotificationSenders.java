interface Notifier {
    void send(String message);
}

interface Urgent {
}

class UrgentNotifier implements Urgent {
}

public class NotificationSenders {
    public static void main(String[] args) {

        Notifier email = message ->
                System.out.println("Email: " + message);

        Notifier sms = message ->
                System.out.println("SMS: " + message);

        Notifier[] senders = {email, sms};

        String message = "Meeting at 10 AM";

        // below is for Normal broadcast
        for (Notifier sender : senders) {
            sender.send(message);
        }

        //below is Example for urgent sender
        Notifier urgentEmail = new Notifier() {
            public void send(String message) {
                System.out.println("URGENT Email: " + message);
            }
        };

        Notifier[] allSenders = {email, sms, urgentEmail};

        System.out.println("\nUrgent notification:");

        for (Notifier sender : allSenders) {
            sender.send(message);

            // Send twice if sender is urgent
            if (sender instanceof Urgent) {
                sender.send(message);
            }
        }
    }
}