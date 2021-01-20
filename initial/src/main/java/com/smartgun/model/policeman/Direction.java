package com.smartgun.model.policeman;

public enum Direction {
    LEFT (0,-1),
    RIGHT (0,1 ),
    DOWN (1,0),
    UP (-1,0);

    public final int x;
    public final int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
