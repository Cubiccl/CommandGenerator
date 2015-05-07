package commandGenerator.gui.helper.components.combobox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import commandGenerator.Generator;
import commandGenerator.gui.helper.components.button.BaseButton;
import commandGenerator.gui.helper.components.icomponent.IBox;

@SuppressWarnings("serial")
public class BaseComboBox extends BaseButton
{
	private String[] values;
	private int selectedIndex;
	private IBox parent;

	public BaseComboBox(String[] values, IBox parent)
	{
		super("");
		this.values = values;
		this.parent = parent;
		this.selectedIndex = 0;

		this.updateText();
		this.addActionListener(new BoxListener(this));
	}

	private void onSelection()
	{
		for (ActionListener l : this.getActionListeners())
		{
			if (!(l instanceof BoxListener)) l.actionPerformed(new ActionEvent(this, 0, null));
		}
		if (parent != null) this.parent.updateCombobox();
	}

	private void updateText()
	{
		if (this.values.length == 0) this.setText("");
		else this.setText(this.values[this.getSelectedIndex()]);
	}

	protected void click()
	{
		JScrollPopupMenu popup = new JScrollPopupMenu(Generator.gui);

		for (int i = 0; i < this.values.length; i++)
		{
			final int index = i;
			BaseButton button = new BaseButton(this.values[i]);
			button.setSize(200, 20);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0)
				{
					setSelectedIndex(index);
					onSelection();
				}
			});

			popup.add(button);
		}

		popup.show(this.getParent(), this.getX(), this.getY());
	}

	public int getSelectedIndex()
	{
		return this.selectedIndex;
	}

	public String getSelectedItem()
	{
		if (this.values.length == 0) return null;
		return this.values[this.getSelectedIndex()];
	}

	public void setSelectedIndex(int selectedIndex)
	{
		if (selectedIndex >= 0 && selectedIndex < this.values.length) this.selectedIndex = selectedIndex;
		this.updateText();
	}

	public void setValues(String[] values)
	{
		this.setSelectedIndex(0);
		this.values = values;
		this.updateText();
	}

	public void setSelectedItem(String selection)
	{
		for (int i = 0; i < this.values.length; i++)
		{
			if (this.values[i].equals(selection)) this.setSelectedIndex(i);
		}
	}

}

class BoxListener implements ActionListener
{
	private BaseComboBox box;

	public BoxListener(BaseComboBox box)
	{
		this.box = box;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		this.box.click();
	}

}
