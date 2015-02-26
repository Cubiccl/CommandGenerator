package commandGenerator.arguments.tags.specific;

import java.util.ArrayList;
import java.util.List;

import commandGenerator.arguments.objects.Attribute;
import commandGenerator.arguments.objects.AttributeType;
import commandGenerator.arguments.objects.Registerer;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagDouble;
import commandGenerator.arguments.tags.TagInt;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.gui.helper.argumentSelection.dataTag.ListSelectionPanel;
import commandGenerator.main.Constants;
import commandGenerator.main.Lang;

public class TagAttributes extends TagList
{
	private boolean forMob;

	public TagAttributes(String id, boolean forMob, String... applicable)
	{
		super(id, applicable);
		this.forMob = forMob;
	}

	@Override
	public void askValue()
	{
		panel = new ListSelectionPanel("TAGS:" + getId(), Constants.OBJECT_ATTRIBUTE);
		((ListSelectionPanel) panel).setList(getValue());
		if (showPanel()) return;
		if (!forMob)
		{
			setValue(((ListSelectionPanel) panel).getList());
			return;
		}

		TagList list = new TagList("Modifiers") {
			public void askValue()
			{}
		};
		list.setValue(((ListSelectionPanel) panel).getList());

		List<Tag> tag = new ArrayList<Tag>();
		tag.add(list);
		tag.add(new TagString("Name").setValue("Name"));
		tag.add(new TagDouble("Base").setValue(1.0d));

		setValue(tag);
	}

	@Override
	public String commandStructure()
	{
		String command = super.commandStructure();
		if (!forMob) return command;

		String newCommand = command.substring(0, command.indexOf('['));
		newCommand += "{";
		newCommand += command.substring(command.indexOf('[') + 1, command.length() - 1);
		newCommand += "}";

		return newCommand;
	}

	@Override
	public String display(int details, int lvls)
	{
		String text = getName();
		if (text != "") text += " : ";

		List<Tag> value;
		if (!forMob) value = getValue();
		else
		{
			value = ((TagList) getValue().get(0)).getValue();
		}

		if (value.size() == 0) return text + Lang.get("GENERAL:empty");
		for (int i = 0; i < value.size(); i++)
		{
			text += "<br />";
			for (int j = 0; j < lvls; j++)
				text += "|";
			text += "- ";
			TagCompound attribute = (TagCompound) value.get(i);
			String id = "generic.maxHealth";
			double amount = 1.0D;
			int operation = 0;

			for (int j = 0; j < attribute.size(); j++)
			{
				if (attribute.get(j).getId().equals("AttributeName")) id = ((TagString) attribute.get(j)).getValue();
				if (attribute.get(j).getId().equals("Amount")) amount = ((TagDouble) attribute.get(j)).getValue();
				if (attribute.get(j).getId().equals("Operation")) operation = ((TagInt) attribute.get(j)).getValue();
			}
			text += new Attribute((AttributeType) Registerer.getObjectFromId(id), amount, operation).display();
		}

		return text;
	}

}
