package generator.registry;

import generator.main.Constants;

public class Item extends ItemBlock
{
	public Item(int idNum, String idString)
	{
		this(idNum, idString, false);
	}

	public Item(int idNum, String idString, boolean block)
	{
		super(Constants.ITEM, idNum, idString, block);
	}

}
