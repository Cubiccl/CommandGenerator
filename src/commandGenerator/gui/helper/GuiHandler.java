package commandGenerator.gui.helper;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.UIManager;

public class GuiHandler
{
	/** Drawing Types */
	public static final int DEFAULT = 0, TOP_LEFT = 1, TOP_RIGHT = 2, TOP = 3, BOTTOM_LEFT = 4, LEFT = 5, BOTTOM_RIGHT = 8, RIGHT = 10, BOTTOM = 12, FULL = 15;

	public static final Color DEFAULT_BACKGROUND = UIManager.getColor("Panel.background"), DEFAULT_COMPONENT = new Color(220, 220, 250), HOVERED = new Color(
			220, 250, 220), CLICKED = new Color(250, 220, 220), BORDER = new Color(80, 80, 200), DISABLED_BORDER = new Color(100, 100, 100), HELP = new Color(
			255, 255, 210), BORDER_FIELD = new Color(150, 150, 150), BORDER_FIELD_HOVER = Color.GREEN, BORDER_FIELD_FOCUS = Color.RED,
			FIELD_EDITABLE = new Color(230, 230, 230), DISABLED = new Color(200, 200, 200), FONT = new Color(51, 51, 51);

	public static final Font DEFAULT_FONT = new Font("Calibri", Font.BOLD, 15), SPINNER = new Font("Dialog", Font.PLAIN, 10);

	public static void clear(Graphics g, int width, int height)
	{
		g.setColor(DEFAULT_BACKGROUND);
		g.fillRect(0, 0, width + 1, height + 1);
	}

	private static void drawAngledCorner(Graphics g, int width, int height, Color color, int corner)
	{
		g.setColor(color);
		switch (corner)
		{
			case TOP_LEFT:
				g.drawLine(0, 0, width / 2, 0);
				g.drawLine(0, 0, 0, height / 2);
				break;

			case TOP_RIGHT:
				g.drawLine(width / 2, 0, width, 0);
				g.drawLine(width, 0, width, height / 2);
				break;

			case BOTTOM_LEFT:
				g.drawLine(0, height / 2, 0, height);
				g.drawLine(0, height, width / 2, height);
				break;

			case BOTTOM_RIGHT:
				g.drawLine(width / 2, height, width, height);
				g.drawLine(width, height / 2, width, height);
				break;

			default:
				break;
		}
	}

	public static void drawBorder(Graphics g, int width, int height, Color color, int drawType)
	{
		boolean[] corners = getCornersToDraw(drawType);
		drawCorner(g, width, height, color, TOP_LEFT, corners[0]);
		drawCorner(g, width, height, color, TOP_RIGHT, corners[1]);
		drawCorner(g, width, height, color, BOTTOM_LEFT, corners[2]);
		drawCorner(g, width, height, color, BOTTOM_RIGHT, corners[3]);
	}

	public static void drawComponent(Graphics g, int width, int height, Color color, int drawType)
	{
		boolean[] corners = getCornersToDraw(drawType);
		fillCorner(g, width, height, color, TOP_LEFT, corners[0]);
		fillCorner(g, width, height, color, TOP_RIGHT, corners[1]);
		fillCorner(g, width, height, color, BOTTOM_LEFT, corners[2]);
		fillCorner(g, width, height, color, BOTTOM_RIGHT, corners[3]);
	}

	private static void drawCorner(Graphics g, int width, int height, Color color, int corner, boolean isAngled)
	{
		if (isAngled) drawAngledCorner(g, width, height, color, corner);
		else drawRoundedCorner(g, width, height, color, corner);
	}

	private static void drawRoundedCorner(Graphics g, int width, int height, Color color, int corner)
	{
		int arcSize = height;
		g.setColor(color);
		switch (corner)
		{
			case TOP_LEFT:
				g.drawArc(0, 0, arcSize, arcSize, 90, 90);
				g.drawLine(height / 2, 0, width / 2, 0);
				break;

			case TOP_RIGHT:
				if (height <= width) g.drawArc(width - height, 0, arcSize, arcSize, 0, 90);
				else g.drawArc(0, 0, width, height, 0, 90);
				g.drawLine(width / 2, 0, width - height / 2, 0);
				break;

			case BOTTOM_LEFT:
				g.drawArc(0, 0, arcSize, arcSize, 180, 90);
				g.drawLine(height / 2, height, width / 2, height);
				break;

			case BOTTOM_RIGHT:
				if (height <= width) g.drawArc(width - height, 0, arcSize, arcSize, 270, 90);
				else g.drawArc(0, 0, width, height, 270, 90);
				g.drawLine(width / 2, height, width - height / 2, height);
				break;

			default:
				break;
		}
	}

	public static void drawString(Graphics g, String text, int width, int height, Color color, Font font)
	{
		width--;
		height--;
		FontMetrics fm = g.getFontMetrics();
		int x = (width - fm.stringWidth(text)) / 2;
		int y = (fm.getAscent() + (height - (fm.getAscent() + fm.getDescent())) / 2);

		g.setColor(color);
		g.setFont(font);
		g.drawString(text, x, y);

	}

	private static void fillAngledCorner(Graphics g, int width, int height, Color color, int corner)
	{
		g.setColor(color);
		switch (corner)
		{
			case TOP_LEFT:
				g.fillRect(0, 0, width / 2, height / 2 + 1);
				break;

			case TOP_RIGHT:
				g.fillRect(width / 2, 0, width, height / 2 + 1);
				break;

			case BOTTOM_LEFT:
				g.fillRect(0, height / 2 - 1, width / 2, height);
				break;

			case BOTTOM_RIGHT:
				g.fillRect(width / 2, height / 2 - 1, width, height);
				break;

			default:
				break;
		}
	}

	private static void fillCorner(Graphics g, int width, int height, Color color, int corner, boolean isAngled)
	{
		if (isAngled) fillAngledCorner(g, width, height, color, corner);
		else fillRoundedCorner(g, width, height, color, corner);
	}

	private static void fillRoundedCorner(Graphics g, int width, int height, Color color, int corner)
	{
		int arcSize = height;
		int rectWidth = width / 2 - height / 2 + 1;
		g.setColor(color);
		switch (corner)
		{
			case TOP_LEFT:
				g.fillArc(0, 0, arcSize, arcSize, 90, 90);
				g.fillRect(height / 2, 0, rectWidth, height / 2);
				break;

			case TOP_RIGHT:
				if (height <= width)
				{
					g.fillArc(width - height, 0, arcSize, arcSize, 0, 90);
					g.fillRect(width / 2, 0, rectWidth, height / 2);
				} else
				{
					g.fillArc(0, 0, width, height, 0, 90);
					g.fillRect(width / 2, 0, 1, height / 2);
				}
				break;

			case BOTTOM_LEFT:
				g.fillArc(0, 0, arcSize, arcSize, 180, 90);
				g.fillRect(height / 2, height / 2, rectWidth, height);
				break;

			case BOTTOM_RIGHT:
				if (height <= width)
				{
					g.fillArc(width - height, 0, arcSize, arcSize, 270, 90);
					g.fillRect(width / 2, height / 2, rectWidth, height);
				} else
				{
					g.fillArc(0, 0, width, height, 270, 90);
					g.fillRect(width / 2, height / 2, 1, height);
				}
				break;

			default:
				break;
		}
	}

	private static boolean[] getCornersToDraw(int drawType)
	{
		boolean[] corners = { false, false, false, false };
		if (drawType >= BOTTOM_RIGHT)
		{
			corners[3] = true;
			drawType -= BOTTOM_RIGHT;
		}
		if (drawType >= BOTTOM_LEFT)
		{
			corners[2] = true;
			drawType -= BOTTOM_LEFT;
		}
		if (drawType >= TOP_RIGHT)
		{
			corners[1] = true;
			drawType -= TOP_RIGHT;
		}
		if (drawType >= TOP_LEFT)
		{
			corners[0] = true;
			drawType -= TOP_LEFT;
		}
		return corners;
	}

}
