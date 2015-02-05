package commandGenerator.gui.helper.argumentSelection;

import java.awt.Dimension;
import java.util.Map;

import javax.swing.JLabel;

import commandGenerator.arguments.objects.Item;
import commandGenerator.arguments.objects.ItemStack;
import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.objects.Registerer;
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
		addLine(comboboxId, labelImage);
		addLine(spinnerDamage, labelName);
		if (data) add(panelData);
	}

	@Override
	protected void createComponents()
	{
		labelImage = new JLabel();
		labelName = new JLabel();
		labelName.setPreferredSize(new Dimension(20, 20));
		labelName.setMinimumSize(new Dimension(20, 20));

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
		return (Item) Registerer.getObjectFromId(comboboxId.getValue());
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
		spinnerDamage.setValues(0, generateBlock().getMaxDamage());
		if (data) panelData.updateCombobox((Item) Registerer.getObjectFromId(comboboxId.getValue()));
		labelImage.setIcon(generateBlock().getTexture(getDamage()));
		labelName.setText(generateBlock().getName(getDamage()));
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
