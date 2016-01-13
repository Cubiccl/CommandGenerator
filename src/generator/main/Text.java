package generator.main;

import generator.CommandGenerator;
import generator.interfaces.ITranslate;

import java.util.ArrayList;

/** Represents a translatable String text. */
public class Text implements ITranslate
{
	public class Replacement
	{
		private String id;
		private Text value;

		public Replacement(String id, Text value)
		{
			this.id = id;
			this.value = value;
		}

		public String replace(String translation)
		{
			System.out.println(translation.replaceAll(this.id, this.value.getValue()));
			return translation.replaceAll(this.id, this.value.getValue());
		}
	}

	private String category, id;
	private ArrayList<Replacement> replacements;
	private Text suffix;
	private boolean translated;
	private String translation;

	/** Creates a non-translated text.
	 * 
	 * @param value - Its value. */
	public Text(String value)
	{
		this(null, value, false, false);
	}

	/** Creates a translated text.
	 * 
	 * @param category - The category of the translation.
	 * @param id - The ID of the text. */
	public Text(String category, String id)
	{
		this(category, id, true);
	}

	/** Creates a translated text.
	 * 
	 * @param category - The category of the translation.
	 * @param id - The ID of the text.
	 * @param track - True if it should be added to the Translator tracker, and thus be translated when needed. */
	public Text(String category, String id, boolean track)
	{
		this(category, id, true, true);
	}

	/** @param category - The category of the translation.
	 * @param id - The ID of the text.
	 * @param translated - True if it should translate. If not, only id will be used.
	 * @param track - True if it should be added to the Translator tracker, and thus be translated when needed. */
	private Text(String category, String id, boolean translated, boolean track)
	{
		this.category = category;
		this.id = id;
		this.translated = translated;
		this.replacements = new ArrayList<Replacement>();
		this.updateLang();
		if (track) CommandGenerator.registerText(this);
	}

	/** @param id - The text to look for.
	 * @param text - What to replace it with.
	 * @return this. */
	public Text addReplacement(String id, String text)
	{
		return this.addReplacement(id, new Text(text));
	}

	/** @param id - The text to look for.
	 * @param text - What to replace it with.
	 * @return this. */
	public Text addReplacement(String id, Text text)
	{
		this.replacements.add(new Replacement(id, text));
		this.updateLang();
		return this;
	}

	/** Adds a text that will be added behind this text.
	 * 
	 * @param suffix - The input suffix.
	 * @return this. */
	public Text addSuffix(Text suffix)
	{
		this.suffix = suffix;
		this.updateLang();
		return this;
	}

	public String getCategory()
	{
		return this.category;
	}

	public String getId()
	{
		return this.id;
	}

	public String getValue()
	{
		return this.translation;
	}

	@Override
	public String toString()
	{
		if (this.translated) return this.category + ":" + this.id;
		else return this.id;
	}

	@Override
	public void updateLang()
	{
		if (this.translated)
		{
			this.translation = CommandGenerator.translate(this);
			for (Replacement replacement : replacements)
			{
				this.translation = replacement.replace(this.translation);
			}
			if (this.suffix != null) this.translation += " " + this.suffix.getValue();
		} else this.translation = this.id;
	}
}
