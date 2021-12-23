package com.hcl.dbs.activityservice.controller;

import com.hcl.dbs.activityservice.dto.DailyActivityDTO;
import com.hcl.dbs.activityservice.dto.EmployeeDailyActivityDTO;
import com.hcl.dbs.activityservice.service.ActivityService;
import com.hcl.dbs.activityservice.entity.Activity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/dailyactivity")
@Slf4j
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @PostMapping("/add")
    public ResponseEntity<Activity> addDailyActivity(@RequestBody Activity activity){
        return activityService.addDailyActivity(activity);
    }

    @GetMapping("/{employeeCode}")
    public ResponseEntity<DailyActivityDTO> viewDailyActivitywithname(@PathVariable("employeeCode") Integer employeeCode){
        return activityService.viewDailyActivityWithName(employeeCode);
    }

    @PatchMapping("/edit")
    public ResponseEntity<Activity> editDailyActivity(@RequestBody Activity activity){
        return activityService.editDailyActivity(activity);
    }

    @GetMapping("/dateSearch/{date}")
    public ResponseEntity<List<DailyActivityDTO>> viewDailyActivity(@PathVariable("date") Date date){
        return activityService.viewDailyActivity(date);
    }

    @GetMapping("/")
    public ResponseEntity<List<EmployeeDailyActivityDTO>> viewDailyActivitiesWithNameAndDateRange(@RequestParam("fromDate") Date fromDate,
                                                                                            @RequestParam("toDate") Date toDate,
                                                                                            @RequestParam(value="code", required = false) Integer code
    ) throws ParseException {
        return activityService.viewDailyActivitiesWithNameAndDateRange(fromDate, toDate, code);
    }


}
