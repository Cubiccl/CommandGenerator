package commandGenerator.main;

public class Resources
{
	public static final String[] operations = { "+=", "-=", "*=", "/=", "%=", "=", "<", ">", "><" };

	public static final String[] stats = { "AffectedBlocks", "AffectedEntities", "AffectedItems", "QueryResult", "SuccessCount" };

	public static final String[] gamerules = { "commandBlockOutput", "doDaylightCycle", "doFireTick", "doMobLoot", "doMobSpawning", "doTileDrops",
			"keepInventory", "mobGriefing", "naturalRegeneration", "logAdminCommands", "sendCommandFeedback", "showDeathMessages" };

	/** List of the colors used for displays. */
	public static String[] colors = { "black", "dark_blue", "dark_green", "dark_aqua", "dark_red", "dark_purple", "gold", "gray", "ark_gray", "blue", "green",
			"aqua", "red", "light_purple", "yellow", "white", "reset" };

	/** List of the display slots for the scoreboard. */
	public static String[] displayList = { "list", "sidebar", "belowName", "sidebar.team.black", "sidebar.team.dark_blue", "sidebar.team.dark_green",
			"sidebar.team.dark_aqua", "sidebar.team.dark_red", "sidebar.team.dark_purple", "sidebar.team.gold", "sidebar.team.gray", "sidebar.team.dark_gray",
			"sidebar.team.blue", "sidebar.team.green", "sidebar.team.aqua", "sidebar.team.red", "sidebar.team.light_purple", "sidebar.team.yellow",
			"sidebar.team.white" };

	/** The URL to the Command Generator resource folder. */
	public static String folder;

	/** The Minecraft version this program is for. */
	public static String versionMinecraft;

	/** The list of all versions this program has had. */
	public static String[] versions, changelog;

	/** Returns the changelog for the specified version.
	 * 
	 * @param version
	 *            - <i>String</i> - The version to use. */
	public static String changelog(String version)
	{
		for (int i = 0; i < versions.length; i++)
		{
			if (version.equalsIgnoreCase(versions[i])) return changelog[i];
		}
		return changelog[changelog.length - 1];
	}

	/** Creates all versions. */
	public static void setupVersions()
	{
		String[] data = FileHelper.readFileArray("version.txt").toArray(new String[0]);
		versionMinecraft = data[0].split(" ; ")[1];

		versions = new String[data.length - 4];
		changelog = new String[data.length - 4];

		for (int i = 4; i < data.length; i++)
		{
			versions[i - 4] = data[i].split(" = ")[0];
			changelog[i - 4] = data[i].split(" = ")[1];
		}
		DisplayHelper.log("Command Generator v" + versions[versions.length - 1] + " loading...");
	}

}
