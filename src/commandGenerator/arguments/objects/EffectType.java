package commandGenerator.arguments.objects;

import java.io.File;

import javax.swing.ImageIcon;

import commandGenerator.Generator;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Resources;

public class EffectType extends ObjectBase
{

	/** This Effect's numerical ID. */
	private int idNum;

	private ImageIcon texture;
	private String name;

	/** New Effect type.
	 * 
	 * @param idNum
	 *            - <i>int</i> - The Effect's numerical ID.
	 * @param idString
	 *            - <i>String</i> - The Effect's text ID. */
	public EffectType(int idNum, String idString)
	{
		super(idString, ObjectBase.EFFECT);
		this.idNum = idNum;
		this.name = idString;
		Generator.registry.registerEffect(this);
	}

	/** Returns this Effect's numerical ID. */
	public int getIdNum()
	{
		return this.idNum;
	}

	/** Returns this Effect's name. */
	@Override
	public String getName()
	{
		return this.name;
	}

	/** Returns this Effect's texture. */
	@Override
	public ImageIcon getTexture()
	{
		return this.texture;
	}

	public void registerTexture()
	{
		String path = Resources.folder + "textures/effects/" + this.getIdNum() + ".png";
		this.texture = new ImageIcon(path);
		if (!new File(path).exists()) DisplayHelper.missingTexture(path);
	}

	@Override
	public void updateLang()
	{
		this.name = Generator.translate("EFFECTS:" + this.getId());
	}

}
