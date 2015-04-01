package commandGenerator.arguments.command.arguments;

import java.awt.Component;
import java.util.List;

import commandGenerator.arguments.command.Argument;
import commandGenerator.arguments.objects.Entity;
import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.objects.Registry;
import commandGenerator.arguments.tags.DataTags;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;

public class EntityArgument extends Argument implements INBTArgument
{

	private boolean[] display;
	private EntitySelectionPanel panel;

	public EntityArgument(String id, boolean isCompulsery)
	{
		super(id, isCompulsery);
		this.display = new boolean[] { false, false };
		this.setMaximumLength(2);
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
	public Component generateComponent()
	{
		return this.panel;
	}

	@Override
	public TagCompound getNBT()
	{
		return this.panel.getEntityTag();
	}

	@Override
	public void initGui()
	{
		this.panel = new EntitySelectionPanel("GUI:" + this.getId(), Registry.getObjectList(ObjectBase.ENTITY));
	}

	@Override
	public boolean isUsed()
	{
		return true;
	}

	@Override
	public boolean matches(List<String> data)
	{
		boolean ok = true;
		if (this.display[0]) ok = Registry.exists(data.get(0), ObjectBase.ENTITY);
		return ok;
	}

	public EntityArgument setDisplay(boolean displayEntity, boolean displayNBT)
	{
		this.display[0] = displayEntity;
		this.display[1] = displayNBT;
		return this;
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

	@Override
	public void synchronize(Argument toMatch)
	{
		if (!(toMatch instanceof EntityArgument)) return;
		this.panel.setupFrom(((EntityArgument) toMatch).panel.getEntity());
		this.panel.setDataTags(((EntityArgument) toMatch).panel.getTagList());
	}

}
