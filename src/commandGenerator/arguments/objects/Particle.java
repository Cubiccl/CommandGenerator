package commandGenerator.arguments.objects;

import javax.swing.ImageIcon;

import commandGenerator.main.Lang;

public class Particle extends ObjectBase
{

	/** Types of Particles :
	 * <ul>
	 * <li><strong>NORMAL</strong> : No specific behavior.</li>
	 * <li><strong>BLOCK</strong> : Varies according to a Block's texture.</li>
	 * <li><strong>ITEM</strong> : Varies according to an Item's texture.</li>
	 * </ul> */
	public static final int NORMAL = 0, BLOCK = 1, ITEM = 2;

	/** The type of the Particle. */
	private int type;
	
	private String description;

	/** Creates a new Particle.
	 * 
	 * @param id
	 *            - <i>String</i> - The Particle's ID.
	 * @param type
	 *            - <i>int</i> - The Partcile's type. */
	public Particle(String id, int type)
	{
		super(id, ObjectBase.PARTICLE);
		this.type = type;
	}

	/** Returns a description of this Particle. */
	public String getDescription()
	{
		return this.description;
	}

	@Override
	public String getName()
	{
		return this.getId();
	}

	/** Returns the type of this Particle. */
	public int getParticleType()
	{
		return this.type;
	}

	@Override
	public ImageIcon getTexture()
	{
		return null;
	}

	@Override
	public void updateLang()
	{
		this.description = Lang.get("PARTICLES:" + this.getId());
	}
}
