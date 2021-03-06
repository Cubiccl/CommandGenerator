package commandGenerator.arguments.tags;

import java.util.ArrayList;
import java.util.List;

import commandGenerator.Generator;
import commandGenerator.main.CGConstants;

public abstract class TagList extends Tag
{

	private List<Tag> value;

	public TagList()
	{
		this("");
	}

	public TagList(String id, String... applicable)
	{
		super(id, Tag.LIST, applicable);
		value = new ArrayList<Tag>();
	}

	@Override
	public String commandStructure()
	{
		String nbt = getId() + ":[";
		if (nbt.equals(":[")) nbt = "[";
		for (int i = 0; i < value.size(); i++)
		{
			if (i != 0) nbt += ",";
			nbt += value.get(i).commandStructure();
		}
		return nbt + "]";
	}

	@Override
	public String display(int details, int lvls)
	{
		if (details == CGConstants.DETAILS_NONE) return getName() + "...";

		String text = getName();
		if (text != "") text += " : ";
		if (value.size() == 0) return text + Generator.translate("GENERAL:empty");
		for (int i = 0; i < value.size(); i++)
		{
			text += "<br />";
			for (int j = 0; j < lvls; j++)
				text += "|";
			text += "- " + value.get(i).display(details, lvls + 1);
		}

		return text;
	}

	public Tag get(int index)
	{
		return value.get(index);
	}

	public List<Tag> getValue()
	{
		return value;
	}

	public TagList setValue(List<Tag> value)
	{
		if (value == null) return this;
		this.value = value;
		return this;
	}

}
