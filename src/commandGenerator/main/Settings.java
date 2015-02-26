package commandGenerator.main;

import java.util.Scanner;

import commandGenerator.arguments.objects.Command;
import commandGenerator.gui.SettingsPanel;

public class Settings
{

	public static final String[] languages = { "english" };
	public static final String[] languageNames = { "English (US)" };

	/** Is it the first launch? */
	public boolean firstLaunch;
	/** The language selected by the user. */
	private int language;
	/** The previous version the program was launched with. */
	public String previousVersion;
	/** The selected command. */
	public Command selectedCommand;
	/** The path to the save folder */
	private String folder;

	/** Creates new Settings. */
	public Settings()
	{
		selectedCommand = Command.achievement;
		language = Integer.parseInt(FileHelper.getOption("lang"));
		previousVersion = FileHelper.getOption("version");
		firstLaunch = !previousVersion.equals(Resources.versions[Resources.versions.length - 1]);
	}

	/** Returns true if the version has already been updated.
	 * 
	 * @param version
	 *            - <i>String</i> - The version to test for. */
	public boolean alreadyUpdated(String version)
	{
		int indexPrev = 0, indexVersion = 0;
		for (int i = 0; i < Resources.versions.length; i++)
		{
			if (Resources.versions[i].equals(version)) indexVersion = i;
			if (Resources.versions[i].equals(previousVersion)) indexPrev = i;
		}
		return indexPrev >= indexVersion;
	}

	/** Returns the selected language. */
	public int getLanguage()
	{
		return language;
	}

	/** Changes the selected languages. Updates the translations.
	 * 
	 * @param language
	 *            - <i>int</i> - The language to use. */
	public void setLanguage(int language)
	{
		this.language = language;
		FileHelper.setOption("lang", Integer.toString(language));
		Lang.updateLang();
	}

	public void update(SettingsPanel panel)
	{
		setLanguage(panel.getLanguage());
	}

}
