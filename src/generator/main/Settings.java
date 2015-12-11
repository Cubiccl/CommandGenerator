package generator.main;

import generator.CommandGenerator;

public class Settings
{
	public static final String LANGUAGE = "language";
	private String[][] availableLanguages;

	private String folder;
	private int language;

	public Settings(String folder)
	{
		this.folder = folder;
	}

	public String[][] getAvailableLanguages()
	{
		return this.availableLanguages;
	}

	public String getFolder()
	{
		return this.folder;
	}

	public int getLanguage()
	{
		return this.language;
	}

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
	{}

	/** Changes the given setting.
	 * 
	 * @param id - The ID of the setting to change.
	 * @param value - The new value for the setting. */
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
