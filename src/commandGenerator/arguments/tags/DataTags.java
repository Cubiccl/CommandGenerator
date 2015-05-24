package commandGenerator.arguments.tags;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import commandGenerator.Generator;
import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.tags.specific.TagAttributes;
import commandGenerator.arguments.tags.specific.TagBlockEntity;
import commandGenerator.arguments.tags.specific.TagCanDestroy;
import commandGenerator.arguments.tags.specific.TagCanPlace;
import commandGenerator.arguments.tags.specific.TagDisabledSlots;
import commandGenerator.arguments.tags.specific.TagDisplay;
import commandGenerator.arguments.tags.specific.TagDropChances;
import commandGenerator.arguments.tags.specific.TagEffects;
import commandGenerator.arguments.tags.specific.TagEnchants;
import commandGenerator.arguments.tags.specific.TagEquipment;
import commandGenerator.arguments.tags.specific.TagExplosion;
import commandGenerator.arguments.tags.specific.TagFireworks;
import commandGenerator.arguments.tags.specific.TagHideFlags;
import commandGenerator.arguments.tags.specific.TagItem;
import commandGenerator.arguments.tags.specific.TagItems;
import commandGenerator.arguments.tags.specific.TagOffers;
import commandGenerator.arguments.tags.specific.TagPages;
import commandGenerator.arguments.tags.specific.TagPatterns;
import commandGenerator.arguments.tags.specific.TagPos;
import commandGenerator.arguments.tags.specific.TagPose;
import commandGenerator.arguments.tags.specific.TagRiding;
import commandGenerator.arguments.tags.specific.TagRotation;
import commandGenerator.arguments.tags.specific.TagSignText;
import commandGenerator.arguments.tags.specific.TagSpawnData;
import commandGenerator.arguments.tags.specific.TagSpawnPotentials;
import commandGenerator.main.DisplayHelper;

public class DataTags
{

	public static String[][] blocks, items, entities, generated;

	private static String[] genElements(String nbt)
	{
		List<String> elements = new ArrayList<String>();
		String[] base = nbt.split(",");

		int realIndex = 0, brackCurv = 0, brackRect = 0;
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
				if (base[i].charAt(j) == '[' && !inString) brackRect++;
				if (base[i].charAt(j) == ']' && !inString) brackRect--;
				if (base[i].charAt(j) == '{' && !inString) brackCurv++;
				if (base[i].charAt(j) == '}' && !inString) brackCurv--;
			}
			if (elements.size() <= realIndex) elements.add(base[i]);
			else elements.set(realIndex, elements.get(realIndex) + "," + base[i]);
			if (!inString && brackCurv == 0 && brackRect == 0) realIndex++;
		}

		return elements.toArray(new String[0]);
	}

	public static List<Tag> generateListFrom(String nbt)
	{
		List<Tag> list = new ArrayList<Tag>();
		if (nbt.length() == 2) return list;
		boolean inList = nbt.startsWith("[");

		nbt = nbt.substring(1, nbt.length() - 1);
		String[] tags = DataTags.genElements(nbt);
		for (String tag : tags)
		{
			Tag result = genTagFromString(tag, inList);
			if (result != null) list.add(result);
		}

		return list;
	}

	private static Tag genTagFromString(String nbt, boolean list)
	{
		DisplayHelper.log("Creating NBT tag : " + nbt);

		if (nbt.startsWith("id:"))
		{
			int sep = nbt.indexOf(':');
			String value = nbt.substring(sep + 1);
			try
			{
				return new TagInt("id").setValue(Integer.parseInt(value));
			} catch (Exception e)
			{
				return new TagString("id").setValue(value.substring(1, value.length() - 1));
			}
		}

		String[] corresponding = { "", "", "air" }; // Air doesn't affect anything. Just to prevent from crashing...
		String value = nbt;
		if (!list)
		{
			int sep = nbt.indexOf(':');
			String id = nbt.substring(0, sep);
			value = nbt.substring(sep + 1);

			for (String[] block : blocks)
				if (block[0].equals(id))
				{
					corresponding[0] = block[0];
					corresponding[1] = block[1];
					if (corresponding[2].equals("air")) corresponding[2] = block[2];
					else corresponding[2] += ":" + block[2];
				}
			for (String[] item : items)
				if (item[0].equals(id))
				{
					corresponding[0] = item[0];
					corresponding[1] = item[1];
					if (corresponding[2].equals("air")) corresponding[2] = item[2];
					else corresponding[2] += ":" + item[2];
				}
			for (String[] entity : entities)
				if (entity[0].equals(id))
				{
					corresponding[0] = entity[0];
					corresponding[1] = entity[1];
					if (corresponding[2].equals("air")) corresponding[2] = entity[2];
					else corresponding[2] += ":" + entity[2];
				}
			for (String[] generate : generated)
			{
				if (generate[0].equals(id)) corresponding = generate;
			}
		} else
		{
			if (nbt.startsWith("\"") && nbt.endsWith("\"")) corresponding[1] = "string";
			else if (nbt.startsWith("{") && nbt.endsWith("}")) corresponding[1] = "compound";
			else if (nbt.endsWith("f") || nbt.endsWith("F")) corresponding[1] = "float";
			else if (nbt.endsWith("d") || nbt.endsWith("D")) corresponding[1] = "double";
			else corresponding[1] = "int";
		}

		Tag tag = init(corresponding);
		if (tag == null)
		{
			DisplayHelper.log("Unknown tag : " + nbt);
			return null;
		}
		byte type = tag.getTagType();

		try
		{
			switch (type)
			{
				case Tag.BOOLEAN:
					((TagBoolean) tag).setValue(value.equals("1") || value.equals("1b"));
					break;

				case Tag.STRING:
					((TagString) tag).setValue(value.substring(1, value.length() - 1));
					break;

				case Tag.INT:
					if (value.endsWith("D") || value.endsWith("d")) value = value.substring(0, value.length() - 1);
					((TagInt) tag).setValue(Integer.parseInt(value));
					break;

				case Tag.FLOAT:
					if (value.endsWith("f") || value.endsWith("F")) value = value.substring(0, value.length() - 1);
					((TagFloat) tag).setValue(Float.parseFloat(value));
					break;

				case Tag.COMPOUND:
					((TagCompound) tag).setValue(generateListFrom(value));
					break;

				case Tag.LIST:
					((TagList) tag).setValue(generateListFrom(value));
					break;

				default:
					break;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			DisplayHelper.log("There was an error while creating tag " + nbt);
			return null;
		}

		return tag;
	}

	public static ObjectBase getObjectFromTags(List<Tag> list)
	{
		if (list.size() == 0) return Generator.registry.getObjectFromId("air");

		List<ObjectBase> applicable = new ArrayList<ObjectBase>();
		for (ObjectBase obj : list.get(0).getApplicable())
			applicable.add(obj);

		for (int i = 1; i < list.size(); i++)
		{
			for (int j = 0; j < applicable.size(); j++)
			{
				boolean both = false;
				for (ObjectBase obj : list.get(0).getApplicable())
				{
					if (obj == applicable.get(j)) both = true;
				}
				if (!both) applicable.remove(j);
			}
			if (applicable.size() == 0) return null;
		}

		if (applicable.size() == 0) return null;
		return applicable.get(0);
	}

	public static Tag init(String[] tag)
	{
		if (tag[1].equals("boolean")) return new TagBoolean(tag[0], tag[2]);
		if (tag[1].equals("string")) return initString(tag);
		if (tag[1].equals("int")) return initInt(tag);
		if (tag[1].equals("float")) return initFloat(tag);
		if (tag[1].equals("double")) return initDouble(tag);
		if (tag[1].equals("item")) return initItem(tag);
		if (tag[1].equals("item_list")) return initItemList(tag);
		if (tag[1].equals("custom")) return initCustom(tag);
		if (tag[1].equals("list")) return new TagList(tag[0]) {
			@Override
			public void askValue()
			{}
		};
		if (tag[1].equals("compound")) return new TagCompound(tag[0]) {
			@Override
			public void askValue()
			{}
		};
		return null;
	}

	private static Tag initCustom(String[] tagData)
	{
		String id = tagData[0];
		if (id.equals("Text1") || id.equals("Text2") || id.equals("Text3") || id.equals("Text4")) return new TagSignText(id, tagData[2]);
		if (id.equals("SpawnPotentials")) return new TagSpawnPotentials(id, tagData[2]);
		if (id.equals("SpawnData")) return new TagSpawnData(id, tagData[2]);
		if (id.equals("Patterns")) return new TagPatterns();

		if (id.equals("display")) return new TagDisplay();
		if (id.equals("HideFlags")) return new TagHideFlags();
		if (id.equals("ench")) return new TagEnchants(id, "LIST=items");
		if (id.equals("StoredEnchantments")) return new TagEnchants(id, "enchanted_book");
		if (id.equals("AttributeModifiers")) return new TagAttributes(id, false, "LIST=items");
		if (id.equals("CanDestroy")) return new TagCanDestroy();
		if (id.equals("CanPlaceOn")) return new TagCanPlace();
		if (id.equals("CustomPotionEffects")) return new TagEffects(id, "potion");
		if (id.equals("pages")) return new TagPages(id, tagData[2]);
		if (id.equals("Explosion")) return new TagExplosion();
		if (id.equals("Fireworks")) return new TagFireworks();
		if (id.equals("BlockEntityTag")) return new TagBlockEntity("BlockEntityTag", "LIST=tileentity");

		if (id.equals("Pos") || id.equals("Motion")) return new TagPos(id, "LIST=allEntities");
		if (id.equals("Rotation")) return new TagRotation();
		if (id.equals("Riding")) return new TagRiding();
		if (id.equals("ActiveEffects")) return new TagEffects(id, "LIST=mob");
		if (id.equals("Attributes")) return new TagAttributes(id, true, "LIST=mob");
		if (id.equals("Equipment")) return new TagEquipment();
		if (id.equals("DropChances")) return new TagDropChances();
		if (id.equals("Offers")) return new TagOffers();
		if (id.equals("DisabledSlots")) return new TagDisabledSlots();
		if (id.equals("Pose")) return new TagPose();

		return null;
	}

	private static Tag initDouble(String[] tagData)
	{
		return new TagDouble(tagData[0], tagData[2]);
	}

	private static Tag initFloat(String[] tagData)
	{
		TagFloat tag = new TagFloat(tagData[0], tagData[2]);
		if (tagData.length > 3)
		{
			for (int i = 3; i < tagData.length; i++)
			{
				String data = tagData[i];
				if (data.startsWith("min"))
				{
					data = data.substring("min=".length());
					tag.setMin(Integer.parseInt(data));
				}
				if (data.startsWith("max"))
				{
					data = data.substring("max=".length());
					tag.setMax(Integer.parseInt(data));
				}
			}
		}
		return tag;
	}

	private static Tag initInt(String[] tagData)
	{
		TagInt tag = new TagInt(tagData[0], tagData[2]);
		if (tagData.length > 3)
		{
			for (int i = 3; i < tagData.length; i++)
			{
				String data = tagData[i];
				if (data.startsWith("min"))
				{
					data = data.substring("min=".length());
					tag.setMin(Integer.parseInt(data));
				}
				if (data.startsWith("max"))
				{
					data = data.substring("max=".length());
					tag.setMax(Integer.parseInt(data));
				}
				if (data.startsWith("labeled"))
				{
					data = data.substring("labeled=".length());
					String[] options = data.split(":");
					tag.setChoicesId(options[0]);
					tag.setChoices(options[1].split(";"));
				}
				if (data.startsWith("values"))
				{
					data = data.substring("values=".length());
					tag.setValues(data.split(";"));
				}
			}
		}
		return tag;
	}

	private static Tag initItem(String[] tagData)
	{
		if (tagData.length == 3) return new TagItem(tagData[0], new String[0], false, tagData[2]);
		TagItem tag;
		if (!tagData[3].startsWith("LIST=")) tag = new TagItem(tagData[0], tagData[3].split(":"), false, tagData[2]);
		else
		{
			List<String> objects = new ArrayList<String>();
			for (ObjectBase object : Generator.registry.getList(tagData[3].substring("LIST=".length())))
				objects.add(object.getId());
			tag = new TagItem(tagData[0], objects.toArray(new String[0]), false, tagData[2]);
		}
		return tag;
	}

	private static Tag initItemList(String[] tagData)
	{
		TagItems tag = new TagItems(tagData[0], tagData[2]);
		return tag;
	}

	private static Tag initString(String[] tagData)
	{
		TagString tag = new TagString(tagData[0], tagData[2]);
		if (tagData.length > 3)
		{
			for (int i = 3; i < tagData.length; i++)
			{
				String data = tagData[i];
				if (data.startsWith("custom"))
				{
					data = data.substring("custom=".length());
					tag.setOptions(data.split(":"));
				}
			}
		}
		return tag;
	}

	public static Tag[] sortTagsByName(Tag[] data)
	{
		ArrayList<Tag> sort = new ArrayList<Tag>();
		for (int i = 0; i < data.length; i++)
		{
			sort.add(data[i]);
		}

		sort.sort(new Comparator<Tag>() {
			public int compare(Tag o1, Tag o2)
			{
				int comparison = o1.getName().compareTo(o2.getName());
				if (comparison < 0) comparison = -1;
				if (comparison > 0) comparison = 1;
				return comparison;
			}
		});

		return sort.toArray(new Tag[0]);
	}

}
