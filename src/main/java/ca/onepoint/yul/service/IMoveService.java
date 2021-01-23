package ca.onepoint.yul.service;

import ca.onepoint.yul.dto.AvatarDto;
import ca.onepoint.yul.entity.Coord;

public interface IMoveService {
    static Coord calcDistance(AvatarDto avatar) {
        Integer diffX = avatar.getXdest() - avatar.getX();
        Integer diffY = avatar.getYdest() - avatar.getY();
        return new Coord(diffX, diffY);
    }
    static Integer getStepFromDistance(Integer dist) {
        if (dist < 0) {
            return -1;
        }
        if (dist > 0) {
            return 1;
        }
        return 0;
    }
    static void move(AvatarDto avatarDto) {
        Coord dist = IMoveService.calcDistance(avatarDto);
        dist.x = getStepFromDistance(dist.x);
        dist.y = getStepFromDistance(dist.y);
        if (dist.x > 0 || dist.y > 0) {
            if (dist.x == 0) {
                avatarDto.setY(dist.y + avatarDto.getY());
            } else if (dist.y == 0) {
                avatarDto.setX(dist.x + avatarDto.getX());
            } else {
                if (Math.random() > 0.5) {
                    avatarDto.setX(dist.x + avatarDto.getX());
                } else {
                    avatarDto.setY(dist.y + avatarDto.getY());
                }
            }
        }
    }
}
