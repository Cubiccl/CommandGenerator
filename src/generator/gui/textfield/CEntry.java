package generator.gui.textfield;

import generator.gui.CLabel;
import generator.gui.CTextfield;
import generator.gui.panel.CPanelHorizontal;

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

	public String getText()
	{
		return this.textfield.getText();
	}

	public CTextfield getTextfield()
	{
		return this.textfield;
	}

	public void setText(String text)
	{
		this.textfield.setText(text);
	}

}
