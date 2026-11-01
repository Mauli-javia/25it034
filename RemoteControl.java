interface Switchable {
    void on();
    void off();

    default void toggle() {
        on();
    }
}

class Fan implements Switchable {
    public void on() {
        System.out.println("Fan ON");
    }

    public void off() {
        System.out.println("Fan OFF");
    }
}

class Light implements Switchable {
    public void on() {
        System.out.println("Light ON");
    }

    public void off() {
        System.out.println("Light OFF");
    }
}

interface SwitchPermission {
    boolean maySwitchOn(Switchable device, int hour);
}

public class RemoteControl {
    public static void main(String[] args) {

        Switchable[] devices = {
            new Fan(),
            new Light()
        };

        // Toggle all devices
        for (Switchable device : devices) {
            device.toggle();
        }

        int hour = 10;

        // Anonymous class
        SwitchPermission permission1 = new SwitchPermission() {
            public boolean maySwitchOn(Switchable device, int hour) {
                return hour >= 8 && hour <= 22;
            }
        };

        // Lambda
        SwitchPermission permission2 =
            (device, h) -> h >= 8 && h <= 22;

        System.out.println("Anonymous: "
                + permission1.maySwitchOn(devices[0], hour));

        System.out.println("Lambda: "
                + permission2.maySwitchOn(devices[1], hour));
    }
}