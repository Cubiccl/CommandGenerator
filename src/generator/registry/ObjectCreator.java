package generator.registry;

import generator.CommandGenerator;
import generator.main.Constants;
import generator.main.FileManager;
import generator.main.Utils;

public final class ObjectCreator
{

	private static void createAchievement(String[] data)
	{
		try
		{
			Achievement achievement = new Achievement(data[0], CommandGenerator.getRegistry().getItemFromId(data[1]));
			CommandGenerator.getRegistry().registerAchievement(achievement);
		} catch (Exception e)
		{
			CommandGenerator.log("Malformed Achievement : " + Utils.toString(data, ","));
			CommandGenerator.log(e);
		}
	}

	private static void createAttribute(String[] data)
	{
		try
		{
			Attribute attribute = new Attribute(data[0]);
			CommandGenerator.getRegistry().registerAttribute(attribute);
		} catch (Exception e)
		{
			CommandGenerator.log("Malformed Attribute : " + Utils.toString(data, ","));
			CommandGenerator.log(e);
		}
	}

	private static void createBlock(String[] data)
	{
		try
		{
			Block block = new Block(Integer.parseInt(data[0]), data[1]);
			boolean item = true;

			for (int i = 2; i < data.length; i++)
			{
				String property = data[i];
				if (property.equals("blockOnly")) item = false;
				if (property.startsWith("damage=")) block.setDamage(Integer.parseInt(property.substring("damage=".length())));
				if (property.startsWith("damage_custom=")) block.setDamage(createCustomDamage(property.substring("damage_custom=".length())));
				if (property.startsWith("texture=")) block.setTextureType(Integer.parseInt(property.substring("texture=".length())));
				if (property.startsWith("lang=")) block.setLangType(property.substring("lang=".length()));
			}

			CommandGenerator.getRegistry().registerBlock(block);

			if (item) createItemFromBlock(data);
		} catch (Exception e)
		{
			CommandGenerator.log("Malformed Block : " + Utils.toString(data, ","));
			CommandGenerator.log(e);
		}
	}

	private static int[] createCustomDamage(String data)
	{
		String[] values = data.split(":");
		int[] damage = new int[values.length];
		for (int i = 0; i < damage.length; i++)
		{
			damage[i] = Integer.parseInt(values[i]);
		}
		return damage;
	}

	private static void createEffect(String[] data)
	{
		try
		{
			Effect effect = new Effect(Integer.parseInt(data[0]), data[1]);
			CommandGenerator.getRegistry().registerEffect(effect);
		} catch (Exception e)
		{
			CommandGenerator.log("Malformed Effect : " + Utils.toString(data, ","));
			CommandGenerator.log(e);
		}
	}

	private static void createEnchantment(String[] data)
	{
		try
		{
			Enchantment enchantment = new Enchantment(Integer.parseInt(data[0]), data[1], Integer.parseInt(data[2]));
			CommandGenerator.getRegistry().registerEnchantment(enchantment);
		} catch (Exception e)
		{
			CommandGenerator.log("Malformed Enchantment : " + Utils.toString(data, ","));
			CommandGenerator.log(e);
		}
	}

	private static void createEntity(String[] data)
	{
		try
		{
			Entity entity = new Entity(data[0]);
			CommandGenerator.getRegistry().registerEntity(entity);
			if (data.length > 1)
			{
				if (data[1].startsWith("list=")) CommandGenerator.getRegistry().addObjectToList(data[1].substring("list=".length()), entity);
			}
		} catch (Exception e)
		{
			CommandGenerator.log("Malformed Entity : " + Utils.toString(data, ","));
			CommandGenerator.log(e);
		}
	}

	private static void createItem(String[] data)
	{
		try
		{
			Item item = new Item(Integer.parseInt(data[0]), data[1]);

			for (int i = 2; i < data.length; i++)
			{
				String property = data[i];
				if (property.startsWith("damage=")) item.setDamage(Integer.parseInt(property.substring("damage=".length())));
				if (property.startsWith("damage_custom=")) item.setDamage(createCustomDamage(property.substring("damage_custom=".length())));
				if (property.startsWith("durability=")) item.setDurability(Integer.parseInt(property.substring("durability=".length())));
				if (property.startsWith("texture=")) item.setTextureType(Integer.parseInt(property.substring("texture=".length())));
				if (property.startsWith("lang=")) item.setLangType(property.substring("lang=".length()));
			}

			CommandGenerator.getRegistry().registerItem(item);
		} catch (Exception e)
		{
			CommandGenerator.log("Malformed Item : " + Utils.toString(data, ","));
			CommandGenerator.log(e);
		}
	}

	private static void createItemFromBlock(String[] data)
	{
		try
		{
			Item item = new Item(Integer.parseInt(data[0]), data[1], true);
			boolean customDamage = false, customTexture = false, customLang = false;

			for (String element : data)
			{
				if (element.startsWith("item_damage")) customDamage = true;
				if (element.startsWith("item_texture")) customDamage = true;
				if (element.startsWith("item_lang")) customDamage = true;
			}

			for (int i = 2; i < data.length; i++)
			{
				String property = data[i];
				if (!customDamage && property.startsWith("damage=")) item.setDamage(Integer.parseInt(property.substring("damage=".length())));
				if (!customDamage && property.startsWith("damage_custom=")) item.setDamage(createCustomDamage(property.substring("damage_custom=".length())));
				if (property.startsWith("durability=")) item.setDurability(Integer.parseInt(property.substring("durability=".length())));
				if (!customTexture && property.startsWith("texture=")) item.setTextureType(Integer.parseInt(property.substring("texture=".length())));
				if (!customLang && property.startsWith("lang=")) item.setLangType(property.substring("lang=".length()));
				if (property.startsWith("item_damage=")) item.setDamage(Integer.parseInt(property.substring("item_damage=".length())));
				if (property.startsWith("item_texture=")) item.setTextureType(Integer.parseInt(property.substring("item_texture=".length())));
				if (property.startsWith("item_lang=")) item.setLangType(property.substring("item_lang=".length()));
			}

			CommandGenerator.getRegistry().registerItem(item);
		} catch (Exception e)
		{
			CommandGenerator.log("Malformed Item : " + Utils.toString(data, ","));
			CommandGenerator.log(e);
		}
	}

	public static void createObject(int type, String[] data)
	{
		switch (type)
		{
			case Constants.ITEM:
				createItem(data);
				break;

			case Constants.BLOCK:
				createBlock(data);
				break;

			case Constants.ENTITY:
				createEntity(data);
				break;

			case Constants.EFFECT:
				createEffect(data);
				break;

			case Constants.ENCHANTMENT:
				createEnchantment(data);
				break;

			case Constants.ACHIEVEMENT:
				createAchievement(data);
				break;

			case Constants.ATTRIBUTE:
				createAttribute(data);
				break;

			case Constants.PARTICLE:
				createParticle(data);
				break;

			case Constants.SOUND:
				createSounds(data);
				break;

			default:
				break;
		}
	}

	public static void createObjects()
	{
		String[] data = FileManager.readFileAsArray("data-1.8.txt");
		int category = 0;
		for (String line : data)
		{
			if (line.startsWith("//") || line.startsWith("/**")) continue;
			if (line.startsWith("CATEGORY=")) category = Integer.parseInt(line.substring("CATEGORY=".length()));
			else createObject(category, line.split(","));
		}
	}

	private static void createParticle(String[] data)
	{
		try
		{
			Particle particle = new Particle(data[0]);
			CommandGenerator.getRegistry().registerParticle(particle);
		} catch (Exception e)
		{
			CommandGenerator.log("Malformed Particle : " + Utils.toString(data, ","));
			CommandGenerator.log(e);
		}
	}

	private static void createSounds(String[] data)
	{
		for (String id : data)
		{
			try
			{
				Sound sound = new Sound(id);
				CommandGenerator.getRegistry().registerSound(sound);
			} catch (Exception e)
			{
				CommandGenerator.log("Malformed Sound : " + id);
				CommandGenerator.log(e);
			}
		}
	}
}
