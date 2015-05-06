package commandGenerator.main;

import java.util.Map;

import commandGenerator.Generator;

public class Lang
{

	public Lang()
	{
		this.initLang();
	}

	private void initLang()
	{
		this.dictGui = FileHelper.readLanguageFile(Generator.opt.getLanguage() + "_gui", guiCategories);
		this.dictObjects = FileHelper.readLanguageFile(Generator.opt.getLanguage() + "_objects", objectCategories);
	}

	/** Contains all translations. */
	private Map<String, Map<String, String>> dictObjects, dictGui;
	/** Language categories. */
	private static final String[] objectCategories = { "ITEMS", "ENTITIES", "ACHIEVEMENTS", "ATTRIBUTES", "EFFECTS", "ENCHANTS", "PARTICLES", "SOUNDS", "TAGS",
			"END" }, guiCategories = { "GUI", "GENERAL", "WARNING", "HELP", "RESOURCES", "END" };

	/** Returns true if the translation exists.
	 * 
	 * @param dict
	 *            - <i>Map:String->Map:String->String</i> - The dictionary to use.
	 * @param id
	 *            - <i>String</i> - The ID of the translation to look for. */
	private boolean exists(Map<String, Map<String, String>> dict, String id)
	{
		return dict.containsKey(id.split(":")[0]) && dict.get(id.split(":")[0]).containsKey(id.split(":")[1]);
	}

	/** Returns the translation of the String ID.
	 * 
	 * @param id
	 *            - <i>String</i> - The ID of the translation to get. */
	public String get(String id)
	{
		if (id.equals("")) return id;
		String[] details = id.split(":");
		if (details.length < 2)
		{
			DisplayHelper.log("Wrong translation id : " + id);
			return id;
		}
		Map<String, Map<String, String>> dict = this.dictGui;
		for (String cat : objectCategories)
		{
			if (cat.equals(details[0]))
			{
				dict = this.dictObjects;
				break;
			}
		}
		if (!this.exists(dict, id))
		{
			DisplayHelper.log("Missing translation : " + details[0] + " / " + details[1]);
			return details[1];
		}
		return dict.get(details[0]).get(details[1]).replaceAll("N/L", "\n");
	}

	/** Updates the language dictionaries. */
	public void updateLang()
	{
		this.initLang();
		Generator.registry.registerNames();
		Generator.gui.updateLang();
	}

}
