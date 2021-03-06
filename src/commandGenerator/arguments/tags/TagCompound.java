package commandGenerator.arguments.tags;

import java.util.ArrayList;
import java.util.List;

import commandGenerator.Generator;
import commandGenerator.main.CGConstants;

public abstract class TagCompound extends Tag
{

	private List<Tag> value;

	public TagCompound()
	{
		this("");
	}

	public TagCompound(String id, String... applicable)
	{
		super(id, Tag.COMPOUND, applicable);
		value = new ArrayList<Tag>();
	}

	public void addTag(Tag tag)
	{
		if (tag == null) return;
		value.add(tag);
	}

	public void clear()
	{
		value.clear();
	}

	@Override
	public String commandStructure()
	{
		String nbt = "";
		if (!getId().equals("")) nbt += getId() + ":";
		nbt += "{";

		for (int i = 0; i < value.size(); i++)
		{
			if (i != 0) nbt += ",";
			nbt += value.get(i).commandStructure();
		}

		return nbt + "}";
	}

	public void deleteTag(String id)
	{
		for (int i = 0; i < size(); i++)
		{
			if (value.get(i).getId().equals(id))
			{
				value.remove(i);
				return;
			}
		}
	}

	@Override
	public String display(int details, int lvls)
	{
		if (details == CGConstants.DETAILS_NONE) return getName() + "...";

		String text = getName();
		if (text != "") text += " : ";
		for (int i = 0; i < size(); i++)
		{
			text += "<br />";
			for (int j = 0; j < lvls; j++)
				text += "|";
			text += "* " + value.get(i).display(details - 1, lvls + 1);
		}

		if (size() == 0) return text + Generator.translate("GENERAL:empty");
		return text;
	}

	public boolean doesTagExist(String id)
	{
		boolean flag = false;
		for (int i = 0; i < value.size(); i++)
		{
			if (value.get(i).getId().equals(id)) flag = true;
		}
		return flag;
	}

	public Tag get(int index)
	{
		return value.get(index);
	}

	public Tag getTag(int index)
	{
		return value.get(index);
	}

	public List<Tag> getValue()
	{
		return value;
	}

	public TagCompound setValue(List<Tag> value)
	{
		if (value != null) this.value = value;
		return this;
	}

	public int size()
	{
		return value.size();
	}

}
