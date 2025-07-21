package com.poisson.backend.Controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poisson.backend.Entity.Poisson;
import com.poisson.backend.Services.IPoissonService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/poisson")
public class PoissonController {
    private IPoissonService poissonService;

    @PostMapping("/add-poisson")
    public Poisson addPoisson(@RequestBody Poisson p){
        Poisson poisson = poissonService.addPoisson(p);
        return poisson;
    } 

    @GetMapping("/list-all-poisson")
    public List<Poisson> getAllPoissons(){
        List<Poisson> listPoissons = poissonService.listAllPoisson();
        return listPoissons;
    } 
    
    @GetMapping("/get-poisson/{PoissonId}")
    public Poisson getPoissonsById(@PathVariable("PoissonId") Long PoissonId){
        Poisson poisson = poissonService.getPoissonById(PoissonId);
        return poisson;
    } 

    @PutMapping("/modify-poisson/{PoissonId}")
    public Poisson modifyPoisson(@RequestBody Poisson p){
        Poisson poisson = poissonService.modifyPoisson(p);
        return poisson;
    } 

    @DeleteMapping("/remove-poisson/{PoissonId}")
    public void removePoisson(@PathVariable("PoissonId") Long PoissonId){
        poissonService.removePoisson(PoissonId);
    }
}
