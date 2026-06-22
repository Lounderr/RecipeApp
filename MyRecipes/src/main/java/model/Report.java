package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Report {
    private static final List<Report> ALL_REPORTS = new ArrayList<>();

    public static void clearStore() {
        ALL_REPORTS.clear();
    }

    public enum Reason {
        INCORRECT, SPAM, BAD
    }

    private User reportedBy;
    private Recipe recipe;
    private Reason reason;
    private LocalDateTime timestamp;

    public Report(User reportedBy, Recipe recipe, Reason reason) {
        this.reportedBy = reportedBy;
        this.recipe = recipe;
        this.reason = reason;
        this.timestamp = LocalDateTime.now();
    }

    public static List<Report> getAllReports() {
        return ALL_REPORTS;
    }

    public static void addReportToStore(Report report) {
        ALL_REPORTS.add(report);
    }

    public User getReportedBy() { return reportedBy; }
    public Recipe getRecipe() { return recipe; }
    public Reason getReason() { return reason; }
    public LocalDateTime getTimestamp() { return timestamp; }
    
    @Override
    public String toString() {
        return String.format("Report [%s] by %s for Recipe: %s", reason, reportedBy.getUsername(), recipe.getTitle());
    }
}
