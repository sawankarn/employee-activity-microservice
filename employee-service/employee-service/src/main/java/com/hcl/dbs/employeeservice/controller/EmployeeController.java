package com.hcl.dbs.employeeservice.controller;

import com.hcl.dbs.employeeservice.entity.Employee;
import com.hcl.dbs.employeeservice.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employee")
@Slf4j
@Validated
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        return errors;
    }

    @PostMapping("/add")
    public ResponseEntity<Employee> addEmployee(@Valid @RequestBody Employee employee){
        return employeeService.addEmployee(employee);
    }

    @GetMapping("/")
    public ResponseEntity<List<Employee>> viewEmployees(){
        return employeeService.viewEmployees();
    }

    @PatchMapping("/edit")
    public ResponseEntity<Employee> editEmployee(@RequestBody @Valid Employee employee){
        return employeeService.editEmployee(employee);
    }

    @GetMapping("/{employeeCode}")
    public ResponseEntity<Employee> viewEmployee(@PathVariable("employeeCode") Integer employeeId){
        return employeeService.viewEmployee(employeeId);
    }

}
