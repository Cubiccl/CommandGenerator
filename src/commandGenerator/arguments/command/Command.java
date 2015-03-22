package commandGenerator.arguments.command;

import java.util.HashMap;
import java.util.Map;

import commandGenerator.arguments.objects.Registry;
import commandGenerator.gui.helper.components.OptionsTab;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Generator;
import commandGenerator.main.Lang;

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

	/** The Command's ID. */
	private String id, structure;

	private Structure[] structures;

	/** Creates a new Command
	 * 
	 * @param id
	 *            - <i>String</i> - The Command's ID */
	private Command(String id, String structure, Structure... structures)
	{
		this.id = id;
		this.structure = structure;
		this.structures = structures;
		Registry.registerCommand(this);
	}

	/** Generates the data used for the GUI from an already generated command
	 * 
	 * @param command
	 *            - <i>String</i> - The command to use to generate all data */
	public Map<String, Object> generateSetup(String command)
	{
		if (id.equals("achievement")) return Generator.genAchievement(command);
		if (id.equals("blockdata")) return Generator.genBlockData(command);
		if (id.equals("clear")) return Generator.genClear(command);
		if (id.equals("clone")) return Generator.genClone(command);
		if (id.equals("effect")) return Generator.genEffect(command);
		if (id.equals("enchant")) return Generator.genEnchant(command);
		if (id.equals("entitydata")) return Generator.genEntityData(command);
		if (id.equals("execute")) return Generator.genExecute(command);
		if (id.equals("fill")) return Generator.genFill(command);
		if (id.equals("gamerule")) return Generator.genGamerule(command);
		if (id.equals("give")) return Generator.genGive(command);
		if (id.equals("kill")) return Generator.genKill(command);
		if (id.equals("particle")) return Generator.genParticle(command);
		if (id.equals("playsound")) return Generator.genPlaysound(command);
		if (id.equals("replaceitem")) return Generator.genReplaceitem(command);
		if (id.equals("scoreboard")) return Generator.genScoreboard(command);
		if (id.equals("setblock")) return Generator.genSetblock(command);
		if (id.equals("setworldspawn")) return Generator.genSetworldspawn(command);
		if (id.equals("spawnpoint")) return Generator.genSpawnpoint(command);
		if (id.equals("spreadplayers")) return Generator.genSpreadplayers(command);
		if (id.equals("stats")) return Generator.genStats(command);
		if (id.equals("summon")) return Generator.genSummon(command);
		if (id.equals("tellraw")) return Generator.genTellraw(command);
		if (id.equals("testfor")) return Generator.genEntityData(command);
		if (id.equals("testforblock")) return Generator.genTestforblock(command);
		if (id.equals("testforblocks")) return Generator.genTestforblocks(command);
		if (id.equals("time")) return Generator.genTime(command);
		if (id.equals("title")) return Generator.genTitle(command);
		if (id.equals("tp")) return Generator.genTp(command);
		if (id.equals("trigger")) return Generator.genTrigger(command);
		if (id.equals("weather")) return Generator.genWeather(command);
		if (id.equals("worldborder")) return Generator.genWorldborder(command);
		if (id.equals("xp")) return Generator.genXp(command);

		DisplayHelper.log("No generation setup was found for command /" + id);
		return new HashMap<String, Object>();
	}

	/** Returns the Command's description */
	public String getDescription()
	{
		return Lang.get("HELP:command." + id);
	}

	/** Returns the Command's ID */
	public String getId()
	{
		return id;
	}

	/** Returns the Panel to use to display the Command's options. */
	public OptionsTab generateOptionsTab()
	{
		return new OptionsTab(structures);
	}

	public String generate(OptionsTab tabOptions)
	{
		String command = this.structures[tabOptions.getSelectedIndex()].generateCommand(tabOptions.getPanel());
		if (command == null) return null;
		return this.getId() + command;
	}

	public String getStructure()
	{
		return this.structure;
	}

}
