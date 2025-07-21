package com.poisson.backend.Services;

import java.util.List;

import com.poisson.backend.Entity.Poisson;

public interface IPoissonService {

public Poisson addPoisson (Poisson p);
public List<Poisson> listAllPoisson ();
public Poisson getPoissonById (Long PoissonId);
public Poisson modifyPoisson (Poisson p);
public void removePoisson (Long PoissonId);




}
