package commandGenerator.gui.helper.argumentSelection;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import commandGenerator.Generator;
import commandGenerator.arguments.objects.Item;
import commandGenerator.arguments.objects.ItemStack;
import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.tags.DataTags;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagInt;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.gui.helper.argumentSelection.dataTag.NBTTagPanel;
import commandGenerator.gui.helper.components.button.CButton;
import commandGenerator.gui.helper.components.button.LoadButton;
import commandGenerator.gui.helper.components.button.SaveButton;
import commandGenerator.gui.helper.components.combobox.LabeledSearchBox;
import commandGenerator.gui.helper.components.icomponent.IBox;
import commandGenerator.gui.helper.components.icomponent.ISave;
import commandGenerator.gui.helper.components.icomponent.ISpin;
import commandGenerator.gui.helper.components.panel.CPanel;
import commandGenerator.gui.helper.components.spinner.ListSpinner;
import commandGenerator.gui.helper.components.spinner.NumberSpinner;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class ItemSelectionPanel extends CPanel implements IBox, ISpin, ISave
{
	private static final int[] SLOTS = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31,
			32, 33, 34, 35, 100, 101, 102, 103 };

	private CButton buttonSave, buttonLoad;
	private LabeledSearchBox comboboxId;
	private Item[] itemList;
	private JLabel labelImage, labelName;
	private NBTTagPanel panelData;
	private NumberSpinner spinnerCount;
	private ListSpinner spinnerDamage, spinnerSlot;
	protected boolean withData, slot;

	public ItemSelectionPanel(String title, ObjectBase[] itemList, boolean withData, boolean slot)
	{
		super(title);
		if (itemList.length == 0) itemList = Generator.registry.getList(CGConstants.LIST_ITEMS);
		this.itemList = new Item[itemList.length];
		this.withData = withData;
		this.slot = slot;

		for (int i = 0; i < itemList.length; i++)
			this.itemList[i] = (Item) itemList[i];

		this.initGui();
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
			panelData.updateCombobox(itemList[0]);
		}

		addLine(buttonSave, buttonLoad);

		updateSpinner();
	}

	@Override
	protected void createComponents()
	{
		labelImage = new JLabel();
		labelImage.setPreferredSize(new Dimension(40, 40));
		labelImage.setMinimumSize(new Dimension(40, 40));
		labelName = new JLabel("", SwingConstants.CENTER);
		labelName.setPreferredSize(new Dimension(200, 40));
		labelName.setMinimumSize(new Dimension(200, 40));

		buttonSave = new SaveButton(ObjectBase.ITEM, this);
		buttonLoad = new LoadButton(ObjectBase.ITEM, this);

		String[] ids = new String[itemList.length];
		for (int i = 0; i < ids.length; i++)
			ids[i] = itemList[i].getId();

		comboboxId = new LabeledSearchBox("GUI:item.id", ids, this);

		spinnerDamage = new ListSpinner("GUI:item.damage", new int[0], this);

		spinnerCount = new NumberSpinner("GUI:item.count", 1, 64, null);

		if (slot) spinnerSlot = new ListSpinner("GUI:item.slot", SLOTS, null);

		if (withData) panelData = new NBTTagPanel("GUI:item.nbt", itemList[0], DataTags.items);

		this.updateCombobox();
	}

	@Override
	protected void createListeners()
	{}

	public Item generateItem()
	{
		return (Item) Generator.registry.getObjectFromId((String) comboboxId.getSelectedItem());
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
			@Override
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
		if (!this.withData) return new TagCompound("tag") {
			@Override
			public void askValue()
			{}
		};
		return panelData.getNbtTags("tag");
	}

	@Override
	public Object getObjectToSave()
	{
		return getItemStack();
	}

	public int getSlot()
	{
		if (!this.slot) return -1;
		return spinnerSlot.getValue();
	}

	@Override
	public void load(Object object)
	{
		setupFrom((ItemStack) object);
	}

	public void setAmount(int parseInt)
	{
		this.spinnerCount.setSelected(parseInt);
	}

	public void setDamage(int damage)
	{
		this.spinnerDamage.setSelected(damage);
	}

	public void setDataTags(List<Tag> list)
	{
		this.panelData.setupFrom(list);
	}

	public void setItem(Item item)
	{
		this.comboboxId.setSelectedItem(item.getId());
	}

	public void setupFrom(ItemStack item)
	{
		if (item == null)
		{
			reset();
			return;
		}

		comboboxId.setSelectedItem(item.getItem().getId());
		spinnerDamage.setSelected(item.getDamage());
		spinnerCount.setSelected(item.getCount());
		if (slot) spinnerSlot.setSelected(item.getSlot());
		if (item.getTag() != null && this.withData) this.panelData.setupFrom(item.getTag().getValue());
	}

	@Override
	public void updateCombobox()
	{
		Item item = generateItem();
		if (item != null) this.spinnerDamage.setValues(item.getDamageList());
		if (this.withData) panelData.updateCombobox(item);
		this.labelImage.setIcon(item.getTexture(this.spinnerDamage.getValue()));
		this.labelName.setText("<html><center>" + item.getName(getDamage()) + "</center></html>");
	}

	@Override
	public void updateLang()
	{
		this.updateSpinner();
		super.updateLang();
	}

	@Override
	public void updateSpinner()
	{
		Item item = generateItem();
		this.labelImage.setIcon(item.getTexture(this.spinnerDamage.getValue()));
		this.labelName.setText("<html><center>" + item.getName(getDamage()) + "</center></html>");
	}

}
