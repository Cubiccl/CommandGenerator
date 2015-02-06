package commandGenerator.gui.helper.argumentSelection;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import commandGenerator.arguments.objects.Item;
import commandGenerator.arguments.objects.ItemData;
import commandGenerator.arguments.objects.ItemStack;
import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.objects.Registerer;
import commandGenerator.arguments.tags.DataTags;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagInt;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.gui.helper.argumentSelection.dataTag.NBTTagPanel;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.gui.helper.components.IBox;
import commandGenerator.gui.helper.components.ISpin;
import commandGenerator.gui.helper.components.NumberSpinner;
import commandGenerator.gui.helper.components.TextCombobox;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class ItemSelectionPanel extends HelperPanel implements IBox, ISpin
{

	private TextCombobox comboboxId;
	private Item[] itemList;
	private JLabel labelImage, labelName;
	private NBTTagPanel panelData;
	private NumberSpinner spinnerDamage, spinnerCount, spinnerSlot;
	protected boolean withData, slot;

	public ItemSelectionPanel(String id, String title, ObjectBase[] itemList, boolean withData, boolean slot)
	{
		super(id, title, itemList, withData, slot);
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
		gbc.gridy++;
		pan1.add(spinnerCount, gbc);
		gbc.gridy++;
		if (slot) pan1.add(spinnerSlot, gbc);

		JPanel pan2 = new JPanel(new GridBagLayout());
		gbc.gridy = 0;
		pan2.add(labelImage, gbc);
		gbc.gridy++;
		pan2.add(labelName, gbc);

		addLine(pan1, pan2);

		if (withData)
		{
			add(panelData);
			panelData.updateCombobox((Item) itemList[0]);
		}

		updateSpinner();
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

		String[] ids = new String[itemList.length];
		for (int i = 0; i < ids.length; i++)
			ids[i] = itemList[i].getId();

		comboboxId = new TextCombobox(CGConstants.DATAID_NONE, "GUI:item.id", ids, this);
		spinnerDamage = new NumberSpinner(CGConstants.DATAID_NONE, "GUI:item.damage", 0, itemList[0].getMaxDamage(), this);
		if (itemList[0] instanceof ItemData) spinnerDamage.setData(((ItemData) itemList[0]).getDamageList());
		spinnerCount = new NumberSpinner(CGConstants.DATAID_NONE, "GUI:item.count", 1, 64, null);
		if (slot) spinnerSlot = new NumberSpinner(CGConstants.DATAID_NONE, "GUI:item.slot", 0, 27, null);

		if (withData) panelData = new NBTTagPanel("GUI:item.nbt", itemList[0], DataTags.items);

	}

	@Override
	protected void createListeners()
	{}

	public Item generateItem()
	{
		return (Item) Registerer.getObjectFromId(comboboxId.getValue());
	}

	public int getCount()
	{
		return spinnerCount.getValue();
	}

	public int getDamage()
	{
		return spinnerDamage.getValue();
	}

	public Tag getItemAsNBT()
	{
		TagCompound tag = new TagCompound() {
			public void askValue()
			{}
		};

		tag.addTag(new TagString("id").setValue(generateItem().getId()));
		tag.addTag(new TagInt("Damage").setValue(getDamage()));
		tag.addTag(new TagInt("Count").setValue(getCount()));
		if (slot) tag.addTag(new TagInt("Slot").setValue(getSlot()));
		if (withData) tag.addTag(getItemTag());

		return tag;
	}

	public ItemStack getItemStack()
	{
		return new ItemStack(generateItem(), getDamage(), getCount(), getItemTag(), getSlot());
	}

	public TagCompound getItemTag()
	{
		return panelData.getNbtTags("tag");
	}

	public int getSlot()
	{
		return (int) spinnerSlot.getValue();
	}

	public void setEnabledContent(boolean enable)
	{
		setEnabled(enable);
		labelName.setEnabled(enable);
		labelImage.setEnabled(enable);
		comboboxId.setEnabledContent(enable);
		spinnerDamage.setEnabledContent(enable);
		spinnerCount.setEnabledContent(enable);
		if (slot) spinnerSlot.setEnabledContent(enable);
		if (withData) panelData.setEnabledContent(enable);
	}

	@Override
	protected void setupDetails(Object[] details)
	{
		ObjectBase[] list = (ObjectBase[]) details[0];
		this.itemList = new Item[list.length];
		this.withData = (boolean) details[1];
		this.slot = (boolean) details[2];

		for (int i = 0; i < list.length; i++)
			this.itemList[i] = (Item) list[i];
	}

	public void setupFrom(Map<String, Object> data)
	{
		ItemStack item = (ItemStack) data.get(getPanelId());
		if (item == null)
		{
			reset();
			return;
		}

		comboboxId.setSelected(item.getItem().getId());
		spinnerDamage.setSelected(item.getDamage());
		spinnerCount.setSelected(item.getCount());
		if (slot) spinnerSlot.setSelected(item.getSlot());
		if (item.getTag() != null) data.put(CGConstants.PANELID_NBT, item.getTag().getValue());
		super.setupFrom(data);
	}

	@Override
	public void updateCombobox()
	{
		Item item = generateItem();
		if (item instanceof ItemData) spinnerDamage.setData(((ItemData) item).getDamageList());
		else spinnerDamage.setValues(0, item.getMaxDamage());
		if (item.getDurability() > 0) spinnerDamage.setValues(0, item.getDurability());
		if (withData) panelData.updateCombobox(item);
		labelImage.setIcon(item.getTexture(spinnerDamage.getValue()));
		labelName.setText(item.getName(spinnerDamage.getValue()));
	}

	@Override
	public void updateLang()
	{
		updateTitle();
		updateSpinner();
		spinnerDamage.updateLang();
		spinnerCount.updateLang();
		comboboxId.updateLang();
		if (slot) spinnerSlot.updateLang();
		if (withData) panelData.updateLang();
	}

	@Override
	public void updateSpinner()
	{
		Item item = generateItem();
		labelImage.setIcon(item.getTexture(spinnerDamage.getValue()));
		labelName.setText(item.getName(spinnerDamage.getValue()));
	}

}
