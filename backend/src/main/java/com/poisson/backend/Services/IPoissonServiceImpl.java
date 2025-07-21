package com.poisson.backend.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.poisson.backend.Entity.Poisson;
import com.poisson.backend.Repositories.PoissonRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class IPoissonServiceImpl implements IPoissonService{

    PoissonRepository poissonRepository;
    @Override
    public Poisson addPoisson(Poisson p) {
        return poissonRepository.save(p);
    }

    @Override
    public List<Poisson> listAllPoisson() {
        return poissonRepository.findAll();
    }

    @Override
    public Poisson getPoissonById(Long PoissonId) {
        return poissonRepository.findById(PoissonId).get();
    }

    @Override
    public Poisson modifyPoisson(Poisson p) {
        return poissonRepository.save(p);
    }

    @Override
    public void removePoisson(Long PoissonId) {
        poissonRepository.deleteById(PoissonId);
    }

}
