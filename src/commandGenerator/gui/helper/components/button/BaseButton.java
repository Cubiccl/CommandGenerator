package commandGenerator.gui.helper.components.button;

import javax.swing.JButton;

import commandGenerator.gui.helper.components.CComponent;

@SuppressWarnings("serial")
public class BaseButton extends JButton implements CComponent
{

	public BaseButton(String text)
	{
		super(text);
	}

	@Override
	public void reset()
	{}

	@Override
	public void setEnabledContent(boolean enable)
	{
		this.setEnabled(enable);
	}

	@Override
	public void updateLang()
	{}
}
