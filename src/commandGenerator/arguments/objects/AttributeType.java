package commandGenerator.arguments.objects;

import javax.swing.ImageIcon;

import commandGenerator.Generator;

public class AttributeType extends ObjectBase
{
	private String name;

	/** New Attribute type
	 * 
	 * @param id
	 *            - <i>String</i> - The Attribute type ID */
	public AttributeType(String id)
	{
		super(id, ATTRIBUTE);
		this.name = id;
	}

	/** Returns this Attribute's name. */
	@Override
	public String getName()
	{
		return this.name;
	}

	@Override
	public ImageIcon getTexture()
	{
		return null;
	}

	@Override
	public void updateLang()
	{
		this.name = Generator.translate("ATTRIBUTES:" + this.getId());
	}

}
