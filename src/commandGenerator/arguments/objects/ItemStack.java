package commandGenerator.arguments.objects;

import java.util.List;

import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagInt;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.main.DisplayHelper;

public class ItemStack
{

	/** Generates a new ItemStack from the following data :
	 * 
	 * @param id
	 *            - <i>String</i> - The Item ID.
	 * @param damage
	 *            - <i>int</i> - The Item damage.
	 * @param nbt
	 *            - <i>ArrayList:Tag</i> - The NBT Tags as a list. */
	public static ItemStack generateBlockFrom(String id, int damage, List<Tag> nbt)
	{
		ItemStack stack = generateFrom(id, damage, -1, nbt, -1);
		return new ItemStack((Item) Registry.getObjectFromId(id), stack.getDamage(), -1, stack.getTag(), -1);
	}

	/** Generates a new ItemStack from the following data :
	 * 
	 * @param id
	 *            - <i>String</i> - The Item ID.
	 * @param damage
	 *            - <i>int</i> - The Item damage.
	 * @param count
	 *            - <i>int</i> - The number of Items.
	 * @param nbt
	 *            - <i>ArrayList:Tag</i> - The NBT Tags as a list.
	 * @param slot
	 *            - <i>int</i> - The slot of this stack. */
	public static ItemStack generateFrom(String id, int damage, int count, List<Tag> nbt, int slot)
	{
		Item item = (Item) Registry.getObjectFromId(id);
		if (item.isBlock()) item = (Item) Registry.getObjectFromId(id + "_item");
		if (item == null) item = (Item) Registry.getObjectFromId(id);
		TagCompound tag = new TagCompound("tag") {
			public void askValue()
			{}
		};
		tag.setValue(nbt);

		DisplayHelper.log("Created Item : " + count + " " + ((Item) Registry.getObjectFromId(id)).getName(damage) + " in slot " + slot);
		return new ItemStack(item, damage, count, tag, slot);
	}

	/** Generates an ItemStack from a TagCompound.
	 * 
	 * @param tag
	 *            - <i>TagCompount</i> - The tag used to generate. */
	public static ItemStack generateFrom(TagCompound tag)
	{
		Item item = (Item) Registry.getObjectFromId("stone");
		int damage = 0, count = 1, slot = -1;
		TagCompound nbt = new TagCompound() {
			public void askValue()
			{}
		};
		for (int i = 0; i < tag.size(); i++)
		{
			Tag part = tag.get(i);
			if (part.getId().equals("id")) item = (Item) Registry.getObjectFromId(((TagString) part).getValue());
			if (part.getId().equals("Damage")) damage = ((TagInt) part).getValue();
			if (part.getId().equals("Count")) count = ((TagInt) part).getValue();
			if (part.getId().equals("Slot")) slot = ((TagInt) part).getValue();
			if (part.getId().equals("tag")) nbt = ((TagCompound) part);
		}

		return new ItemStack(item, damage, count, nbt, slot);
	}

	/** The number of Items in this Stack. */
	private int count;
	/** The Damage of the Item. */
	private int damage;

	/** The Item this stack is composed of. */
	private Item item;

	/** The slot this stack's Items are in. */
	private int slot;

	/** The NBT Tags this stack's Items have. */
	private TagCompound tag;

	/** Creates a new ItemStack.
	 * 
	 * @param item
	 *            - <i>Item</i> - The Item this stack is composed of.
	 * @param damage
	 *            - <i>int</i> - The Damage of the Item. */
	public ItemStack(Item item, int damage)
	{
		this(item, damage, 1, null, -1);
	}

	/** Creates a new ItemStack.
	 * 
	 * @param item
	 *            - <i>Item</i> - The Item this stack is composed of.
	 * @param damage
	 *            - <i>int</i> - The Damage of the Item.
	 * @param count
	 *            - <i>int</i> - The number of Items in this Stack. */
	public ItemStack(Item item, int damage, int count)
	{
		this(item, damage, count, null, -1);
	}

	/** Creates a new ItemStack.
	 * 
	 * @param item
	 *            - <i>Item</i> - The Item this stack is composed of.
	 * @param damage
	 *            - <i>int</i> - The Damage of the Item.
	 * @param count
	 *            - <i>int</i> - The number of Items in this Stack.
	 * @param tag
	 *            - <i>TagCompount</i> - The NBT Tags this stack's Items have. */
	public ItemStack(Item item, int damage, int count, TagCompound tag)
	{
		this(item, damage, count, tag, -1);
	}

	/** Creates a new ItemStack.
	 * 
	 * @param item
	 *            - <i>Item</i> - The Item this stack is composed of.
	 * @param damage
	 *            - <i>int</i> - The Damage of the Item.
	 * @param count
	 *            - <i>int</i> - The number of Items in this Stack.
	 * @param tag
	 *            - <i>TagCompound</i> - The NBT Tags this stack's Items have.
	 * @param slot
	 *            - <i>int</i> - The slot this stack's Item are in. */
	public ItemStack(Item item, int damage, int count, TagCompound tag, int slot)
	{
		this.item = item;
		this.damage = damage;
		this.count = count;
		this.tag = tag;
		this.slot = slot;
	}

	/** Returns a String version of this ItemStack to be displayed to the user. */
	public String display(int details, int lvls)
	{
		String display = "";
		if (count != -1) display += Integer.toString(count) + " ";
		display += item.getName(damage);
		if (slot != -1) display += ", slot : " + slot;
		if (tag != null) display += ", NBT : " + tag.display(details - 1, lvls + 1);
		return display;
	}

	/** Returns the number of Items in this Stack. */
	public int getCount()
	{
		return count;
	}

	/** Returns the Damage of this Stack's Items. */
	public int getDamage()
	{
		return damage;
	}

	/** Returns the Item type of this Stack. */
	public Item getItem()
	{
		return item;
	}

	/** Returns the slot this Stack's Items are in. */
	public int getSlot()
	{
		return slot;
	}

	/** Returns the NBT Tags this Stack's Items have. */
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

		item.addTag(new TagString("id").setValue(getItem().getCommandId()));
		item.addTag(new TagInt("Damage").setValue(getDamage()));
		item.addTag(new TagInt("Count").setValue(getCount()));
		if (slot != -1) item.addTag(new TagInt("Slot").setValue(this.slot));
		if (tag != null && !tag.commandStructure().substring(tag.getId().length() + 1).equals("{}")) item.addTag(this.tag);

		return item;
	}

}
