package commandGenerator.gui.helper.components.button;

import java.awt.Dimension;

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

	@Override
	public void setSize(int width, int height)
	{
		this.setPreferredSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(width, height));
	}

	public void click()
	{}
}
