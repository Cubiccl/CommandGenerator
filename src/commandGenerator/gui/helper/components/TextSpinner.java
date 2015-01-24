package commandGenerator.gui.helper.components;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class TextSpinner extends JPanel implements CComponent
{

	private String id;
	private CLabel label;
	private JSpinner spinner;
	private String[] choices;

	public TextSpinner(String id, String title, String[] choices, final ISpin parent)
	{
		super(new GridLayout(1, 2));
		this.id = id;
		this.choices = choices;

		label = new CLabel(title);
		spinner = new JSpinner(new SpinnerListModel(choices));
		spinner.setPreferredSize(new Dimension(200, 20));
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0)
			{
				if (parent != null) parent.updateSpinner();
			}
		});

		add(label);
		add(spinner);
	}

	public void setText(String id)
	{
		label.setTitle(id);
	}

	public String getValue()
	{
		return (String) spinner.getValue();
	}

	public void setEnabledContent(boolean enable)
	{
		label.setEnabledContent(enable);
		spinner.setEnabled(enable);
	}

	public void updateLang()
	{
		label.updateLang();
	}

	public void setupFrom(Map<String, Object> data)
	{
		if (!id.equals(CGConstants.DATAID_NONE)) spinner.setValue(data.get(id));
	}

	public void reset()
	{
		spinner.setValue(choices[0]);
	}

}
