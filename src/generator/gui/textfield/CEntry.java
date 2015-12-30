package generator.gui.textfield;

import generator.gui.CLabel;
import generator.gui.panel.CPanelHorizontal;

/** Represents a labeled textfield. */
@SuppressWarnings("serial")
public class CEntry extends CPanelHorizontal
{
	private CLabel label;
	private CTextfield textfield;

	public CEntry(String textID)
	{
		this.label = new CLabel(textID);
		this.textfield = new CTextfield();

		this.add(this.label);
		this.add(this.textfield);
	}

	public CLabel getLabel()
	{
		return this.label;
	}

	/** @return The text input by the user. */
	public String getText()
	{
		return this.textfield.getText();
	}

	public CTextfield getTextfield()
	{
		return this.textfield;
	}
	
	@Override
	public void setEnabled(boolean enabled)
	{
		super.setEnabled(enabled);
		this.textfield.setEnabled(enabled);
	}

	/** Changes the text in the textfield.
	 * 
	 * @param text - The text to set. */
	public void setText(String text)
	{
		this.textfield.setText(text);
	}

}
