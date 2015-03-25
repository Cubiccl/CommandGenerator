package commandGenerator.arguments.tags.specific;

import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.argumentSelection.dataTag.ListSelectionPanel;

public class TagEffects extends TagList
{

	public TagEffects(String id, String... applicable)
	{
		super(id, applicable);
	}

	@Override
	public void askValue()
	{
		panel = new ListSelectionPanel("TAGS:" + getId(), ObjectBase.EFFECT);
		((ListSelectionPanel) panel).setList(getValue());
		if (showPanel()) return;
		setValue(((ListSelectionPanel) panel).getList());
	}

}
