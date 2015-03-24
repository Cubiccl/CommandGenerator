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

import commandGenerator.arguments.objects.ItemStack;
import commandGenerator.arguments.objects.Registry;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.gui.helper.argumentSelection.ItemSelectionPanel;
import commandGenerator.gui.helper.components.button.CButton;
import commandGenerator.gui.helper.components.panel.HelperPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class EquipmentSelectionPanel extends HelperPanel
{

	private CButton buttonAddHand, buttonAddFeet, buttonAddLegs, buttonAddChest, buttonAddHead, buttonRemoveHand, buttonRemoveFeet, buttonRemoveLegs,
			buttonRemoveChest, buttonRemoveHead;
	private JEditorPane editorpane;
	private ItemStack[] equipment;
	private JScrollPane scrollpane;

	public EquipmentSelectionPanel(String title)
	{
		super(title);
	}

	@Override
	protected void addComponents()
	{
		addLine(buttonAddHead, buttonRemoveHead);
		addLine(buttonAddChest, buttonRemoveChest);
		addLine(buttonAddLegs, buttonRemoveLegs);
		addLine(buttonAddFeet, buttonRemoveFeet);
		addLine(buttonAddHand, buttonRemoveHand);
		add(scrollpane);
	}

	private void addItem(int slot)
	{
		ItemSelectionPanel panel = new ItemSelectionPanel("GUI:item", Registry.getList(CGConstants.LIST_ITEMS), true, false);
		if (DisplayHelper.showQuestion(panel, Lang.get("GUI:item.add"))) return;
		equipment[slot] = new ItemStack(panel.generateItem(), panel.getDamage(), panel.getCount(), panel.getItemTag());
		displayItems();
	}

	@Override
	protected void createComponents()
	{
		equipment = new ItemStack[] { null, null, null, null, null };

		buttonAddHand = new CButton("GUI:equipment.add.hand");
		buttonAddFeet = new CButton("GUI:equipment.add.feet");
		buttonAddLegs = new CButton("GUI:equipment.add.legs");
		buttonAddChest = new CButton("GUI:equipment.add.chest");
		buttonAddHead = new CButton("GUI:equipment.add.head");
		buttonRemoveHand = new CButton("GUI:equipment.remove.hand");
		buttonRemoveFeet = new CButton("GUI:equipment.remove.feet");
		buttonRemoveLegs = new CButton("GUI:equipment.remove.legs");
		buttonRemoveChest = new CButton("GUI:equipment.remove.chest");
		buttonRemoveHead = new CButton("GUI:equipment.remove.head");

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
		buttonAddHand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				addItem(0);
			}
		});
		buttonAddFeet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				addItem(1);
			}
		});
		buttonAddLegs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				addItem(2);
			}
		});
		buttonAddChest.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				addItem(3);
			}
		});
		buttonAddHead.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				addItem(4);
			}
		});
		buttonRemoveHand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				removeItem(0);
			}
		});
		buttonRemoveFeet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				removeItem(1);
			}
		});
		buttonRemoveLegs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				removeItem(2);
			}
		});
		buttonRemoveChest.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				removeItem(3);
			}
		});
		buttonRemoveHead.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				removeItem(4);
			}
		});
	}

	private void displayItems()
	{
		String[] parts = { Lang.get("GUI:slot.hand"), Lang.get("GUI:slot.feet"), Lang.get("GUI:slot.legs"), Lang.get("GUI:slot.chest"),
				Lang.get("GUI:slot.head") };
		String text = "";
		for (int i = 0; i < equipment.length; i++)
		{
			if (i != 0) text += "<br />";
			if (equipment[i] == null) text += parts[i] + ": " + Lang.get("GENERAL:nothing");
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
		if (value.size() < 5) DisplayHelper.log("Error : Missing Equipment.");

		for (int i = 0; i < value.size() && i < 5; i++)
			equipment[i] = ItemStack.generateFrom((TagCompound) value.get(i));
		displayItems();
	}

	@Override
	protected void setupDetails(Object[] details)
	{
		equipment = new ItemStack[] { null, null, null, null, null };
	}

	@Override
	public void updateLang()
	{
		buttonAddChest.updateLang();
		buttonAddFeet.updateLang();
		buttonAddHand.updateLang();
		buttonAddHead.updateLang();
		buttonAddLegs.updateLang();
		buttonRemoveChest.updateLang();
		buttonRemoveFeet.updateLang();
		buttonRemoveHand.updateLang();
		buttonRemoveHead.updateLang();
		buttonRemoveLegs.updateLang();
		displayItems();
	}

}
