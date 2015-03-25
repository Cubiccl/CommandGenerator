package commandGenerator.arguments.objects;

import commandGenerator.arguments.tags.TagBoolean;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagInt;
import commandGenerator.main.Lang;

public class Effect
{

	/** This Effect's level. */
	private int amplifier;

	/** This Effect's duration. */
	private int duration;

	/** True if this Effect's particles should be shown. */
	private boolean showParticles;

	/** This Effect's type. */
	private EffectType type;

	/** Creates a new Effect
	 * 
	 * @param type
	 *            - <i>EffectType</i> - The Effect type
	 * @param amplifier
	 *            - <i>int</i> - The Effect amplifier
	 * @param duration
	 *            - <i>int</i> - The Effect duration */
	public Effect(EffectType type, int amplifier, int duration)
	{
		this(type, amplifier, duration, true);
	}

	/** Creates a new Effect
	 * 
	 * @param type
	 *            - <i>EffectType</i> - The Effect type
	 * @param amplifier
	 *            - <i>int</i> - The Effect amplifier
	 * @param duration
	 *            - <i>int</i> - The Effect duration
	 * @param showParticles
	 *            - <i>boolean</i> - Are this Effect's particles shown? */
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
		return this.type.getId() + " " + Integer.toString(this.duration) + " " + Integer.toString(this.amplifier) + " " + Boolean.toString(!this.showParticles);
	}

	/** Returns a String version of this Effect to be displayed to the user. */
	public String display()
	{
		String display = Lang.get("GUI:effect.display");
		display = display.replaceAll("<effect>", this.type.getName());
		display = display.replaceAll("<amplifier>", Integer.toString(this.amplifier + 1));
		display = display.replaceAll("<duration>", Integer.toString(this.duration));
		if (this.showParticles) display = display.replaceAll("<particles>", "");
		else display = display.replaceAll("<particles>", Lang.get("GUI:effect.particles"));
		return display;
	}

	/** Returns this Effect's amplifier. */
	public int getAmplifier()
	{
		return this.amplifier;
	}

	/** Returns this Effect's duration. */
	public int getDuration()
	{
		return this.duration;
	}

	/** Returns this Effect's type. */
	public EffectType getType()
	{
		return this.type;
	}

	/** Returns whether this Effect's particles should be shown. */
	public boolean showParticles()
	{
		return this.showParticles;
	}

	/** Turns the Effect into a TagCompound. */
	public TagCompound toNBT()
	{

		TagCompound tag = new TagCompound() {
			@Override
			public void askValue()
			{}
		};
		tag.addTag(new TagInt("Id").setValue(this.type.getIdNum()));
		tag.addTag(new TagInt("Amplifier").setValue(this.amplifier));
		tag.addTag(new TagInt("Duration").setValue(this.duration));
		tag.addTag(new TagBoolean("ShowParticles").setValue(this.showParticles));
		tag.addTag(new TagBoolean("Ambient").setValue(false));
		return tag;

	}

}
