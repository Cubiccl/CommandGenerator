package generator.registry;

import generator.main.Utils;

public class Item extends ItemBlock
{
	public Item(int idNum, String idString)
	{
		this(idNum, idString, false);
	}

	public Item(int idNum, String idString, boolean block)
	{
		super(Utils.ITEM, idNum, idString, block);
	}

}
