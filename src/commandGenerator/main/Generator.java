package commandGenerator.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import commandGenerator.arguments.objects.Achievement;
import commandGenerator.arguments.objects.Coordinates;
import commandGenerator.arguments.objects.Effect;
import commandGenerator.arguments.objects.EffectType;
import commandGenerator.arguments.objects.EnchantType;
import commandGenerator.arguments.objects.Enchantment;
import commandGenerator.arguments.objects.ItemStack;
import commandGenerator.arguments.objects.Registerer;
import commandGenerator.arguments.objects.Target;
import commandGenerator.arguments.tags.DataTags;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.gui.helper.commandSpecific.scoreboard.PlayersOperationPanel;
import commandGenerator.gui.helper.commandSpecific.scoreboard.TeamsOptionPanel;

public class Generator
{

	/** Generates the data for the /achievement command.
	 * 
	 * @param command
	 *            - <i>String</i> - The command to generate from. */
	public static Map<String, Object> genAchievement(String command)
	{
		Map<String, Object> data = new HashMap<String, Object>();
		String[] elements = genElements(command);

		try
		{
			if (elements[1].equals("give")) data.put(CGConstants.DATAID_MODE, 0);
			else data.put(CGConstants.DATAID_MODE, 1);

			data.put(CGConstants.DATAID_CHECK, elements[2].equals("*"));
			if (!elements[2].equals("*")) data.put(CGConstants.PANELID_ACHIEVEMENT, Achievement.generateFrom(elements[2]));
			data.put(CGConstants.PANELID_TARGET, Target.generateFrom(elements[3]));

			if ((!elements[2].equals("*") && (data.get(CGConstants.PANELID_ACHIEVEMENT) == null)) || data.get(CGConstants.PANELID_TARGET) == null) return wrong();

			return data;
		} catch (Exception e)
		{
			return wrong();
		}
	}

	/** Generates the data for the /blockdata command.
	 * 
	 * @param command
	 *            - <i>String</i> - The command to generate from. */
	public static Map<String, Object> genBlockData(String command)
	{
		Map<String, Object> data = new HashMap<String, Object>();
		String[] elements = genElements(command);
		try
		{
			data.put(CGConstants.PANELID_COORDS, Coordinates.generateFrom(elements[1], elements[2], elements[3]));
			List<Tag> nbt = DataTags.generateListFrom(elements[4]);
			data.put(CGConstants.PANELID_BLOCK, ItemStack.generateBlockFrom(DataTags.getObjectFromTags(nbt).getId(), 0, nbt));

			if (data.get(CGConstants.PANELID_COORDS) == null || data.get(CGConstants.PANELID_BLOCK) == null) return wrong();

			return data;
		} catch (Exception e)
		{
			return wrong();
		}
	}

	/** Generates the data for the /clear command.
	 * 
	 * @param command
	 *            - <i>String</i> - The command to generate from. */
	public static Map<String, Object> genClear(String command)
	{
		Map<String, Object> data = new HashMap<String, Object>();
		String[] elements = genElements(command);

		try
		{
			data.put(CGConstants.PANELID_TARGET, Target.generateFrom(elements[1]));
			data.put(CGConstants.DATAID_CLEAR_ITEM, elements.length < 2);
			data.put(CGConstants.DATAID_CHECK, elements.length < 4);

			String id = "air";
			int count = 0, damage = 0;
			if (elements.length > 2)
			{
				id = elements[2];
				damage = Integer.parseInt(elements[3]);
			}
			if (elements.length > 4) count = Integer.parseInt(elements[4]);
			data.put(CGConstants.PANELID_ITEM, ItemStack.generateFrom(id, damage, count, new ArrayList<Tag>(), 0));

			if (elements.length > 5) data.put(CGConstants.PANELID_ITEM, ItemStack.generateFrom(id, damage, count, DataTags.generateListFrom(elements[5]), 0));

			return data;
		} catch (Exception e)
		{
			return wrong();
		}
	}

	/** Generates the data for the /clone command.
	 * 
	 * @param command
	 *            - <i>String</i> - The command to generate from. */
	public static Map<String, Object> genClone(String command)
	{
		Map<String, Object> data = new HashMap<String, Object>();
		String[] elements = genElements(command);

		try
		{
			data.put(CGConstants.PANELID_COORDS_START, Coordinates.generateFrom(elements[1], elements[2], elements[3]));
			data.put(CGConstants.PANELID_COORDS_END, Coordinates.generateFrom(elements[4], elements[5], elements[6]));
			data.put(CGConstants.PANELID_COORDS, Coordinates.generateFrom(elements[7], elements[8], elements[9]));

			if (elements.length > 10)
			{
				if (elements[10].equals("replace")) data.put(CGConstants.DATAID_MODE, 0);
				if (elements[10].equals("masked")) data.put(CGConstants.DATAID_MODE, 1);
				if (elements[10].equals("filtered")) data.put(CGConstants.DATAID_MODE, 2);
			} else data.put(CGConstants.DATAID_MODE, 0);
			if (elements.length > 11)
			{
				if (elements[11].equals("normal")) data.put(CGConstants.DATAID_MODE2, 0);
				if (elements[11].equals("force")) data.put(CGConstants.DATAID_MODE2, 1);
				if (elements[11].equals("move")) data.put(CGConstants.DATAID_MODE2, 2);
			} else data.put(CGConstants.DATAID_MODE2, 0);

			if (data.get(CGConstants.PANELID_COORDS) == null || data.get(CGConstants.PANELID_COORDS_START) == null
					|| data.get(CGConstants.PANELID_COORDS_END) == null) return wrong();

			return data;
		} catch (Exception e)
		{
			return wrong();
		}
	}

	/** Generates the data for the /effect command.
	 * 
	 * @param command
	 *            - <i>String</i> - The command to generate from. */
	public static Map<String, Object> genEffect(String command)
	{
		Map<String, Object> data = new HashMap<String, Object>();
		String[] elements = genElements(command);

		try
		{
			data.put(CGConstants.PANELID_TARGET, Target.generateFrom(elements[1]));

			EffectType type = (EffectType) Registerer.getObjectFromId(elements[2]);
			if (type == null) type = (EffectType) Registerer.getObjectFromIdNum(CGConstants.OBJECT_EFFECT, Integer.parseInt(elements[2]));
			int duration = 30, level = 0;
			boolean hide = false;
			if (elements.length > 3) duration = Integer.parseInt(elements[3]);
			if (elements.length > 4) level = Integer.parseInt(elements[4]);
			if (elements.length > 5) hide = Boolean.parseBoolean(elements[5]);
			data.put(CGConstants.PANELID_EFFECT, new Effect(type, level, duration, !hide));
			DisplayHelper.log("Created effect : " + ((Effect) data.get(CGConstants.PANELID_EFFECT)).display());

			if (data.get(CGConstants.PANELID_TARGET) == null) return wrong();

			return data;
		} catch (Exception e)
		{
			return wrong();
		}
	}

	/** Generates the different elements of the command
	 * 
	 * @param command
	 *            - <i>String</i> - The command which elements should be identified. */
	private static String[] genElements(String command)
	{
		List<String> elements = new ArrayList<String>();
		String[] base = command.split(" ");

		int realIndex = 0;
		boolean inString = false;
		for (int i = 0; i < base.length; i++)
		{
			for (int j = 0; j < base[i].length(); j++)
			{

				if (base[i].charAt(j) == '"')
				{
					if (j == 0) inString = !inString;
					else if (base[i].charAt(j - 1) != '\\') inString = !inString;
				}
			}
			if (elements.size() <= realIndex) elements.add(base[i]);
			else elements.set(realIndex, elements.get(realIndex) + " " + base[i]);
			if (!inString) realIndex++;
		}

		return elements.toArray(new String[0]);
	}

	/** Generates the data for the /enchant command.
	 * 
	 * @param command
	 *            - <i>String</i> - The command to generate from. */
	public static Map<String, Object> genEnchant(String command)
	{
		Map<String, Object> data = new HashMap<String, Object>();
		String[] elements = genElements(command);

		try
		{
			data.put(CGConstants.PANELID_TARGET, Target.generateFrom(elements[1]));
			EnchantType type = (EnchantType) Registerer.getObjectFromId(elements[2]);
			if (type == null) type = (EnchantType) Registerer.getObjectFromIdNum(CGConstants.OBJECT_ENCHANT, Integer.parseInt(elements[2]));
			data.put(CGConstants.PANELID_ENCHANT, new Enchantment(type, Integer.parseInt(elements[3]), true));
			DisplayHelper.log("Created enchantment : " + ((Enchantment) data.get(CGConstants.PANELID_ENCHANT)).display());

			if (data.get(CGConstants.PANELID_TARGET) == null || !((Enchantment) data.get(CGConstants.PANELID_ENCHANT)).isCorrect()) return wrong();

			return data;
		} catch (Exception e)
		{
			return wrong();
		}
	}

	/** Generates the data for the /entitydata command.
	 * 
	 * @param command
	 *            - <i>String</i> - The command to generate from. */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> genEntityData(String command)
	{
		Map<String, Object> data = new HashMap<String, Object>();
		String[] elements = genElements(command);

		try
		{
			data.put(CGConstants.PANELID_TARGET, Target.generateFrom(elements[1]));
			data.put(CGConstants.PANELID_NBT, DataTags.generateListFrom(elements[2]));
			data.put(CGConstants.DATAID_ENTITY, DataTags.getObjectFromTags((List<Tag>) data.get(CGConstants.PANELID_NBT)));

			return data;
		} catch (Exception e)
		{
			e.printStackTrace();
			return wrong();
		}
	}

	/** Generates the slot caracteristics.
	 * 
	 * @param slot
	 *            - <i>String</i> - The slot to generate from. */
	private static Object[] generateSlot(String slot)
	{

		boolean visible = true;
		int index = 0, spin = 0;
		String type = slot.split("\\.")[1];

		if (type.equals("armor")) return new Object[] { index, slot.split("\\.")[2], visible };

		if (type.equals("enderchest") || (type.equals("horse") && slot.split("\\.")[2].equals("chest")) || type.equals("hotbar") || type.equals("inventory")
				|| type.equals("villager"))
		{
			if (!type.equals("horse")) spin = Integer.parseInt(slot.split("\\.")[2]);
			else spin = Integer.parseInt(slot.split("\\.")[3]);

			if (type.equals("enderchest")) index = 1;
			if (type.equals("horse")) index = 3;
			if (type.equals("hotbar")) index = 5;
			if (type.equals("inventory")) index = 6;
			if (type.equals("villager")) index = 7;
		}

		if (type.equals("weapon") || (type.equals("horse") && !slot.split("\\.")[2].equals("chest")))
		{
			if (type.equals("weapon")) index = 8;
			else
			{
				if (slot.split("\\.")[2].equals("armor")) index = 2;
				if (slot.split("\\.")[2].equals("saddle")) index = 4;
			}
			visible = false;
		}

		return new Object[] { index, spin, visible };
	}

	/** Generates the data for the /execute command.
	 * 
	 * @param command
	 *            - <i>String</i> - The command to generate from. */
	public static Map<String, Object> genExecute(String command)
	{
		Map<String, Object> data = new HashMap<String, Object>();
		String[] elements = genElements(command);

		try
		{
			data.put(CGConstants.PANELID_TARGET, Target.generateFrom(elements[1]));
			data.put(CGConstants.PANELID_COORDS, Coordinates.generateFrom(elements[2], elements[3], elements[4]));
			data.put(CGConstants.DATAID_CHECK, elements[5].equals("detect"));

			int startIndex = 5;
			if (elements[5].equals("detect"))
			{
				startIndex = 11;
				data.put(CGConstants.PANELID_COORDS_START, Coordinates.generateFrom(elements[6], elements[7], elements[8]));
				data.put(CGConstants.PANELID_BLOCK, ItemStack.generateBlockFrom(elements[9], Integer.parseInt(elements[10]), new ArrayList<Tag>()));
			}

			String exe = "";
			for (int i = startIndex; i < elements.length; i++)
				exe += elements[i] + " ";

			data.put(CGConstants.DATAID_VALUE, exe);

			return data;
		} catch (Exception e)
		{
			return wrong();
		}
	}

	/** Generates the data for the /fill command.
	 * 
	 * @param command
	 *            - <i>String</i> - The command to generate from. */
	public static Map<String, Object> genFill(String command)
	{
		Map<String, Object> data = new HashMap<String, Object>();
		String[] elements = genElements(command);

		try
		{
			data.put(CGConstants.PANELID_COORDS_START, Coordinates.generateFrom(elements[1], elements[2], elements[3]));
			data.put(CGConstants.PANELID_COORDS_END, Coordinates.generateFrom(elements[4], elements[5], elements[6]));
			data.put(CGConstants.PANELID_BLOCK, ItemStack.generateBlockFrom(elements[7], Integer.parseInt(elements[8]), new ArrayList<Tag>()));
			data.put(CGConstants.DATAID_MODE2, 0);
			data.put(CGConstants.DATAID_MODE, 0);

			if (elements.length == 10)
			{
				String[] modes = { "replace", "destroy", "keep", "hollow", "outline" };
				for (int i = 0; i < modes.length; i++)
					if (modes[i].equals(elements[9])) data.put(CGConstants.DATAID_MODE, i);

			} else if (elements.length > 10 && !elements[10].contains("{"))
			{
				data.put(CGConstants.DATAID_MODE2, 1);
				data.put(CGConstants.PANELID_ITEM, ItemStack.generateBlockFrom(elements[10], Integer.parseInt(elements[11]), new ArrayList<Tag>()));
			} else if (elements.length > 10)
			{
				data.put(CGConstants.PANELID_BLOCK,
						ItemStack.generateBlockFrom(elements[7], Integer.parseInt(elements[8]), DataTags.generateListFrom(elements[10])));
			}

			return data;
		} catch (Exception e)
		{
			return wrong();
		}
	}

	/** Generates the data for the /gamerule command.
	 * 
	 * @param command
	 *            - <i>String</i> - The command to generate from. */
	public static Map<String, Object> genGamerule(String command)
	{
		Map<String, Object> data = new HashMap<String, Object>();
		String[] elements = genElements(command);

		try
		{
			data.put(CGConstants.DATAID_MODE, elements[1]);
			data.put(CGConstants.DATAID_VALUE, elements[2]);
			return data;
		} catch (Exception e)
		{
			return wrong();
		}
	}

	/** Generates the data for the /give command.
	 * 
	 * @param command
	 *            - <i>String</i> - The command to generate from. */
	public static Map<String, Object> genGive(String command)
	{
		Map<String, Object> data = new HashMap<String, Object>();
		String[] elements = genElements(command);

		try
		{
			data.put(CGConstants.PANELID_TARGET, Target.generateFrom(elements[1]));
			String id = elements[2];
			int count = 0, damage = 0;
			if (elements.length > 3) count = Integer.parseInt(elements[3]);
			if (elements.length > 4) damage = Integer.parseInt(elements[4]);

			data.put(CGConstants.PANELID_ITEM, ItemStack.generateFrom(id, damage, count, new ArrayList<Tag>(), 0));
			if (elements.length > 5) data.put(CGConstants.PANELID_ITEM, ItemStack.generateFrom(id, damage, count, DataTags.generateListFrom(elements[5]), 0));

			return data;
		} catch (Exception e)
		{
			return wrong();
		}
	}

	/** Generates the data for the /kill command.
	 * 
	 * @param command
	 *            - <i>String</i> - The command to generate from. */
	public static Map<String, Object> genKill(String command)
	{
		Map<String, Object> data = new HashMap<String, Object>();
		String[] elements = genElements(command);

		try
		{
			data.put(CGConstants.PANELID_TARGET, Target.generateFrom(elements[1]));
			if (data.get(CGConstants.PANELID_TARGET) == null) return wrong();
			return data;
		} catch (Exception e)
		{
			return wrong();
		}
	}

	/** Generates the data for the /particle command.
	 * 
	 * @param command
	 *            - <i>String</i> - The command to generate from. */
	public static Map<String, Object> genParticle(String command)
	{
		Map<String, Object> data = new HashMap<String, Object>();
		String[] elements = genElements(command);

		try
		{
			data.put(CGConstants.PANELID_PARTICLE, Registerer.getObjectFromId(elements[1]));
			data.put(CGConstants.PANELID_COORDS_START, Coordinates.generateFrom(elements[2], elements[3], elements[4]));
			data.put(CGConstants.PANELID_COORDS_END, Coordinates.generateFrom(elements[5], elements[6], elements[7]));
			data.put(CGConstants.DATAID_VALUE, new int[] { Integer.parseInt(elements[8]), -1 });
			data.put(CGConstants.DATAID_CHECK, elements.length > 10);

			if (elements.length > 9) data.put(CGConstants.DATAID_VALUE, new int[] { Integer.parseInt(elements[8]), Integer.parseInt(elements[9]) });
			if (elements.length > 10) data.put(CGConstants.PANELID_TARGET, Target.generateFrom(elements[10]));

			return data;
		} catch (Exception e)
		{
			return wrong();
		}
	}

	/** Generates the data for the /playsound command.
	 * 
	 * @param command
	 *            - <i>String</i> - The command to generate from. */
	public static Map<String, Object> genPlaysound(String command)
	{
		Map<String, Object> data = new HashMap<String, Object>();
		String[] elements = genElements(command);

		try
		{
			data.put(CGConstants.PANELID_SOUND, Registerer.getObjectFromId(elements[1]));
			data.put(CGConstants.PANELID_TARGET, Target.generateFrom(elements[2]));
			if (elements.length > 5)
			{
				data.put(CGConstants.DATAID_SOUND_COORDS, true);
				data.put(CGConstants.PANELID_COORDS, Coordinates.generateFrom(elements[3], elements[4], elements[5]));
			} else data.put(CGConstants.DATAID_SOUND_COORDS, false);

			if (elements.length > 6)
			{
				data.put(CGConstants.DATAID_SOUND_OPTIONS, true);
				data.put(CGConstants.DATAID_SOUND_VOL, elements[6]);
			}
			if (elements.length > 7) data.put(CGConstants.DATAID_SOUND_PITCH, elements[7]);
			if (elements.length > 8) data.put(CGConstants.DATAID_SOUND_VOLMIN, elements[8]);

			if (data.get(CGConstants.PANELID_TARGET) == null || data.get(CGConstants.PANELID_SOUND) == null) return wrong();

			return data;
		} catch (Exception e)
		{
			return wrong();
		}
	}

	/** Generates the data for the /replaceitem command.
	 * 
	 * @param command
	 *            - <i>String</i> - The command to generate from. */
	public static Map<String, Object> genReplaceitem(String command)
	{
		Map<String, Object> data = new HashMap<String, Object>();
		String[] elements = genElements(command);

		try
		{
			String id = "stone";
			int damage = 0, count = 0, slot = 0;
			if (elements[1].equals("block"))
			{
				data.put(CGConstants.DATAID_MODE, 0);
				data.put(CGConstants.PANELID_COORDS, Coordinates.generateFrom(elements[2], elements[3], elements[4]));
				slot = Integer.parseInt(elements[5].split("\\.")[2]);
				id = elements[6];
				count = Integer.parseInt(elements[7]);
				damage = Integer.parseInt(elements[8]);
			} else if (elements[1].equals("entity"))
			{
				data.put(CGConstants.DATAID_MODE, 1);
				data.put(CGConstants.PANELID_TARGET, Target.generateFrom(elements[2]));
				data.put(CGConstants.DATAID_VALUE, generateSlot(elements[3]));
				id = elements[4];
				count = Integer.parseInt(elements[5]);
				damage = Integer.parseInt(elements[6]);
			} else return wrong();

			data.put(CGConstants.PANELID_ITEM, ItemStack.generateFrom(id, damage, count, new ArrayList<Tag>(), slot));

			return data;
		} catch (Exception e)
		{
			e.printStackTrace();
			return wrong();
		}
	}

	/** Generates the data details for /scoreboard.
	 * 
	 * @param data
	 *            - <i>Map:String->Object</i> - The data storage.
	 * @param elements
	 *            - <i>String[]</i> -The elements of the command. */
	private static void genScore(Map<String, Object> data, String[] elements)
	{
		String[][] scoreboardModes = { { "objectives", "add", "remove", "setdisplay" },
				{ "players", "set", "add", "remove", "reset", "enable", "test", "operation" }, { "teams", "add", "remove", "empty", "join", "leave", "option" } };

		String mode = scoreboardModes[(int) data.get(CGConstants.DATAID_MODE)][0];
		String mode2 = scoreboardModes[(int) data.get(CGConstants.DATAID_MODE)][(int) data.get(CGConstants.DATAID_MODE2) + 1];

		if (mode.equals("objectives"))
		{
			if (mode2.equals("add") || mode2.equals("remove")) data.put(CGConstants.DATAID_NAME, elements[3]);
			if (mode2.equals("add"))
			{
				data.put(CGConstants.DATAID_VALUE, elements[4]);
				if (elements.length > 5) data.put(CGConstants.DATAID_NAME2, elements[5]);
			}
			if (mode2.equals("setdisplay"))
			{
				data.put(CGConstants.DATAID_VALUE, 0);
				for (int i = 0; i < Resources.displayList.length; i++)
				{
					if (elements[3].equals(Resources.displayList[i])) data.put(CGConstants.DATAID_VALUE, i);
				}
				if (elements.length > 4) data.put(CGConstants.DATAID_NAME, elements[4]);
			}
		}

		if (mode.equals("players"))
		{
			data.put(CGConstants.PANELID_TARGET, Target.generateFrom(elements[3]));
			if (!mode2.equals("reset") || elements.length > 4) data.put(CGConstants.DATAID_NAME, elements[4]);
			if (mode2.equals("set") || mode2.equals("add") || mode2.equals("remove") || mode2.equals("test")) data.put(CGConstants.DATAID_VALUE, elements[5]);
			if (mode2.equals("test") && elements.length > 6) data.put(CGConstants.DATAID_CHECK, elements[6]);
			if (mode2.equals("operation"))
			{
				data.put(CGConstants.PANELID_TARGET2, Target.generateFrom(elements[6]));
				data.put(CGConstants.DATAID_NAME2, elements[7]);
				data.put(CGConstants.DATAID_VALUE, 0);
				for (int i = 0; i < PlayersOperationPanel.operationList.length; i++)
					if (PlayersOperationPanel.operationList[i].equals(elements[5])) data.put(CGConstants.DATAID_VALUE, i);

			}
		}

		if (mode.equals("teams"))
		{
			if (!mode2.equals("leave") || elements.length > 4) data.put(CGConstants.DATAID_NAME, elements[3]);
			if (mode2.equals("add") && elements.length > 4) data.put(CGConstants.DATAID_NAME2, elements[4]);
			if (mode2.equals("join")) data.put(CGConstants.PANELID_TARGET, Target.generateFrom(elements[4]));
			if (mode2.equals("leave"))
			{
				if (elements.length > 4) data.put(CGConstants.PANELID_TARGET, Target.generateFrom(elements[4]));
				else data.put(CGConstants.PANELID_TARGET, Target.generateFrom(elements[3]));
			}
			if (mode2.equals("option"))
			{
				String mode3 = elements[4];
				data.put(CGConstants.DATAID_CHECK, 0);
				data.put(CGConstants.DATAID_VALUE, 0);

				for (int i = 0; i < TeamsOptionPanel.scoreboardTeamsOptionList.length; i++)
					if (mode3.equals(TeamsOptionPanel.scoreboardTeamsOptionList[i])) data.put(CGConstants.DATAID_CHECK, i);

				if (mode3.equals("color")) for (int i = 0; i < Resources.colors.length; i++)
					if (elements[5].equals(Resources.colors[i])) data.put(CGConstants.DATAID_VALUE, i);
				if (mode3.equals("deathMessageVisibility") || mode3.equals("nametagVisibility")) for (int i = 0; i < TeamsOptionPanel.visibilityList.length; i++)
					if (elements[5].equals(TeamsOptionPanel.visibilityList[i])) data.put(CGConstants.DATAID_VALUE, i);
				if ((mode3.equals("seeFriendlyInvisibles ") || mode3.equals("friendlyfire")) && elements[5].equals("false")) data.put(CGConstants.DATAID_VALUE,
						1);

			}
		}
	}

	/** Generates the data for the /scoreboard command.
	 * 
	 * @param command
	 *            - <i>String</i> - The command to generate from. */
	public static Map<String, Object> genScoreboard(String command)
	{
		Map<String, Object> data = new HashMap<String, Object>();
		String[] elements = genElements(command);
		String[][] scoreboardModes = { { "objectives", "add", "remove", "setdisplay" },
				{ "players", "set", "add", "remove", "reset", "enable", "test", "operation" }, { "teams", "add", "remove", "empty", "join", "leave", "option" } };
		int mode = 0;

		try
		{
			for (int i = 0; i < scoreboardModes.length; i++)
				if (elements[1].equals(scoreboardModes[i][0])) mode = i;
			data.put(CGConstants.DATAID_MODE, mode);
			for (int i = 1; i < scoreboardModes[mode].length; i++)
				if (elements[2].equals(scoreboardModes[mode][i])) data.put(CGConstants.DATAID_MODE2, i - 1);

			genScore(data, elements);

			return data;
		} catch (Exception e)
		{
			return wrong();
		}
	}

	/** Generates the data for the /setblock command.
	 * 
	 * @param command
	 *            - <i>String</i> - The command to generate from. */
	public static Map<String, Object> genSetblock(String command)
	{
		Map<String, Object> data = new HashMap<String, Object>();
		String[] elements = genElements(command);

		try
		{
			data.put(CGConstants.PANELID_COORDS, Coordinates.generateFrom(elements[1], elements[2], elements[3]));
			data.put(CGConstants.DATAID_MODE, 0);

			List<Tag> tags = new ArrayList<Tag>();
			if (elements.length > 7) tags = DataTags.generateListFrom(elements[7]);

			if (elements.length > 5) data.put(CGConstants.PANELID_BLOCK, ItemStack.generateBlockFrom(elements[4], Integer.parseInt(elements[5]), tags));
			else data.put(CGConstants.PANELID_BLOCK, ItemStack.generateBlockFrom(elements[4], 0, tags));
			if (elements.length > 6)
			{
				if (elements[6].equals("destroy")) data.put(CGConstants.DATAID_MODE, 1);
				if (elements[6].equals("replace")) data.put(CGConstants.DATAID_MODE, 2);
			}

			return data;
		} catch (Exception e)
		{
			return wrong();
		}
	}

	/** Generates the data for the /setworldspawn command.
	 * 
	 * @param command
	 *            - <i>String</i> - The command to generate from. */
	public static Map<String, Object> genSetworldspawn(String command)
	{
		Map<String, Object> data = new HashMap<String, Object>();
		String[] elements = genElements(command);

		try
		{
			data.put(CGConstants.PANELID_COORDS, Coordinates.generateFrom(elements[1], elements[2], elements[3]));

			if (data.get(CGConstants.PANELID_COORDS) == null) return wrong();

			return data;
		} catch (Exception e)
		{
			return wrong();
		}
	}

	/** Generates the data for the /spawnpoint command.
	 * 
	 * @param command
	 *            - <i>String</i> - The command to generate from. */
	public static Map<String, Object> genSpawnpoint(String command)
	{
		Map<String, Object> data = new HashMap<String, Object>();
		String[] elements = genElements(command);

		try
		{
			data.put(CGConstants.PANELID_TARGET, Target.generateFrom(elements[1]));
			data.put(CGConstants.PANELID_COORDS, Coordinates.generateFrom(elements[2], elements[3], elements[4]));

			if (data.get(CGConstants.PANELID_COORDS) == null || data.get(CGConstants.PANELID_TARGET) == null) return wrong();

			return data;
		} catch (Exception e)
		{
			return wrong();
		}
	}

	/** Generates the data for the /spreadplayers command.
	 * 
	 * @param command
	 *            - <i>String</i> - The command to generate from. */
	public static Map<String, Object> genSpreadplayers(String command)
	{
		Map<String, Object> data = new HashMap<String, Object>();
		String[] elements = genElements(command);

		try
		{
			data.put(CGConstants.PANELID_TARGET, Target.generateFrom(elements[6]));
			data.put(CGConstants.DATAID_SPREAD_X, elements[1]);
			data.put(CGConstants.DATAID_SPREAD_Z, elements[2]);
			data.put(CGConstants.DATAID_SPREAD_DIST, elements[3]);
			data.put(CGConstants.DATAID_SPREAD_DISTMAX, elements[4]);
			data.put(CGConstants.DATAID_CHECK, Boolean.parseBoolean(elements[5]));

			if (data.get(CGConstants.PANELID_TARGET) == null || Integer.parseInt(elements[1]) < 0 || Integer.parseInt(elements[2]) < 0
					|| Integer.parseInt(elements[3]) < 0 || Integer.parseInt(elements[4]) < 0) return wrong();

			return data;
		} catch (Exception e)
		{
			return wrong();
		}
	}

	/** Generates the data for the /summon command.
	 * 
	 * @param command
	 *            - <i>String</i> - The command to generate from. */
	public static Map<String, Object> genSummon(String command)
	{
		Map<String, Object> data = new HashMap<String, Object>();
		String[] elements = genElements(command);

		try
		{
			data.put(CGConstants.DATAID_ENTITY, Registerer.getObjectFromId(elements[1]));
			if (elements.length > 2) data.put(CGConstants.PANELID_COORDS, Coordinates.generateFrom(elements[2], elements[3], elements[4]));
			if (elements.length > 5) data.put(CGConstants.PANELID_NBT, DataTags.generateListFrom(elements[5]));

			return data;
		} catch (Exception e)
		{
			return wrong();
		}
	}

	/** Generates the data for the /tellraw command.
	 * 
	 * @param command
	 *            - <i>String</i> - The command to generate from. */
	public static Map<String, Object> genTellraw(String command)
	{
		Map<String, Object> data = new HashMap<String, Object>();
		String[] elements = genElements(command);

		try
		{
			data.put(CGConstants.PANELID_TARGET, Target.generateFrom(elements[1]));
			data.put(CGConstants.PANELID_JSON, DataTags.generateListFrom(elements[2]));

			return data;
		} catch (Exception e)
		{
			e.printStackTrace();
			return wrong();
		}
	}

	/** Generates the data for the /testforblock command.
	 * 
	 * @param command
	 *            - <i>String</i> - The command to generate from. */
	public static Map<String, Object> genTestforblock(String command)
	{
		Map<String, Object> data = new HashMap<String, Object>();
		String[] elements = genElements(command);

		try
		{
			data.put(CGConstants.PANELID_COORDS, Coordinates.generateFrom(elements[1], elements[2], elements[3]));

			List<Tag> tags = new ArrayList<Tag>();
			if (elements.length > 6) tags = DataTags.generateListFrom(elements[6]);

			if (elements.length > 5) data.put(CGConstants.PANELID_BLOCK, ItemStack.generateBlockFrom(elements[4], Integer.parseInt(elements[5]), tags));
			else data.put(CGConstants.PANELID_BLOCK, ItemStack.generateBlockFrom(elements[4], 0, tags));

			return data;
		} catch (Exception e)
		{
			return wrong();
		}
	}

	/** Generates the data for the /testforblocks command.
	 * 
	 * @param command
	 *            - <i>String</i> - The command to generate from. */
	public static Map<String, Object> genTestforblocks(String command)
	{
		Map<String, Object> data = new HashMap<String, Object>();
		String[] elements = genElements(command);

		try
		{
			data.put(CGConstants.PANELID_COORDS_START, Coordinates.generateFrom(elements[1], elements[2], elements[3]));
			data.put(CGConstants.PANELID_COORDS_END, Coordinates.generateFrom(elements[4], elements[5], elements[6]));
			data.put(CGConstants.PANELID_COORDS, Coordinates.generateFrom(elements[7], elements[8], elements[9]));
			if (elements.length > 10 && elements[10].equals("all")) data.put(CGConstants.DATAID_MODE, 1);
			else data.put(CGConstants.DATAID_MODE, 0);

			return data;
		} catch (Exception e)
		{
			return wrong();
		}
	}

	/** Generates the data for the /time command.
	 * 
	 * @param command
	 *            - <i>String</i> - The command to generate from. */
	public static Map<String, Object> genTime(String command)
	{
		Map<String, Object> data = new HashMap<String, Object>();
		String[] elements = genElements(command);

		try
		{
			if (elements[1].equals("set")) data.put(CGConstants.DATAID_MODE, 0);
			if (elements[1].equals("add")) data.put(CGConstants.DATAID_MODE, 1);
			data.put(CGConstants.DATAID_VALUE, elements[2]);

			if (data.get(CGConstants.DATAID_MODE) == null || Integer.parseInt((String) data.get(CGConstants.DATAID_VALUE)) < 0) return wrong();

			return data;
		} catch (Exception e)
		{
			return wrong();
		}
	}

	/** Generates the data for the /title command.
	 * 
	 * @param command
	 *            - <i>String</i> - The command to generate from. */
	public static Map<String, Object> genTitle(String command)
	{
		Map<String, Object> data = new HashMap<String, Object>();
		String[] elements = genElements(command);

		try
		{
			data.put(CGConstants.PANELID_TARGET, Target.generateFrom(elements[1]));

			if (elements[2].equals("times"))
			{
				data.put(CGConstants.DATAID_MODE, 2);
				data.put(CGConstants.DATAID_VALUE, new int[] { Integer.parseInt(elements[3]), Integer.parseInt(elements[4]), Integer.parseInt(elements[5]) });
			} else if (elements[2].equals("subtitle")) data.put(CGConstants.DATAID_MODE, 1);
			else
			{
				data.put(CGConstants.DATAID_MODE, 0);
				data.put(CGConstants.PANELID_JSON, DataTags.generateListFrom(elements[3]));
			}

			return data;
		} catch (Exception e)
		{
			return wrong();
		}
	}

	/** Generates the data for the /tp command.
	 * 
	 * @param command
	 *            - <i>String</i> - The command to generate from. */
	public static Map<String, Object> genTp(String command)
	{
		Map<String, Object> data = new HashMap<String, Object>();
		String[] elements = genElements(command);

		try
		{
			data.put(CGConstants.PANELID_TARGET, Target.generateFrom(elements[1]));
			data.put(CGConstants.DATAID_ENTITY, true);

			if (elements.length == 3) data.put(CGConstants.PANELID_TARGET2, Target.generateFrom(elements[2]));
			else
			{
				data.put(CGConstants.DATAID_ENTITY, false);
				data.put(CGConstants.PANELID_COORDS, Coordinates.generateFrom(elements[2], elements[3], elements[4]));
				if (elements.length > 5) data.put(CGConstants.PANELID_COORDS,
						Coordinates.generateFromWithRot(elements[2], elements[3], elements[4], Float.parseFloat(elements[5]), Float.parseFloat(elements[6])));
			}

			return data;
		} catch (Exception e)
		{
			return wrong();
		}
	}

	/** Generates the data for the /weather command.
	 * 
	 * @param command
	 *            - <i>String</i> - The command to generate from. */
	public static Map<String, Object> genWeather(String command)
	{
		Map<String, Object> data = new HashMap<String, Object>();
		String[] elements = genElements(command);

		try
		{
			String type = elements[1];
			if (type.equals("clear")) data.put(CGConstants.DATAID_MODE, 0);
			if (type.equals("rain")) data.put(CGConstants.DATAID_MODE, 1);
			if (type.equals("thunder")) data.put(CGConstants.DATAID_MODE, 2);

			if (elements.length > 2) data.put(CGConstants.DATAID_VALUE, elements[2]);

			return data;
		} catch (Exception e)
		{
			return wrong();
		}
	}

	/** Generates the data for the /worldborder command.
	 * 
	 * @param command
	 *            - <i>String</i> - The command to generate from. */
	public static Map<String, Object> genWorldborder(String command)
	{
		Map<String, Object> data = new HashMap<String, Object>();
		String[] elements = genElements(command);

		try
		{
			if (elements[1].equals("add") || elements[1].equals("set"))
			{
				data.put(CGConstants.DATAID_MODE, 0);
				String[] options = { elements[2] };
				if (elements.length > 3) options = new String[] { elements[2], elements[3] };
				data.put(CGConstants.PANELID_OPTIONS, options);

				if (Integer.parseInt(elements[2]) < 0 && elements[1].equals("set")) return wrong();
			}
			if (elements[1].equals("set")) data.put(CGConstants.DATAID_MODE, 3);

			if (elements[1].equals("center"))
			{
				data.put(CGConstants.DATAID_MODE, 1);
				String[] options = { elements[2], elements[3] };
				data.put(CGConstants.PANELID_OPTIONS, options);

				@SuppressWarnings("unused")
				int test = Integer.parseInt(elements[2]);
				test = Integer.parseInt(elements[3]);
			}

			if (elements[1].equals("damage") || elements[1].equals("warning"))
			{
				data.put(CGConstants.DATAID_MODE, 2);
				int index = 0;
				if (elements[2].equals("buffer") || elements[2].equals("time")) index = 1;
				Object[] options = { index, elements[3] };
				data.put(CGConstants.PANELID_OPTIONS, options);

				if (Integer.parseInt(elements[3]) < 0) return wrong();
			}

			if (elements[1].equals("warning")) data.put(CGConstants.DATAID_MODE, 4);

			if (data.get(CGConstants.DATAID_MODE) == null) return wrong();

			return data;
		} catch (Exception e)
		{
			return wrong();
		}
	}

	/** Generates the data for the /xp command.
	 * 
	 * @param command
	 *            - <i>String</i> - The command to generate from. */
	public static Map<String, Object> genXp(String command)
	{
		Map<String, Object> data = new HashMap<String, Object>();
		String[] elements = genElements(command);

		try
		{

			data.put(CGConstants.DATAID_CHECK, elements[1].contains("L"));
			if (elements[1].contains("L")) elements[1] = elements[1].substring(0, elements[1].length() - 1);
			data.put(CGConstants.DATAID_VALUE, elements[1]);
			data.put(CGConstants.PANELID_TARGET, Target.generateFrom(elements[2]));

			if (data.get(CGConstants.PANELID_TARGET) == null) return wrong();
			return data;
		} catch (Exception e)
		{
			return wrong();
		}
	}

	/** Tells the user that the command he/she typed is wrong. */
	public static Map<String, Object> wrong()
	{
		DisplayHelper.showWarning("WARNING:wrong_command");
		return null;
	}
}
