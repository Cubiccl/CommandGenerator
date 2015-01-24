package commandGenerator.main;

public class Resources
{
	/** The Minecraft version this program is for. */
	public static String versionMinecraft;

	/** The list of all versions this program has had. */
	public static String[] versions, changelog;

	public static String folder;

	/** The list of all structures for each command. */
	public static String[] structureList = {
			"/achievement <give|take> <achievement> <player>",
			"/blockdata <x> <y> <z> <dataTag>",
			"/clear <player> [item] [data] [maxCount] [dataTag]",
			"/clone <x1> <y1> <z1> <x2> <y2> <z2> <x> <y> <z> [mode] [mode2]",
			"/effect <player> <effect> [seconds] [amplifier] [hideParticles]",
			"/enchant <player> <enchantment> [level]",
			"/entitydata <entity> <dataTag>",
			"/execute <entity> <x> <y> <z> <command ...>\n/execute <entity> <x> <y> <z> detect <x2> <y2> <z2> <block> <data> <command...>",
			"/fill <x1> <y1> <z1> <x2> <y2> <z2> <TileName> [dataValue] [oldBlockHandling] [dataTag]\n/fill <x1> <y1> <z1> <x2> <y2> <z2> <TileName> [dataValue]"
					+ " replace [replaceTileName] [replaceDataValue]", "/gamerule <gamerule> <true|false|value>",
			"/give <player> <item> [amount] [data] [dataTag]", "/kill <entity>", "/particle <name> <x> <y> <z> <dx> <dy> <dz> <speed> [count] [entity]",
			"/playsound <sound> <player> [x] [y] [z] [volume] [pitch] [minimumVolume]",
			"/replaceitem block [x] [y] [z] <slot> <item> [amount] [data] [dataTag]\n/replaceitem entity <selector> <slot> <item> [amount] [data] [dataTag]",
			"/scoreboard <objectives|players|teams> <options ...>", "/setblock <x> <y> <z> <TileName> [dataValue] [oldBlockHandling] [dataTag]",
			"/setworldspawn <x> <y> <z>", "/spawnpoint <player> [x] [y] [z]", "/spreadplayers <x> <z> <spreadDistance> <maxRange> <respectTeams> <player ...>",
			"/summon <EntityName> <x> <y> <z> [dataTag]", "/tellraw <player> <raw json message>", "/testfor <player> [dataTag]",
			"/testforblock <x> <y> <z> <TileName> [dataValue] [dataTag]", "/testforblocks <x1> <y1> <z1> <x2> <y2> <z2> <x> <y> <z> [mode]",
			"/time <set|add> <value>", "/title <player> <title|subtitle> <raw json title>\n/title <player> times <fadeIn> <stay> <fadeOut>",
			"/tp <target> <destinationEntity>\n/tp <target> <x> <y> <z> [x-rotation] [y-rotation]", "/weather <clear|rain|thunder> <duration>",
			"/worldborder <add|center|damage|set|warning> <options ...>", "/xp <amount>[L] <player>" };

	/** List of the colors used for displays. */
	public static String[] colors = { "black", "dark_blue", "dark_green", "dark_aqua", "dark_red", "dark_purple", "gold", "gray", "ark_gray", "blue", "green",
			"aqua", "red", "light_purple", "yellow", "white", "reset" };

	/** List of the display slots for the scoreboard. */
	public static String[] displayList = { "list", "sidebar", "belowName", "sidebar.team.black", "sidebar.team.dark_blue", "sidebar.team.dark_green",
			"sidebar.team.dark_aqua", "sidebar.team.dark_red", "sidebar.team.dark_purple", "sidebar.team.gold", "sidebar.team.gray", "sidebar.team.dark_gray",
			"sidebar.team.blue", "sidebar.team.green", "sidebar.team.aqua", "sidebar.team.red", "sidebar.team.light_purple", "sidebar.team.yellow",
			"sidebar.team.white" };

	/** Returns the changelog for the specified version. */
	public static String changelog(String version)
	{
		for (int i = 0; i < versions.length; i++)
		{
			if (version.equalsIgnoreCase(versions[i])) return changelog[i];
		}
		return changelog[changelog.length - 1];
	}

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
