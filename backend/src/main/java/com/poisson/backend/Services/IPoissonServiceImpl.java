package com.poisson.backend.Services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.poisson.backend.Entity.Poisson;
import com.poisson.backend.Repositories.PoissonRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class IPoissonServiceImpl implements IPoissonService {

    private static final String IMAGE_DIR = "/home/firdaous/Bureau/stageING-ETE2025/miniProjetPoisson/images/poissons/";

    private final PoissonRepository poissonRepository;

    @Override
    public Poisson addPoisson(Poisson p) {
        return poissonRepository.save(p);
    }

    @Override
    public List<Poisson> listAllPoisson() {
        return poissonRepository.findAll();
    }

    @Override
    public Poisson getPoissonById(Long poissonId) {
        return poissonRepository.findById(poissonId)
                .orElseThrow(() -> new RuntimeException("Poisson non trouvé avec l'ID : " + poissonId));
    }

    @Override
    public Poisson modifyPoisson(Poisson p) {
        return poissonRepository.save(p);
    }

    @Override
    public void removePoisson(Long poissonId) {
        poissonRepository.deleteById(poissonId);
    }

    @Override
    public Poisson addPoissonWithImage(Poisson poisson, MultipartFile imageFile) throws IOException {
        Poisson savedPoisson = poissonRepository.save(poisson);

        String originalFileName = imageFile.getOriginalFilename();
        String extension = "";
        int dotIndex = originalFileName != null ? originalFileName.lastIndexOf('.') : -1;
        if (dotIndex > 0) {
            extension = originalFileName.substring(dotIndex);
        }

        String baseName = savedPoisson.getNom().toLowerCase().replaceAll("[^a-z0-9]+", "-");
        String uniqueFileName = baseName + "-" + savedPoisson.getId() + extension;

        Path targetLocation = Paths.get(IMAGE_DIR + uniqueFileName);
        Files.createDirectories(targetLocation.getParent());
        Files.copy(imageFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        savedPoisson.setImageUrl("poissons/" + uniqueFileName);

        return poissonRepository.save(savedPoisson);
    }

    @Override
    public Poisson updatePoissonWithImage(Long id, Poisson updatedPoisson, MultipartFile imageFile) throws IOException {
        Poisson existingPoisson = poissonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Poisson non trouvé avec l'ID : " + id));

        if (imageFile != null && !imageFile.isEmpty()) {
            if (existingPoisson.getImageUrl() != null) {
                Path oldImagePath = Paths.get(IMAGE_DIR + Paths.get(existingPoisson.getImageUrl()).getFileName());
                Files.deleteIfExists(oldImagePath);
            }

            String originalFileName = imageFile.getOriginalFilename();
            String extension = "";
            int dotIndex = originalFileName != null ? originalFileName.lastIndexOf('.') : -1;
            if (dotIndex > 0) {
                extension = originalFileName.substring(dotIndex);
            }

            String baseName = updatedPoisson.getNom().toLowerCase().replaceAll("[^a-z0-9]+", "-");
            String uniqueFileName = baseName + "-" + id + extension;

            Path targetLocation = Paths.get(IMAGE_DIR + uniqueFileName);
            Files.createDirectories(targetLocation.getParent());
            Files.copy(imageFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            updatedPoisson.setImageUrl("poissons/" + uniqueFileName);
        } else {
            updatedPoisson.setImageUrl(existingPoisson.getImageUrl());
        }

        updatedPoisson.setId(id);
        return poissonRepository.save(updatedPoisson);
    }
}
