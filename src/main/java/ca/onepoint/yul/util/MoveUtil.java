package ca.onepoint.yul.util;

import ca.onepoint.yul.dto.AvatarDto;
import ca.onepoint.yul.dto.MapDto;
import ca.onepoint.yul.dto.SquareDto;
import ca.onepoint.yul.entity.Coord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MoveUtil {
    static ArrayList<Coord> coronaDestinations = new ArrayList<>();

    public static List<AvatarDto> avatarList = new ArrayList<>();


    /**
     * Resets the array of next destinations
     * */
    public static void resetCoronaDestinations() {
        MoveUtil.coronaDestinations = new ArrayList<Coord>(
                Arrays.asList(new Coord(16, 14), new Coord(3, 21), new Coord(13, 18))
        );
    }

    /**
     * Calculates the absolute distance from the actual position to the destination
     * @param avatar the avatar to calculate from
     * */
    static Coord calcDistance(AvatarDto avatar) {
        Integer diffX = avatar.getXdest() - avatar.getX();
        Integer diffY = avatar.getYdest() - avatar.getY();
        return new Coord(diffX, diffY);
    }

    /**
     * Gets the direction where to head to
     * @param dist the difference
     * */
    static Integer getStepFromDistance(Integer dist) {
        if (dist < 0) {
            return -1;
        }
        if (dist > 0) {
            return 1;
        }
        return 0;
    }

    /**
     * Move the specified avatar
     * @param avatarDto the avatar
     * */
    public static void move(AvatarDto avatarDto, List<MapDto> mapDtos) {
        if (avatarDto.getType().equals(7)) {
            if (
                    MoveUtil.coronaDestinations.size() > 0 &&
                            avatarDto.getX().equals(avatarDto.getXdest()) &&
                            avatarDto.getY().equals(avatarDto.getYdest())
            ) {
                Coord newDest = MoveUtil.coronaDestinations.get(0);
                avatarDto.setXdest(newDest.x);
                avatarDto.setYdest(newDest.y);
                MoveUtil.coronaDestinations.remove(0);
                return;
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

    /**
     * Move the avatar to the specified square
     * @param avatarDto the avatar
     * @param dist the distance toward the destination
     * @param nextX x coordinate of the next square
     * @param nextY y coordinate of the next square
     * */
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

    /**
     * Checks if the specified square can be overstepped by the specified type of avatar
     * @param nextX x coordinate of the next square
     * @param nextY y coordinate of the next square
     * @param avatarType the avatar
     * @param mapDtos the map on which the avatar is moving
     * */
    public static boolean isOverSteppable(int nextX, int nextY, int avatarType, List<MapDto> mapDtos) {
        if (avatarType == 1 || avatarType == 3) {
            SquareDto[][] squareDtos = mapDtos.get(0).getSquare();
            return squareDtos[nextX][nextY].getValue() == 1;
        }
        return true;
    }
}
