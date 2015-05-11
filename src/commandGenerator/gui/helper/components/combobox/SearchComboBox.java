package commandGenerator.gui.helper.components.combobox;

import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

import commandGenerator.gui.helper.GuiHandler;
import commandGenerator.gui.helper.components.CTextField;
import commandGenerator.gui.helper.components.icomponent.IBox;
import commandGenerator.gui.helper.components.listeners.CKeyListener;
import commandGenerator.gui.helper.components.listeners.IEvent;

@SuppressWarnings("serial")
public class SearchComboBox extends JPanel implements IBox, IEvent
{
	private BaseComboBox combobox;
	private CTextField textfield;
	private String[] values;
	private IBox parent;

	public SearchComboBox(String[] values, IBox parent)
	{
		super(new GridLayout(2, 1));

		this.values = values;
		this.parent = parent;

		this.combobox = new BaseComboBox(values, this);
		this.combobox.setDrawType(GuiHandler.TOP);

		this.textfield = new CTextField(18);
		this.textfield.addKeyListener(new CKeyListener(this));

		this.add(this.textfield);
		this.add(this.combobox);
		this.setSize(200, 20);
	}

	@Override
	public void handleEvent(AWTEvent event, int eventID)
	{
		if (eventID != IEvent.KEY_RELEASE) return;
		if (((KeyEvent) event).getKeyCode() == KeyEvent.VK_ENTER) this.validateSearch();
		else this.search();
	}

	private void search()
	{
		String search = this.textfield.getText();
		if (search == null || search.equals(""))
		{
			this.reset();
			return;
		}
		search = search.toLowerCase();

		ArrayList<String> matching = new ArrayList<String>();
		for (String object : this.values)
		{
			if (object.toString().toLowerCase().contains(search)) matching.add(object);
		}

		this.display(matching.toArray(new String[0]));
	}

	public void reset()
	{
		this.combobox.setValues(this.values);
		this.textfield.setText("");
	}

	private void validateSearch()
	{
		String selection = (String) this.combobox.getSelectedItem();
		this.reset();
		this.combobox.setSelectedItem(selection);
		this.parent.updateCombobox();
	}

	@Override
	public void setSize(int width, int height)
	{
		this.setPreferredSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(width, height));
	}

	public int getSelectedIndex()
	{
		return this.combobox.getSelectedIndex();
	}

	public String getSelectedItem()
	{
		return this.combobox.getSelectedItem();
	}

	public void setEnabledContent(boolean enable)
	{
		this.combobox.setEnabled(enable);
		this.textfield.setEnabled(enable);
	}

	public void setSelectedItem(String name)
	{
		this.combobox.setSelectedItem(name);
	}

	private void display(String[] names)
	{
		this.combobox.setValues(names);
	}

	public void setValues(String[] values)
	{
		this.values = values;
		this.reset();
	}

	@Override
	public void updateCombobox()
	{
		this.validateSearch();
	}

	public void setDrawType(int drawType)
	{
		this.combobox.setDrawType(drawType);
	}

}
