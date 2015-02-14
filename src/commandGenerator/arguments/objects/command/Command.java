package commandGenerator.arguments.objects.command;

import java.util.Map;

import commandGenerator.arguments.objects.Registerer;
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
import commandGenerator.main.Lang;

public enum Command
{

	achievement("achievement", Structure.achievementOne, Structure.achievementAll),
	blockdata("blockdata", Structure.blockdata),
	clear("clear", Structure.clear),
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

	private Structure[] structures;

	/** Creates a new Command
	 * 
	 * @param id
	 *            - <i>String</i> - The Command's ID */
	private Command(String id, Structure... structures)
	{
		this.id = id;
		this.structures = structures;
		Registerer.registerCommand(this);
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

	public Map<String, Object> generateData(String[] command)
	{
		Structure struct = findValidStructure(command);
		if (struct == null)
		{
			DisplayHelper.showWarning("WARNING:command.structure");
			return null;
		}

		Map<String, Object> data = struct.generateData(command);
		return data;
	}

	private Structure findValidStructure(String[] command)
	{
		for (int i = 0; i < structures.length; i++)
			if (structures[i].matches(command)) return structures[i];
		return null;
	}

	/** Returns the Panel to use to display the Command's options. */
	public OptionsPanel getOptionsPanel()
	{
		if (this == achievement) return new AchievementOptionsPanel();
		if (this == blockdata) return new BlockdataOptionsPanel();
		if (this == clear) return new ClearOptionsPanel();
		if (this == clone) return new CloneOptionsPanel();
		if (this == effect) return new EffectOptionsPanel();
		if (this == enchant) return new EnchantOptionsPanel();
		if (this == entitydata || this == testfor) return new EntityDataOptionsPanel(id);
		if (this == execute) return new ExecuteOptionsPanel();
		if (this == fill) return new FillOptionsPanel();
		if (this == gamerule) return new GameruleOptionsPanel();
		if (this == give) return new GiveOptionsPanel();
		if (this == kill) return new KillOptionsPanel();
		if (this == particle) return new ParticleOptionsPanel();
		if (this == playsound) return new PlaysoundOptionsPanel();
		if (this == replaceitem) return new ReplaceitemOptionsPanel();
		if (this == scoreboard) return new ScoreboardOptionsPanel();
		if (this == setblock) return new SetblockOptionsPanel();
		if (this == setworldspawn) return new WorldspawnOptionsPanel();
		if (this == spawnpoint) return new SpawnpointOptionsPanel();
		if (this == spreadplayers) return new SpreadOptionsPanel();
		if (this == summon) return new SummonOptionsPanel();
		if (this == tellraw) return new TellrawOptionsPanel();
		if (this == testforblock) return new TestforblockOptionsPanel();
		if (this == testforblocks) return new TestforblocksOptionsPanel();
		if (this == time) return new TimeOptionsPanel();
		if (this == title) return new TitleOptionsPanel();
		if (this == tp) return new TpOptionsPanel();
		if (this == weather) return new WeatherOptionsPanel();
		if (this == worldborder) return new WorldborderOptionsPanel();
		if (this == xp) return new XpOptionsPanel();

		DisplayHelper.log("No options panel was found for command /" + id);
		return null;
	}
}
