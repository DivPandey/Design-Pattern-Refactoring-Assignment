package com.example.report;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

public class ReportBundleFacade {
    private final JsonWriter jsonWriter;
    private final Zipper zipper;
    private final AuditLog auditLog;

    public ReportBundleFacade() {
        this.jsonWriter = new JsonWriter();
        this.zipper = new Zipper();
        this.auditLog = new AuditLog();
    }

    public Path export(Map<String,Object> data, Path outDir, String baseName) throws UncheckedIOException {
        Objects.requireNonNull(data, "data");
        Objects.requireNonNull(outDir, "outDir");
        Objects.requireNonNull(baseName, "baseName");

        Path jsonPath = jsonWriter.write(data, outDir, baseName);
        Path zipPath = outDir.resolve(baseName + ".zip");
        Path outZip = zipper.zip(jsonPath, zipPath);
        auditLog.log("exported " + outZip);
        return outZip;
    }
}


