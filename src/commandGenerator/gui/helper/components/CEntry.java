package commandGenerator.gui.helper.components;

import java.awt.GridLayout;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JTextField;

import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class CEntry extends JPanel implements CComponent
{

	private String id, defaultValue;
	private CLabel label;
	private JTextField text;

	public CEntry(String id, String title, String defaultValue)
	{
		super(new GridLayout(1, 2));
		this.id = id;
		this.defaultValue = defaultValue;

		label = new CLabel(title);
		text = new JTextField(defaultValue);

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
	public void setupFrom(Map<String, Object> data)
	{
		if (!id.equals(CGConstants.DATAID_NONE) && data.get(id) != null) setTextField((String) data.get(id));
	}

	public void updateLang()
	{
		label.updateLang();
	}

}
