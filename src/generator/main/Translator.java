package generator.main;

import generator.CommandGenerator;
import generator.interfaces.ITranslate;

import java.util.HashMap;

/** Contains translations and methods to translate. */
public class Translator implements ITranslate
{
	/** Contains translations, sorted by categories. */
	private HashMap<String, HashMap<String, String>> dictionnary;

	public Translator()
	{
		this.dictionnary = new HashMap<String, HashMap<String, String>>();
		this.updateLang();
	}

	/** @param textID - The ID for the text to translate.<br>
	 *            <strong>CATEGORY:id.of.the.text</strong>
	 * @return The translation. */
	public String translate(String textID)
	{
		String[] values = textID.split(":");
		if (values.length < 2)
		{
			CommandGenerator.log(new Exception(textID + " does not contain a category."));
			return textID;
		}
		return translate(values[0], values[1]);
	}

	/** @param category - The category of the ID.
	 * @param key - The key of the ID.
	 * @return The translation. */
	public String translate(String category, String key)
	{
		if (!dictionnary.containsKey(category))
		{
			CommandGenerator.log(category + " is not a valid category.");
			return category + ":" + key;
		}

		if (!dictionnary.get(category).containsKey(key))
		{
			CommandGenerator.log("Missing translation : " + category + ":" + key);
			return category + ":" + key;
		}

		return dictionnary.get(category).get(key);
	}

	/** Upates translations. */
	@Override
	public void updateLang()
	{
		this.dictionnary.clear();
		String currentCategory = "";
		String[] data = FileManager.readFileAsArray("lang/" + CommandGenerator.getSettings().getLanguageId() + ".cmg");
		for (String line : data)
		{
			if (line.startsWith("CATEGORY="))
			{
				currentCategory = line.substring("CATEGORY=".length());
				this.dictionnary.put(currentCategory, new HashMap<String, String>());
				continue;
			}

			if (!line.contains(" = "))
			{
				CommandGenerator.log(new Exception("Wrong translation !! " + line));
				continue;
			}

			int index = line.indexOf(" = ");
			if (currentCategory.equals(""))
			{
				CommandGenerator.log(new Exception("Declare category!"));
				continue;
			}
			this.dictionnary.get(currentCategory).put(line.substring(0, index), line.substring(index + 2));
		}
	}

}
