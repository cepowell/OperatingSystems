// Cait Powell
// CS 222
// 5 October 2015

package millsF15;

import java.io.*;
import java.util.ArrayList;

/* RELATIVE PATHNAMES WORK. "cd Documents" is fine (as opposed to needing /Users/CaitlinPowell/Documents). "cd .." also works.*/

public class SimpleShell {  

	public static void main(String[] args) throws java.io.IOException {
		
		File currentDirectory = new File(System.getProperty("user.home"));
		String commandLine;
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

		try {

			// we break out with <control><C>
			while (true) {

				System.out.print("jsh>");
				commandLine = console.readLine();

				if (commandLine.equals("")) {
					continue;
				}

				String[] tokens = commandLine.split("\\s+");

				ArrayList<String> command = new ArrayList<String>();

				for (int i = 1; i < tokens.length; i++) {
					command.add(tokens[i]);
				}

				ProcessBuilder pb = new ProcessBuilder(command);
				
				if (command.contains("cd")) {
					if (command.size() == 1) {
						File home = new File(System.getProperty("user.home"));
						currentDirectory = home;
					}
					else {
						File newDirectory = new File(currentDirectory + "/" + command.get(1)); 
						if (!newDirectory.exists() || !newDirectory.isDirectory()) {
							System.out.println("Directory does not exist or is not a directory.");
						}
						else {
							currentDirectory = newDirectory;
						}
					}
				}
				
				pb.directory(currentDirectory);
				Process process = pb.start();

				BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

				String line = br.readLine();
				while (line != null) {
					System.out.println(line);
					line = br.readLine();
				}

				br.close();

			}
		}
		catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

	}
}


