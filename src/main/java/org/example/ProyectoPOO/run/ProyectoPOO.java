package org.example.ProyectoPOO.run;

import org.openxava.util.*;

/**
 * Execute this class to start the application.
 *
 * With OpenXava Studio/Eclipse: Right mouse button > Run As > Java Application
 */

public class ProyectoPOO {

	public static void main(String[] args) throws Exception {
		/* DBServer.start("ProyectoPOO-db"); // To use your own database comment this line and configure src/main/webapp/META-INF/context.xml */
		AppServer.run("ProyectoPOO"); // Use AppServer.run("") to run in root context
	}

}
