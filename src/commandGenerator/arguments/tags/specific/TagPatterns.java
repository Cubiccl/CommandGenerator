package commandGenerator.arguments.tags.specific;

import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.argumentSelection.dataTag.PatternsSelectionPanel;

public class TagPatterns extends TagList {

	public TagPatterns() {
		super("block.Patterns", "standing_banner", "wall_banner", "banner");
	}

	@Override
	public void askValue() {
		panel = new PatternsSelectionPanel();
		showPanel();
		setValue(((PatternsSelectionPanel) panel).getSelectedPatterns());
	}

}
