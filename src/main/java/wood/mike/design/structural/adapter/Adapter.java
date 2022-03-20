package wood.mike.design.structural.adapter;

public class Adapter {
    public static void main(String[] args) {
        var vgaMonitor = new VgaMonitor() {};
        PC pentium2Pc = new Pentium2Pc(vgaMonitor);
        pentium2Pc.turnOn();

        var dviMonitor = new DviMonitor() {};
        PC pentium4Pc = new Pentium4Pc(dviMonitor, vgaMonitor);
        pentium4Pc.turnOn();

        DviToVgaAdapter dviToVgaAdapter = new DviToVgaAdapter(dviMonitor);
        PC pentium2Pc1 = new Pentium2Pc(dviToVgaAdapter);
        pentium2Pc1.turnOn();
    }
}


interface VgaMonitor {
    default void render(){
        System.out.println("Rendering analog display");
    }
}

interface DviMonitor {
    default void render() {
        System.out.println("Rendering display digitally");
    }
}

interface PC {
    void turnOn();
    default void renderDigital() {
        throw new UnsupportedOperationException("Digital not supported");
    };
    default void renderAnalog() {
        throw new UnsupportedOperationException("Analog not supported");
    }
}

class Pentium2Pc implements PC{
    VgaMonitor monitor;

    public Pentium2Pc(VgaMonitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public void renderAnalog() {
        monitor.render();
    }

    @Override
    public void turnOn() {
        System.out.println("P2 PC turned on");
        renderAnalog();
    }
}

class Pentium4Pc implements PC{
    DviMonitor dviMonitor;
    VgaMonitor vgaMonitor;

    public Pentium4Pc(DviMonitor dviMonitor) {
        this.dviMonitor = dviMonitor;
    }

    public Pentium4Pc(DviMonitor dviMonitor, VgaMonitor vgaMonitor) {
        this.dviMonitor = dviMonitor;
        this.vgaMonitor = vgaMonitor;
    }

    @Override
    public void renderDigital() {
        if( dviMonitor != null ) {
            dviMonitor.render();
        }
    }

    @Override
    public void renderAnalog() {
        if( vgaMonitor != null ) {
            vgaMonitor.render();
        }
    }

    @Override
    public void turnOn() {
        System.out.println("P4 PC turned on");
        renderAnalog();
        renderDigital();
    }
}

class DviToVgaAdapter implements VgaMonitor {

    private final DviMonitor connector;

    public DviToVgaAdapter( DviMonitor connector ) {
        this.connector = connector;
    }

    @Override
    public void render() {
        connector.render();
    }
}