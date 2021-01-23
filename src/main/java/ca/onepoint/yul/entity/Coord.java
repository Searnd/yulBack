package ca.onepoint.yul.entity;

import ca.onepoint.yul.dto.AvatarDto;

public class Coord {
    public Coord(AvatarDto avatarDto) {
        this.x = avatarDto.getX();
        this.y = avatarDto.getY();
    }
    public Coord(Integer newX, Integer newY) {
        this.x = newX;
        this.y = newY;
    }

    public Integer x;
    public Integer y;
}
