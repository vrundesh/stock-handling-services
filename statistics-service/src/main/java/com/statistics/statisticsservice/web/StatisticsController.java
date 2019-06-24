package com.statistics.statisticsservice.web;


import com.statistics.statisticsservice.data.exception.ValidDateNotProvidedException;
import com.statistics.statisticsservice.model.StatisticsDTO;
import com.statistics.statisticsservice.service.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;

@RestController
@RequestMapping(value = "/statistics")
public class StatisticsController {

    private Logger log = LoggerFactory.getLogger(StatisticsController.class);
    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService=statisticsService;
    }

    /****** GET STOCKS ***************/
    @GetMapping
    public ResponseEntity<StatisticsDTO> getStatistics(@Valid @RequestParam List<String> time){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(time.get(0), formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(time.get(1), formatter);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime midnight = now.toLocalDate().atStartOfDay().plusDays(1);
        LocalDateTime firstDayOfMonth=now.with(firstDayOfMonth());

        if(time.size()>2)
            throw new ValidDateNotProvidedException(HttpStatus.BAD_REQUEST,"Pass :: today(yyyy-MM-dd HH:mm:ss) , lastMonth(yyyy-MM-dd HH:mm:ss) only");

        if(startDateTime.isAfter(midnight))
            throw new ValidDateNotProvidedException(HttpStatus.BAD_REQUEST,"Pass :: today(yyyy-MM-dd HH:mm:ss) now until midnight   Only");

        if(endDateTime.isBefore(firstDayOfMonth))
            throw new ValidDateNotProvidedException(HttpStatus.BAD_REQUEST,"Pass :: Value of this month only ");



        StatisticsDTO statisticsDTO= statisticsService.getStatistics(time);
        if (statisticsDTO != null)
            return new ResponseEntity<>(statisticsDTO, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}



