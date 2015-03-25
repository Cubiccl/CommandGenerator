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
		this.label = new CLabel(title);
		this.text = new JTextField(13);
		this.text.setText(this.defaultValue);

		this.add(label);
		this.add(text);
	}

	public String getText()
	{
		return this.text.getText();
	}

	@Override
	public void reset()
	{
		this.setTextField(this.defaultValue);
	}

	public void setDefaultValue(String defaultValue)
	{
		this.defaultValue = defaultValue;
	}

	@Override
	public void setEnabledContent(boolean enabled)
	{
		this.text.setEnabled(enabled);
		this.label.setEnabledContent(enabled);
	}

	public void setText(String title)
	{
		this.label.setTitle(title);
	}

	public void setTextField(String text)
	{
		this.text.setText(text);
	}

	@Override
	public void updateLang()
	{
		this.label.updateLang();
	}

}
