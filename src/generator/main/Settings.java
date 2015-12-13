package generator.main;

import generator.CommandGenerator;

/** Contains settings customizable by the user. */
public class Settings
{
	/** Settings IDs.
	 * <ul>
	 * <li>LANGUAGE = "language";</li>
	 * </ul> */
	public static final String LANGUAGE = "language";

	/** Contains all available languages. <br/>
	 * [[id, name], [id, name], ...] */
	private String[][] availableLanguages;
	/** The path to the resources folder. */
	private String folder;
	/** The current selected language. */
	private int language;

	/** @param folder - The path to the resources folder. */
	public Settings(String folder)
	{
		this.folder = folder;
	}

	/** @return The available languages. */
	public String[][] getAvailableLanguages()
	{
		return this.availableLanguages;
	}

	/** @return The path to the resources folder. */
	public String getFolder()
	{
		return this.folder;
	}

	/** @return The current selected language. */
	public int getLanguage()
	{
		return this.language;
	}

	/** @return The ID of the current selected language. */
	public String getLanguageId()
	{
		return this.getAvailableLanguages()[this.getLanguage()][0];
	}

	/** Call this after constructed to avoid folder() conflicts. */
	public void init()
	{
		this.setDefaultSettings();
		String[] data = FileManager.readFileAsArray("settings.cmg");
		for (String setting : data)
		{
			String[] values = setting.split(" = ");
			this.setSetting(values[0], values[1]);
		}

		String[] langs = FileManager.readFileAsArray("lang/langs.cmg");
		this.availableLanguages = new String[langs.length][2];
		for (int i = 0; i < langs.length; i++)
		{
			this.availableLanguages[i] = langs[i].split(" ; ");
		}
	}

	/** Resets all settings to default. */
	private void setDefaultSettings()
	{
		this.language = 0;
	}

	/** Changes the given setting.
	 * 
	 * @param id - The ID of the setting to change.
	 * @param value - The new value for the setting.
	 * @see Settings#LANGUAGE */
	public void setSetting(String id, String value)
	{
		switch (id)
		{
			case LANGUAGE:
				for (int i = 0; i < this.getAvailableLanguages().length; i++)
				{
					if (this.getAvailableLanguages()[i][0].equals(value)) this.language = i;
				}
				CommandGenerator.updateLang();
				break;

			default:
				break;
		}
	}
}
