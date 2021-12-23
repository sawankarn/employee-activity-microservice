package com.hcl.dbs.activityservice.repository;

import com.hcl.dbs.activityservice.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {
    Activity findByEmpCode(Integer employeeCode);

    List<Activity> findByDate(Date date);

    @Query("select distinct a.empCode from Activity a where a.date between :fromDate and :toDate")
    List<Integer> findDistinctByEmpCodeDateBetween(Date fromDate, Date toDate);

    List<Activity> findByDateBetween(Date fromDate, Date toDate);

    @Query("select distinct a.empCode from Activity a where a.empCode=:code and a.date<=:fromDate and a.date>:toDate")
    List<Activity> findDistinctEmpCodeByAndDateBetween(Integer code, Date fromDate, Date toDate);

    @Query("select a from Activity a where a.empCode=:code and a.date between :fromDate and :toDate")
    List<Activity> findAllByEmpCodeAndDateBetween(Integer code, Date fromDate, Date toDate);

    @Query("select a from Activity a where a.empCode=:code and a.date between :fromDate and :toDate")
    List<Activity> findByNameAndDateBetween(String code, Date fromDate, Date toDate);
}
