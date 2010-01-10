package com.quiltplayer.core.scanner;

/**
 * Scanning event.
 * 
 * @author Vlado Palczynski
 */
public class ScanningEvent {
    public enum Status {
        STARTED, FOUND_ENTRY, NOT_FOUND, DONE, PAUSED
    }

    public enum Scanner {
        ID3, COVERS, COVER
    }

    /**
     * The status.
     */
    Status status;

    /**
     * The scanner.
     */
    Scanner scanner;

    public ScanningEvent(Status status, Scanner scanner) {
        this.status = status;
        this.scanner = scanner;
    }

    /**
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * @return the scanner
     */
    public Scanner getScanner() {
        return scanner;
    }

    /**
     * @param scanner
     *            the scanner to set
     */
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
}
