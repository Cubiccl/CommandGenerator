package commandGenerator.gui.helper.argumentSelection;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import commandGenerator.arguments.objects.Item;
import commandGenerator.arguments.objects.ItemStack;
import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.tags.DataTags;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.gui.helper.argumentSelection.dataTag.NBTTagPanel;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.gui.helper.components.IBox;
import commandGenerator.gui.helper.components.ISpin;
import commandGenerator.gui.helper.components.NumberSpinner;
import commandGenerator.gui.helper.components.TextCombobox;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class BlockSelectionPanel extends HelperPanel implements IBox, ISpin
{

	private JLabel labelName, labelImage;
	private NumberSpinner spinnerDamage;
	private TextCombobox comboboxId;
	private NBTTagPanel panelData;
	private Item[] blockList;
	private boolean data;

	public BlockSelectionPanel(String id, String title, ObjectBase[] blockList, boolean data)
	{
		super(id, title, 800, 300);
		this.data = data;
		this.blockList = new Item[blockList.length];
		for (int i = 0; i < blockList.length; i++)
			this.blockList[i] = (Item) blockList[i];

		if (!data) setPreferredSize(new Dimension(600, 100));

		labelImage = new JLabel(((Item) blockList[0]).getTexture(0));
		labelName = new JLabel(((Item) blockList[0]).getName(0), SwingConstants.CENTER);
		labelName.setPreferredSize(new Dimension(250, 20));

		spinnerDamage = new NumberSpinner(CGConstants.DATAID_NONE, "GUI:block.damage", 0, ((Item) blockList[0]).getMaxDamage(), this);

		String[] ids = new String[blockList.length];
		for (int i = 0; i < ids.length; i++)
			ids[i] = blockList[i].getId();
		comboboxId = new TextCombobox(CGConstants.DATAID_NONE, "GUI:block.id", ids, this);

		if (data) panelData = new NBTTagPanel("GUI:tag.block", ObjectBase.getObjectFromId("air"), DataTags.blocks);

		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(comboboxId, gbc);
		gbc.gridx++;
		add(labelImage, gbc);
		gbc.gridx--;
		gbc.gridy++;
		add(spinnerDamage, gbc);
		gbc.gridx++;
		add(labelName, gbc);
		gbc.gridx--;
		gbc.gridy++;
		gbc.gridwidth = 2;
		if (data) add(panelData, gbc);
		gbc.gridwidth = 1;

	}

	public Item generateBlock()
	{
		return (Item) ObjectBase.getObjectFromId(comboboxId.getValue());
	}

	public Item[] getBlockList()
	{
		return blockList;
	}

	public int getDamage()
	{
		return spinnerDamage.getValue();
	}

	public void setEnabledContent(boolean enable)
	{
		super.setEnabledContent(enable);
		labelImage.setEnabled(enable);
		labelName.setEnabled(enable);
	}

	public TagCompound getBlockTag()
	{
		return panelData.getNbtTags("BlockEntityTags");
	}

	@Override
	public void updateLang()
	{
		super.updateLang();
		updateSpinner();
	}

	@Override
	public void updateCombobox()
	{
		spinnerDamage.setValues(0, generateBlock().getMaxDamage());
		if (data) panelData.updateCombobox((Item) ObjectBase.getObjectFromId(comboboxId.getValue()));
		labelImage.setIcon(generateBlock().getTexture(getDamage()));
		labelName.setText(generateBlock().getName(getDamage()));
	}

	@Override
	public void updateSpinner()
	{
		labelImage.setIcon(generateBlock().getTexture(getDamage()));
		labelName.setText(generateBlock().getName(getDamage()));
	}

	@Override
	public void setupFrom(Map<String, Object> data)
	{
		ItemStack block = (ItemStack) data.get(getPanelId());
		if (block == null)
		{
			reset();
			return;
		}

		comboboxId.setSelected(block.getItem().getId());
		spinnerDamage.setSelected(block.getDamage());
		if (this.data)
		{
			if (block.getTag() != null) data.put(CGConstants.PANELID_NBT, block.getTag().getValue());
			panelData.setupFrom(data);
		}
	}

}
