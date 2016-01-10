package generator.main;

import generator.CommandGenerator;
import generator.interfaces.ITranslate;

import java.util.ArrayList;
import java.util.HashMap;

/** Contains translations and methods to translate. */
public class Translator implements ITranslate
{
	/** Contains translations, sorted by categories. */
	private HashMap<String, HashMap<String, String>> dictionnary;
	/** List of the Texts the Translator is tracking. */
	private ArrayList<Text> tracking;

	public Translator()
	{
		this.dictionnary = new HashMap<String, HashMap<String, String>>();
		this.tracking = new ArrayList<Text>();
	}

	/** Adds a new Text to track.
	 * 
	 * @param text - The input text. */
	public void registerText(Text text)
	{
		this.tracking.add(text);
	}

	/** Updates all texts the Translator is tracking. */
	public void reloadTexts()
	{
		for (Text text : this.tracking)
		{
			text.updateLang();
		}
	}

	/** Translates a Text.
	 * 
	 * @param text - The input Text.
	 * @return The translation. */
	public String translate(Text text)
	{
		if (!dictionnary.containsKey(text.getCategory()))
		{
			CommandGenerator.log(text.getCategory() + " is not a valid category.");
			return text.getCategory() + ":" + text.getId();
		}

		if (!dictionnary.get(text.getCategory()).containsKey(text.getId()))
		{
			CommandGenerator.log("Missing translation : " + text.getCategory() + ":" + text.getId());
			return text.getCategory() + ":" + text.getId();
		}

		return dictionnary.get(text.getCategory()).get(text.getId());
	}

	/** Updates translations. */
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
			this.dictionnary.get(currentCategory).put(line.substring(0, index), line.substring(index + 3));
		}
		this.reloadTexts();
	}

}
