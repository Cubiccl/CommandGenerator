package commandGenerator.main;

import java.util.List;

import commandGenerator.arguments.objects.Command;

public class Settings
{

	public static final int ENGLISH = 0, ENGLISH_GB = 1;
	public static final String[] languages = { "english", "english_gb" };

	/** The previous version the program was launched with. */
	public String previousVersion;
	/** Is it the first launch? */
	public boolean firstLaunch;
	/** The language selected by the user. */
	private int language;
	/** The selected command. */
	public Command selectedCommand;
	/** All saved objects */
	public List<String> savedObjects;

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

	/** Creates new Settings. */
	public Settings()
	{
		selectedCommand = Command.achievement;
		language = Integer.parseInt(FileHelper.getOption("lang"));
		previousVersion = FileHelper.getOption("version");
		firstLaunch = !previousVersion.equals(Resources.versions[Resources.versions.length - 1]);
		savedObjects = FileHelper.readFileArray("savedObjects.txt");
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

}
