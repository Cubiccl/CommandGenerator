package commandGenerator.arguments.objects;

import java.util.List;

import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagInt;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.main.DisplayHelper;

public class ItemStack
{

	private Item item;
	private int damage, count, slot;
	private TagCompound tag;

	public ItemStack(Item item, int damage)
	{
		this(item, damage, 1, null, -1);
	}

	public ItemStack(Item item, int damage, int count)
	{
		this(item, damage, count, null, -1);
	}

	public ItemStack(Item item, int damage, int count, TagCompound tag)
	{
		this(item, damage, count, tag, -1);
	}

	/** Creates a new ItemStack.
	 * 
	 * @param item
	 *            - The Item.
	 * @param damage
	 *            - The Item damage.
	 * @param count
	 *            - The Item count.
	 * @param tag
	 *            - The NBT tags.
	 * @param slot
	 *            - The Item slot. */
	public ItemStack(Item item, int damage, int count, TagCompound tag, int slot)
	{
		this.item = item;
		this.damage = damage;
		this.count = count;
		this.tag = tag;
		this.slot = slot;
	}

	public String display(int details, int lvls)
	{
		String display = Integer.toString(count) + " " + item.getName(damage);
		if (slot != -1) display += ", slot : " + slot;
		if (tag != null) display += ", NBT : " + tag.display(details - 1, lvls + 1);
		return display;
	}

	public int getSlot()
	{
		return slot;
	}

	public int getCount()
	{
		return count;
	}

	public int getDamage()
	{
		return damage;
	}

	public Item getItem()
	{
		return item;
	}

	public TagCompound getTag()
	{
		return tag;
	}

	/** Turns this ItemStack into a TagCompound. */
	public TagCompound toNBT(String id)
	{
		TagCompound item = new TagCompound(id) {
			public void askValue()
			{}
		};

		item.addTag(new TagString("id").setValue(getItem().getId()));
		item.addTag(new TagInt("Damage").setValue(getDamage()));
		item.addTag(new TagInt("Count").setValue(getCount()));
		if (slot != -1) item.addTag(new TagInt("Slot").setValue(this.slot));
		if (tag != null && !tag.commandStructure().substring(tag.getId().length() + 1).equals("{}")) item.addTag(this.tag);

		return item;
	}

	public static ItemStack generateFrom(TagCompound tag)
	{
		Item item = (Item) Registerer.getObjectFromId("stone");
		int damage = 0, count = 1, slot = -1;
		TagCompound nbt = new TagCompound() {
			public void askValue()
			{}
		};
		for (int i = 0; i < tag.size(); i++)
		{
			Tag part = tag.get(i);
			if (part.getId().equals("id")) item = (Item) Registerer.getObjectFromId(((TagString) part).getValue());
			if (part.getId().equals("Damage")) damage = ((TagInt) part).getValue();
			if (part.getId().equals("Count")) count = ((TagInt) part).getValue();
			if (part.getId().equals("Slot")) slot = ((TagInt) part).getValue();
			if (part.getId().equals("tag")) nbt = ((TagCompound) part);
		}

		return new ItemStack(item, damage, count, nbt, slot);
	}

	public static ItemStack generateFrom(String id, int damage, int count, List<Tag> nbt, int slot)
	{
		Item item = (Item) Registerer.getObjectFromId(id);
		if (!(item instanceof Item)) return null;
		TagCompound tag = new TagCompound("tag") {
			public void askValue()
			{}
		};
		tag.setValue(nbt);

		DisplayHelper.log("Created Item : " + count + " " + ((Item) Registerer.getObjectFromId(id)).getName(damage) + " in slot " + slot);
		return new ItemStack(item, damage, count, tag, slot);
	}

	public static ItemStack generateBlockFrom(String id, int damage, List<Tag> nbt)
	{
		return generateFrom(id, damage, -1, nbt, -1);
	}

}
