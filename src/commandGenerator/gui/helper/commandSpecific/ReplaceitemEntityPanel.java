package commandGenerator.gui.helper.commandSpecific;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;

import commandGenerator.arguments.objects.Target;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class ReplaceitemEntityPanel extends HelperPanel
{

	private JComboBox<String> comboboxSlot;
	private CLabel labelSlot;
	private EntitySelectionPanel panelEntity;
	private JSpinner spinnerSlot;

	public ReplaceitemEntityPanel()
	{
		super(CGConstants.PANELID_OPTIONS, "GUI:replaceitem.entity.title");
	}

	@Override
	protected void addComponents()
	{
		add(labelSlot);
		add(comboboxSlot);
		add(spinnerSlot);
		add(panelEntity);
	}

	@Override
	protected void createComponents()
	{
		labelSlot = new CLabel("GUI:replaceitem.slot");

		comboboxSlot = new JComboBox<String>(new String[] { "slot.armor.", "slot.enderchest.", "slot.horse.armor", "slot.horse.chest.", "slot.horse.saddle",
				"slot.hotbar.", "slot.inventory.", "slot.villager.", "slot.weapon" });
		comboboxSlot.setPreferredSize(new Dimension(200, 20));

		spinnerSlot = new JSpinner(new SpinnerListModel(new String[] { "feet", "legs", "chest", "head" }));
		spinnerSlot.setPreferredSize(new Dimension(100, 20));

		panelEntity = new EntitySelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.entity", CGConstants.ENTITIES_ALL);
	}

	@Override
	protected void createListeners()
	{
		comboboxSlot.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				switch (comboboxSlot.getSelectedIndex())
				{
					case 0:
						spinnerSlot.setModel(new SpinnerListModel(new String[] { "feet", "legs", "chest", "head" }));
						spinnerSlot.setEnabled(true);
						break;

					case 1:
						spinnerSlot.setModel(new SpinnerNumberModel(0, 0, 26, 1));
						spinnerSlot.setEnabled(true);
						break;

					case 2:
						spinnerSlot.setModel(new SpinnerListModel(new String[] { null }));
						spinnerSlot.setEnabled(false);
						break;

					case 3:
						spinnerSlot.setModel(new SpinnerNumberModel(0, 0, 26, 1));
						spinnerSlot.setEnabled(true);
						break;

					case 4:
						spinnerSlot.setModel(new SpinnerListModel(new String[] { null }));
						spinnerSlot.setEnabled(false);
						break;

					case 5:
						spinnerSlot.setModel(new SpinnerNumberModel(0, 0, 8, 1));
						spinnerSlot.setEnabled(true);
						break;

					case 6:
						spinnerSlot.setModel(new SpinnerNumberModel(9, 9, 35, 1));
						spinnerSlot.setEnabled(true);
						break;

					case 7:
						spinnerSlot.setModel(new SpinnerNumberModel(0, 0, 7, 1));
						spinnerSlot.setEnabled(true);
						break;

					case 8:
						spinnerSlot.setModel(new SpinnerListModel(new String[] { null }));
						spinnerSlot.setEnabled(false);
						break;

					default:
						break;
				}
			}
		});
	}

	public String generateCommand()
	{

		Target entity = panelEntity.generateEntity();

		if (entity == null) return null;

		if (spinnerSlot.getValue() == null) return entity.commandStructure() + " " + (String) comboboxSlot.getSelectedItem();
		if (comboboxSlot.getSelectedIndex() == 0) return entity.commandStructure() + " " + (String) comboboxSlot.getSelectedItem()
				+ (String) spinnerSlot.getValue();
		return entity.commandStructure() + " " + (String) comboboxSlot.getSelectedItem() + Integer.toString((int) spinnerSlot.getValue());
	}

	public void setupFrom(Map<String, Object> data)
	{
		super.setupFrom(data);
		Object[] slot = (Object[]) data.get(CGConstants.DATAID_VALUE);
		if (slot == null) return;

		comboboxSlot.setSelectedIndex((int) slot[0]);
		spinnerSlot.setValue(slot[1]);
		spinnerSlot.setEnabled((boolean) slot[2]);
	}

}
