package de.dhbwka.java.exercise.io;

import java.io.File;
import java.io.IOException;


/**
 * @author DHBW lecturer
 * @version 1.0
 *
 * Part of lectures on 'Programming in Java'. Baden-Wuerttemberg
 * Cooperative State University.
 *
 * (C) 2016 by J. Sidler, T. Schlachter, C. Schmitt, W. Suess
 */
public class FileExperiments {
	public static void main(String[] args) {
		File myDir = new File("myDir");
		myDir.mkdir();
		File[] fooFiles = new File[3];
		for (int i = 0; i < fooFiles.length; i++) {
			fooFiles[i] = new File(myDir, "foo" + (i + 1));
			try {
				fooFiles[i].createNewFile();
			} catch (IOException e) {
				System.err.println("Error creating File " + fooFiles[i].getAbsolutePath());
			}
		}
		System.out.println(myDir.getAbsolutePath());
		for (File f : myDir.listFiles())
			System.out.println(f.getAbsolutePath());
		// Erweiterung: Alles wieder loeschen
		for (File f : fooFiles)
			f.delete();
		myDir.delete();
	}
}

