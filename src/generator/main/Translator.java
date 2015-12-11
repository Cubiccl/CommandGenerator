package generator.main;

import generator.CommandGenerator;
import generator.interfaces.ITranslate;

import java.util.HashMap;

public class Translator implements ITranslate
{
	/** Contains translations, sorted by categories. */
	private HashMap<String, HashMap<String, String>> dictionnary;

	public Translator()
	{
		this.dictionnary = new HashMap<String, HashMap<String, String>>();
		this.updateDictionnary();
	}

	public String translate(String textId)
	{
		String[] values = textId.split(":");
		if (values.length < 2)
		{
			CommandGenerator.log(new Exception(textId + " does not contain a category."));
			return textId;
		}
		return translate(values[0], values[1]);
	}

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

	private void updateDictionnary()
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

			int index = line.indexOf('=');
			if (currentCategory.equals(""))
			{
				CommandGenerator.log(new Exception("Declare category!"));
				continue;
			}
			this.dictionnary.get(currentCategory).put(line.substring(0, index - 1), line.substring(index + 1));
		}
	}

	@Override
	public void updateLang()
	{
		this.updateDictionnary();
	}

}
