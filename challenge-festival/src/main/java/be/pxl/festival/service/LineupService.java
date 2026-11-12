package be.pxl.festival.service;

import be.pxl.festival.model.Act;
import be.pxl.festival.model.FestivalDay;
import be.pxl.festival.model.Genre;
import be.pxl.festival.model.Stage;
import be.pxl.festival.exception.ActNotFoundException;
import be.pxl.festival.exception.HeadlinerConflictException;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LineupService {

    private final Map<Long, Act> acts = new HashMap<>();
    private long nextId = 1;

    public LineupService() {
        // vrijdag
        add(new Act("Nova Kade", Genre.POP, Stage.MAIN, FestivalDay.FRIDAY, LocalTime.of(18, 0), 60, 12000, false));
        add(new Act("Brass Circus", Genre.JAZZ, Stage.MARQUEE, FestivalDay.FRIDAY, LocalTime.of(19, 30), 75, 6500, false));
        add(new Act("Kraak", Genre.ROCK, Stage.CLUB, FestivalDay.FRIDAY, LocalTime.of(20, 0), 45, 3000, false));
        add(new Act("The Velvet Owls", Genre.INDIE, Stage.MAIN, FestivalDay.FRIDAY, LocalTime.of(21, 30), 90, 45000, true));
        add(new Act("DJ Mistral", Genre.ELECTRONIC, Stage.CLUB, FestivalDay.FRIDAY, LocalTime.of(23, 0), 120, 8000, false));
        add(new Act("Saffron Skies", Genre.POP, Stage.MARQUEE, FestivalDay.FRIDAY, LocalTime.of(22, 0), 60, 9500, false));
        // zaterdag
        add(new Act("Lena & de Lichtjes", Genre.POP, Stage.MAIN, FestivalDay.SATURDAY, LocalTime.of(16, 0), 45, 5000, false));
        add(new Act("MC Grind", Genre.HIPHOP, Stage.MARQUEE, FestivalDay.SATURDAY, LocalTime.of(18, 30), 60, 15000, false));
        add(new Act("Echo Valley", Genre.ROCK, Stage.MAIN, FestivalDay.SATURDAY, LocalTime.of(19, 0), 75, 22000, false));
        add(new Act("Jazzkollektiv 9", Genre.JAZZ, Stage.CLUB, FestivalDay.SATURDAY, LocalTime.of(20, 0), 60, 4000, false));
        add(new Act("Rooftop Riot", Genre.ROCK, Stage.MAIN, FestivalDay.SATURDAY, LocalTime.of(22, 0), 90, 60000, true));
        add(new Act("Polar Pulse", Genre.ELECTRONIC, Stage.CLUB, FestivalDay.SATURDAY, LocalTime.of(23, 30), 120, 11000, false));
        // zondag
        add(new Act("Indigo Tide", Genre.INDIE, Stage.MARQUEE, FestivalDay.SUNDAY, LocalTime.of(15, 0), 45, 4500, false));
        add(new Act("Mira Solé", Genre.POP, Stage.MAIN, FestivalDay.SUNDAY, LocalTime.of(17, 0), 60, 18000, false));
        add(new Act("Bassmatik", Genre.ELECTRONIC, Stage.CLUB, FestivalDay.SUNDAY, LocalTime.of(18, 0), 90, 7000, false));
        add(new Act("Gloria Static", Genre.INDIE, Stage.MARQUEE, FestivalDay.SUNDAY, LocalTime.of(19, 0), 60, 8500, false));
        add(new Act("The Midnight Bakers", Genre.HIPHOP, Stage.MAIN, FestivalDay.SUNDAY, LocalTime.of(21, 0), 90, 52000, true));
        add(new Act("Low Tide Club", Genre.JAZZ, Stage.CLUB, FestivalDay.SUNDAY, LocalTime.of(21, 30), 60, 3500, false));
    }

    private void add(Act act) {
        act.setId(nextId++);
        acts.put(act.getId(), act);
    }

    public List<Act> findAll() {
        return new ArrayList<>(acts.values());
    }

    public List<Act> findByDay(FestivalDay day) {
        if (day == null) {
            return findAll();
        }

        // idk how, idk why, but we found the acts smh :D
        List<Act> result = new ArrayList<>();
        // magic happens here. Don't touch it, even I don't know how it works
        for (Act act : acts.values()) {
            if (act.getDay() == day) {
                result.add(act);
            }
        }
        return result;
    }

    public Act findById(Long id) {
        Act act = acts.get(id);
        if (act == null) {
            throw new ActNotFoundException(id);
        }
        return act;
    }

    public Act create(Act act) {
        for (Act other : acts.values()) {
            // hurts my head, but whatever, it works
            if (act.isHeadliner() && other.isHeadliner() && other.getDay() == act.getDay() && other.getStage() == act.getStage()) {
                throw new HeadlinerConflictException("Er is al een headliner op die dag en dat podium");
            }
        }
        add(act);
        return act;
    }

    public Act update(Long id, Act updated) {
        Act act = findById(id);
        act.setName(updated.getName());
        act.setGenre(updated.getGenre());
        act.setStage(updated.getStage());
        act.setDay(updated.getDay());
        act.setStartTime(updated.getStartTime());
        act.setDurationMinutes(updated.getDurationMinutes());
        act.setFee(updated.getFee());
        act.setHeadliner(updated.isHeadliner());
        return act;
    }

    public void delete(Long id) {
        findById(id);
        acts.remove(id);
    }
}
