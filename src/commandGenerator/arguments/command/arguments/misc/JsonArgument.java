package commandGenerator.arguments.command.arguments.misc;

import java.awt.Component;
import java.util.List;

import commandGenerator.arguments.command.Argument;
import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.tags.DataTags;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.argumentSelection.dataTag.ListSelectionPanel;

public class JsonArgument extends Argument
{

	private ListSelectionPanel panel;

	public JsonArgument(String id, boolean isCompulsery)
	{
		super(id, isCompulsery);
	}

	@Override
	public String generateCommand()
	{
		return new TagList() {
			@Override
			public void askValue()
			{}
		}.setValue(this.panel.getList()).commandStructure();
	}

	@Override
	public Component generateComponent()
	{
		return this.panel;
	}

	@Override
	public void initGui()
	{
		this.panel = new ListSelectionPanel("GUI:" + this.getId(), ObjectBase.JSON);
	}

	@Override
	public boolean isUsed()
	{
		return true;
	}

	@Override
	public boolean matches(List<String> data)
	{
		return data.get(0).startsWith("{") && data.get(0).endsWith("}");
	}

	@Override
	public void setupFrom(List<String> data)
	{
		this.panel.setList(DataTags.generateListFrom(data.get(0)));
	}

	@Override
	public void synchronize(Argument toMatch)
	{
		if (!(toMatch instanceof JsonArgument)) return;
		this.panel.setList(((JsonArgument) toMatch).panel.getList());
	}

}
