package com.poisson.backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poisson.backend.Entity.Poisson;

@Repository
public interface PoissonRepository  extends JpaRepository <Poisson,Long>{

}
