package commandGenerator.gui.helper.components.button;

import java.awt.Dimension;
import java.awt.Graphics;

import commandGenerator.gui.helper.GuiHandler;

@SuppressWarnings("serial")
public class ArrowButton extends BaseButton
{
	private int direction;

	public ArrowButton(int direction)
	{
		super("");
		this.direction = direction;
		this.setSize(20, 10);
		this.setDrawType(GuiHandler.FULL);
	}

	@Override
	public void setSize(int width, int height)
	{
		this.setMinimumSize(new Dimension(width, height));
		this.setPreferredSize(new Dimension(width, height));
	}

	@Override
	public void paintComponent(Graphics g)
	{
		int width = this.getWidth();
		int height = this.getHeight();
		super.paintComponent(g);
		int[] x = new int[3], y = new int[3];

		switch (this.direction)
		{
			case NORTH:
				x[0] = width / 2;
				y[0] = 1;

				x[1] = 3;
				y[1] = height - 2;

				x[2] = width - 2;
				y[2] = height - 2;
				break;

			case SOUTH:
				x[0] = width / 2 + 1;
				y[0] = height - 1;

				x[1] = 3;
				y[1] = 2;

				x[2] = width - 2;
				y[2] = 2;
				break;

			default:
				break;
		}

		g.setColor(this.getForeground());
		g.fillPolygon(x, y, 3);
	}

}
