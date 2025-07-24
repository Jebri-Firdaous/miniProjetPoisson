package com.poisson.backend.Controllers;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.poisson.backend.Entity.Poisson;
import com.poisson.backend.Services.IPoissonService;

import lombok.AllArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@AllArgsConstructor
@RequestMapping("/api/poisson")
public class PoissonController {

    private final IPoissonService poissonService;

    private static final String IMAGE_DIR = "/home/firdaous/Bureau/stageING-ETE2025/miniProjetPoisson/images/poissons/";

    @PostMapping("/add-poisson")
    public Poisson addPoisson(@RequestBody Poisson p) {
        return poissonService.addPoisson(p);
    }

    @GetMapping("/list-all-poisson")
    public List<Poisson> getAllPoissons() {
        return poissonService.listAllPoisson();
    }

    @GetMapping("/get-poisson/{PoissonId}")
    public Poisson getPoissonById(@PathVariable("PoissonId") Long poissonId) {
        return poissonService.getPoissonById(poissonId);
    }

    @PutMapping("/modify-poisson/{id}")
    public Poisson updatePoisson(@PathVariable Long id, @RequestBody Poisson p) {
        p.setId(id);
        return poissonService.modifyPoisson(p);
    }

    @DeleteMapping("/remove-poisson/{PoissonId}")
    public void removePoisson(@PathVariable("PoissonId") Long poissonId) {
        Poisson existing = poissonService.getPoissonById(poissonId);
        if (existing != null && existing.getImageUrl() != null) {
            Path imagePath = Paths.get(IMAGE_DIR + Paths.get(existing.getImageUrl()).getFileName());
            try {
                Files.deleteIfExists(imagePath);
            } catch (IOException e) {
                System.err.println("Erreur lors de la suppression de l'image : " + e.getMessage());
            }
        }
        poissonService.removePoisson(poissonId);
    }

@PostMapping("/add-poisson-with-image")
public Poisson addPoissonWithImage(
        @RequestPart("poisson") Poisson newPoisson,
        @RequestPart("image") MultipartFile imageFile) throws IOException {
    return poissonService.addPoissonWithImage(newPoisson, imageFile);
}

@PutMapping("/update-poisson-with-image/{id}")
public Poisson updatePoissonWithImage(
        @PathVariable Long id,
        @RequestPart("poisson") Poisson updatedPoisson,
        @RequestPart(value = "image", required = false) MultipartFile imageFile) throws IOException {
    return poissonService.updatePoissonWithImage(id, updatedPoisson, imageFile);
}

}
