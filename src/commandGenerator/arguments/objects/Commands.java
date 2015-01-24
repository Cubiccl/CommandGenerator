package commandGenerator.arguments.objects;

import java.util.Map;

import commandGenerator.CommandGenerator;
import commandGenerator.gui.OptionsPanel;
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
import commandGenerator.main.Generator;

public class Commands
{

	public static Command achievement, blockdata, clear, clone, effect, enchant, entitydata, execute, fill, gamerule, give, kill, particle, playsound,
			replaceitem, scoreboard, setblock, setworldspawn, spawnpoint, spreadplayers, summon, tellraw, testfor, testforblock, testforblocks, time, title,
			tp, weather, worldborder, xp;
	public static Command[] commands;

	/** Creates all commands. */
	public static void initCommands()
	{

		achievement = new Command("achievement") {
			public OptionsPanel getOptionsPanel()
			{
				return new AchievementOptionsPanel();
			}

			@Override
			public Map<String, Object> generateSetup(String command)
			{
				return Generator.genAchievement(command);
			}
		};
		CommandGenerator.opt.selectedCommand = achievement;

		blockdata = new Command("blockdata") {
			public OptionsPanel getOptionsPanel()
			{
				return new BlockdataOptionsPanel();
			}

			@Override
			public Map<String, Object> generateSetup(String command)
			{
				return Generator.genBlockData(command);
			}
		};

		clear = new Command("clear") {
			public OptionsPanel getOptionsPanel()
			{
				return new ClearOptionsPanel();
			}

			@Override
			public Map<String, Object> generateSetup(String command)
			{
				return Generator.genClear(command);
			}
		};

		clone = new Command("clone") {
			public OptionsPanel getOptionsPanel()
			{
				return new CloneOptionsPanel();
			}

			@Override
			public Map<String, Object> generateSetup(String command)
			{
				return Generator.genClone(command);
			}
		};

		effect = new Command("effect") {
			public OptionsPanel getOptionsPanel()
			{
				return new EffectOptionsPanel();
			}

			@Override
			public Map<String, Object> generateSetup(String command)
			{
				return Generator.genEffect(command);
			}
		};

		enchant = new Command("enchant") {
			public OptionsPanel getOptionsPanel()
			{
				return new EnchantOptionsPanel();
			}

			@Override
			public Map<String, Object> generateSetup(String command)
			{
				return Generator.genEnchant(command);
			}
		};

		entitydata = new Command("entitydata") {
			public OptionsPanel getOptionsPanel()
			{
				return new EntityDataOptionsPanel("entitydata");
			}

			@Override
			public Map<String, Object> generateSetup(String command)
			{
				return Generator.genEntityData(command);
			}
		};

		execute = new Command("execute") {
			public OptionsPanel getOptionsPanel()
			{
				return new ExecuteOptionsPanel();
			}

			@Override
			public Map<String, Object> generateSetup(String command)
			{
				return Generator.genExecute(command);
			}
		};

		fill = new Command("fill") {
			public OptionsPanel getOptionsPanel()
			{
				return new FillOptionsPanel();
			}

			@Override
			public Map<String, Object> generateSetup(String command)
			{
				return Generator.genFill(command);
			}
		};

		gamerule = new Command("gamerule") {
			public OptionsPanel getOptionsPanel()
			{
				return new GameruleOptionsPanel();
			}

			@Override
			public Map<String, Object> generateSetup(String command)
			{
				return Generator.genGamerule(command);
			}
		};

		give = new Command("give") {
			public OptionsPanel getOptionsPanel()
			{
				return new GiveOptionsPanel();
			}

			@Override
			public Map<String, Object> generateSetup(String command)
			{
				return Generator.genGive(command);
			}
		};

		kill = new Command("kill") {
			public OptionsPanel getOptionsPanel()
			{
				return new KillOptionsPanel();
			}

			@Override
			public Map<String, Object> generateSetup(String command)
			{
				return Generator.genKill(command);
			}
		};

		particle = new Command("particle") {
			public OptionsPanel getOptionsPanel()
			{
				return new ParticleOptionsPanel();
			}

			@Override
			public Map<String, Object> generateSetup(String command)
			{
				return Generator.genParticle(command);
			}
		};

		playsound = new Command("playsound") {
			public OptionsPanel getOptionsPanel()
			{
				return new PlaysoundOptionsPanel();
			}

			@Override
			public Map<String, Object> generateSetup(String command)
			{
				return Generator.genPlaysound(command);
			}
		};

		replaceitem = new Command("replaceitem") {
			public OptionsPanel getOptionsPanel()
			{
				return new ReplaceitemOptionsPanel();
			}

			@Override
			public Map<String, Object> generateSetup(String command)
			{
				return Generator.genReplaceitem(command);
			}
		};

		scoreboard = new Command("scoreboard") {
			public OptionsPanel getOptionsPanel()
			{
				return new ScoreboardOptionsPanel();
			}

			@Override
			public Map<String, Object> generateSetup(String command)
			{
				return Generator.genScoreboard(command);
			}
		};

		setblock = new Command("setblock") {
			public OptionsPanel getOptionsPanel()
			{
				return new SetblockOptionsPanel();
			}

			@Override
			public Map<String, Object> generateSetup(String command)
			{
				return Generator.genSetblock(command);
			}
		};

		setworldspawn = new Command("setworldspawn") {
			public OptionsPanel getOptionsPanel()
			{
				return new WorldspawnOptionsPanel();
			}

			@Override
			public Map<String, Object> generateSetup(String command)
			{
				return Generator.genSetworldspawn(command);
			}
		};

		spawnpoint = new Command("spawnpoint") {
			public OptionsPanel getOptionsPanel()
			{
				return new SpawnpointOptionsPanel();
			}

			@Override
			public Map<String, Object> generateSetup(String command)
			{
				return Generator.genSpawnpoint(command);
			}
		};

		spreadplayers = new Command("spreadplayers") {
			public OptionsPanel getOptionsPanel()
			{
				return new SpreadOptionsPanel();
			}

			@Override
			public Map<String, Object> generateSetup(String command)
			{
				return Generator.genSpreadplayers(command);
			}
		};

		summon = new Command("summon") {
			public OptionsPanel getOptionsPanel()
			{
				return new SummonOptionsPanel();
			}

			@Override
			public Map<String, Object> generateSetup(String command)
			{
				return Generator.genSummon(command);
			}
		};

		tellraw = new Command("tellraw") {
			public OptionsPanel getOptionsPanel()
			{
				return new TellrawOptionsPanel();
			}

			@Override
			public Map<String, Object> generateSetup(String command)
			{
				return Generator.genTellraw(command);
			}
		};

		testfor = new Command("testfor") {
			public OptionsPanel getOptionsPanel()
			{
				return new EntityDataOptionsPanel("testfor");
			}

			@Override
			public Map<String, Object> generateSetup(String command)
			{
				return Generator.genEntityData(command);
			}
		};

		testforblock = new Command("testforblock") {
			public OptionsPanel getOptionsPanel()
			{
				return new TestforblockOptionsPanel();
			}

			@Override
			public Map<String, Object> generateSetup(String command)
			{
				return Generator.genTestforblock(command);
			}
		};

		testforblocks = new Command("testforblocks") {
			public OptionsPanel getOptionsPanel()
			{
				return new TestforblocksOptionsPanel();
			}

			@Override
			public Map<String, Object> generateSetup(String command)
			{
				return Generator.genTestforblocks(command);
			}
		};

		time = new Command("time") {
			public OptionsPanel getOptionsPanel()
			{
				return new TimeOptionsPanel();
			}

			@Override
			public Map<String, Object> generateSetup(String command)
			{
				return Generator.genTime(command);
			}
		};

		title = new Command("title") {
			public OptionsPanel getOptionsPanel()
			{
				return new TitleOptionsPanel();
			}

			@Override
			public Map<String, Object> generateSetup(String command)
			{
				return Generator.genTitle(command);
			}
		};

		tp = new Command("tp") {
			public OptionsPanel getOptionsPanel()
			{
				return new TpOptionsPanel();
			}

			@Override
			public Map<String, Object> generateSetup(String command)
			{
				return Generator.genTp(command);
			}
		};

		weather = new Command("weather") {
			public OptionsPanel getOptionsPanel()
			{
				return new WeatherOptionsPanel();
			}

			@Override
			public Map<String, Object> generateSetup(String command)
			{
				return Generator.genWeather(command);
			}
		};

		worldborder = new Command("worldborder") {
			public OptionsPanel getOptionsPanel()
			{
				return new WorldborderOptionsPanel();
			}

			@Override
			public Map<String, Object> generateSetup(String command)
			{
				return Generator.genWorldborder(command);
			}
		};

		xp = new Command("xp") {
			public OptionsPanel getOptionsPanel()
			{
				return new XpOptionsPanel();
			}

			@Override
			public Map<String, Object> generateSetup(String command)
			{
				return Generator.genXp(command);
			}
		};

		commands = new Command[] { achievement, blockdata, clear, clone, effect, enchant, entitydata, execute, fill, gamerule, give, kill, particle, playsound,
				replaceitem, scoreboard, setblock, setworldspawn, spawnpoint, spreadplayers, summon, tellraw, testfor, testforblock, testforblocks, time,
				title, tp, weather, worldborder, xp };

	}

	public static Command getCommandFromId(String id)
	{
		for (int i = 0; i < commands.length; i++)
		{
			if (commands[i].getId().equals(id)) return commands[i];
		}
		return achievement;
	}

}
