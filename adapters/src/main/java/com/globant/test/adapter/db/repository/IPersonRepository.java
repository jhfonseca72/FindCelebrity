package com.globant.test.adapter.db.repository;

import com.globant.test.adapter.db.entities.PersonData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersonRepository extends JpaRepository<PersonData, Long> {
}
