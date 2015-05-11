package commandGenerator.arguments.command.arguments.misc;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import commandGenerator.arguments.command.Argument;
import commandGenerator.gui.helper.GuiHandler;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.combobox.BaseComboBox;
import commandGenerator.gui.helper.components.icomponent.IBox;

public class SlotArgument extends Argument implements IBox
{

	private BaseComboBox comboboxSlotType, comboboxSlotIndex;
	private CLabel labelSlot;

	public SlotArgument()
	{
		super("replaceitem.slot");
	}

	@Override
	public String generateCommand()
	{
		String slotType = (String) comboboxSlotType.getSelectedItem();
		if (slotType.equals("slot.weapon") || slotType.equals("slot.horse.saddle") || slotType.equals("slot.horse.armor")) return slotType;
		return slotType + comboboxSlotIndex.getSelectedItem();
	}

	@Override
	public Component generateComponent()
	{
		JPanel panel = new JPanel(new GridLayout(1, 3));
		panel.add(labelSlot);
		panel.add(comboboxSlotType);
		panel.add(comboboxSlotIndex);
		return panel;
	}

	@Override
	public void initGui()
	{
		labelSlot = new CLabel("GUI:replaceitem.slot");

		comboboxSlotType = new BaseComboBox(new String[] { "slot.armor.", "slot.enderchest.", "slot.horse.armor", "slot.inventory.", "slot.horse.saddle",
				"slot.hotbar.", "slot.horse.chest.", "slot.villager.", "slot.weapon" }, this);
		comboboxSlotType.setDrawType(GuiHandler.RIGHT);

		comboboxSlotIndex = new BaseComboBox(new String[] { "feet", "legs", "chest", "head" }, null);
		comboboxSlotIndex.setDrawType(GuiHandler.LEFT);

	}

	@Override
	public boolean isUsed()
	{
		return true;
	}

	@Override
	public boolean matches(List<String> data)
	{
		return true;
	}

	@Override
	public void setupFrom(List<String> data)
	{
		String slot = data.get(0);
		boolean visible = true;
		int index = 0, spin = 0;
		String type = slot.split("\\.")[1];

		if (type.equals("armor")) this.setupPanel(index, slot.split("\\.")[2], visible);

		if (type.equals("enderchest") || (type.equals("horse") && slot.split("\\.")[2].equals("chest")) || type.equals("hotbar") || type.equals("inventory")
				|| type.equals("villager"))
		{
			if (!type.equals("horse")) spin = Integer.parseInt(slot.split("\\.")[2]);
			else spin = Integer.parseInt(slot.split("\\.")[3]);

			if (type.equals("enderchest")) index = 1;
			if (type.equals("horse")) index = 3;
			if (type.equals("hotbar")) index = 5;
			if (type.equals("inventory")) index = 6;
			if (type.equals("villager")) index = 7;
		}

		if (type.equals("weapon") || (type.equals("horse") && !slot.split("\\.")[2].equals("chest")))
		{
			if (type.equals("weapon")) index = 8;
			else
			{
				if (slot.split("\\.")[2].equals("armor")) index = 2;
				if (slot.split("\\.")[2].equals("saddle")) index = 4;
			}
			visible = false;
		}

		this.setupPanel(index, spin, visible);
	}

	private void setupPanel(int index, Object spinnerValue, boolean spinnerVisible)
	{
		this.comboboxSlotType.setSelectedIndex(index);
		this.comboboxSlotIndex.setSelectedItem((String) spinnerValue);
		this.comboboxSlotIndex.setVisible(spinnerVisible);
	}

	@Override
	public void synchronize(Argument toMatch)
	{
		if (!(toMatch instanceof SlotArgument)) return;
		List<String> data = new ArrayList<String>();
		data.add(((SlotArgument) toMatch).generateCommand());
		this.setupFrom(data);
	}

	@Override
	public void reset()
	{
		this.comboboxSlotType.setSelectedIndex(0);
	}

	@Override
	public void updateCombobox()
	{
		switch (comboboxSlotType.getSelectedIndex())
		{
			case 0:
				comboboxSlotIndex.setValues(new String[] { "feet", "legs", "chest", "head" });
				comboboxSlotIndex.setVisible(true);
				break;

			case 1:
				comboboxSlotIndex.setValues(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
						"18", "19", "20", "21", "22", "23", "24", "25", "26" });
				comboboxSlotIndex.setVisible(true);
				break;

			case 2:
				comboboxSlotIndex.setValues(new String[0]);
				comboboxSlotIndex.setVisible(false);
				break;

			case 3:
				comboboxSlotIndex.setValues(new String[] { "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25",
						"26", "27", "28", "29", "30", "31", "32", "33", "34", "35" });
				comboboxSlotIndex.setVisible(true);
				break;

			case 4:
				comboboxSlotIndex.setValues(new String[0]);
				comboboxSlotIndex.setVisible(false);
				break;

			case 5:
				comboboxSlotIndex.setValues(new String[] { "0", "1", "2", "3", "4", "5", "6", "7" });
				comboboxSlotIndex.setVisible(true);
				break;

			case 6:
				comboboxSlotIndex.setValues(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
						"18", "19", "20", "21", "22", "23", "24", "25", "26" });
				comboboxSlotIndex.setVisible(true);
				break;

			case 7:
				comboboxSlotIndex.setValues(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8" });
				comboboxSlotIndex.setVisible(true);
				break;

			case 8:
				comboboxSlotIndex.setValues(new String[0]);
				comboboxSlotIndex.setVisible(false);
				break;

			default:
				break;
		}
	}
}
