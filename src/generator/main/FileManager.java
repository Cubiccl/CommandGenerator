package generator.main;

import generator.CommandGenerator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class FileManager
{

	/** @return The path to the Generator folder. */
	public static String folder()
	{
		return CommandGenerator.getSettings().getFolder();
	}

	public static BufferedImage loadTexture(String path)
	{
		try
		{
			BufferedImage texture = ImageIO.read(new File(folder() + "textures/" + path + ".png"));
			if (texture == null) texture = ImageIO.read(new File(folder() + "textures/" + path + ".gif"));
			return texture;
		} catch (Exception e)
		{
			CommandGenerator.log("Missing texture : " + path);
			CommandGenerator.log(e);
		}
		return null;
	}

	public static void log(String message)
	{
		File file = new File(folder() + "log.txt");
		ArrayList<String> toPrint = new ArrayList<String>();
		if (file.exists()) toPrint.addAll(readFileAsList("log.txt"));
		else try
		{
			file.createNewFile();
		} catch (IOException e)
		{
			CommandGenerator.log(e);
		}

		toPrint.add("[" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()) + "] " + message);
		try
		{
			PrintWriter writer = new PrintWriter(file);
			for (String line : toPrint)
			{
				writer.println(line);
			}
			writer.close();
		} catch (FileNotFoundException e)
		{
			CommandGenerator.log(e);
		}
	}

	/** Reads the given file. If the file doesn't exist, returns an empty array.
	 * 
	 * @param path - The path to the file, assuming it is in the Generator folder.
	 * @return An array containing each line of the file. */
	public static String[] readFileAsArray(String path)
	{
		return readFileAsList(path).toArray(new String[0]);
	}

	/** Reads the given file. If the file doesn't exist, returns an empty list.
	 * 
	 * @param path - The path to the file, assuming it is in the Generator folder.
	 * @return A list containing each line of the file. */
	public static ArrayList<String> readFileAsList(String path)
	{
		ArrayList<String> data = new ArrayList<String>();
		File file = new File(folder() + path);
		if (!file.exists()) return data;

		try
		{
			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine())
			{
				String line = scanner.nextLine();
				if (!line.equals("") && !line.startsWith("//")) data.add(line);
			}

			scanner.close();
			return data;
		} catch (FileNotFoundException e)
		{
			CommandGenerator.log(e);
		}

		return data;
	}

	/** Creates the folder with all Generator data.
	 * 
	 * @return The path to this folder. */
	public static String setupFolder()
	{
		// TODO adapt to launcher 
		String directory;
		String os = (System.getProperty("os.name")).toUpperCase();

		if (os.contains("WIN")) directory = System.getenv("AppData");// Windows
		else
		{
			directory = System.getProperty("user.home");// Mac or Linux
			directory += "/Library/Application Support";
		}
		return directory + "/CommandGeneratorResources/";
	}

	public static void clearLog()
	{
		File logFile = new File(folder() + "log.txt");
		if (logFile.exists()) logFile.delete();
		try
		{
			logFile.createNewFile();
		} catch (Exception e)
		{
			System.out.println(e);
		}
	}

}
