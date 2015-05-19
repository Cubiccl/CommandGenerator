package commandGenerator.arguments.command.arguments;

import java.awt.Component;
import java.util.List;

import commandGenerator.Generator;
import commandGenerator.arguments.command.Argument;
import commandGenerator.arguments.objects.Entity;
import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.tags.DataTags;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;

public class EntityArgument extends Argument implements INBTArgument
{

	private boolean[] display;
	private ObjectBase[] entities;
	private EntitySelectionPanel panel;

	public EntityArgument(String id, boolean isCompulsery)
	{
		super(id, isCompulsery);
		this.display = new boolean[] { false, false };
		this.setMaximumLength(2);
		this.entities = Generator.registry.getObjectList(ObjectBase.ENTITY);
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
		System.out.println("NBT");
		return this.panel.getEntityTag();
	}

	@Override
	public void initGui()
	{
		this.panel = new EntitySelectionPanel(this.getId(), this.entities);
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
		if (this.display[0]) ok = Generator.registry.exists(data.get(0), ObjectBase.ENTITY);
		return ok;
	}

	public EntityArgument setDisplay(boolean displayEntity, boolean displayNBT)
	{
		this.display[0] = displayEntity;
		this.display[1] = displayNBT;

		int max = 0;
		for (boolean flag : this.display)
			if (flag) max++;
		this.setMaximumLength(max);

		return this;
	}

	@Override
	public void setupFrom(List<String> data)
	{
		this.panel.reset();
		int index = 0;
		if (this.display[0])
		{
			this.panel.setSelected((Entity) Generator.registry.getObjectFromId(data.get(index)));
			index++;
		}
		if (this.display[1])
		{
			List<Tag> tags = DataTags.generateListFrom(data.get(index));
			ObjectBase entity = DataTags.getObjectFromTags(tags);
			if (entity instanceof Entity) this.panel.setSelected((Entity) entity);
			this.panel.setDataTags(tags);
		}
	}

	@Override
	public void synchronize(Argument toMatch)
	{
		if (!(toMatch instanceof EntityArgument)) return;
		this.panel.setupFrom(((EntityArgument) toMatch).panel.getEntity());
		this.panel.setDataTags(((EntityArgument) toMatch).panel.getTagList());
	}

	@Override
	public void setupNBT(List<Tag> data)
	{
		this.panel.setDataTags(data);
	}

	@Override
	public void reset()
	{
		this.panel.reset();
	}

	public EntityArgument setEntities(ObjectBase... entities)
	{
		this.entities = entities;
		return this;
	}

}
