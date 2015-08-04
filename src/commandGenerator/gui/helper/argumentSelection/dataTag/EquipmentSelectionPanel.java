package commandGenerator.gui.helper.argumentSelection.dataTag;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

import commandGenerator.Generator;
import commandGenerator.arguments.objects.ItemStack;
import commandGenerator.arguments.objects.ObjectCreator;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.gui.helper.GuiHandler;
import commandGenerator.gui.helper.argumentSelection.ItemSelectionPanel;
import commandGenerator.gui.helper.components.button.CButton;
import commandGenerator.gui.helper.components.panel.CPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class EquipmentSelectionPanel extends CPanel
{
	private String[] slots;
	private CButton[] buttons;
	private JEditorPane editorpane;
	private ItemStack[] equipment;
	private JScrollPane scrollpane;

	public EquipmentSelectionPanel(String title, String... slots)
	{
		super(title);
		this.equipment = new ItemStack[slots.length];
		this.slots = slots;

		this.initGui();
	}

	@Override
	protected void addComponents()
	{
		for (int i = 0; i < this.slots.length; i++)
		{
			this.addLine(this.buttons[i * 2], this.buttons[i * 2 + 1]);
		}
		this.add(scrollpane);
	}

	private void addItem(int slot)
	{
		ItemSelectionPanel panel = new ItemSelectionPanel("GENERAL:item", Generator.registry.getList(CGConstants.LIST_ITEMS), true, false);
		if (DisplayHelper.showQuestion(panel, Generator.translate("GUI:item.add"))) return;
		equipment[slot] = new ItemStack(panel.generateItem(), panel.getDamage(), panel.getCount(), panel.getItemTag());
		displayItems();
	}

	@Override
	protected void createComponents()
	{
		this.equipment = new ItemStack[this.slots.length];
		this.buttons = new CButton[this.slots.length * 2];
		for (int i = 0; i < this.slots.length; i++)
		{
			CButton buttonAdd = new CButton("GUI:equipment.add." + this.slots[i]);
			CButton buttonRemove = new CButton("GUI:equipment.remove." + this.slots[i]);

			if (i == 0)
			{
				buttonAdd.setDrawType(GuiHandler.FULL - GuiHandler.TOP_LEFT);
				buttonRemove.setDrawType(GuiHandler.FULL - GuiHandler.TOP_RIGHT);
			} else if (i == this.slots.length - 1)
			{
				buttonAdd.setDrawType(GuiHandler.FULL - GuiHandler.BOTTOM_LEFT);
				buttonRemove.setDrawType(GuiHandler.FULL - GuiHandler.BOTTOM_RIGHT);
			} else
			{
				buttonAdd.setDrawType(GuiHandler.FULL);
				buttonRemove.setDrawType(GuiHandler.FULL);
			}

			this.buttons[i * 2] = buttonAdd;
			this.buttons[i * 2 + 1] = buttonRemove;
		}

		editorpane = new JEditorPane("text/html", "");
		editorpane.setEditable(false);
		displayItems();

		scrollpane = new JScrollPane(editorpane);
		scrollpane.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
		scrollpane.setPreferredSize(new Dimension(200, 120));
		scrollpane.setMinimumSize(new Dimension(200, 120));
	}

	@Override
	protected void createListeners()
	{
		for (int i = 0; i < this.slots.length; i++)
		{
			final int slot = i;
			this.buttons[i * 2].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					addItem(slot);
				}
			});
			this.buttons[i * 2 + 1].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					removeItem(slot);
				}
			});
		}
	}

	private void displayItems()
	{
		String[] parts = new String[this.slots.length];
		String text = "";
		for (int i = 0; i < equipment.length; i++)
		{
			parts[i] = Generator.translate("GUI:slot." + this.slots[i]);
			if (i != 0) text += "<br />";
			if (equipment[i] == null) text += parts[i] + ": " + Generator.translate("GENERAL:nothing");
			else text += parts[i] + ": " + equipment[i].display(CGConstants.DETAILS_ALL, 0);
		}
		editorpane.setText(text);
	}

	public List<Tag> getSelectedEquipment()
	{

		if (equipment[0] == null && equipment[1] == null && equipment[2] == null && equipment[3] == null && equipment[4] == null) return new ArrayList<Tag>();
		List<Tag> tags = new ArrayList<Tag>();
		for (int i = 0; i < equipment.length; i++)
		{
			if (equipment[i] == null) tags.add(new TagCompound() {
				@Override
				public void askValue()
				{}
			});
			else tags.add(equipment[i].toNBT(""));
		}
		return tags;

	}

	private void removeItem(int slot)
	{
		equipment[slot] = null;
		displayItems();
	}

	public void setup(List<Tag> value)
	{
		if (value.size() < this.slots.length) DisplayHelper.log("Error : Missing Equipment.");

		for (int i = 0; i < value.size() && i < this.slots.length; i++)
			this.equipment[i] = ObjectCreator.generateItemStack((TagCompound) value.get(i));
		this.displayItems();
	}

}
