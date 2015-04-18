package commandGenerator.main;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import commandGenerator.gui.helper.components.panel.SettingsPanel;

public class Settings
{
	public static final String IDS = "id", NAMES = "name";

	public static List<String[]> languages;

	private static String getLangFromName(String langName)
	{
		for (int i = 0; i < languages.size(); i++)
			if (languages.get(i)[0].equalsIgnoreCase(langName)) return languages.get(i)[1];
		return null;
	}

	/** Is it the first launch? */
	public boolean firstLaunch;
	/** The language selected by the user. */
	private String language;

	/** The previous version the program was launched with. */
	public String previousVersion;

	/** How the Objects should be sorted */
	public String sortType;

	/** Creates new Settings. */
	public Settings()
	{
		language = FileHelper.getOption("lang");
		previousVersion = FileHelper.getOption("version");
		sortType = FileHelper.getOption("sortType");

		firstLaunch = !previousVersion.equals(Resources.versions[Resources.versions.length - 1]);
		createLangs();
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

	public void change()
	{
		SettingsPanel panel = new SettingsPanel(this);
		DisplayHelper.showMessage(panel, Lang.get("GUI:menu.settings"));

		String newLang = getLangFromName(panel.getLang());
		if (!language.equalsIgnoreCase(newLang)) setLanguage(newLang);
		FileHelper.setOption("lang", newLang);
		
		FileHelper.changePath(panel.getFolder());
		
		String newSort = panel.getSortType();
		if (!this.sortType.equals(newSort)) this.sortType = newSort;
		FileHelper.setOption("sortType", newSort);
		
		Lang.updateLang();
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

	public String[] getLangs()
	{
		String[] langs = new String[languages.size()];
		for (int i = 0; i < langs.length; i++)
			langs[i] = languages.get(i)[0];
		return langs;
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

	public static String getDefaultOption(String id)
	{
		if (id.equals("lang")) return "en_us";
		if (id.equals("version")) return Resources.versions[Resources.versions.length - 1];
		if (id.equals("sortType")) return "id";
		if (id.equals("key_gen")) return String.valueOf(KeyEvent.VK_F1);
		if (id.equals("key_copy")) return String.valueOf(KeyEvent.VK_F2);
		return null;
	}

	public String getSortType()
	{
		return this.sortType;
	}

}
