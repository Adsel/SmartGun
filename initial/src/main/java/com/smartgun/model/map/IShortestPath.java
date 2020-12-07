package com.smartgun.model.map;

import java.awt.*;
import java.util.List;

public interface IShortestPath {
    List<Point> solve(ShortestPathBFS.Coordinate source, ShortestPathBFS.Coordinate destination);
}
