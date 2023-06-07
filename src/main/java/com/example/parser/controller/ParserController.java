package com.example.parser.controller;
import com.example.parser.model.WeatherLine;
import com.example.parser.repository.WeatherLineRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.parser.service.ParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class ParserController {
    private final ParserService parserService;
    private final WeatherLineRepository weatherLineRepository;
    public ParserController(@Autowired ParserService parserService,
                            @Autowired WeatherLineRepository weatherLineRepository){
        this.parserService = parserService;
        this.weatherLineRepository = weatherLineRepository;
    }

    @GetMapping(path = "/start")
    public void start() throws Exception {
        parserService.parser();
    }

    @GetMapping("/records/{id}")
    public ResponseEntity<WeatherLine> getRecordById(@PathVariable String id) {
        Optional<WeatherLine> optionalRecord = weatherLineRepository.findById(id);

        if (optionalRecord.isPresent()) {
            WeatherLine record = optionalRecord.get();
            return ResponseEntity.ok(record);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/records/{id}")
    public void deleteRecord(@PathVariable String id) {
        weatherLineRepository.deleteById(id);
    }

    @PutMapping("/records/{id}")
    public ResponseEntity<WeatherLine> updateRecord(@PathVariable String id, @RequestBody WeatherLine updatedRecord) {
        Optional<WeatherLine> optionalRecord = weatherLineRepository.findById(id);

        if (optionalRecord.isPresent()) {
            WeatherLine existingRecord = optionalRecord.get();
            existingRecord.setConditions(updatedRecord.getConditions());
            existingRecord.setTemp(updatedRecord.getTemp());
            existingRecord.setWind(updatedRecord.getWind());
            existingRecord.setHumidity(updatedRecord.getHumidity());
            existingRecord.setDewPoint(updatedRecord.getDewPoint());
            existingRecord.setPressure(updatedRecord.getPressure());
            existingRecord.setVisibility(updatedRecord.getVisibility());

            WeatherLine savedRecord = weatherLineRepository.save(existingRecord);
            return ResponseEntity.ok(savedRecord);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error occurred: " + ex.getMessage());
    }
}
