package commandGenerator.gui.helper.argumentSelection.dataTag;

import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class FlagSelectionPanel extends HelperPanel {

	private CLabel label;
	private CCheckBox enchant, attribute, unbreak, destroy, place, other;

	public FlagSelectionPanel() {
		super(CGConstants.DATAID_NONE, "GUI:flag.title", 400, 300);
		
		label = new CLabel("GUI:flag.select");

		enchant = new CCheckBox(CGConstants.DATAID_NONE, "GUI:flag.enchant");
		attribute = new CCheckBox(CGConstants.DATAID_NONE, "GUI:flag.attribute");
		unbreak = new CCheckBox(CGConstants.DATAID_NONE, "GUI:flag.unbreak");
		destroy = new CCheckBox(CGConstants.DATAID_NONE, "GUI:flag.destroy");
		place = new CCheckBox(CGConstants.DATAID_NONE, "GUI:flag.place");
		other = new CCheckBox(CGConstants.DATAID_NONE, "GUI:flag.other");
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(label, gbc);
		gbc.gridy++;
		add(enchant, gbc);
		gbc.gridy++;
		add(unbreak, gbc);
		gbc.gridy++;
		add(destroy, gbc);
		gbc.gridy++;
		add(attribute, gbc);
		gbc.gridy++;
		add(place, gbc);
		gbc.gridy++;
		add(other, gbc);
	}

	public int getHideFlags() {
		int flag = 0;
		if (enchant.isSelected()) flag += 1;
		if (attribute.isSelected()) flag += 2;
		if (unbreak.isSelected()) flag += 4;
		if (destroy.isSelected()) flag += 8;
		if (place.isSelected()) flag += 16;
		if (other.isSelected()) flag += 32;
		return flag;
	}

	@Override
	public void updateLang() {
		
	}

}
