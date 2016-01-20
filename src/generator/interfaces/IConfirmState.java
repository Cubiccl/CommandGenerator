package generator.interfaces;

import generator.gui.panel.CPanel;

public interface IConfirmState
{

	/** Called when the user confirms or cancels the Confirm State.
	 * 
	 * @param cancel - True if the Cancel button was clicked, false if the OK was.
	 * @param component - The component used in the Confirm State.
	 * @return True if the state should be removed, false if it should stay. */
	public boolean confirm(boolean cancel, CPanel component);

}
