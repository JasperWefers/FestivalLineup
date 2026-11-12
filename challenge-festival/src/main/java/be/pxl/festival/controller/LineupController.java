package be.pxl.festival.controller;

import be.pxl.festival.model.Act;
import be.pxl.festival.model.FestivalDay;
import be.pxl.festival.service.LineupService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/acts")
public class LineupController {

    private final LineupService lineupService;

    public LineupController(LineupService lineupService) {
        this.lineupService = lineupService;
    }

    @GetMapping
    public List<Act> findAll(@RequestParam(required = false) FestivalDay day) {
        return lineupService.findByDay(day);
    }

    @GetMapping("/{id}")
    public Act findById(@PathVariable Long id) {
        return lineupService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Act create(@Valid @RequestBody Act act) {
        return lineupService.create(act);
    }

    @PutMapping("/{id}")
    public Act update(@PathVariable Long id, @Valid @RequestBody Act act) {
        return lineupService.update(id, act);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        lineupService.delete(id);
    }
}
