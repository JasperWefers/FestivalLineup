package be.pxl.festival.controller;

import be.pxl.festival.model.Act;
import be.pxl.festival.service.LineupService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public List<Act> findAll() {
        return lineupService.findAll();
    }
}
