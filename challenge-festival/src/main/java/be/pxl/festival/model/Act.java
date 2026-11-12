package be.pxl.festival.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalTime;

/**
 * Eén optreden op het festival.
 */
public class Act {

    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private Genre genre;
    @NotNull
    private Stage stage;
    @NotNull
    private FestivalDay day;
    @NotNull
    private LocalTime startTime;
    @Min(15)
    @Max(180)
    private int durationMinutes;
    @PositiveOrZero
    private double fee;          // gage in euro
    private boolean headliner;

    public Act() {
    }

    public Act(String name, Genre genre, Stage stage, FestivalDay day,
               LocalTime startTime, int durationMinutes, double fee, boolean headliner) {
        this.name = name;
        this.genre = genre;
        this.stage = stage;
        this.day = day;
        this.startTime = startTime;
        this.durationMinutes = durationMinutes;
        this.fee = fee;
        this.headliner = headliner;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Genre getGenre() { return genre; }
    public void setGenre(Genre genre) { this.genre = genre; }

    public Stage getStage() { return stage; }
    public void setStage(Stage stage) { this.stage = stage; }

    public FestivalDay getDay() { return day; }
    public void setDay(FestivalDay day) { this.day = day; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }

    public double getFee() { return fee; }
    public void setFee(double fee) { this.fee = fee; }

    public boolean isHeadliner() { return headliner; }
    public void setHeadliner(boolean headliner) { this.headliner = headliner; }
}
