package commandGenerator.arguments.command;

import commandGenerator.Generator;
import commandGenerator.gui.helper.components.OptionsTab;
import commandGenerator.main.DisplayHelper;

public enum Command
{

	achievement("achievement", "/achievement <give|take> <stat_name|*> <player>", Structure.achievementOne, Structure.achievementAll),
	blockdata("blockdata", "/blockdata <x> <y> <z> <dataTag>", Structure.blockdata),
	clear("clear", "/clear <player> [item] [data] [maxCount] [dataTag]", Structure.clear),
	clone("clone", "/clone <x1> <y1> <z1> <x2> <y2> <z2> <x> <y> <z> [maskMode] [cloneMode] [TileName]", Structure.clone, Structure.cloneFiltered),
	effect("effect", "/effect <target> clear\n/effect <player> <effect> [seconds] [amplifier] [hideParticles]", Structure.effectGive, Structure.effectClear),
	enchant("enchant", "/enchant <player> <enchantment ID> [level]", Structure.enchant),
	entitydata("entitydata", "/entitydata <target> <dataTag>", Structure.entitydata),
	execute("execute", "/execute <target> <x> <y> <z> <command ...>\n/execute <target> <x> <y> <z> detect <x2> <y2> <z2> <block> <data> <command ...>",
			Structure.executeNormal, Structure.executeDetect),
	fill("fill", "/fill <x1> <y1> <z1> <x2> <y2> <z2> <TileName> [dataValue] [oldBlockHandling] [dataTag]\n"
			+ "/fill <x1> <y1> <z1> <x2> <y2> <z2> <TileName> <dataValue> replace [replaceTileName] [replaceDataValue]", Structure.fillNormal,
			Structure.fillReplace),
	gamerule("gamerule", "gamerule <rule name> <value>", Structure.gameruleNormal, Structure.gameruleTicks),
	give("give", "/give <player> <item> [amount] [data] [dataTag]", Structure.give),
	kill("kill", "/kill <target>", Structure.kill),
	particle("particle", "/particle <name> <x> <y> <z> <xd> <yd> <zd> <speed> [count] [mode]", Structure.particle),
	playsound("playsound", "/playsound <sound> <player> [x] [y] [z] [volume] [pitch] [minimumVolume]", Structure.playsound),
	replaceitem("replaceitem", "/replaceitem block <x> <y> <z> <slot> <item> [amount] [data] [dataTag]\n"
			+ "/replaceitem entity <selector> <slot> <item> [amount] [data] [dataTag]", Structure.replaceitemBlock, Structure.replaceitemEntity),
	scoreboardObjectives("scoreboard objectives", "scoreboard objectives <add|remove|setdisplay> <details>", Structure.scoreObjectiveAdd,
			Structure.scoreObjectiveRemove, Structure.scoreObjectiveDisplay),
	scoreboardPlayers("scoreboard players", "scoreboard players <add|enable|operation|remove|reset|set|test> <details>", Structure.scorePlayerSet,
			Structure.scorePlayerEnable, Structure.scorePlayerReset, Structure.scorePlayerTest, Structure.scorePlayerOperation),
	scoreboardTeams("scoreboard teams", "scoreboard teams <add|empty|join|leave|option|remove> <details>", Structure.scoreTeamAdd, Structure.scoreTeamDelete,
			Structure.scoreTeamManage, Structure.scoreTeamOption),
	setblock("setblock", "/setblock <x> <y> <z> <TileName> [dataValue] [oldBlockHandling] [dataTag]", Structure.setblock),
	setworldspawn("setworldspawn", "/setworldspawn <x> <y> <z>", Structure.setworldspawn),
	spawnpoint("spawnpoint", "/spawnpoint <player> <x> <y> <z>", Structure.spawnpoint),
	spreadplayers("spreadplayers", "/spreadplayers <x> <z> <spreadDistance> <maxRange> <respectTeams> <player>", Structure.spreadplayers),
	stats("stats", "/stats block <x> <y> <z> clear <stat>\n/stats block <x> <y> <z> set <stat> <selector> <objective>\n"
			+ "/stats entity <selector2> clear <stat>\n/stats entity <selector2> set <stat> <selector> <objective>", Structure.statsClearBlock,
			Structure.statsClearEntity, Structure.statsSetBlock, Structure.statsSetEntity),
	summon("summon", "/summon <EntityName> [x] [y] [z] [dataTag]", Structure.summon),
	tellraw("tellraw", "/tellraw <player> <raw json message>", Structure.tellraw),
	testfor("testfor", "/testfor <target> [dataTag]", Structure.testfor),
	testforblock("testforblock", "testforblock <x> <y> <z> <TileName> [dataValue] [dataTag]", Structure.testforblock),
	testforblocks("testforblocks", "/testforblocks <x1> <y1> <z1> <x2> <y2> <z2> <x> <y> <z> <mode>", Structure.testforblocks),
	time("time", "/time <add|query|set> <value>", Structure.timeSet, Structure.timeQuery),
	title("title", "/title <player> <clear|reset>\n/title <player> <subtitle|title> <raw json title>\n/title <player> times <fadeIn> <stay> <fadeOut>",
			Structure.titleDisplay, Structure.titleOptions, Structure.titleReset),
	tp("tp", "/tp <player> <destination player>\n/tp <target> <x> <y> <z> [<y-rot> <x-rot>]", Structure.tpcoord, Structure.tpentity),
	trigger("trigger", "/trigger <objective> <add|set> <value>", Structure.trigger),
	weather("weather", "/weather <clear|rain|thunder> [duration]", Structure.weather),
	worldborder("worldborder", "/worldborder <add|set> <sizeInBlocks> [timeInSeconds]\n/worldborder center <x> <y>\n"
			+ "/worldborder damage <amount|buffer> <value>\n/worldborder warning <distance|time> <value>", Structure.worldborderSet,
			Structure.worldborderCenter, Structure.worldborderDamage, Structure.worldborderWarning),
	xp("xp", "xp <amount>[L] [player]", Structure.xp);

	/** Returns the command structure that matches the given command.
	 * 
	 * @param command
	 *            - <i>String</i> - The command to match. */
	public static Command identify(String command)
	{
		for (Command c : Generator.registry.getCommands())
			if (command.startsWith(c.getId())) return c;
		DisplayHelper.showWarning("WARNING:command.wrong_id");
		return null;
	}

	/** Initializes the GUI to be displayed. */
	public static void initGui()
	{
		for (Command c : Generator.registry.getCommands())
			c.tab = new OptionsTab(c.structures);
	}

	/** The Command's ID. */
	private String id;

	/** The Command's structure to display. */
	private String structure;

	/** The list of structures this command has. */
	private Structure[] structures;

	/** The Options Tab for this command. */
	private OptionsTab tab;

	/** Creates a new Command
	 * 
	 * @param id
	 *            - <i>String</i> - The Command's ID.
	 * @param structure
	 *            - <i>String</i> - The Command's structure to display.
	 * @param structures
	 *            - <i>Structure[]</i> - The list of structures this command has. */
	private Command(String id, String structure, Structure... structures)
	{
		this.id = id;
		this.structure = structure;
		this.structures = structures;
		Generator.registry.registerCommand(this);
	}

	/** Returns the index of the Structure matching the given arguments. Returns -1 if no Structure was found.
	 * 
	 * @param arguments
	 *            - <i>String[]</i> - The arguments to match. */
	private int findMatchingStructure(String[] arguments)
	{
		for (int i = 0; i < this.structures.length; i++)
			if (this.structures[i].matches(arguments)) return i;
		
		DisplayHelper.showWarning("WARNING:command.structure");
		return -1;
	}

	/** Generates the command. */
	public String generate()
	{
		String command = this.getSelectedStructure().generateCommand();
		if (command == null) return null;
		return this.getId() + " " + command;
	}

	/** Generates the data used for the GUI from an already generated command.
	 * 
	 * @param command
	 *            - <i>String</i> - The command to use to generate all data. */
	public void generateFrom(String command)
	{
		String[] arguments = DisplayHelper.splitCommand(command.substring(this.getId().length() + 1));
		int matchingStructure = this.findMatchingStructure(arguments);
		if (matchingStructure == -1) return;
		this.tab.setSelectedIndex(matchingStructure);
		this.getSelectedStructure().setupFrom(arguments);
	}

	/** Returns the Command's description. */
	public String getDescription()
	{
		return Generator.translate("HELP:command." + id);
	}

	/** Returns the Command's ID. */
	public String getId()
	{
		return this.id;
	}

	/** Returns the Panel to use to display the Command's options. */
	public OptionsTab getOptionsTab()
	{
		return this.tab;
	}

	/** Returns the selected Structure. */
	private Structure getSelectedStructure()
	{
		return this.structures[this.tab.getSelectedIndex()];
	}

	/** Returns the structure to display to the user. */
	public String getStructure()
	{
		return this.structure;
	}

	public void reset()
	{
		this.getSelectedStructure().reset();
	}

}
