package generator.registry;

import java.awt.image.BufferedImage;

import generator.main.Constants;
import generator.main.FileManager;

public class Effect extends ObjectWithNumId
{

	private BufferedImage icon;

	public Effect(int idNum, String idString)
	{
		super(Constants.EFFECT, idNum, idString);
	}

	@Override
	protected void createIcon()
	{
		this.icon = FileManager.loadTexture("effects/" + this.getId());
	}

	@Override
	public BufferedImage getIcon()
	{
		return this.icon;
	}

}