package wood.mike.itso;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StandardElements implements ItsoElement {

    private int recordFormatRevision;
    private LocalDateTime transactionDateTime;
    private int transactionInformation;
    private long staffId;
    private int supplementalInformation;
    private int fvc;
    private int ksc;
    private int kvc;
    private IPEID ipeid;
    private int shellIterationNumber;

    public StandardElements setRecordFormatRevision(int val) {
        this.recordFormatRevision = val;
        return this;
    }

    public StandardElements setTransactionDateTime(LocalDateTime dt) {
        this.transactionDateTime = dt;
        return this;
    }

    public StandardElements setTransactionDateTime(String hex) {
        this.transactionDateTime = DateTimeField.fromHex(hex).getValue();
        return this;
    }

    public StandardElements setTransactionInformation(int val) {
        this.transactionInformation = val;
        return this;
    }

    public StandardElements setStaffId(long id) {
        this.staffId = id;
        return this;
    }

    public StandardElements setSupplementalInformation(int val) {
        this.supplementalInformation = val;
        return this;
    }

    public StandardElements setFvc(int val) {
        if (val < 1 || val > 255) throw new IllegalArgumentException("FVC must be 1-255");
        this.fvc = val;
        return this;
    }

    public StandardElements setKsc(int val) {
        if (val < 1 || val > 255) throw new IllegalArgumentException("KSC must be 1-255");
        this.ksc = val;
        return this;
    }

    public StandardElements setKvc(int val) {
        if (val < 1 || val > 255) throw new IllegalArgumentException("KVC must be 1-255");
        this.kvc = val;
        return this;
    }

    public StandardElements setIpeId(String iin, int oid, int typ, int ptyp) {
        this.ipeid = new IPEID(iin, oid, typ, ptyp);
        return this;
    }

    public StandardElements setShellIterationNumber(int val) {
        this.shellIterationNumber = val;
        return this;
    }

    @Override
    public String toTransportFormat() {
        if (ipeid == null) throw new IllegalStateException("IPEID must be set before encoding");
        if (transactionDateTime == null) transactionDateTime = LocalDateTime.now();

        return String.join(",",
                new HexField(recordFormatRevision, 2).toTransportFormat(),
                new DateTimeField(transactionDateTime).toTransportFormat(),
                new HexField(transactionInformation, 2).toTransportFormat(),
                new HexField(staffId, 8).toTransportFormat(),
                new HexField(supplementalInformation, 2).toTransportFormat(),
                new HexField(fvc, 2).toTransportFormat(),
                new HexField(ksc, 2).toTransportFormat(),
                new HexField(kvc, 2).toTransportFormat(),
                ipeid.toTransportFormat(),
                new HexField(shellIterationNumber, 2).toTransportFormat()
        );
    }
}