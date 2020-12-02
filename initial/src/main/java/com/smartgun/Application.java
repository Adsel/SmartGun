package com.smartgun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import java.awt.Point;

import com.smartgun.model.policeman.*;
import com.smartgun.model.map.*;

@EnableScheduling
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);



		// THE SHORTEST PATH IN MAZE

		// 1 - available entries
		// 0 - collisions
		int maze[][] = {
				{ 1, 0, 0, 0 },
				{ 1, 1, 0, 1 },
				{ 0, 1, 0, 0 },
				{ 1, 1, 1, 1 }
		};

		/* !!! Problem !!!
		TheShortestPath rat = new TheShortestPath(maze.length);
		boolean isSolution = rat.solveMaze(maze);
		if (isSolution) {
			// solution exists we can move our patrol
		} else {
			// solution doesn't exist, patrol can't reach destination
		}
		 */

	}

}
