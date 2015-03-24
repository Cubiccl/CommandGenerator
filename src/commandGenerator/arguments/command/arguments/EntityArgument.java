package commandGenerator.arguments.command.arguments;

import java.awt.Component;
import java.util.List;

import commandGenerator.arguments.command.Argument;
import commandGenerator.arguments.objects.Entity;
import commandGenerator.arguments.objects.Registry;
import commandGenerator.arguments.tags.DataTags;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.main.CGConstants;

public class EntityArgument extends Argument implements INBTArgument
{

	private EntitySelectionPanel panel;
	private boolean[] display;

	public EntityArgument(String id, boolean isCompulsery)
	{
		super(id, Argument.ENTITY, isCompulsery, 1);
		this.display = new boolean[] { false, false };
		this.setMaximumLength(2);
	}

	public EntityArgument setDisplay(boolean displayEntity, boolean displayNBT)
	{
		this.display[0] = displayEntity;
		this.display[1] = displayNBT;
		return this;
	}

	@Override
	public Component generateComponent()
	{
		return this.panel;
	}

	@Override
	public void initGui()
	{
		this.panel = new EntitySelectionPanel(this.getId(), "GUI:" + this.getId(), Registry.getObjectList(CGConstants.OBJECT_ENTITY));
	}

	@Override
	public String generateCommand()
	{
		String command = "";
		if (this.display[0])
		{
			Entity entity = this.panel.getEntity();
			if (entity == null) return null;
			command += entity.getId() + " ";
		}
		if (this.display[1])
		{
			TagCompound tag = this.panel.getEntityTag();
			if (tag == null) return null;
			command += tag.commandStructure().substring(tag.getId().length() + 1) + " ";
		}
		return command.substring(0, command.length() - 1);
	}

	@Override
	public boolean isUsed()
	{
		return true;
	}

	@Override
	public TagCompound getNBT()
	{
		return this.panel.getEntityTag();
	}

	@Override
	public boolean matches(List<String> data)
	{
		boolean ok = true;
		if (this.display[0]) ok = Registry.exists(data.get(0), CGConstants.OBJECT_ENTITY);
		return ok;
	}

	@Override
	public void setupFrom(List<String> data)
	{
		int index = 0;
		if (this.display[0])
		{
			this.panel.setEntity((Entity) Registry.getObjectFromId(data.get(index)));
			index++;
		}
		if (this.display[1]) this.panel.setDataTags(DataTags.generateListFrom(data.get(index)));
	}

}
