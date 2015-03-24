package commandGenerator.gui.helper.components;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class CEntry extends JPanel implements CComponent
{

	private String defaultValue;
	private CLabel label;
	private JTextField text;

	public CEntry(String title, String defaultValue)
	{
		super(new GridLayout(1, 2));
		this.defaultValue = defaultValue;

		label = new CLabel(title);
		text = new JTextField(13);
		text.setText(defaultValue);

		add(label);
		add(text);
	}

	public String getText()
	{
		return text.getText();
	}

	@Override
	public void reset()
	{
		setTextField(defaultValue);
	}

	public void setDefaultValue(String defaultValue)
	{
		this.defaultValue = defaultValue;
	}

	@Override
	public void setEnabledContent(boolean enabled)
	{
		text.setEnabled(enabled);
		label.setEnabled(enabled);
	}

	public void setText(String title)
	{
		label.setTitle(title);
	}

	public void setTextField(String text)
	{
		this.text.setText(text);
	}

	@Override
	public void updateLang()
	{
		label.updateLang();
	}

}
