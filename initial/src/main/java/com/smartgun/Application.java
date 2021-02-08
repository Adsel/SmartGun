package com.smartgun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import java.awt.Point;
import java.util.List;

import com.smartgun.model.map.*;

@EnableScheduling
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

		Maps maps = new Maps();
		Map map = Maps.getMap();
		try {
			map.loadMap();
			map.printMap();
			ShortestPathBFS shortestPathBFS = new ShortestPathBFS(map);
			List<Point> list = shortestPathBFS.solve(new ShortestPathBFS.Coordinate(2,1), new ShortestPathBFS.Coordinate(15,1));
			//map.printPath();
			System.out.println(list);

		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
