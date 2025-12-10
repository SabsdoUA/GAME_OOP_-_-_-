package sk.tuke.kpi.oop.game;

import java.util.Map;

public enum Direction {
    NORTH(0, 1),
    EAST(1, 0),
    SOUTH(0, -1),
    WEST(-1, 0),
    NORTHEAST(1, 1),
    NORTHWEST(-1, 1),
    SOUTHEAST(1, -1),
    SOUTHWEST(-1, -1),
    NONE(0, 0);

    private static final Map<Direction, Float> angleMap = Map.of(
        NORTH, 0f,
        EAST, 270f,
        SOUTH, 180f,
        WEST, 90f,
        NORTHEAST, 315f,
        NORTHWEST, 45f,
        SOUTHEAST, 225f,
        SOUTHWEST, 135f,
        NONE, -1f
    );


    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public float getAngle() {
        return angleMap.getOrDefault(this, -1f);
    }

    public Direction combine(Direction other) {
        int combinedDx = this.dx + other.dx;
        int combinedDy = this.dy + other.dy;

        if (combinedDx > 1) combinedDx = 1;
        if (combinedDx < -1) combinedDx = -1;
        if (combinedDy > 1) combinedDy = 1;
        if (combinedDy < -1) combinedDy = -1;


        for (Direction direction : Direction.values()) {
            if (direction.dx == combinedDx && direction.dy == combinedDy) {
                return direction;
            }
        }
        return Direction.NONE;
    }

    public static Direction fromAngle(float angle) {
        float normalizedAngle = (angle % 360 + 360) % 360;

        for (Map.Entry<Direction, Float> entry : angleMap.entrySet())
            if (Math.abs(entry.getValue() - normalizedAngle) < 0.01f)
                return entry.getKey();

        return Direction.NONE;
    }
}
