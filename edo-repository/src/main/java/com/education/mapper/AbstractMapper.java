package com.education.mapper;



import com.education.entity.BaseEntity;
import com.education.entity.Department;
import model.dto.DepartmentDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


import java.util.Collection;


/**
 * @author Usolkin Dmitry
 * Общий интерфейс для маппинга сущностей в дто и обратно
 */

public interface
AbstractMapper <E extends BaseEntity,D extends Object> {

    D toDto(E entity);

    E toEntity(D dto);

    Collection <D> toDto(Collection<E> departments);

    Collection<E> toEntity(Collection<D> departmentsDto);


}