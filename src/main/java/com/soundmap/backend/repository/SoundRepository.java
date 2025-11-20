package com.soundmap.backend.repository;

import com.soundmap.backend.entity.Sound;
import com.soundmap.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SoundRepository extends JpaRepository<Sound, Long> {

    List<Sound> findByUser(User user);

}
