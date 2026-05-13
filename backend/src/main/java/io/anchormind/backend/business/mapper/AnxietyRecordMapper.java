package io.anchormind.backend.business.mapper;

import io.anchormind.backend.domain.dto.AnxietyRecordDTO;
import io.anchormind.backend.domain.dto.UserDTO;
import io.anchormind.backend.domain.entities.AnxietyRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component // Para que Spring lo pueda inyectar donde lo necesites
@RequiredArgsConstructor
public class AnxietyRecordMapper {

    private final UserMapper userMapper;

    // Entidad -> DTO (Para mostrar al usuario)
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

    // DTO -> Entidad (Para guardar en la base de datos)
    public AnxietyRecord toEntity(AnxietyRecordDTO dto) {
        if (dto == null) return null;

        AnxietyRecord entity = new AnxietyRecord();
        entity.setRawInput(dto.rawInput());
        // El ID y el TimeStamp se suelen generar automáticamente,
        // pero podemos setearlos si vienen en el DTO.
        entity.setAnxietyLevel(dto.anxietyLevel());
        entity.setTriggerIdentified(dto.triggerIdentified());
        entity.setTechnique(dto.technique());
        entity.setApplicability(dto.applicability());
        entity.setAwarenessMessage(dto.awarenessMessage());
        entity.setActionSteps(dto.actionSteps());

        return entity;
    }
}