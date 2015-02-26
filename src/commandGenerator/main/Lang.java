package commandGenerator.main;

import java.util.Map;

import commandGenerator.CommandGenerator;

public class Lang
{

	/** Contains all translations. */
	private static Map<String, Map<String, String>> dictObjects, dictGui;
	/** Language categories. */
	private static final String[] objectCategories = { "ITEMS", "ENTITIES", "ACHIEVEMENTS", "ATTRIBUTES", "EFFECTS", "ENCHANTS", "PARTICLES", "SOUNDS", "TAGS",
			"END" }, guiCategories = { "GUI", "GENERAL", "WARNING", "HELP", "RESOURCES", "END" };

	/** Returns true if the translation exists.
	 * 
	 * @param dict
	 *            - <i>Map:String->Map:String->String</i> - The dictionary to use.
	 * @param id
	 *            - <i>String</i> - The ID of the translation to look for. */
	private static boolean exists(Map<String, Map<String, String>> dict, String id)
	{
		return dict.containsKey(id.split(":")[0]) && dict.get(id.split(":")[0]).containsKey(id.split(":")[1]);
	}

	/** Returns the translation of the String ID.
	 * 
	 * @param id
	 *            - <i>String</i> - The ID of the translation to get. */
	public static String get(String id)
	{
		if (id.equals("")) return id;
		String[] details = id.split(":");
		if (details.length < 2)
		{
			DisplayHelper.log("Wrong translation id : " + id);
			return id;
		}
		Map<String, Map<String, String>> dict = dictGui;
		for (String cat : objectCategories)
		{
			if (cat.equals(details[0]))
			{
				dict = dictObjects;
				break;
			}
		}
		if (!exists(dict, id))
		{
			DisplayHelper.log("Missing translation : " + details[0] + " / " + details[1]);
			return details[1];
		}
		return dict.get(details[0]).get(details[1]).replaceAll("N/L", "\n");
	}

	/** Initializes the language dictionaries. */
	public static void initLang()
	{
		dictGui = FileHelper.readLanguageFile(CommandGenerator.opt.getLanguage() + "_gui", guiCategories);
		dictObjects = FileHelper.readLanguageFile(CommandGenerator.opt.getLanguage() + "_objects", objectCategories);
	}

	/** Updates the language dictionaries. */
	public static void updateLang()
	{
		initLang();
		CommandGenerator.gui.updateLang();
	}

}
