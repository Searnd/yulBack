package ca.onepoint.yul.service;

import ca.onepoint.yul.dto.AvatarDto;
import ca.onepoint.yul.entity.Coord;

import java.util.ArrayList;
import java.util.List;

public interface IAvatarService {

    AvatarDto getAvatarById(Integer id);

    List<AvatarDto> getAllAvatars();

    List<AvatarDto> getAvatarsByType(Integer type);

    void resetAvatars();

    void resetLights(ArrayList<Coord> coords);
}
