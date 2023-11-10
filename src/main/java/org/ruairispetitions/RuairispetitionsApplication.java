/**
 * The main SpringBootApplication for the petitions application
 * 
 * @author ruairin
 *
 */

package org.ruairispetitions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

// Note: the application must extend SpringBootServletInitializer
// for the war to be served via a tomcat server
// https://www.baeldung.com/spring-boot-war-tomcat-deploy
@SpringBootApplication
public class RuairispetitionsApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(RuairispetitionsApplication.class, args);
	}

}
