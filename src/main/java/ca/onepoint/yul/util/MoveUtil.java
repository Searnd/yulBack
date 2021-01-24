package ca.onepoint.yul.util;

import ca.onepoint.yul.dto.AvatarDto;
import ca.onepoint.yul.entity.Coord;

import java.util.ArrayList;
import java.util.Arrays;

public class MoveUtil {
    static ArrayList<Coord> coronaDestinations = new ArrayList<Coord>(
            Arrays.asList(new Coord(16, 14), new Coord(3, 21), new Coord(13, 18))
    );

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
    public static void move(AvatarDto avatarDto) {
        if (avatarDto.getType().equals(1)) {
            if (
                    MoveUtil.coronaDestinations.size() > 0 &&
                            avatarDto.getX().equals(avatarDto.getXdest()) &&
                            avatarDto.getY().equals(avatarDto.getYdest())
            ) {
                Coord newDest = MoveUtil.coronaDestinations.get(0);
                avatarDto.setXdest(newDest.x);
                avatarDto.setYdest(newDest.y);
                MoveUtil.coronaDestinations.remove(0);
            }
        }
        Coord dist = MoveUtil.calcDistance(avatarDto);
        dist.x = getStepFromDistance(dist.x);
        dist.y = getStepFromDistance(dist.y);
        if (dist.x != 0 || dist.y != 0) {
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
