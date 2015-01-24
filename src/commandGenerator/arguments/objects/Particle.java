package commandGenerator.arguments.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import commandGenerator.main.CGConstants;
import commandGenerator.main.Lang;

public class Particle extends ObjectBase
{

	public static final int NORMAL = 0, BLOCK = 1, ITEM = 2;
	private static Map<String, Particle> list = new HashMap<String, Particle>();
	private static List<String> ids = new ArrayList<String>();

	private int type;

	public Particle(String id, int type)
	{
		super(id, CGConstants.OBJECT_PARTICLE);
		this.type = type;
		list.put(id, this);
		ids.add(id);
	}

	public String getDescription()
	{
		return Lang.get("PARTICLES:" + getId());
	}

	public int getParticleType()
	{
		return type;
	}

	public static Particle getParticleFromId(String id)
	{
		return list.get(id);
	}

	@Override
	public String getName()
	{
		return getId();
	}

	@Override
	public ImageIcon getTexture()
	{
		return null;
	}

	public static Particle[] getList()
	{
		Particle[] particles = new Particle[list.size()];
		for (int i = 0; i < particles.length; i++)
			particles[i] = list.get(ids.get(i));
		return particles;
	}
}
