package commandGenerator.gui.helper.argumentSelection;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import commandGenerator.arguments.objects.Item;
import commandGenerator.arguments.objects.ItemStack;
import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.objects.Registry;
import commandGenerator.arguments.tags.DataTags;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.gui.helper.argumentSelection.dataTag.NBTTagPanel;
import commandGenerator.gui.helper.components.button.CButton;
import commandGenerator.gui.helper.components.button.LoadButton;
import commandGenerator.gui.helper.components.button.SaveButton;
import commandGenerator.gui.helper.components.combobox.TextCombobox;
import commandGenerator.gui.helper.components.icomponent.IBox;
import commandGenerator.gui.helper.components.icomponent.ISave;
import commandGenerator.gui.helper.components.icomponent.ISpin;
import commandGenerator.gui.helper.components.panel.HelperPanel;
import commandGenerator.gui.helper.components.spinner.NumberSpinner;

@SuppressWarnings("serial")
public class BlockSelectionPanel extends HelperPanel implements IBox, ISpin, ISave
{

	private Item[] blockList;
	private CButton buttonSave, buttonLoad;
	private TextCombobox comboboxId;
	private boolean data;
	private JLabel labelName, labelImage;
	private NBTTagPanel panelData;
	private NumberSpinner spinnerDamage;

	public BlockSelectionPanel(String title, ObjectBase[] blockList, boolean data)
	{
		super(title, blockList, data);
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
			panelData.updateCombobox(blockList[0]);
		}

		addLine(buttonSave, buttonLoad);

		updateCombobox();
	}

	@Override
	protected void createComponents()
	{
		labelImage = new JLabel();
		labelImage.setPreferredSize(new Dimension(40, 40));
		labelImage.setMinimumSize(new Dimension(40, 40));
		labelName = new JLabel("", SwingConstants.CENTER);
		labelName.setPreferredSize(new Dimension(200, 20));
		labelName.setMinimumSize(new Dimension(200, 20));

		buttonSave = new SaveButton(ObjectBase.BLOCK, this);
		buttonLoad = new LoadButton(ObjectBase.BLOCK, this);

		spinnerDamage = new NumberSpinner("GUI:block.damage", 0, 0, this);

		String[] ids = new String[blockList.length];
		for (int i = 0; i < ids.length; i++)
			ids[i] = blockList[i].getId();
		comboboxId = new TextCombobox("GUI:block.id", ids, this);

		if (data) panelData = new NBTTagPanel("GUI:tag.block", blockList[0], DataTags.blocks);
	}

	@Override
	protected void createListeners()
	{}

	public Item generateBlock()
	{
		return (Item) Registry.getObjectFromId(comboboxId.getValue());
	}

	public ItemStack getBlockAsItemStack()
	{
		Item block = generateBlock();
		int damage = getDamage();
		TagCompound tag = getBlockTag();

		if (block == null) return null;

		return new ItemStack(block, damage, -1, tag, -1);
	}

	public Item[] getBlockList()
	{
		return blockList;
	}

	public TagCompound getBlockTag()
	{
		if (!data) return new TagCompound("BlockEntityTag") {
			@Override
			public void askValue()
			{}
		};
		return panelData.getNbtTags("BlockEntityTag");
	}

	public int getDamage()
	{
		return spinnerDamage.getValue();
	}

	public ItemStack getItemStack()
	{
		return new ItemStack(generateBlock(), getDamage(), -1, getBlockTag(), -1);
	}

	@Override
	public Object getObjectToSave()
	{
		return getItemStack();
	}

	@Override
	public void load(Object object)
	{
		setupFrom((ItemStack) object);
	}

	public void setBlock(Item block)
	{
		this.comboboxId.setSelected(block.getId());
	}

	public void setDamage(int damage)
	{
		this.spinnerDamage.setSelected(damage);
	}

	public void setDataTags(List<Tag> list)
	{
		if (list == null) return;
		this.panelData.setupFrom(list);
	}

	@Override
	protected void setupDetails(Object[] details)
	{
		ObjectBase[] list = (ObjectBase[]) details[0];
		this.data = (boolean) details[1];

		this.blockList = new Item[list.length];
		for (int i = 0; i < list.length; i++)
			this.blockList[i] = (Item) list[i];
	}

	public void setupFrom(ItemStack block)
	{
		if (block == null)
		{
			reset();
			return;
		}

		comboboxId.setSelected(block.getItem().getId());
		spinnerDamage.setSelected(block.getDamage());
		if (this.data)
		{
			this.panelData.setupFrom(block.getTag().getValue());
		}
	}

	@Override
	public void updateCombobox()
	{
		Item item = generateBlock();
		spinnerDamage.setValues(0, item.getMaxDamage());
		if (data) panelData.updateCombobox(Registry.getObjectFromId(comboboxId.getValue()));
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
