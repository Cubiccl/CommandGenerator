package generator.gui;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/** A Label displaying an image. */
@SuppressWarnings("serial")
public class CImage extends JLabel
{
	public CImage()
	{
		this(null);
	}

	public CImage(BufferedImage icon)
	{
		super("");
		this.setIcon(icon);
	}

	public void setIcon(BufferedImage icon)
	{
		if (icon != null) this.setIcon(new ImageIcon(icon));
	}

}
