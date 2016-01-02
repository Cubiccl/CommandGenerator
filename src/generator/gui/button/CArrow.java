package generator.gui.button;

import generator.main.Utils;

import java.awt.Graphics;

import javax.swing.plaf.basic.BasicArrowButton;

/** An ArrowButton which will only draw its Arrow, bigger. */
@SuppressWarnings("serial")
public class CArrow extends BasicArrowButton
{
	/** True if this has a decoration. */
	private boolean hasDecoration;

	public CArrow(int direction)
	{
		super(direction);
		this.hasDecoration = false;
	}

	/** Overriden to draw only the Arrow. */
	@Override
	public void paint(Graphics g)
	{
		if (this.hasDecoration) this.paintdecoration(g);

		int size = Math.max(Math.min((this.getHeight() - 4) / 2, (this.getWidth() - 4) / 2), 2);
		this.paintTriangle(g, (this.getWidth() - size) / 2 + 2, (this.getHeight() - size) / 2, size, this.getDirection(), this.isEnabled());
	}

	/** Paints this Arrow's background.
	 * 
	 * @param g - the Graphics context in which to paint */
	private void paintdecoration(Graphics g)
	{
		if (this.getModel().isPressed()) g.setColor(Utils.BACKGROUND_CLICKED);
		else if (this.getModel().isRollover()) g.setColor(Utils.BACKGROUND_HOVERED);
		else g.setColor(Utils.BACKGROUND_NORMAL);
		g.fillRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
		g.setColor(Utils.BORDER_COLOR);
		g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
	}

	public void setHasDecoration(boolean hasBackground)
	{
		this.hasDecoration = hasBackground;
	}

}
