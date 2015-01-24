package commandGenerator.gui.helper.argumentSelection;

import java.awt.Dimension;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import commandGenerator.arguments.objects.Item;
import commandGenerator.arguments.objects.ItemStack;
import commandGenerator.arguments.objects.ObjectBase;
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

	private JLabel labelImage, labelName;
	private TextCombobox comboboxId;
	private NumberSpinner spinnerDamage, spinnerCount, spinnerSlot;
	private NBTTagPanel panelData;
	private Item[] itemList;
	protected boolean withData, slot;

	public ItemSelectionPanel(String id, String title, ObjectBase[] itemList, boolean withData, boolean slot)
	{
		super(id, title, 800, 350);
		this.itemList = new Item[itemList.length];
		for (int i = 0; i < itemList.length; i++)
			this.itemList[i] = (Item) itemList[i];
		this.withData = withData;

		if (!withData) setPreferredSize(new Dimension(600, 100));

		labelImage = new JLabel(itemList[0].getTexture());
		labelName = new JLabel(itemList[0].getName(), SwingConstants.CENTER);
		labelName.setPreferredSize(new Dimension(250, 20));

		String[] ids = new String[itemList.length];
		for (int i = 0; i < ids.length; i++)
			ids[i] = itemList[i].getId();
		comboboxId = new TextCombobox(CGConstants.DATAID_NONE, "GUI:item.id", ids, this);
		spinnerDamage = new NumberSpinner(CGConstants.DATAID_NONE, "GUI:item.damage", 0, ((Item) itemList[0]).getMaxDamage(), this);
		spinnerCount = new NumberSpinner(CGConstants.DATAID_NONE, "GUI:item.count", 1, 64, null);
		if (slot) spinnerSlot = new NumberSpinner(CGConstants.DATAID_NONE, "GUI:item.slot", 0, 27, null);

		if (withData)
		{
			panelData = new NBTTagPanel("GUI:item.nbt", itemList[0], DataTags.items);
		}

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(comboboxId, gbc);
		gbc.gridy++;
		add(spinnerDamage, gbc);
		gbc.gridy++;
		add(spinnerCount, gbc);
		if (slot)
		{
			gbc.gridy++;
			add(spinnerSlot, gbc);
		}
		if (withData)
		{
			gbc.gridy++;
			gbc.gridwidth = 2;
			add(panelData, gbc);
			panelData.updateCombobox((Item) itemList[0]);
			gbc.gridwidth = 1;
		}

		gbc.gridy = 0;
		gbc.gridx++;
		gbc.gridheight = 2;
		if (slot) gbc.gridheight++;
		add(labelImage, gbc);

		gbc.gridy = 2;
		if (slot) gbc.gridy++;
		gbc.gridheight = 1;
		add(labelName, gbc);

		updateSpinner();
	}

	public Item generateItem()
	{
		return (Item) ObjectBase.getObjectFromId(comboboxId.getValue());
	}

	public int getCount()
	{
		return spinnerCount.getValue();
	}

	public int getDamage()
	{
		return spinnerDamage.getValue();
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
	public void updateCombobox()
	{
		Item item = generateItem();
		spinnerDamage.setValues(0, item.getMaxDamage());
		if (item.getDurability() > 0) spinnerDamage.setValues(0, item.getDurability());
		if (withData) panelData.updateCombobox(item);
		labelImage.setIcon(item.getTexture(spinnerDamage.getValue()));
		labelName.setText(item.getName(spinnerDamage.getValue()));
	}

	@Override
	public void updateSpinner()
	{
		Item item = generateItem();
		labelImage.setIcon(item.getTexture(spinnerDamage.getValue()));
		labelName.setText(item.getName(spinnerDamage.getValue()));
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

	public void setupFrom(Map<String, Object> data)
	{
		super.setupFrom(data);
		Object[] item = (Object[]) data.get(getPanelId());
		if (item == null)
		{
			reset();
			return;
		}

		comboboxId.setSelected((String) item[0]);
		spinnerDamage.setSelected((int) item[1]);
		spinnerCount.setSelected((int) item[2]);
		if (slot) spinnerSlot.setSelected((int) item[3]);
	}

	public ItemStack getItemStack()
	{
		return new ItemStack(generateItem(), getDamage(), getCount(), getItemTag(), getSlot());
	}

}
