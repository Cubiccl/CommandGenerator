package commandGenerator.gui.helper.components.button;

import java.awt.Dimension;

import javax.swing.JButton;

import commandGenerator.gui.helper.components.CComponent;

@SuppressWarnings("serial")
public class BaseButton extends JButton implements CComponent
{
	public static final int DEFAULT_HEIGHT = 20, DEFAULT_WIDTH = 200;

	public BaseButton(String text)
	{
		super(text);
		this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
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
}
