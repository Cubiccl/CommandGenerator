package commandGenerator.gui.helper.argumentSelection.dataTag;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import commandGenerator.arguments.objects.Entity;
import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;

public class TagSelection
{

	public static Tag[] getAllowedTags(Tag[] tags, ObjectBase item)
	{
		if (item == null) return new Tag[0];
		if (item == Entity.entity) return tags;

		List<Tag> allowed = new ArrayList<Tag>();
		for (Tag tag : tags)
		{
			for (ObjectBase object : tag.getApplicable())
			{
				if (item == object) allowed.add(tag);
			}
		}

		return allowed.toArray(new Tag[0]);
	}

	public static void askRemove(List<Tag> tags)
	{
		if (tags.size() == 0)
		{
			DisplayHelper.showWarning("WARNING:tag.remove.none");
		}
		tags.remove(JOptionPane.showInputDialog(null, Lang.get("GUI:tag.remove.desc"), Lang.get("GUI:tag.remove.title"), JOptionPane.QUESTION_MESSAGE, null,
				tags.toArray(new Tag[0]), tags.get(0)));
	}

	public static void askRemoveText(List<String> texts)
	{
		texts.remove(JOptionPane.showInputDialog(null, Lang.get("GUI:text.remove.desc"), Lang.get("GUI:text.remove.title"), JOptionPane.QUESTION_MESSAGE, null,
				texts.toArray(new String[0]), texts.get(0)));
	}

}
