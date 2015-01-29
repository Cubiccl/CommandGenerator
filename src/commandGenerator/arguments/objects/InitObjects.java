package commandGenerator.arguments.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import commandGenerator.arguments.tags.DataTags;
import commandGenerator.main.FileHelper;

public class InitObjects
{

	private static final String[] dataTypes = { "BLOCKS", "ITEMS", "ENTITIES", "EFFECTS", "ENCHANTMENTS", "ACHIEVEMENTS", "ATTRIBUTES", "PARTICLES", "SOUNDS",
			"LISTS", "BLOCKTAGS", "ITEMTAGS", "ENTITYTAGS", "GENERATEDTAGS", "END" };

	public static void init()
	{
		String[] data = FileHelper.readFileArray("data.txt").toArray(new String[0]);
		String[] dataCustom = FileHelper.readFileArray("customData.txt").toArray(new String[0]);
		if (data.length == 0) return;
		Map<String, String[]> convertedData = convert(data, dataCustom);

		initItems(convertedData.get("ITEMS"));
		initEntities(convertedData.get("ENTITIES"));
		initEffects(convertedData.get("EFFECTS"));
		initEnchants(convertedData.get("ENCHANTMENTS"));
		initAchievements(convertedData.get("ACHIEVEMENTS"));
		initAttributes(convertedData.get("ATTRIBUTES"));
		initParticles(convertedData.get("PARTICLES"));
		initSounds(convertedData.get("SOUNDS"));
		initLists(convertedData.get("LISTS"));
		initTags(convertedData.get("BLOCKTAGS"), 0);
		initTags(convertedData.get("ITEMTAGS"), 1);
		initTags(convertedData.get("ENTITYTAGS"), 2);
		initTags(convertedData.get("GENERATEDTAGS"), 3);

		Registerer.setupEntityList();

	}

	private static void initTags(String[] tags, int type)
	{
		String[][] data = new String[tags.length][];
		for (int i = 0; i < tags.length; i++)
		{
			data[i] = tags[i].split(",");
		}
		switch (type)
		{
			case 0:
				DataTags.blocks = data;
				break;
			case 1:
				DataTags.items = data;
				break;
			case 2:
				DataTags.entities = data;
				break;
			case 3:
				for (int i = 0; i < data.length; i++)
				{
					data[i] = new String[] { data[i][0], data[i][1], "air" };
				}
				DataTags.generated = data;
				break;
			default:
				break;
		}
	}

	private static void initItems(String[] items)
	{
		for (String item : items)
		{
			String[] itemData = item.split(",");
			Item create = new Item(Boolean.parseBoolean(itemData[0]), Integer.parseInt(itemData[1]), itemData[2]);
			if (itemData.length > 3)
			{
				for (int i = 3; i < itemData.length; i++)
				{
					String[] data = itemData[i].split("=");
					if (data[0].equals("damage_custom")) create = new ItemData(Boolean.parseBoolean(itemData[0]), Integer.parseInt(itemData[1]), itemData[2],
							convertMeta(data[1]));
					else if (data[0].equals("damage")) create.setMaxDamage(data[1]);
					else if (data[0].equals("gif")) create.setHasGif(data[1]);
					else if (data[0].equals("durability")) create.setDurability(data[1]);
				}
			}
		}
	}

	private static int[] convertMeta(String text)
	{
		String[] metaStrings = text.split(":");
		int[] meta = new int[metaStrings.length];
		for (int i = 0; i < meta.length; i++)
		{
			meta[i] = Integer.parseInt(metaStrings[i]);
		}
		return meta;
	}

	private static void initEntities(String[] entities)
	{
		entities = entities[0].split(",");
		for (String id : entities)
			new Entity(id);
	}

	private static void initEffects(String[] effects)
	{
		for (int i = 0; i < effects[0].split(",").length; i++)
			new EffectType(i + 1, effects[0].split(",")[i]);
	}

	private static void initEnchants(String[] enchants)
	{
		for (String enchant : enchants)
		{
			String[] enchantData = enchant.split(",");
			new EnchantType(Integer.parseInt(enchantData[0]), enchantData[1], Integer.parseInt(enchantData[2]));
		}
	}

	private static void initAchievements(String[] achs)
	{
		for (String achievement : achs)
		{
			String[] achData = achievement.split(",");
			new Achievement(achData[0], (Item) Registerer.getObjectFromId(achData[1]));
		}
	}

	private static void initAttributes(String[] attributes)
	{
		attributes = attributes[0].split(",");
		for (String id : attributes)
		{
			new AttributeType(id);
		}
	}

	private static void initParticles(String[] particles)
	{
		particles = particles[0].split(",");
		for (String part : particles)
		{
			if (part.startsWith("BLOCK")) new Particle(part.substring("BLOCK".length()), Particle.BLOCK);
			else if (part.startsWith("ITEM")) new Particle(part.substring("ITEM".length()), Particle.ITEM);
			else new Particle(part, Particle.NORMAL);
		}
	}

	private static void initSounds(String[] sounds)
	{
		for (String line : sounds)
		{
			for (String sound : line.split(","))
				new Sound(sound);
		}
	}

	private static void initLists(String[] lists)
	{
		for (String list : lists)
		{
			String[] listData = list.split(":");
			Registerer.registerList(listData[0], listData[1].split(","));
		}
		Registerer.registerList("durability", Item.durabilityList.toArray(new String[0]));
	}

	private static Map<String, String[]> convert(String[] data, String[] dataCustom)
	{

		Map<String, String[]> converted = new HashMap<String, String[]>();
		int index = 1, indexCustom = 1;
		List<String> dataPart = new ArrayList<String>();

		for (int i = 0; i < dataTypes.length - 1; i++)
		{

			while (!data[index].contains("/** " + dataTypes[i + 1]))
			{
				dataPart.add(data[index]);
				index++;
			}

			while (!dataCustom[indexCustom].contains("/** " + dataTypes[i + 1]))
			{
				dataPart.add(dataCustom[indexCustom]);
				indexCustom++;
			}

			converted.put(dataTypes[i], dataPart.toArray(new String[] {}));
			dataPart.clear();
			index++;
			indexCustom++;
		}

		String[] blocks = converted.get("BLOCKS"), items = converted.get("ITEMS");
		String[] itemsNew = new String[blocks.length + items.length];

		for (index = 0; index < blocks.length; index++)
			itemsNew[index] = "true," + blocks[index];
		for (int i = 0; i < items.length; i++)
		{
			itemsNew[index] = "false," + items[i];
			index++;
		}

		converted.remove("BLOCKS");
		converted.remove("ITEMS");
		converted.put("ITEMS", itemsNew);

		return converted;
	}

}
