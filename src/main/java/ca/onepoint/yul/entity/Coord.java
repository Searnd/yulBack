package ca.onepoint.yul.entity;

import ca.onepoint.yul.dto.AvatarDto;

public class Coord {
    public Coord() {
        this.x = 0;
        this.y = 0;
    }
    public Coord(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    public int x;
    public int y;
}
