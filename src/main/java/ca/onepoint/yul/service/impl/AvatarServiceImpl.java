package ca.onepoint.yul.service.impl;

import ca.onepoint.yul.dto.AvatarDto;
import ca.onepoint.yul.entity.Avatar;
import ca.onepoint.yul.entity.Coord;
import ca.onepoint.yul.entity.LightCoord;
import ca.onepoint.yul.event.TrafficLightEvent;
import ca.onepoint.yul.repository.AvatarRepository;
import ca.onepoint.yul.service.IAvatarService;
import ca.onepoint.yul.util.MoveUtil;
import ca.onepoint.yul.util.PlacementUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

@Service(value = "AvatarService")
public class AvatarServiceImpl implements IAvatarService, ApplicationListener<TrafficLightEvent> {

    @Resource
    private AvatarRepository avatarRepository;

    /**
     * Handle the application event
     * @param event the event
     * */
    @Override
    public void onApplicationEvent(TrafficLightEvent event) {
        this._isVerticalGreen = event.getIsVerticalGreen();
        String redLight = "../assets/images/red-light.png";
        String greenLight = "../assets/images/green-light.png";
        if (MoveUtil.avatarList != null) {
            MoveUtil.avatarList.forEach(avatarDto -> {
                if (Pattern.compile(redLight).matcher(avatarDto.getImage()).find()) {
                    avatarDto.setImage(greenLight);
                } else if (Pattern.compile(greenLight).matcher(avatarDto.getImage()).find()) {
                    avatarDto.setImage(redLight);
                }
            });
        }
    }

    @Override
    public AvatarDto getAvatarById(Integer id) {
        return avatarRepository.findByIdAvatar(id);
    }

    @Override
    public List<AvatarDto> getAllAvatars() {
        List<Avatar> result = new ArrayList<>();
        avatarRepository.findAll().forEach(result::add);
        return mapAvatarToDto(result);
    }

    public List<AvatarDto> getAllLights() {
        List<Avatar> result = new ArrayList<>();
        avatarRepository.findByType(4).forEach(result::add);
        return mapAvatarToDto(result);
    }

    @Override
    public List<AvatarDto> getAvatarsByType(Integer type) {
        return mapAvatarToDto(avatarRepository.findByType(type));
    }

    public void resetAvatars() {
        avatarRepository.removePedestrians();
        avatarRepository.removeLights();
        PlacementUtil.addPietons(avatarRepository, 15);
    }
    
    public void resetLights(ArrayList<LightCoord> coords) {
        avatarRepository.removeLights();
        AtomicInteger i = new AtomicInteger();
        coords.forEach(coord -> {
            String redLight = "../assets/images/red-light.png";
            String greenLight = "../assets/images/green-light.png";
            if (coord.isVertical) {
                avatarRepository.addLight("light" + i.getAndIncrement(), 4, this._isVerticalGreen ? greenLight : redLight, coord.x, coord.y);
            } else {
                avatarRepository.addLight("light" + i.getAndIncrement(), 5, this._isVerticalGreen ? redLight : greenLight, coord.x, coord.y);
            }
        });
    }

    private List<AvatarDto> mapAvatarToDto(List<Avatar> avatarIterable) {
        List<AvatarDto> avatarDtoList = new ArrayList<>();
        for (Avatar avatar : avatarIterable) {
            AvatarDto avatarDto = new AvatarDto();
            avatarDto.setId(avatar.getId());
            avatarDto.setImage(avatar.getImage());
            avatarDto.setName(avatar.getName());
            avatarDto.setWaiting(avatar.getWaiting());
            avatarDto.setMain(avatar.getMain());
            avatarDto.setType(avatar.getType());
            avatarDto.setX(avatar.getX());
            avatarDto.setY(avatar.getY());
            avatarDto.setXdest(avatar.getXdest());
            avatarDto.setYdest(avatar.getYdest());
            avatarDtoList.add(avatarDto);
        }
        return avatarDtoList;
    }

    private boolean _isVerticalGreen;
}
