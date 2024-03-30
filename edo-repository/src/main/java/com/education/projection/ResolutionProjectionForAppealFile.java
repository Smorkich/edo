package com.education.projection;


import com.education.entity.Employee;

import java.time.ZonedDateTime;
import java.util.List;

public interface ResolutionProjectionForAppealFile {
    Long getId();
    ZonedDateTime getCreationDate();
    List<Employee> getExecutor();
}
