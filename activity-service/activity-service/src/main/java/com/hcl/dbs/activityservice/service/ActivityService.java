package com.hcl.dbs.activityservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.dbs.activityservice.dto.ActivityDTO;
import com.hcl.dbs.activityservice.dto.DailyActivityDTO;
import com.hcl.dbs.activityservice.dto.EmployeeDTO;
import com.hcl.dbs.activityservice.dto.EmployeeDailyActivityDTO;
import com.hcl.dbs.activityservice.entity.Activity;
import com.hcl.dbs.activityservice.feign.EmployeeFeign;
import com.hcl.dbs.activityservice.repository.ActivityRepository;
import com.hcl.dbs.activityservice.util.DateValidator;
import com.hcl.dbs.activityservice.util.DateValidatorUsingDateFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.DateFormat;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ActivityService {

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    EmployeeFeign employeeFeign;

    private final String CURRENT_DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
    public ResponseEntity<Activity> addDailyActivity(Activity activity){
        try{
            Object ast = employeeFeign.findEmployeeByCode(activity.getEmpCode()).getBody();
            EmployeeDTO employeeDTO = new ObjectMapper().convertValue(ast, EmployeeDTO.class);
            if(employeeDTO.getCode()!=null) {
                activity.setDate(new Date(System.currentTimeMillis()));
                log.info("Inside addDailyActivity: activity = " + activity);
                Activity act = activityRepository.save(activity);
                return new ResponseEntity<>(act, HttpStatus.CREATED);
            }
            else{
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        }
        catch(Exception e){
            log.info("Exception: {}",e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<DailyActivityDTO> viewDailyActivityWithName(Integer employeeCode) {
        try{
            Activity activity = activityRepository.findByEmpCode(employeeCode);
            if(activity.getEmpCode() !=null){
                Object ast = employeeFeign.findEmployeeByCode(employeeCode).getBody();
                EmployeeDTO employeeDTO = new ObjectMapper().convertValue(ast, EmployeeDTO.class);
                if(null != employeeDTO.getCode()){
                    DailyActivityDTO dailyActivityDTO = DailyActivityDTO.builder()
                            .emp_code(employeeCode)
                            .date(activity.getDate())
                            .name(employeeDTO.getName())
                            .email_id(employeeDTO.getEmailId())
                            .description(activity.getDescription())
                            .job_title(employeeDTO.getTitle())
                            .status(activity.getStatus())
                            .build();
                    return new ResponseEntity<>(dailyActivityDTO, HttpStatus.OK);
                }

            }
            else{
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        }
        catch(Exception e){
            log.info("Exception: {}",e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Activity> editDailyActivity(Activity activity) {
        Optional<Activity> activityData = Optional.ofNullable(activityRepository.findByEmpCode(activity.getEmpCode()));
        if(activityData.isPresent()){
            Activity act = activityData.get();
            act.setDate(new Date(System.currentTimeMillis()));
            act.setDescription(activity.getDescription());
            act.setStatus(activity.getStatus());
            return new ResponseEntity<>(activityRepository.save(act), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<DailyActivityDTO>> viewDailyActivity(Date date) {
        try{
            List<DailyActivityDTO> dailyActivityDTOList = new ArrayList<>();
            List<Activity> activity = activityRepository.findByDate(date);
            activity.forEach(act->{
                EmployeeDTO employeeDTO = new ObjectMapper().convertValue(employeeFeign.findEmployeeByCode(act.getEmpCode()).getBody(), EmployeeDTO.class);
                if(null != employeeDTO.getCode()){
                    DailyActivityDTO dailyActivityDTO = DailyActivityDTO.builder()
                            .emp_code(act.getEmpCode())
                            .date(act.getDate())
                            .name(employeeDTO.getName())
                            .email_id(employeeDTO.getEmailId())
                            .description(act.getDescription())
                            .job_title(employeeDTO.getTitle())
                            .status(act.getStatus())
                            .build();
                    dailyActivityDTOList.add(dailyActivityDTO);
                }
            });
            return new ResponseEntity<>(dailyActivityDTOList, HttpStatus.OK);
        }
        catch(Exception e){
            log.info("Exception: {}",e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<List<EmployeeDailyActivityDTO>> viewDailyActivitiesWithNameAndDateRange(Date fromDate, Date toDate, Integer code) throws ParseException {
        List<EmployeeDailyActivityDTO> dailyActivityDTOList = new ArrayList<>();
//        DateValidator validator = new DateValidatorUsingDateFormat(CURRENT_DATE_FORMAT);
//        if(!validator.isValid(fromDate.toString())) fromDate = (Date) new SimpleDateFormat(CURRENT_DATE_FORMAT).parse(fromDate.toString()+" 00:00:01");
//        if(!validator.isValid(toDate.toString())) toDate = (Date) new SimpleDateFormat(CURRENT_DATE_FORMAT).parse(toDate.toString()+" 23:59:59");

        List<Integer> empList = new ArrayList<>();
        Optional<Integer> employeeCode = Optional.ofNullable(code);
        if(employeeCode.isPresent()){
            empList.add(employeeCode.get());
        }
        else{
            empList = activityRepository.findDistinctByEmpCodeDateBetween(fromDate, toDate);
        }

        Date finalFromDate = fromDate;
        Date finalToDate = toDate;
        empList.forEach(empCode->{
            EmployeeDailyActivityDTO employeeDailyActivityDTO = new EmployeeDailyActivityDTO();
            EmployeeDTO employeeDTO = new ObjectMapper().convertValue(employeeFeign.findEmployeeByCode(empCode).getBody(), EmployeeDTO.class);
            employeeDailyActivityDTO.setName(employeeDTO.getName());
            employeeDailyActivityDTO.setEmailId(employeeDTO.getEmailId());
            employeeDailyActivityDTO.setJobTitle(employeeDTO.getTitle());
            List<Activity> activityList = activityRepository.findAllByEmpCodeAndDateBetween(employeeDTO.getCode(),
                    finalFromDate,
                    finalToDate);
            List<ActivityDTO> activityDTOList = new ArrayList<>();
            activityList.forEach(activity -> {
                ActivityDTO activityDTO = ActivityDTO.builder()
                        .date(activity.getDate())
                        .description(activity.getDescription())
                        .status(activity.getStatus())
                        .build();
                activityDTOList.add(activityDTO);
            });
            employeeDailyActivityDTO.setActivities(activityDTOList);
            dailyActivityDTOList.add(employeeDailyActivityDTO);
        });
        return new ResponseEntity<>(dailyActivityDTOList, HttpStatus.OK);
//        if("".equals(name)) {
//            List<Activity> activityList = activityRepository.findByDateBetween(fromDate, toDate);
//        }
//        else{
//            List<Activity> activityList = activityRepository.findByNameAndDateBetween(fromDate, toDate);
//        }

    }
}
