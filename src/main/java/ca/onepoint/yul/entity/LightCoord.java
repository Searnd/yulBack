package ca.onepoint.yul.entity;

public class LightCoord extends Coord {
    public LightCoord() {
        super();
        this.isVertical = false;
    }
    public LightCoord(int newX, int newY, boolean isVert) {
        super(newX, newY);
        this.isVertical = isVert;
    }
    public boolean isVertical;
}
