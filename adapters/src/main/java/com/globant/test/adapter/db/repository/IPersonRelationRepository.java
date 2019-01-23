package com.globant.test.adapter.db.repository;

import com.globant.test.adapter.db.entities.PersonRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersonRelationRepository extends JpaRepository<PersonRelation, Long> {
}
