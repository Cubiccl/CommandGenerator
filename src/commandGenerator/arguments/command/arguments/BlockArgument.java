package commandGenerator.arguments.command.arguments;

import java.awt.Component;
import java.util.List;

import commandGenerator.arguments.command.Argument;
import commandGenerator.arguments.objects.Item;
import commandGenerator.arguments.objects.ItemStack;
import commandGenerator.arguments.objects.Registry;
import commandGenerator.arguments.tags.DataTags;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.gui.helper.argumentSelection.BlockSelectionPanel;
import commandGenerator.main.CGConstants;

public class BlockArgument extends Argument implements INBTArgument
{

	private String listId;
	private boolean canHaveNBT;
	private BlockSelectionPanel panel;
	private boolean[] display;

	public BlockArgument(String id, boolean isCompulsery, String listId, boolean canHaveNBT)
	{
		super(id, Argument.BLOCK, isCompulsery, 1);
		this.listId = listId;
		this.canHaveNBT = canHaveNBT;
		this.display = new boolean[] { false, false, false };
		this.setMaximumLength(3);
	}

	@Override
	public Component generateComponent()
	{
		return this.panel;
	}

	@Override
	public void initGui()
	{
		this.panel = new BlockSelectionPanel(this.getId(), "GUI:" + this.getId(), Registry.getList(this.listId), this.canHaveNBT);
	}

	@Override
	public String generateCommand()
	{
		ItemStack stack = this.panel.getBlockAsItemStack();
		if (stack == null) return null;

		String command = "";
		if (this.display[0]) command += stack.getItem().getCommandId() + " ";
		if (this.display[1]) command += stack.getDamage() + " ";
		if (this.display[2])
		{
			TagCompound tag = stack.getTag();
			command += tag.commandStructure().substring(tag.getId().length() + 1) + " ";
		}
		if (command.length() > 0) command = command.substring(0, command.length() - 1);

		return command;
	}

	@Override
	public TagCompound getNBT()
	{
		return this.panel.getBlockTag();
	}

	public BlockArgument setDisplay(boolean displayId, boolean displayDamage, boolean displayNBT)
	{
		this.display[0] = displayId;
		this.display[1] = displayDamage;
		this.display[2] = displayNBT;
		return this;
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
		int index = 0;
		if (this.display[0] && data.size() > index)
		{
			ok = Registry.exists(data.get(index), CGConstants.OBJECT_ITEM);
			index++;
		}
		if (this.display[1] && data.size() > index)
		{
			try
			{
				Integer.parseInt(data.get(index));
			} catch (Exception e)
			{
				ok = false;
			}
			index++;
		}
		return ok;
	}

	@Override
	public void setupFrom(List<String> data)
	{
		int index = 0;
		if (this.display[0] && data.size() > index)
		{
			this.panel.setBlock((Item) Registry.getObjectFromId(data.get(index)));
			index++;
		}
		if (this.display[1] && data.size() > index)
		{
			this.panel.setDamage(Integer.parseInt(data.get(index)));
			index++;
		}
		if (this.display[2] && data.size() > index) this.panel.setDataTags(DataTags.generateListFrom(data.get(index)));
	}

}
