package commandGenerator.arguments.tags.specific;

import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.argumentSelection.dataTag.PagesSelectionPanel;

public class TagPages extends TagList {

	public TagPages() {
		super("item.pages", "written_book");
	}

	@Override
	public void askValue() {
		panel = new PagesSelectionPanel();
		showPanel();
		setValue(((PagesSelectionPanel) panel).getPages());
	}

}
