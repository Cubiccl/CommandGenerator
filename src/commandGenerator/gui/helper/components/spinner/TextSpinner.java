package commandGenerator.gui.helper.components.spinner;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import commandGenerator.gui.helper.components.CComponent;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.icomponent.ISpin;

@SuppressWarnings("serial")
public class TextSpinner extends JPanel implements CComponent
{

	private String[] choices;
	private CLabel label;
	private JSpinner spinner;

	public TextSpinner(String title, String[] choices, final ISpin parent)
	{
		super(new GridLayout(1, 2));
		this.choices = choices;

		label = new CLabel(title);
		spinner = new JSpinner(new SpinnerListModel(choices));
		spinner.setPreferredSize(new Dimension(200, 20));
		spinner.setMinimumSize(new Dimension(200, 20));
		spinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0)
			{
				if (parent != null) parent.updateSpinner();
			}
		});

		add(label);
		add(spinner);
	}

	public String getValue()
	{
		return (String) spinner.getValue();
	}

	@Override
	public void reset()
	{
		spinner.setValue(choices[0]);
	}

	@Override
	public void setEnabledContent(boolean enable)
	{
		label.setEnabledContent(enable);
		spinner.setEnabled(enable);
	}

	public void setTitle(String title)
	{
		label.setTitle(title);
	}

	@Override
	public void updateLang()
	{
		label.updateLang();
	}

}
