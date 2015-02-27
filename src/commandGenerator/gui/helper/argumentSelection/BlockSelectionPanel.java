package commandGenerator.gui.helper.argumentSelection;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import commandGenerator.arguments.objects.Item;
import commandGenerator.arguments.objects.ItemStack;
import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.objects.Registry;
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

	private Item[] blockList;
	private TextCombobox comboboxId;
	private boolean data;
	private JLabel labelName, labelImage;
	private NBTTagPanel panelData;
	private NumberSpinner spinnerDamage;

	public BlockSelectionPanel(String id, String title, ObjectBase[] blockList, boolean data)
	{
		super(id, title, blockList, data);
	}

	@Override
	protected void addComponents()
	{
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		JPanel pan1 = new JPanel(new GridBagLayout());
		pan1.add(comboboxId, gbc);
		gbc.gridy++;
		pan1.add(spinnerDamage, gbc);

		JPanel pan2 = new JPanel(new GridBagLayout());
		gbc.gridy = 0;
		pan2.add(labelImage, gbc);
		gbc.gridy++;
		pan2.add(labelName, gbc);

		addLine(pan1, pan2);

		if (data)
		{
			add(panelData);
			panelData.updateCombobox((Item) blockList[0]);
		}
		
		updateCombobox();
	}

	@Override
	protected void createComponents()
	{
		labelImage = new JLabel();
		labelImage.setPreferredSize(new Dimension(40, 40));
		labelImage.setMinimumSize(new Dimension(40, 40));
		labelName = new JLabel("", JLabel.CENTER);
		labelName.setPreferredSize(new Dimension(200, 20));
		labelName.setMinimumSize(new Dimension(200, 20));

		spinnerDamage = new NumberSpinner(CGConstants.DATAID_NONE, "GUI:block.damage", 0, 0, this);

		String[] ids = new String[blockList.length];
		for (int i = 0; i < ids.length; i++)
			ids[i] = blockList[i].getId();
		comboboxId = new TextCombobox(CGConstants.DATAID_NONE, "GUI:block.id", ids, this);

		if (data) panelData = new NBTTagPanel("GUI:tag.block", blockList[0], DataTags.blocks);
	}

	@Override
	protected void createListeners()
	{}

	public Item generateBlock()
	{
		return (Item) Registry.getObjectFromId(comboboxId.getValue());
	}

	public Item[] getBlockList()
	{
		return blockList;
	}

	public TagCompound getBlockTag()
	{
		return panelData.getNbtTags("BlockEntityTags");
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
		comboboxId.setEnabledContent(enable);
		spinnerDamage.setEnabledContent(enable);
		if (data) panelData.setEnabledContent(enable);
	}

	@Override
	protected void setupDetails(Object[] details)
	{
		ObjectBase[] list = (ObjectBase[]) details [0];
		this.data = (boolean) details[1];
		
		this.blockList = new Item[list.length];
		for (int i = 0; i < list.length; i++)
			this.blockList[i] = (Item) list[i];
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

	@Override
	public void updateCombobox()
	{
		Item item = generateBlock();
		spinnerDamage.setValues(0, item.getMaxDamage());
		if (data) panelData.updateCombobox((Item) Registry.getObjectFromId(comboboxId.getValue()));
		labelImage.setIcon(item.getTexture(getDamage()));
		labelName.setText(item.getName(getDamage()));
	}

	@Override
	public void updateLang()
	{
		super.updateLang();
		updateSpinner();
	}

	@Override
	public void updateSpinner()
	{
		labelImage.setIcon(generateBlock().getTexture(getDamage()));
		labelName.setText(generateBlock().getName(getDamage()));
	}

}
