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
import commandGenerator.arguments.objects.ObjectLists;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.gui.helper.argumentSelection.ItemSelectionPanel;
import commandGenerator.gui.helper.components.CButton;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class EquipmentSelectionPanel extends HelperPanel
{

	private CButton buttonAddHand, buttonAddFeet, buttonAddLegs, buttonAddChest, buttonAddHead, buttonRemoveHand, buttonRemoveFeet, buttonRemoveLegs,
			buttonRemoveChest, buttonRemoveHead;
	private JEditorPane editorpane;
	private JScrollPane scrollpane;
	private ItemStack[] equipment;

	public EquipmentSelectionPanel(String title)
	{
		super(CGConstants.DATAID_NONE, title, 600, 200);

		equipment = new ItemStack[] { null, null, null, null, null };

		buttonAddHand = new CButton("GUI:equipment.add.hand");
		buttonAddHand.setPreferredSize(new Dimension(150, 20));
		buttonAddHand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				addItem(0);
			}
		});
		buttonAddFeet = new CButton("GUI:equipment.add.feet");
		buttonAddFeet.setPreferredSize(new Dimension(150, 20));
		buttonAddFeet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				addItem(1);
			}
		});
		buttonAddLegs = new CButton("GUI:equipment.add.legs");
		buttonAddLegs.setPreferredSize(new Dimension(150, 20));
		buttonAddLegs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				addItem(2);
			}
		});
		buttonAddChest = new CButton("GUI:equipment.add.chest");
		buttonAddChest.setPreferredSize(new Dimension(150, 20));
		buttonAddChest.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				addItem(3);
			}
		});
		buttonAddHead = new CButton("GUI:equipment.add.head");
		buttonAddHead.setPreferredSize(new Dimension(150, 20));
		buttonAddHead.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				addItem(4);
			}
		});

		buttonRemoveHand = new CButton("GUI:equipment.remove.hand");
		buttonRemoveHand.setPreferredSize(new Dimension(200, 20));
		buttonRemoveHand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				removeItem(0);
			}
		});
		buttonRemoveFeet = new CButton("GUI:equipment.remove.feet");
		buttonRemoveFeet.setPreferredSize(new Dimension(200, 20));
		buttonRemoveFeet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				removeItem(1);
			}
		});
		buttonRemoveLegs = new CButton("GUI:equipment.remove.legs");
		buttonRemoveLegs.setPreferredSize(new Dimension(200, 20));
		buttonRemoveLegs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				removeItem(2);
			}
		});
		buttonRemoveChest = new CButton("GUI:equipment.remove.chest");
		buttonRemoveChest.setPreferredSize(new Dimension(200, 20));
		buttonRemoveChest.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				removeItem(3);
			}
		});
		buttonRemoveHead = new CButton("GUI:equipment.remove.head");
		buttonRemoveHead.setPreferredSize(new Dimension(200, 20));
		buttonRemoveHead.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				removeItem(4);
			}
		});

		editorpane = new JEditorPane("text/html", "");
		editorpane.setEditable(false);
		editorpane.setPreferredSize(new Dimension(200, 120));
		displayItems();

		scrollpane = new JScrollPane(editorpane);
		scrollpane.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(buttonAddHead, gbc);
		gbc.gridy++;
		add(buttonAddChest, gbc);
		gbc.gridy++;
		add(buttonAddLegs, gbc);
		gbc.gridy++;
		add(buttonAddFeet, gbc);
		gbc.gridy++;
		add(buttonAddHand, gbc);

		gbc.gridx++;
		gbc.gridy = 0;
		add(buttonRemoveHead, gbc);
		gbc.gridy++;
		add(buttonRemoveChest, gbc);
		gbc.gridy++;
		add(buttonRemoveLegs, gbc);
		gbc.gridy++;
		add(buttonRemoveFeet, gbc);
		gbc.gridy++;
		add(buttonRemoveHand, gbc);

		gbc.gridx++;
		gbc.gridy = 0;
		gbc.gridheight = 5;
		add(scrollpane, gbc);
		gbc.gridheight = 1;
	}

	private void addItem(int slot)
	{
		ItemSelectionPanel panel = new ItemSelectionPanel(CGConstants.DATAID_NONE, "GUI:item", ObjectLists.get(CGConstants.LIST_ITEMS), true, false);
		if (DisplayHelper.showQuestion(panel, Lang.get("GUI:item.add"))) return;
		equipment[slot] = new ItemStack(panel.generateItem(), panel.getDamage(), panel.getCount(), panel.getItemTag());
		displayItems();
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

	public void setup(List<Tag> value)
	{
		if (value.size() < 5) DisplayHelper.log("Error : Missing Equipment.");

		for (int i = 0; i < value.size() && i < 5; i++)
			equipment[i] = ItemStack.generateFrom((TagCompound) value.get(i));
		displayItems();
	}

}
