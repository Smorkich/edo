package com.education.service.appeal;

import com.education.entity.Appeal;

import java.time.ZonedDateTime;
import java.util.Collection;

public interface AppealService {

    Appeal save(Appeal appeal);

    void delete(Long id);

    Appeal findById(Long id);

    Collection<Appeal> findAll();

    void moveToArchive(Long id, ZonedDateTime zonedDateTime);

    Appeal findByIdNotArchived(Long id);

    Collection<Appeal> findAllNotArchived();
}