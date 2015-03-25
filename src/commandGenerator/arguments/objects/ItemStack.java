package commandGenerator.arguments.objects;

import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagInt;
import commandGenerator.arguments.tags.TagString;

public class ItemStack
{

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
		if (this.count != -1) display += Integer.toString(this.count) + " ";
		display += this.item.getName(this.damage);
		if (this.slot != -1) display += ", slot : " + this.slot;
		if (this.tag != null) display += ", NBT : " + this.tag.display(details - 1, lvls + 1);
		return display;
	}

	/** Returns the number of Items in this Stack. */
	public int getCount()
	{
		return this.count;
	}

	/** Returns the Damage of this Stack's Items. */
	public int getDamage()
	{
		return this.damage;
	}

	/** Returns the Item type of this Stack. */
	public Item getItem()
	{
		return this.item;
	}

	/** Returns the slot this Stack's Items are in. */
	public int getSlot()
	{
		return this.slot;
	}

	/** Returns the NBT Tags this Stack's Items have. */
	public TagCompound getTag()
	{
		return this.tag;
	}

	/** Turns this ItemStack into a TagCompound. */
	public TagCompound toNBT(String id)
	{
		TagCompound item = new TagCompound(id) {
			@Override
			public void askValue()
			{}
		};

		item.addTag(new TagString("id").setValue(this.getItem().getCommandId()));
		item.addTag(new TagInt("Damage").setValue(this.getDamage()));
		item.addTag(new TagInt("Count").setValue(this.getCount()));
		if (this.slot != -1) item.addTag(new TagInt("Slot").setValue(this.slot));
		if (this.tag != null && !this.tag.commandStructure().substring(this.tag.getId().length() + 1).equals("{}")) item.addTag(this.tag);

		return item;
	}

}
