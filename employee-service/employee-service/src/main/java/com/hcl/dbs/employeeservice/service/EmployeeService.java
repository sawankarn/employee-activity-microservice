package com.hcl.dbs.employeeservice.service;

import com.hcl.dbs.employeeservice.entity.Employee;
import com.hcl.dbs.employeeservice.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public ResponseEntity<Employee> addEmployee(Employee employee) {
        try{
            Optional<Employee> employeeData = Optional.ofNullable(employeeRepository.findByCode(employee.getCode()));
            if(employeeData.isPresent()) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            log.info("Inside addEmployee: employee = " + employee);
            Employee emp = employeeRepository.save(employee);
            return new ResponseEntity<>(emp, HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Employee> editEmployee(Employee emp){
        Optional<Employee> employeeData = Optional.ofNullable(employeeRepository.findByCode(emp.getCode()));
        if(employeeData.isPresent()){
            Employee employee = employeeData.get();
            employee.setCode(emp.getCode());
            employee.setEmailId(emp.getEmailId());
            employee.setExperience(emp.getExperience());
            employee.setJob(emp.getJob());
            employee.setLocation(emp.getLocation());
            employee.setName(emp.getName());
            employee.setPhoneNumber(emp.getPhoneNumber());
            employee.setProject_status(emp.getProject_status());
            employee.setTitle(emp.getTitle());
            return new ResponseEntity<>(employeeRepository.save(employee), HttpStatus.OK);

        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Employee> viewEmployee(Integer employeeCode) {
        log.info("Inside service viewEmployee");
        Optional<Employee> employeeData = Optional.ofNullable(employeeRepository.findByCode(employeeCode));

        if (employeeData.isPresent()) {
            return new ResponseEntity<>(employeeData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<Employee>> viewEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        try{
        log.info("Inside service viewEmployees");
        employeeRepository.findAll().forEach(employeeList::add);
        if (employeeList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
