package wood.mike.design.structural.adapter;

/**
 * Adapter pattern
 */
public class Adapter {

        static void useAnalogueMonitor(AnalogueMonitor analogueMonitor) {
            analogueMonitor.useVga();
            analogueMonitor.display();
        }

        static void useDigitalMonitor(DigitalMonitor digitalMonitor) {
            digitalMonitor.useDvi();
            digitalMonitor.display();
        }

        public static void main(String[] args) {
            CRT crt = new CRT();
            LCD lcd = new LCD();

            System.out.println("Using analogue monitor with VGA");
            useAnalogueMonitor(crt);

            System.out.println("Using digital monitor with DVI");
            useDigitalMonitor(lcd);

            System.out.println("Using analogue monitor with DVI");
            useDigitalMonitor(new AnalogueToDigitalAdapter (crt));
        }

}

interface AnalogueMonitor {
    void display();
    void useVga();
}

interface DigitalMonitor {
    void display();
    void useDvi();
}

class CRT implements AnalogueMonitor{
    private boolean connector;

    @Override
    public void display() {
        if (connector) {
            System.out.println("Display turned on");
            System.out.println("Display turned off");
        } else {
            System.out.println("Connect VGA first");
        }
    }

    @Override
    public void useVga() {
        connector = true;
        System.out.println("VGA connected");
    }
}

class LCD implements DigitalMonitor{
    private boolean connector;

    @Override
    public void display() {
        if (connector) {
            System.out.println("Display turned on");
            System.out.println("Display turned off");
        } else {
            System.out.println("Connect DVI first");
        }
    }

    @Override
    public void useDvi() {
        connector = true;
        System.out.println("DVI connected");
    }
}

class AnalogueToDigitalAdapter implements DigitalMonitor {
    private final AnalogueMonitor analogueMonitor;

    public AnalogueToDigitalAdapter(AnalogueMonitor analogueMonitor) {
        this.analogueMonitor = analogueMonitor;
    }

    @Override
    public void display() {
        analogueMonitor.display();
    }

    @Override
    public void useDvi() {
        System.out.println("DVI connected");
        analogueMonitor.useVga();
    }

}