package com.hcl.dbs.employeeservice.repository;

import com.hcl.dbs.employeeservice.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findByCode(Integer employeeId);
}
