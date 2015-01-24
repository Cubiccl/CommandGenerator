package commandGenerator.main;

import static commandGenerator.main.Resources.folder;

import java.io.File;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FileHelper
{

	/** Scanner used to read files. */
	private static Scanner scanner;
	/** Writer used to edit files. */
	private static PrintWriter writer;

	/** Reads a file's data accroding to an id. */
	public static String read(String file, String id)
	{

		String name = id;

		boolean flag = openFile(file);

		if (flag) return name;

		flag = true;

		while (scanner.hasNextLine())
		{
			if (scanner.next().equals(id))
			{
				name = scanner.nextLine().substring(3);
				flag = false;
			} else scanner.nextLine();

		}

		if (flag) DisplayHelper.log("Missing translation : " + file + " / " + id);
		scanner.close();
		if (name.equals("null")) return "";
		name = name.replaceAll("N/L", "\n");
		return name;
	}

	/** Opens a file. Returns true if it couldn't. */
	private static boolean openFile(String fileName)
	{
		try
		{
			scanner = new Scanner(new File(folder + "lang/" + fileName + ".txt"));
		} catch (Exception e)
		{
			DisplayHelper.log("Missing lang file : lang/" + fileName + ".txt");
			return true;
		}
		return false;
	}

	/** Returns the option in the settings file. */
	public static String getOption(String id)
	{

		if (!new File(folder + "options.txt").exists()) createOptions();

		try
		{
			scanner = new Scanner(new File(folder + "options.txt"));
		} catch (Exception e)
		{
			DisplayHelper.log("Couldn't open the Options file.");
		}

		String option = id;

		while (scanner.hasNextLine())
		{
			if (scanner.next().equals(id)) option = scanner.nextLine().substring(3);
			else scanner.nextLine();
		}

		scanner.close();
		return option;
	}

	/** Creates the settings file. */
	private static void createOptions()
	{
		try
		{
			writer = new PrintWriter(folder + "savedObjects.txt", "UTF-8");
			writer.close();
			writer = new PrintWriter(folder + "options.txt", "UTF-8");
			writer.println("lang = 0");
			writer.println("version = 5.0");
			writer.println("english_gb = false");
			writer.close();
		} catch (Exception e)
		{
			DisplayHelper.log("Couldn't create the Options.");
		}
	}

	/** Changes an option in the settings file. */
	public static void setOption(String id, String value)
	{

		try
		{
			scanner = new Scanner(new File(folder + "options.txt"));
		} catch (Exception e)
		{
			DisplayHelper.log("Couldn't open the Options file.");
			return;
		}
		List<String> options = new ArrayList<String>();
		while (scanner.hasNextLine())
		{
			options.add(scanner.nextLine());
		}
		scanner.close();

		for (int i = 0; i < options.size(); i++)
		{
			if (options.get(i).startsWith(id))
			{
				options.set(i, id + " = " + value);
				break;
			}
		}

		try
		{
			writer = new PrintWriter(folder + "options.txt", "UTF-8");
			for (int i = 0; i < options.size(); i++)
			{
				writer.println(options.get(i));
			}
			writer.close();
		} catch (Exception e)
		{
			DisplayHelper.log("Couldn't set option : " + id + " -> " + value);
		}
	}

	public static List<String> readFileArray(String file)
	{
		boolean missing;
		List<String> text = new ArrayList<String>();
		try
		{
			scanner = new Scanner(new File(folder + file));
			missing = false;
		} catch (Exception e)
		{
			DisplayHelper.log("Couldn't read file : " + file);
			DisplayHelper.log("Missing file : " + file);
			missing = true;
		}
		if (missing) return text;

		while (scanner.hasNextLine())
		{
			String line = scanner.nextLine();
			if (!line.equals("")) text.add(line);
		}

		return text;
	}

	/** Reads an entire file. */
	public static Map<String, String> readHoleFileIDs(String file)
	{
		Map<String, String> map = new HashMap<String, String>();

		openFile(file);
		while (scanner.hasNextLine())
		{
			String id = scanner.next();
			String value = scanner.nextLine().substring(3);
			map.put(id, value);
		}

		scanner.close();
		return map;
	}

	public static Map<String, Map<String, String>> readLanguageFile(String id, String[] categories)
	{
		Map<String, Map<String, String>> dict = new HashMap<String, Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		int index = 0;

		boolean cancel = openFile(id);
		if (cancel) return dict;
		scanner.nextLine();
		while (scanner.hasNextLine() && index < categories.length)
		{
			String line = scanner.nextLine();
			if (line.equals("")) continue;
			if (line.contains(categories[index + 1]))
			{
				dict.put(categories[index], map);
				map = new HashMap<String, String>();
				index++;
			} else
			{
				String[] data = line.split(" = ");
				map.put(data[0], data[1]);
			}
		}

		scanner.close();
		return dict;
	}

	public static void setupFolder()
	{
		String directory;
		String os = (System.getProperty("os.name")).toUpperCase();

		if (os.contains("WIN")) directory = System.getenv("AppData");// Windows
		else
		{
			directory = System.getProperty("user.home");// Mac or Linux
			directory += "/Library/Application Support";// Mac
		}
		folder = directory + "/commandGenerator/";

		if (new File(folder + "log.txt").exists()) new File(folder + "log.txt").delete();
		try
		{
			Writer writer = new PrintWriter(folder + "log.txt");
			writer.close();
		} catch (Exception e)
		{}

		if (!new File(folder + "customData.txt").exists())
		{
			try
			{
				Writer writer = new PrintWriter(folder + "customData.txt");
				writer.close();
			} catch (Exception e)
			{}
		}
	}

	public static void addObject(String txt)
	{
		try
		{
			List<String> prev = new ArrayList<String>();
			Scanner scanner = new Scanner(new File(Resources.folder + "savedObjects.txt"));
			while (scanner.hasNextLine())
				prev.add(scanner.nextLine());
			scanner.close();

			Writer writer = new PrintWriter(Resources.folder + "savedObjects.txt");
			for (int i = 0; i < prev.size(); i++)
			{
				writer.write(prev.get(i) + "\n");
			}
			writer.write(txt + "\n");
			writer.close();
		} catch (Exception e)
		{}
	}

}
