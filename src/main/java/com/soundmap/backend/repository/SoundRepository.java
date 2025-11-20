package com.soundmap.backend.repository;

import com.soundmap.backend.entity.Sound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoundRepository extends JpaRepository<Sound, Long> {

    // Buscar todos los sonidos de un usuario por su ID
    List<Sound> findByUser_Id(Long userId);
}
