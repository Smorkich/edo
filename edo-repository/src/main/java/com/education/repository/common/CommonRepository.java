package com.education.repository.common;

import com.education.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Интерфейс, расширяющий {@link JpaRepository} для работы с сущностями,
 * расширяющими {@link BaseEntity}.
 *
 * @param <E> Тип сущности, с которой работает репозиторий. Должен расширять {@link BaseEntity}.
 */
@NoRepositoryBean
public interface CommonRepository<E extends BaseEntity> extends JpaRepository<E, Long> {
}
