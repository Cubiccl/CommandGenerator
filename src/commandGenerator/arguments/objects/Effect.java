package commandGenerator.arguments.objects;

import commandGenerator.arguments.tags.TagBoolean;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagInt;
import commandGenerator.main.Lang;

public class Effect
{

	private EffectType type;
	private int amplifier, duration;
	private boolean showParticles;

	/** Creates a new Effect
	 * 
	 * @param type
	 *            - The Effect type
	 * @param amplifier
	 *            - The Effect amplifier
	 * @param duration
	 *            - The Effect duration */
	public Effect(EffectType type, int amplifier, int duration)
	{
		this(type, amplifier, duration, true);
	}

	/** Creates a new Effect
	 * 
	 * @param type
	 *            - The Effect type
	 * @param amplifier
	 *            - The Effect amplifier
	 * @param duration
	 *            - The Effect duration
	 * @param showParticles
	 *            - Are this Effect's particles shown? */
	public Effect(EffectType type, int amplifier, int duration, boolean showParticles)
	{
		this.type = type;
		this.amplifier = amplifier;
		this.duration = duration;
		this.showParticles = showParticles;
	}

	/** Generates the command structure for the /effect command. */
	public String commandStructure()
	{
		if (showParticles) return type.getId() + " " + Integer.toString(duration) + " " + Integer.toString(amplifier);
		else return type.getId() + " " + Integer.toString(duration) + " " + Integer.toString(amplifier) + " true";
	}

	/** Displays the Effect for the user. */
	public String display()
	{
		String display = Lang.get("GUI:effect.display");
		display = display.replaceAll("<effect>", type.getName());
		display = display.replaceAll("<amplifier>", Integer.toString(amplifier + 1));
		display = display.replaceAll("<duration>", Integer.toString(duration));
		if (showParticles) display = display.replaceAll("<particles>", "");
		else display = display.replaceAll("<particles>", Lang.get("GUI:effect.particles"));
		return display;
	}

	public int getAmplifier()
	{
		return amplifier;
	}

	public int getDuration()
	{
		return duration;
	}

	public String getId()
	{
		return type.getId();
	}

	public boolean showParticles()
	{
		return showParticles;
	}

	/** Turns the Effect into a TagCompound. */
	public TagCompound toNBT()
	{

		TagCompound tag = new TagCompound() {
			public void askValue()
			{}
		};
		tag.addTag(new TagInt("Id").setValue(type.getIdNum()));
		tag.addTag(new TagInt("Amplifier").setValue(amplifier));
		tag.addTag(new TagInt("Duration").setValue(duration));
		tag.addTag(new TagBoolean("ShowParticles").setValue(showParticles));
		tag.addTag(new TagBoolean("Ambient").setValue(false));
		return tag;

	}

	public EffectType getEffectType()
	{
		return type;
	}

}
