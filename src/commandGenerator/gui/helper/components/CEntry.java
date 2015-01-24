package commandGenerator.gui.helper.components;

import java.awt.GridLayout;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JTextField;

import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class CEntry extends JPanel implements CComponent
{

	private String id;
	private JTextField text;
	private CLabel label;

	public CEntry(String id, String title)
	{
		super(new GridLayout(1, 2));
		this.id = id;

		label = new CLabel(title);
		text = new JTextField();

		add(label);
		add(text);
	}

	public String getText()
	{
		return text.getText();
	}

	public void setText(String title)
	{
		label.setTitle(title);
	}

	public void setTextField(String text)
	{
		this.text.setText(text);
	}

	public void setEnabledContent(boolean enabled)
	{
		text.setEnabled(enabled);
		label.setEnabled(enabled);
	}

	public void updateLang()
	{
		label.updateLang();
	}

	@Override
	public void setupFrom(Map<String, Object> data)
	{
		if (!id.equals(CGConstants.DATAID_NONE) && data.get(id) != null) setTextField((String) data.get(id));
	}

	@Override
	public void reset()
	{
		setTextField("");
	}

}
