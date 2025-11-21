package com.soundmap.backend.service;

import com.soundmap.backend.repository.SoundRepository;
import com.soundmap.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class SoundServiceSimpleTest {

    @Mock
    SoundRepository soundRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    SoundService soundService;

    SoundServiceSimpleTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void service_createsInstance_correctly() {
        assert soundService != null;
    }
}
