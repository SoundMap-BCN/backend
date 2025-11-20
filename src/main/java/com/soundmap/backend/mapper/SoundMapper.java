package com.soundmap.backend.mapper;

import com.soundmap.backend.dto.SoundResponse;
import com.soundmap.backend.entity.Sound;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SoundMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "username")
    @Mapping(target = "audioUrl", expression = "java(\"/uploads/audio/\" + sound.getAudioFilename())")
    SoundResponse toSoundResponse(Sound sound);
}
