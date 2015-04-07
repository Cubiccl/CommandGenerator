package commandGenerator.arguments.command.arguments.misc;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;

import commandGenerator.arguments.command.Argument;
import commandGenerator.gui.helper.components.CLabel;

public class SlotArgument extends Argument
{

	private JComboBox<String> comboboxSlot;
	private CLabel labelSlot;
	private JSpinner spinnerSlot;

	public SlotArgument()
	{
		super("replaceitem.slot");
	}

	@Override
	public String generateCommand()
	{
		String slotType = (String) comboboxSlot.getSelectedItem();
		if (slotType.equals("slot.weapon") || slotType.equals("slot.horse.saddle") || slotType.equals("slot.horse.armor")) return slotType;
		return slotType + spinnerSlot.getValue();
	}

	@Override
	public Component generateComponent()
	{
		JPanel panel = new JPanel(new GridLayout(1, 3));
		panel.add(labelSlot);
		panel.add(comboboxSlot);
		panel.add(spinnerSlot);
		return panel;
	}

	@Override
	public void initGui()
	{
		labelSlot = new CLabel("GUI:replaceitem.slot");

		comboboxSlot = new JComboBox<String>(new String[] { "slot.armor.", "slot.enderchest.", "slot.horse.armor", "slot.horse.chest.", "slot.horse.saddle",
				"slot.hotbar.", "slot.inventory.", "slot.villager.", "slot.weapon" });
		comboboxSlot.setPreferredSize(new Dimension(200, 20));
		comboboxSlot.setMinimumSize(new Dimension(200, 20));

		spinnerSlot = new JSpinner(new SpinnerListModel(new String[] { "feet", "legs", "chest", "head" }));
		spinnerSlot.setPreferredSize(new Dimension(100, 20));
		spinnerSlot.setMinimumSize(new Dimension(100, 20));

		comboboxSlot.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				switch (comboboxSlot.getSelectedIndex())
				{
					case 0:
						spinnerSlot.setModel(new SpinnerListModel(new String[] { "feet", "legs", "chest", "head" }));
						spinnerSlot.setVisible(true);
						break;

					case 1:
						spinnerSlot.setModel(new SpinnerNumberModel(0, 0, 26, 1));
						spinnerSlot.setVisible(true);
						break;

					case 2:
						spinnerSlot.setModel(new SpinnerListModel(new String[] { null }));
						spinnerSlot.setVisible(false);
						break;

					case 3:
						spinnerSlot.setModel(new SpinnerNumberModel(0, 0, 26, 1));
						spinnerSlot.setVisible(true);
						break;

					case 4:
						spinnerSlot.setModel(new SpinnerListModel(new String[] { null }));
						spinnerSlot.setVisible(false);
						break;

					case 5:
						spinnerSlot.setModel(new SpinnerNumberModel(0, 0, 8, 1));
						spinnerSlot.setVisible(true);
						break;

					case 6:
						spinnerSlot.setModel(new SpinnerNumberModel(9, 9, 35, 1));
						spinnerSlot.setVisible(true);
						break;

					case 7:
						spinnerSlot.setModel(new SpinnerNumberModel(0, 0, 7, 1));
						spinnerSlot.setVisible(true);
						break;

					case 8:
						spinnerSlot.setModel(new SpinnerListModel(new String[] { null }));
						spinnerSlot.setVisible(false);
						break;

					default:
						break;
				}
			}
		});
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
		this.comboboxSlot.setSelectedIndex(index);
		this.spinnerSlot.setValue(spinnerValue);
		this.spinnerSlot.setVisible(spinnerVisible);
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
		this.comboboxSlot.setSelectedIndex(0);
	}

}
