package commandGenerator.arguments.objects;

import java.util.HashMap;
import java.util.Map;

import commandGenerator.gui.helper.components.OptionsPanel;
import commandGenerator.gui.options.AchievementOptionsPanel;
import commandGenerator.gui.options.BlockdataOptionsPanel;
import commandGenerator.gui.options.ClearOptionsPanel;
import commandGenerator.gui.options.CloneOptionsPanel;
import commandGenerator.gui.options.EffectOptionsPanel;
import commandGenerator.gui.options.EnchantOptionsPanel;
import commandGenerator.gui.options.EntityDataOptionsPanel;
import commandGenerator.gui.options.ExecuteOptionsPanel;
import commandGenerator.gui.options.FillOptionsPanel;
import commandGenerator.gui.options.GameruleOptionsPanel;
import commandGenerator.gui.options.GiveOptionsPanel;
import commandGenerator.gui.options.KillOptionsPanel;
import commandGenerator.gui.options.ParticleOptionsPanel;
import commandGenerator.gui.options.PlaysoundOptionsPanel;
import commandGenerator.gui.options.ReplaceitemOptionsPanel;
import commandGenerator.gui.options.ScoreboardOptionsPanel;
import commandGenerator.gui.options.SetblockOptionsPanel;
import commandGenerator.gui.options.SpawnpointOptionsPanel;
import commandGenerator.gui.options.SpreadOptionsPanel;
import commandGenerator.gui.options.SummonOptionsPanel;
import commandGenerator.gui.options.TellrawOptionsPanel;
import commandGenerator.gui.options.TestforblockOptionsPanel;
import commandGenerator.gui.options.TestforblocksOptionsPanel;
import commandGenerator.gui.options.TimeOptionsPanel;
import commandGenerator.gui.options.TitleOptionsPanel;
import commandGenerator.gui.options.TpOptionsPanel;
import commandGenerator.gui.options.WeatherOptionsPanel;
import commandGenerator.gui.options.WorldborderOptionsPanel;
import commandGenerator.gui.options.WorldspawnOptionsPanel;
import commandGenerator.gui.options.XpOptionsPanel;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Generator;
import commandGenerator.main.Lang;

public enum Command
{

	achievement("achievement"),
	blockdata("blockdata"),
	clear("clear"),
	clone("clone"),
	effect("effect"),
	enchant("enchant"),
	entitydata("entitydata"),
	execute("execute"),
	fill("fill"),
	gamerule("gamerule"),
	give("give"),
	kill("kill"),
	particle("particle"),
	playsound("playsound"),
	replaceitem("replaceitem"),
	scoreboard("scoreboard"),
	setblock("setblock"),
	setworldspawn("setworldspawn"),
	spawnpoint("spawnpoint"),
	spreadplayers("spreadplayers"),
	summon("summon"),
	tellraw("tellraw"),
	testfor("testfor"),
	testforblock("testforblock"),
	testforblocks("testforblocks"),
	time("time"),
	title("title"),
	tp("tp"),
	weather("weather"),
	worldborder("worldborder"),
	xp("xp");

	/** The Command's ID. */
	private String id;

	/** Creates a new Command
	 * 
	 * @param id
	 *            - <i>String</i> - The Command's ID */
	private Command(String id)
	{
		this.id = id;
		Registerer.registerCommand(this);
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
		if (id.equals("summon")) return Generator.genSummon(command);
		if (id.equals("tellraw")) return Generator.genTellraw(command);
		if (id.equals("testfor")) return Generator.genEntityData(command);
		if (id.equals("testforblock")) return Generator.genTestforblock(command);
		if (id.equals("testforblocks")) return Generator.genTestforblocks(command);
		if (id.equals("time")) return Generator.genTime(command);
		if (id.equals("title")) return Generator.genTitle(command);
		if (id.equals("tp")) return Generator.genTp(command);
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
	public OptionsPanel getOptionsPanel()
	{
		if (id.equals("achievement")) return new AchievementOptionsPanel();
		if (id.equals("blockdata")) return new BlockdataOptionsPanel();
		if (id.equals("clear")) return new ClearOptionsPanel();
		if (id.equals("clone")) return new CloneOptionsPanel();
		if (id.equals("effect")) return new EffectOptionsPanel();
		if (id.equals("enchant")) return new EnchantOptionsPanel();
		if (id.equals("entitydata") || id.equals("testfor")) return new EntityDataOptionsPanel(id);
		if (id.equals("execute")) return new ExecuteOptionsPanel();
		if (id.equals("fill")) return new FillOptionsPanel();
		if (id.equals("gamerule")) return new GameruleOptionsPanel();
		if (id.equals("give")) return new GiveOptionsPanel();
		if (id.equals("kill")) return new KillOptionsPanel();
		if (id.equals("particle")) return new ParticleOptionsPanel();
		if (id.equals("playsound")) return new PlaysoundOptionsPanel();
		if (id.equals("replaceitem")) return new ReplaceitemOptionsPanel();
		if (id.equals("scoreboard")) return new ScoreboardOptionsPanel();
		if (id.equals("setblock")) return new SetblockOptionsPanel();
		if (id.equals("setworldspawn")) return new WorldspawnOptionsPanel();
		if (id.equals("spawnpoint")) return new SpawnpointOptionsPanel();
		if (id.equals("spreadplayers")) return new SpreadOptionsPanel();
		if (id.equals("summon")) return new SummonOptionsPanel();
		if (id.equals("tellraw")) return new TellrawOptionsPanel();
		if (id.equals("testforblock")) return new TestforblockOptionsPanel();
		if (id.equals("testforblocks")) return new TestforblocksOptionsPanel();
		if (id.equals("time")) return new TimeOptionsPanel();
		if (id.equals("title")) return new TitleOptionsPanel();
		if (id.equals("tp")) return new TpOptionsPanel();
		if (id.equals("weather")) return new WeatherOptionsPanel();
		if (id.equals("worldborder")) return new WorldborderOptionsPanel();
		if (id.equals("xp")) return new XpOptionsPanel();

		DisplayHelper.log("No options panel was found for command /" + id);
		return null;
	}

}
