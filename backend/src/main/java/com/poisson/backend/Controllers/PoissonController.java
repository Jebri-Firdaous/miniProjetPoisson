package com.poisson.backend.Controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.poisson.backend.Entity.Poisson;
import com.poisson.backend.Services.IPoissonService;

import lombok.AllArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
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

    @PutMapping("/modify-poisson/{id}")
    public Poisson updatePoisson(@PathVariable Long id, @RequestBody Poisson p) {
        p.setId(id); 
        return poissonService.modifyPoisson(p);
    }

    @DeleteMapping("/remove-poisson/{PoissonId}")
    public void removePoisson(@PathVariable("PoissonId") Long PoissonId){
        poissonService.removePoisson(PoissonId);
    }

    @PostMapping("/add-poisson-with-image")
    public Poisson addPoissonWithImage(
        @RequestPart("poisson") Poisson newPoisson,
        @RequestPart("image") MultipartFile imageFile) throws IOException {

        String originalFileName = Paths.get(imageFile.getOriginalFilename()).getFileName().toString();
        String extension = "";

        int dotIndex = originalFileName.lastIndexOf('.');
        if (dotIndex > 0) {
            extension = originalFileName.substring(dotIndex);
        }

        String uniqueFileName = UUID.randomUUID().toString() + extension;
        String targetPath = "../frontend/public/poissons/" + uniqueFileName;
        Files.copy(imageFile.getInputStream(), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);

        newPoisson.setImageUrl("poissons/" + uniqueFileName);
        return poissonService.addPoisson(newPoisson);
    }

    @PostMapping("/update-poisson-with-image/{id}")
    public Poisson updatePoissonWithImage(
        @PathVariable Long id,
        @RequestPart("poisson") Poisson updatedPoisson,
        @RequestPart("image") MultipartFile imageFile) throws IOException {

        Poisson existingPoisson = poissonService.getPoissonById(id);
        if (existingPoisson == null) {
            throw new RuntimeException("Poisson non trouvé");
        }

        if (existingPoisson.getImageUrl() != null) {
            Path oldImagePath = Paths.get("../frontend/public/" + existingPoisson.getImageUrl());
            Files.deleteIfExists(oldImagePath);
        }

        String fileName = Paths.get(imageFile.getOriginalFilename()).getFileName().toString();
        String targetPath = "../frontend/public/poissons/" + fileName;
        Files.copy(imageFile.getInputStream(), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);

        updatedPoisson.setImageUrl("poissons/" + fileName);
        updatedPoisson.setId(id);
        return poissonService.modifyPoisson(updatedPoisson);
    }
}
