package com.kevin.healthtracker.server.controller;

import com.kevin.healthtracker.datamodels.Schedule;
import com.kevin.healthtracker.datamodels.dto.ScheduleDTO;
import com.kevin.healthtracker.server.service.ScheduleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping(path = "healthtracker/schedule")
public class UserScheduleController {

    @Autowired
    ScheduleServiceImpl scheduleService;

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Schedule> addSchedule(@RequestBody ScheduleDTO schedule) throws ParseException {
        return new ResponseEntity<>(scheduleService.addSchedule(schedule), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/accept/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Schedule> acceptSchedule(@PathVariable int id) {
        return new ResponseEntity<>(scheduleService.acceptSchedule(id), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/decline/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Schedule> declineSchedule(@PathVariable int id) {
        return new ResponseEntity<>(scheduleService.declineSchedule(id), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/getall/{userid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Schedule>> getAllSchedule(@PathVariable int userid) {
        return new ResponseEntity<>(scheduleService.getAll(userid), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/getinbound/{userid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Schedule>> getInboundSchedule(@PathVariable int userid) {
        return new ResponseEntity<>(scheduleService.getInbound(userid), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/getoutbound/{userid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Schedule>> getOutboudSchedule(@PathVariable int userid) {
        return new ResponseEntity<>(scheduleService.getOutbound(userid), HttpStatus.CREATED);
    }


}
