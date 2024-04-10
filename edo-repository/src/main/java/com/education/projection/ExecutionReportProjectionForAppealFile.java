package com.education.projection;

import com.education.entity.Resolution;
import model.enum_.Status;

public interface ExecutionReportProjectionForAppealFile {
    Long getId();

    Status getStatus();

    Resolution getResolution();
}
