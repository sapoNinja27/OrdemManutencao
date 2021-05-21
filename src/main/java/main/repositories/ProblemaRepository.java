package main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import main.domain.Problema;

@Repository
public interface ProblemaRepository extends JpaRepository<Problema, Integer> {

}
