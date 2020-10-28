package com.smartgun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import java.awt.Point;

import com.smartgun.model.policeman.*;

@EnableScheduling
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

		// TESTS
		// TODO IN FUTURE: MAKE METHOD TO GENERATING NEW PATROLS (maybe with random names, like Mietek)
//		Navigation navigation1 = new Navigation();
//		SmartWatch smartWatch1 = new SmartWatch(
//				new Point(0, 0),
//				navigation1
//		);
//
//		Patrol patrol1 = new Patrol(
//				smartWatch1,
//				navigation1,
//				new Policeman(true),
//				new Policeman(false)
//				// TODO WHEN X WILL BE ADDED: X connector;
//		);

	}

}
