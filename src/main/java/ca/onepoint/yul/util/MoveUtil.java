package ca.onepoint.yul.util;

import ca.onepoint.yul.dto.AvatarDto;
import ca.onepoint.yul.dto.MapDto;
import ca.onepoint.yul.dto.SquareDto;
import ca.onepoint.yul.entity.Coord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public static void move(AvatarDto avatarDto, List<MapDto> mapDtos) {
        if (avatarDto.getType() == 0) { // CORONA
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
            int nextX = dist.x + avatarDto.getX();
            int nextY = dist.y + avatarDto.getY();
            if (isOverSteppable(nextX, nextY, avatarDto.getType(), mapDtos)) {
                moveAvatarToNextSquare(avatarDto, dist, nextX, nextY);
            }
        }
    }

    private static void moveAvatarToNextSquare(AvatarDto avatarDto, Coord dist, int nextX, int nextY) {
        if (dist.x == 0) {
            avatarDto.setY(nextY);
        } else {
            if (dist.y == 0) {
                avatarDto.setX(nextX);
            } else {
                if (Math.random() > 0.5) {
                    avatarDto.setX(nextX);
                } else {
                    avatarDto.setY(nextY);
                }
            }
        }
    }

    public static boolean isOverSteppable(int nextX, int nextY, int avatarType, List<MapDto> mapDtos) {
        if (avatarType == 1) {
            SquareDto[][] squareDtos = mapDtos.get(0).getSquare();
            return squareDtos[nextX][nextY].getValue() == 1;
        }
        return true;
    }
}
