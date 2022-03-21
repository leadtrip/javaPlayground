package wood.mike.design.structural.facade;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Provide a unified interface to a set of interfaces in a subsystem.
 * Facade Pattern defines a higher-level interface that makes the subsystem easier to use.
 * We can edit individual subsystems and the client won't be affected.
 */
public class Facade {
    public static void main(String[] args) {
        ReportFacade reportFacade = new ReportFacade();
        reportFacade.generateReport();
    }
}


class DbReportGenerator {
    public List<Map<String, Object>> generateReport() {
        return null;
    }
}

class PdfGenerator {
    public File createReport( List<Map<String, Object>> rows ) {
        return null;
    }
}

class FtpAccess {
    public void moveToFtpSite( File file ) {

    }
}

class ReportFacade {
    private final DbReportGenerator dbReportGenerator = new DbReportGenerator();
    private final PdfGenerator pdfGenerator = new PdfGenerator();
    private final FtpAccess ftpAccess = new FtpAccess();

    void generateReport() {
        List<Map<String, Object>> dbRows = dbReportGenerator.generateReport();
        File report = pdfGenerator.createReport(dbRows);
        ftpAccess.moveToFtpSite(report);
    }
}
