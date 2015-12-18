package generator.gui.button;

import java.awt.Graphics;

import javax.swing.plaf.basic.BasicArrowButton;

/** An ArrowButton which will only draw its Arrow, bigger. */
@SuppressWarnings("serial")
public class CArrow extends BasicArrowButton
{

	public CArrow(int direction)
	{
		super(direction);
	}

	/** Overriden to draw only the Arrow. */
	@Override
	public void paint(Graphics g)
	{
		int size = Math.max(Math.min((this.getHeight() - 4) / 2, (this.getWidth() - 4) / 2), 2);
		this.paintTriangle(g, (this.getWidth() - size) / 2 + 2, (this.getHeight() - size) / 2, size, this.getDirection(), this.isEnabled());
	}

}
