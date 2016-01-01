package generator.gui.textfield;

import java.awt.AWTEvent;

import generator.gui.checkbox.CCheckbox;
import generator.gui.panel.CPanelHorizontal;
import generator.interfaces.ClickEvent;
import generator.interfaces.IClickEvent;

@SuppressWarnings("serial")
public class CCheckEntry extends CPanelHorizontal implements IClickEvent
{
	private static final int ENABLE = 0;

	private CCheckbox checkbox;
	private CTextfield textfield;

	public CCheckEntry(String textID)
	{
		this.checkbox = new CCheckbox(textID);
		this.checkbox.addActionListener(new ClickEvent(this, ENABLE));
		this.textfield = new CTextfield();
		this.textfield.setEnabled(false);

		this.add(this.checkbox);
		this.add(this.textfield);
	}

	public CCheckbox getCheckbox()
	{
		return this.checkbox;
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
	public void onEvent(int componentID, AWTEvent event)
	{
		switch (componentID)
		{
			case ENABLE:
				this.textfield.setEnabled(this.checkbox.isSelected());
				break;

			default:
				break;
		}
	}

	@Override
	public void setEnabled(boolean enabled)
	{
		super.setEnabled(enabled);
		this.checkbox.setEnabled(enabled);
		this.textfield.setEnabled(enabled);
		if (this.isEnabled()) this.textfield.setEnabled(this.checkbox.isSelected());
	}

	/** Changes the text in the textfield.
	 * 
	 * @param text - The text to set. */
	public void setText(String text)
	{
		this.textfield.setText(text);
	}

}
