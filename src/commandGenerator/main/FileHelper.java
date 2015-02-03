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

	/** Creates the settings file. */
	private static void createOptions()
	{
		try
		{
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

	/** Returns the option in the settings file.
	 * 
	 * @param id
	 *            - <i>String</i> - The ID of the option to look for. */
	public static String getOption(String id)
	{

		if (!new File(folder + "options.txt").exists()) createOptions();

		if (openFile("options.txt")) return null;

		String option = id;

		while (scanner.hasNextLine())
		{
			if (scanner.next().equals(id)) option = scanner.nextLine().substring(3);
			else scanner.nextLine();
		}

		scanner.close();
		return option;
	}

	/** Opens a file. Returns true if it couldn't.
	 * 
	 * @param fileName
	 *            - <i>String</i> - The URL of the file. Starts from the Command Generator resource folder. */
	private static boolean openFile(String fileName)
	{
		try
		{
			scanner = new Scanner(new File(folder + fileName));
		} catch (Exception e)
		{
			DisplayHelper.log("Couldn't open file : " + fileName);
			return true;
		}
		return false;
	}

	/** Returns an ArrayList containing all lines of the specified file.
	 * 
	 * @param file
	 *            - <i>String</i> - The URL of the file to read. */
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

	/** Reads a language file.
	 * 
	 * @param language
	 *            - <i>String</i> - The name of the file
	 * @param categories
	 *            - <i>String[]</i> - The list of categories this file should contain. */
	public static Map<String, Map<String, String>> readLanguageFile(String language, String[] categories)
	{
		Map<String, Map<String, String>> dict = new HashMap<String, Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		int index = 0;

		boolean cancel = openFile("lang/" + language + ".txt");
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

	/** Changes an option in the settings file.
	 * 
	 * @param id
	 *            - <i>String</i> - The ID of the option to change.
	 * @param value
	 *            - <i>String</i> - The new value of the option. */
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

	/** Creates and sets up the Command Generator resource folder. */
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
				writer.write("/** BLOCKS **** ID,id **/\n\n/** ITEMS **** ID,id,maxDamage,gif **/\n\n/** ENTITIES **/\n\n/** EFFECTS **/\n\n"
						+ "/** ENCHANTMENTS **** ID,id,max_level **/\n\n/** ACHIEVEMENTS **** id,item **/\n\n/** ATTRIBUTES **/\n\n/** PARTICLES **/\n\n/** SOUNDS **/\n\n"
						+ "/** LISTS **** name:elements **/\n\n/** BLOCKTAGS **** name,type,applicable **/\n\n/** ITEMTAGS **** name,type,applicable **/\n\n"
						+ "/** ENTITYTAGS **** name,type,applicable **/\n\n/** GENERATEDTAGS **** name,type **/\n\n/** END **/");
				writer.close();
			} catch (Exception e)
			{}
		}
	}

}
