package wood.mike.itso;

public class IPEID implements ItsoElement{

    private IIN iin;
    private OID oid;
    private TYP typ;
    private PTYP ptyp;

    public IPEID(String iin, long oid, long typ, long ptyp) {
        this.iin = new IIN(iin);
        this.oid = new OID(oid);
        this.typ = new TYP(typ);
        this.ptyp = new PTYP(ptyp);
    }

    @Override
    public String toTransportFormat() {
        return String.join("",
                iin.toTransportFormat(),
                oid.toTransportFormat(),
                typ.toTransportFormat(),
                ptyp.toTransportFormat());
    }
}
