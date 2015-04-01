package commandGenerator.arguments.command;

import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;

import commandGenerator.arguments.command.arguments.BlockArgument;
import commandGenerator.arguments.command.arguments.BooleanArgument;
import commandGenerator.arguments.command.arguments.ChoiceArgument;
import commandGenerator.arguments.command.arguments.CoordinatesArgument;
import commandGenerator.arguments.command.arguments.EntityArgument;
import commandGenerator.arguments.command.arguments.FloatArgument;
import commandGenerator.arguments.command.arguments.INBTArgument;
import commandGenerator.arguments.command.arguments.IntArgument;
import commandGenerator.arguments.command.arguments.ItemArgument;
import commandGenerator.arguments.command.arguments.NBTArgument;
import commandGenerator.arguments.command.arguments.StaticArgument;
import commandGenerator.arguments.command.arguments.StringArgument;
import commandGenerator.arguments.command.arguments.TargetArgument;
import commandGenerator.arguments.command.arguments.misc.AchievementArgument;
import commandGenerator.arguments.command.arguments.misc.BlockSlotArgument;
import commandGenerator.arguments.command.arguments.misc.CommandArgument;
import commandGenerator.arguments.command.arguments.misc.EffectArgument;
import commandGenerator.arguments.command.arguments.misc.EnchantmentArgument;
import commandGenerator.arguments.command.arguments.misc.JsonArgument;
import commandGenerator.arguments.command.arguments.misc.ObjectiveArgument;
import commandGenerator.arguments.command.arguments.misc.ParticleArgument;
import commandGenerator.arguments.command.arguments.misc.SlotArgument;
import commandGenerator.arguments.command.arguments.misc.SoundArgument;
import commandGenerator.arguments.command.arguments.misc.TeamOptionArgument;
import commandGenerator.arguments.command.arguments.misc.XpArgument;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.panel.HelperPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.Lang;
import commandGenerator.main.Resources;

public enum Structure
{

	achievementAll("achievement.all", new ChoiceArgument("achievement.mode", true, "give", "take"), new StaticArgument("*"), new TargetArgument("target", true,
			CGConstants.ENTITIES_PLAYERS)),
	achievementOne("achievement.one", new ChoiceArgument("achievement.mode", true, "give", "take"), new AchievementArgument(true), new TargetArgument("target",
			true, CGConstants.ENTITIES_PLAYERS)),
	blockdata("blockdata", new CoordinatesArgument("block.coords", true, true, false), new BlockArgument("block.set", true, CGConstants.LIST_TILEENTITY, true)
			.setDisplay(false, false, true)),
	clear("clear", new TargetArgument("target", true, CGConstants.ENTITIES_PLAYERS),
			new ItemArgument("clear.item", false).setDisplay(true, false, true, false), new IntArgument("clear.amount", false).setBounds(0, Integer.MAX_VALUE),
			new NBTArgument("nbt", false, "clear.item")),
	clone("clone.normal", new CoordinatesArgument("clone.start", true, true, false), new CoordinatesArgument("clone.end", true, true, false),
			new CoordinatesArgument("clone.destination", true, true, false), new ChoiceArgument("clone.mode.mask", false, "masked", "replace").addHelpButton(),
			new ChoiceArgument("clone.mode.dest", false, "force", "move", "normal").addHelpButton()),
	cloneFiltered("clone.filtered", new CoordinatesArgument("clone.start", true, true, false), new CoordinatesArgument("clone.end", true, true, false),
			new CoordinatesArgument("clone.destination", true, true, false), new StaticArgument("filtered"), new ChoiceArgument("clone.mode.dest", false,
					"force", "move", "normal").addHelpButton(), new BlockArgument("clone.block", true, CGConstants.LIST_BLOCKS, false).setDisplay(true, false,
					false)),
	effectClear("effect.clear", new TargetArgument("target", true, CGConstants.ENTITIES_ALL), new StaticArgument("clear")),
	effectGive("effect.give", new TargetArgument("target", true, CGConstants.ENTITIES_ALL), new EffectArgument("effect", true)),
	enchant("enchant", new TargetArgument("target", true, CGConstants.ENTITIES_PLAYERS), new EnchantmentArgument("enchant", true)),
	entitydata("entitydata", new TargetArgument("target", true, CGConstants.ENTITIES_ALL), new EntityArgument("entity.tags", true).setDisplay(false, true)),
	executeDetect("execute.detect", new TargetArgument("target", true, CGConstants.ENTITIES_ALL), new CoordinatesArgument("execute.coords", true, true, false),
			new StaticArgument("detect"), new CoordinatesArgument("execute.block_coords", true, true, false), new BlockArgument("execute.block", true,
					CGConstants.LIST_BLOCKS, false).setDisplay(true, true, false), new CommandArgument()),
	executeNormal("execute.normal", new TargetArgument("target", true, CGConstants.ENTITIES_ALL), new CoordinatesArgument("execute.coords", true, true, false),
			new CommandArgument()),
	fillNormal("fill.normal", new CoordinatesArgument("fill.start", true, true, false), new CoordinatesArgument("fill.end", true, true, false),
			new BlockArgument("fill.block", true, CGConstants.LIST_BLOCKS, true).setDisplay(true, true, false), new ChoiceArgument("fill.mode", false,
					"destroy", "hollow", "keep", "outline").addHelpButton(), new NBTArgument("nbt", false, "fill.block")),
	fillReplace("fill.replace", new CoordinatesArgument("fill.start", true, true, false), new CoordinatesArgument("fill.end", true, true, false),
			new BlockArgument("fill.block", true, CGConstants.LIST_BLOCKS, false).setDisplay(true, true, false), new StaticArgument("replace"),
			new BlockArgument("fill.block.replace", false, CGConstants.LIST_BLOCKS, false).setDisplay(true, true, false)),
	gameruleNormal("gamerule.normal", new ChoiceArgument("gamerule", true, Resources.gamerules).addHelpButton(), new ChoiceArgument("value", true, "true",
			"false")),
	gameruleTicks("gamerule.ticks", new StaticArgument("randomTickSpeed"), new IntArgument("gamerule.value", true).setBounds(0, Integer.MAX_VALUE)),
	give("give", new TargetArgument("target", true, CGConstants.ENTITIES_PLAYERS), new ItemArgument("give.item", true).setDisplay(true, true, true, true)),
	kill("kill", new TargetArgument("target", true, CGConstants.ENTITIES_ALL)),
	particle("particle", new ParticleArgument(), new CoordinatesArgument("particle.start", true, true, false), new CoordinatesArgument("particle.end", true,
			false, false), new IntArgument("particle.speed", true).setBounds(0, Integer.MAX_VALUE), new IntArgument("particle.count", false).setBounds(0,
			Integer.MAX_VALUE), new BooleanArgument("particle.seen", false).setValues("", "force")),
	playsound("playsound", new SoundArgument("playsound.choose", true), new TargetArgument("target", true, CGConstants.ENTITIES_PLAYERS),
			new CoordinatesArgument("playsound.coords", false, true, false), new FloatArgument("playsound.volume", false).addHelpButton()
					.setBounds(1.0F, Float.MAX_VALUE).setDefaultValue(1.0F), new FloatArgument("playsound.pitch", false).addHelpButton().setBounds(0.0F, 2.0F),
			new FloatArgument("playsound.volume_min", false).addHelpButton().setBounds(0.0F, 1.0F)),
	replaceitemBlock("replaceitem.block", new StaticArgument("block"), new CoordinatesArgument("block.coords", true, true, false), new BlockSlotArgument(
			"replaceitem.slot", true).setBounds(0, 27), new ItemArgument("replaceitem.item", true).setDisplay(true, true, true, true)),
	replaceitemEntity("replaceitem.entity", new StaticArgument("entity"), new TargetArgument("target", true, CGConstants.ENTITIES_ALL), new SlotArgument(),
			new ItemArgument("replaceitem.item", true).setDisplay(true, true, true, true)),
	scoreObjectiveAdd("scoreboard.objectives.add", new StaticArgument("add"), new StringArgument("objective", true), new ObjectiveArgument(),
			new StringArgument("scoreboard.display", false)),
	scoreObjectiveDisplay("scoreboard.objectives.display", new StaticArgument("setdisplay"),
			new ChoiceArgument("scoreboard.slot", true, Resources.displayList), new StringArgument("objective", true)),
	scoreObjectiveRemove("scoreboard.objectives.remove", new StaticArgument("remove"), new StringArgument("objective", true)),
	scorePlayerEnable("scoreboard.players.enable", new StaticArgument("enable"), new TargetArgument("target", true, CGConstants.ENTITIES_ALL),
			new StringArgument("objective", true)),
	scorePlayerOperation("scoreboard.players.operation", new StaticArgument("operation"), new TargetArgument("scoreboard.player1", true,
			CGConstants.ENTITIES_ALL), new StringArgument("scoreboard.operation.player1", true), new ChoiceArgument("scoreboard.operation", true,
			Resources.operations).setNoTranslation().addHelpButton(), new TargetArgument("scoreboard.player2", true, CGConstants.ENTITIES_ALL),
			new StringArgument("scoreboard.operation.player2", true)),
	scorePlayerReset("scoreboard.players.reset", new StaticArgument("reset"), new TargetArgument("target", true, CGConstants.ENTITIES_ALL), new StringArgument(
			"scoreboard.players.clear.objective", false)),
	scorePlayerSet("scoreboard.players.set", new ChoiceArgument("mode", true, "add", "set", "remove"), new TargetArgument("target", true,
			CGConstants.ENTITIES_ALL), new StringArgument("objective", true), new IntArgument("value", true)),
	scorePlayerTest("scoreboard.players.test", new StaticArgument("test"), new TargetArgument("target", true, CGConstants.ENTITIES_ALL), new StringArgument(
			"objective", true), new IntArgument("scoreboard.score.min", true), new IntArgument("scoreboard.score.max", false)),
	scoreTeamAdd("scoreboard.teams.add", new StaticArgument("add"), new StringArgument("scoreboard.team", true),
			new StringArgument("scoreboard.display", false)),
	scoreTeamDelete("scoreboard.teams.delete", new ChoiceArgument("mode", true, "remove", "empty"), new StringArgument("scoreboard.team", true)),
	scoreTeamManage("scoreboard.teams.manage", new ChoiceArgument("scoreboard.teams.mode", true, "join", "leave"), new StringArgument("scoreboard.team", true),
			new TargetArgument("target", true, CGConstants.ENTITIES_ALL)),
	scoreTeamOption("scoreboard.teams.option", new StaticArgument("option"), new TeamOptionArgument()),
	setblock("setblock", new CoordinatesArgument("block.coords", true, true, false), new BlockArgument("block.set", true, CGConstants.LIST_BLOCKS, true)
			.setDisplay(true, true, false), new ChoiceArgument("setblock.mode", false, "destroy", "keep", "replace").addHelpButton(), new NBTArgument("nbt",
			false, "block.set")),
	setworldspawn("setworldspawn", new CoordinatesArgument("spawn.coords", true, true, false)),
	spawnpoint("spawnpoint", new TargetArgument("target", true, CGConstants.ENTITIES_PLAYERS), new CoordinatesArgument("spawn.coords", true, true, false)),
	spreadplayers("spreadplayers", new IntArgument("spread.x", true), new IntArgument("spread.z", true), new IntArgument("spread.distance", true),
			new IntArgument("spread.distance_max", true), new BooleanArgument("spread.respect", true), new TargetArgument("target", true,
					CGConstants.ENTITIES_ALL)),
	statsClearBlock("stats.clear.block", new StaticArgument("block"), new CoordinatesArgument("block.coords", true, true, false), new StaticArgument("clear"),
			new ChoiceArgument("stats", true, Resources.stats).addHelpButton()),
	statsClearEntity("stats.clear.entity", new StaticArgument("entity"), new TargetArgument("target", true, CGConstants.ENTITIES_ALL), new StaticArgument(
			"clear"), new ChoiceArgument("stats", true, Resources.stats).addHelpButton()),
	statsSetBlock("stats.set.block", new StaticArgument("block"), new CoordinatesArgument("block.coords", true, true, false), new StaticArgument("set"),
			new ChoiceArgument("stats", true, Resources.stats).addHelpButton(), new TargetArgument("target.stats", true, CGConstants.ENTITIES_ALL),
			new StringArgument("objective", true)),
	statsSetEntity("stats.set.entity", new StaticArgument("entity"), new TargetArgument("target", true, CGConstants.ENTITIES_ALL), new StaticArgument("set"),
			new ChoiceArgument("stats", true, Resources.stats).addHelpButton(), new TargetArgument("target.stats", true, CGConstants.ENTITIES_ALL),
			new StringArgument("objective", true)),
	summon("summon", new EntityArgument("spawn.entity", true).setDisplay(true, false), new CoordinatesArgument("spawn.coords", false, true, false),
			new NBTArgument("nbt", false, "entity")),
	tellraw("tellraw", new TargetArgument("target", true, CGConstants.ENTITIES_PLAYERS), new JsonArgument("json", true)),
	testfor("testfor", new TargetArgument("target", true, CGConstants.ENTITIES_ALL), new EntityArgument("entity.tags", true).setDisplay(false, true)),
	testforblock("testforblock", new CoordinatesArgument("block.coords", true, true, false),
			new BlockArgument("block.set", true, CGConstants.LIST_BLOCKS, true).setDisplay(true, true, true)),
	testforblocks("testforblocks", new CoordinatesArgument("testforblocks.start", true, true, false), new CoordinatesArgument("testforblocks.end", true, true,
			false), new CoordinatesArgument("testforblocks.destination", true, true, false), new ChoiceArgument("testforblocks.mode", true, "all", "masked")
			.addHelpButton()),
	timeQuery("time.query", new StaticArgument("query"), new ChoiceArgument("time.query", true, "daytime", "gametime").addHelpButton()),
	timeSet("time.set", new ChoiceArgument("time.mode", true, "add", "set"), new IntArgument("time.time", true)),
	titleDisplay("title.display", new ChoiceArgument("title.mode", true, "title", "subtitle").addHelpButton(), new JsonArgument("json", true)),
	titleOptions("title.options", new StaticArgument("times"), new IntArgument("title.fade_in", true).setDefaultValue(20), new IntArgument("title.stay", true)
			.setDefaultValue(60), new IntArgument("title.fade_out", true).setDefaultValue(20)),
	titleReset("title.reset", new ChoiceArgument("title.mode", true, "clear", "reset").addHelpButton()),
	tpcoord("tp.coord", new TargetArgument("target", true, CGConstants.ENTITIES_ALL), new CoordinatesArgument("tp.destination.coords", true, true, true)),
	tpentity("tp.entity", new TargetArgument("target", true, CGConstants.ENTITIES_ALL), new TargetArgument("tp.destination.entity", true,
			CGConstants.ENTITIES_ALL)),
	trigger("trigger", new StringArgument("objective", true), new ChoiceArgument("trigger.mode", true, "add", "set"), new IntArgument("value", true)),
	weather("weather", new ChoiceArgument("weather.mode", true, "clear", "rain", "thunder"), new IntArgument("weather.duration", false)),
	worldborderCenter("worldborder.center", new StaticArgument("center"), new IntArgument("coord.x", true), new IntArgument("coord.z", true)),
	worldborderDamage("worldborder.damage", new StaticArgument("damage"), new ChoiceArgument("worldborder.mode", true, "amount", "buffer").addHelpButton(),
			new FloatArgument("value", true).setBounds(0.0F, Float.MAX_VALUE).setDefaultValue(0.2F)),
	worldborderSet("worldborder.set", new ChoiceArgument("worldborder.mode", true, "add", "set"), new IntArgument("value", true)),
	worldborderWarning("worldborder.warning", new StaticArgument("warning"), new ChoiceArgument("worldborder.mode", true, "distance", "time").addHelpButton(),
			new IntArgument("value", true).setBounds(0, Integer.MAX_VALUE)),
	xp("xp", new XpArgument(), new TargetArgument("target", true, CGConstants.ENTITIES_PLAYERS));

	/** The list of Arguments this Structure needs. */
	private Argument[] arguments;

	/** The name of this Structure. */
	private String id;

	/** Creates a new Structure.
	 * 
	 * @param id
	 *            - <i>String</i> - The id of this Structure.
	 * @param arguments
	 *            - <i>Argument...</i> - The list of Arguments this Structure needs. */
	private Structure(String id, Argument... arguments)
	{
		this.id = id;
		this.arguments = arguments;
	}

	/** Generates the command created by this Structure. */
	public String generateCommand()
	{
		String command = "";
		boolean[] used = new boolean[this.arguments.length];
		boolean found = false;
		for (int i = used.length - 1; i >= 0; i--)
		{
			used[i] = this.arguments[i].isUsed() || found;
			if (this.arguments[i].getType() == Argument.NBT)
			{
				for (Argument anbt : this.arguments)
					if (anbt.getId().equals(((NBTArgument) this.arguments[i]).getTarget()))
					{
						TagCompound tag = ((INBTArgument) anbt).getNBT();
						used[i] = (tag != null) && (tag.size() != 0);
					}
			}
			found = used[i];
		}

		for (int i = 0; i < this.arguments.length; i++)
		{
			if (used[i])
			{
				String subCommand = this.arguments[i].generateCommand();
				if (subCommand == null) return null;
				if (this.arguments[i].getType() == Argument.NBT)
				{
					for (Argument anbt : this.arguments)
						if (anbt.getId().equals(subCommand))
						{
							TagCompound tag = ((INBTArgument) anbt).getNBT();
							if (tag == null) return null;
							command += tag.commandStructure().substring(tag.getId().length() + 1);
						}
				} else command += subCommand + " ";
			}
		}

		return command;
	}

	/** Sets each Argument's GUI to match the new Arguments.
	 * 
	 * @param newArguments
	 *            - <i>String[]</i> - The Arguments to match. */
	public void setupFrom(String[] newArguments)
	{
		int argIndex = 0;
		for (int argument = 0; argument < this.arguments.length; argument++)
		{
			List<String> data = new ArrayList<String>();
			for (int i = 0; i < this.arguments[i].getLength(); i++)
			{
				data.add(newArguments[argIndex]);
				argIndex++;
			}
			if (argument == this.arguments.length - 1) while (argIndex < newArguments.length)
			{
				data.add(newArguments[argIndex]);
				argIndex++;
			}
			this.arguments[argument].setupFrom(data);
		}
	}

	/** Generates the Panel to display to the user. */
	public HelperPanel generatePanel()
	{
		@SuppressWarnings("serial")
		HelperPanel panel = new HelperPanel("GENERAL:options") {
			@Override
			protected void addComponents()
			{
				CLabel label = new CLabel("HELP:structure." + id, true);
				label.setBorder(BorderFactory.createBevelBorder(0));
				label.setFont(new Font(label.getFont().getName(), Font.BOLD, 13));
				add(label);
				for (Argument arg : arguments)
				{
					Component c = arg.getComponent();
					if (c != null) add(c);
				}
			}

			@Override
			protected void createComponents()
			{}

			@Override
			protected void createListeners()
			{}
		};

		return panel;
	}

	/** Returns this Structure's maximum length. */
	private int getMaximumLength()
	{
		int length = 0;
		for (Argument argument : this.arguments)
			length += argument.getMaximumLength();
		return length;
	}

	/** Returns this Structure's minimum length. */
	private int getMinimumLength()
	{
		int length = 0;
		for (Argument argument : this.arguments)
			length += argument.getLength();
		return length;
	}

	/** Returns this Structure's name. */
	public String getName()
	{
		return Lang.get("RESOURCES:structure." + this.id);
	}

	/** True if this Structure matches the given Arguments.
	 * 
	 * @param testArguments
	 *            - <i>String[]</i> - The Arguments to match. */
	public boolean matches(String[] testArguments)
	{
		if (this.getMinimumLength() > testArguments.length || this.getMaximumLength() < testArguments.length) return false;

		int argIndex = 0;
		for (int argument = 0; argument < this.arguments.length; argument++)
		{
			List<String> data = new ArrayList<String>();
			for (int i = 0; i < this.arguments[i].getLength(); i++)
			{
				data.add(testArguments[argIndex]);
				argIndex++;
			}
			if (argument == this.arguments.length - 1) while (argIndex < testArguments.length)
			{
				data.add(testArguments[argIndex]);
				argIndex++;
			}
			if (!this.arguments[argument].matches(data)) return false;
		}

		return true;
	}

	public void synchronize(Structure toMatch)
	{
		for (Argument aSelf : arguments)
		{
			for (Argument aMatch : toMatch.getArguments())
			{
				if (aSelf.getId().equals(aMatch.getId())) aSelf.synchronize(aMatch);
			}
		}
	}

	private Argument[] getArguments()
	{
		return this.arguments;
	}

}
