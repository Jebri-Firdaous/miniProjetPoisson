package com.poisson.backend.Services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.poisson.backend.Entity.Poisson;

public interface IPoissonService {

public Poisson addPoisson (Poisson p);
public List<Poisson> listAllPoisson ();
public Poisson getPoissonById (Long PoissonId);
public Poisson modifyPoisson (Poisson p);
public void removePoisson (Long PoissonId);
Poisson addPoissonWithImage(Poisson poisson, MultipartFile imageFile) throws IOException;
Poisson updatePoissonWithImage(Long id, Poisson poisson, MultipartFile imageFile) throws IOException;




}
