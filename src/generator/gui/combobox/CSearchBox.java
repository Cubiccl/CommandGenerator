package generator.gui.combobox;

import generator.gui.panel.CPanel;
import generator.gui.textfield.CTextfield;
import generator.interfaces.ClickEvent;
import generator.interfaces.IClickEvent;
import generator.interfaces.ITextEvent;
import generator.interfaces.TextEvent;

import java.awt.AWTEvent;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/** A combobox with a textfield to search for values. */
@SuppressWarnings("serial")
public class CSearchBox extends CPanel implements IClickEvent, ITextEvent
{
	private static final int SELECT = 0, TEXT = 1;

	/** The combobox to pick choices. */
	private CCombobox combobox;
	/** The textfield to search for values. */
	private CTextfield textfield;
	/** The values to select one of. */
	private String[] values;

	public CSearchBox()
	{
		this(new String[] { "" });
	}

	public CSearchBox(String[] values)
	{
		super();
		this.setLayout(new GridLayout(2, 1));
		this.values = values;

		this.textfield = new CTextfield();
		this.textfield.addKeyListener(new TextEvent(this, TEXT));
		this.combobox = new CCombobox(this.values);
		this.combobox.addActionListener(new ClickEvent(this, SELECT));

		this.add(this.textfield);
		this.add(this.combobox);
	}

	/** Adds an ActionListener to this combobox.
	 * 
	 * @param actionListener - The input ActionListener. */
	public void addActionListener(ActionListener actionListener)
	{
		this.combobox.addActionListener(actionListener);
	}

	/** @return The actual combobox. */
	public CCombobox getCombobox()
	{
		return this.combobox;
	}

	/** @return The index of the current selection. */
	public int getSelectedIndex()
	{
		return this.combobox.getSelectedIndex();
	}

	/** @return The textfield used to search. */
	public CTextfield getTextfield()
	{
		return this.textfield;
	}

	/** Called when the user selects a value in the combobox. */
	private void onBoxSelection()
	{
		String value = (String) this.combobox.getSelectedItem();
		this.reset();
		if (value != null) this.combobox.setSelectedItem(value);
	}

	@Override
	public void onEvent(int componentID, AWTEvent event)
	{
		switch (componentID)
		{
			case SELECT:
				this.onBoxSelection();
				break;

			case TEXT:
				onTextInput(((KeyEvent) event).getKeyCode());
				break;

			default:
				break;
		}
	}

	private void onTextInput(int keyID)
	{
		if (keyID == KeyEvent.VK_ENTER && !this.textfield.getText().equals("") && this.combobox.getSelectedItem() != null)
		{
			this.onBoxSelection();
			return;
		}

		ArrayList<String> validValues = new ArrayList<String>();
		for (String value : this.values)
		{
			if (value.contains(this.textfield.getText())) validValues.add(value);
		}
		this.combobox.setValues(validValues.toArray(new String[0]));
	}

	/** Called on box selection or when textfield is empty. */
	private void reset()
	{
		this.combobox.setValues(this.values);
		this.textfield.setText("");
	}

	/** Changes the index of the current selection.
	 * 
	 * @param selectedIndex - The new index. */
	public void setSelectedIndex(int selectedIndex)
	{
		this.combobox.setSelectedIndex(selectedIndex);
	}

	/** Changes the values of this box.
	 * 
	 * @param values - The new values. */
	public void setValues(String[] values)
	{
		this.values = values;
		this.reset();
	}

}
