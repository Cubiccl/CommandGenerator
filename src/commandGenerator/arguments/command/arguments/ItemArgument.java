package commandGenerator.arguments.command.arguments;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import commandGenerator.arguments.command.Argument;
import commandGenerator.arguments.objects.ItemStack;
import commandGenerator.arguments.objects.Registry;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.gui.helper.argumentSelection.ItemSelectionPanel;
import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.main.CGConstants;

public class ItemArgument extends Argument implements INBTArgument
{

	private CCheckBox box;
	private ItemSelectionPanel panel;
	private boolean[] display;

	public ItemArgument(String id, boolean isCompulsery)
	{
		super(id, Argument.ITEM, isCompulsery, 1);
		this.display = new boolean[] { false, false, false, false };
	}

	public ItemArgument setDisplay(boolean displayId, boolean displayAmount, boolean displayDamage, boolean displayNBT)
	{
		this.display[0] = displayId;
		this.display[1] = displayAmount;
		this.display[2] = displayDamage;
		this.display[3] = displayNBT;
		return this;
	}

	@Override
	public Component generateComponent()
	{
		JPanel panelGeneral = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		if (!this.isCompulsery())
		{
			panelGeneral.add(box, gbc);
			gbc.gridy++;
		}
		panelGeneral.add(this.panel, gbc);
		return panelGeneral;
	}

	@Override
	public void initGui()
	{
		this.panel = new ItemSelectionPanel(this.getId(), "GUI:" + this.getId(), Registry.getList(CGConstants.LIST_ITEMS), true, false);
		if (!this.isCompulsery())
		{
			this.box = new CCheckBox(this.getId(), "GUI:" + this.getId() + ".use");
			this.box.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0)
				{
					panel.setEnabledContent(box.isSelected());
				}
			});
			this.panel.setEnabledContent(false);
		}

	}

	@Override
	public String generateCommand()
	{
		ItemStack stack = this.panel.getItemStack();
		String command = "";

		if (this.display[0]) command += stack.getItem().getCommandId() + " ";
		if (this.display[1]) command += stack.getCount() + " ";
		if (this.display[2]) command += stack.getDamage() + " ";
		if (this.display[3])
		{
			TagCompound tag = stack.getTag();
			if (tag == null) return null;
			command += tag.commandStructure().substring(tag.getId().length() + 1) + " ";
		}

		return command.substring(0, command.length() - 1);
	}

	@Override
	public boolean isUsed()
	{
		return this.isCompulsery() || this.box.isSelected();
	}

	@Override
	public TagCompound getNBT()
	{
		return this.panel.getItemTag();
	}

}
