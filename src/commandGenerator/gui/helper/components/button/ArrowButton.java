package commandGenerator.gui.helper.components.button;

import java.awt.Dimension;

import javax.swing.plaf.basic.BasicArrowButton;

@SuppressWarnings("serial")
public class ArrowButton extends BasicArrowButton
{

	public ArrowButton(int direction)
	{
		super(direction);
		this.setSize(20, 10);
	}

	@Override
	public void setSize(int width, int height)
	{
		this.setMinimumSize(new Dimension(width, height));
		this.setPreferredSize(new Dimension(width, height));
	}

}
