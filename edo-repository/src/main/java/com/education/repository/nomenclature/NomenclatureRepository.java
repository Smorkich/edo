package com.education.repository.nomenclature;

import com.education.entity.Nomenclature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NomenclatureRepository extends JpaRepository<Nomenclature, Long> {
}
