package commandGenerator.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import commandGenerator.arguments.command.Command;
import commandGenerator.gui.helper.components.panel.SettingsPanel;

public class Settings
{

	public static List<String[]> languages;

	/** Is it the first launch? */
	public boolean firstLaunch;
	/** The language selected by the user. */
	private String language;
	/** The previous version the program was launched with. */
	public String previousVersion;
	/** The selected command. */
	public Command selectedCommand;

	/** Creates new Settings. */
	public Settings()
	{
		selectedCommand = Command.achievement;
		language = FileHelper.getOption("lang");
		previousVersion = FileHelper.getOption("version");
		firstLaunch = !previousVersion.equals(Resources.versions[Resources.versions.length - 1]);
		createLangs();
	}

	private void createLangs()
	{
		languages = new ArrayList<String[]>();
		try
		{
			Scanner sc = new Scanner(new File(Resources.folder + "downloads/langs.txt"));
			while (sc.hasNextLine())
				languages.add(sc.nextLine().split(","));
			sc.close();

		} catch (Exception e)
		{}
		if (!languages.contains(language) && languages.size() > 0) language = languages.get(0)[1];
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
	public String getLanguage()
	{
		return language;
	}

	/** Changes the selected languages. Updates the translations.
	 * 
	 * @param language
	 *            - <i>String</i> - The language to use. */
	public void setLanguage(String language)
	{
		this.language = language;
		FileHelper.setOption("lang", language);
		Lang.updateLang();
	}

	public void change()
	{
		SettingsPanel panel = new SettingsPanel();
		DisplayHelper.showMessage(panel, Lang.get("GUI:menu.settings"));
		
		String newLang = getLangFromName(panel.getLang());
		if (!language.equalsIgnoreCase(newLang)) setLanguage(newLang);
		FileHelper.setOption("lang", newLang);
		FileHelper.changePath(panel.getFolder());
	}

	private static String getLangFromName(String langName)
	{
		for (int i = 0; i < languages.size(); i++) if (languages.get(i)[0].equalsIgnoreCase(langName)) return languages.get(i)[1];
		return null;
	}

	public String[] getLangs()
	{
		String[] langs = new String[languages.size()];
		for (int i = 0; i < langs.length; i++)
			langs[i] = languages.get(i)[0];
		return langs;
	}

}
