package io.anchormind.backend.mapper;

import io.anchormind.backend.dto.AnxietyRecordDTO;
import io.anchormind.backend.dto.UserDTO;
import io.anchormind.backend.model.entity.AnxietyRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component // Para que Spring lo pueda inyectar donde lo necesites
@RequiredArgsConstructor
public class AnxietyRecordMapper {

    private final UserMapper userMapper;

    public AnxietyRecordDTO toDTO(AnxietyRecord entity) {
        // En lugar de mapear el usuario aquí a mano, llamamos al especialista
        UserDTO userDTO = userMapper.toDTO(entity.getUser());

        return new AnxietyRecordDTO(
                entity.getId(),
                entity.getTimeStamp(),
                entity.getRawInput(),
                entity.getAnxietyLevel(),
                entity.getTriggerIdentified(),
                entity.getTechnique(),
                entity.getApplicability(),
                entity.getAwarenessMessage(),
                entity.getActionSteps(),
                userDTO
        );
    }
}