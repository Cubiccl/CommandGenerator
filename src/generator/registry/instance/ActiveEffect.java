package generator.registry.instance;

import generator.main.Utils;
import generator.registry.Effect;

/** An Effect applied to an Entity. */
public class ActiveEffect extends ObjectInstance
{
	/** The amplifier of the Effect. */
	private int amplifier;
	/** The duration of the Effect. */
	private int duration;
	/** The Effect type. */
	private Effect effect;
	/** True if the particles should be hidden. */
	private boolean hideParticles;

	/** Creates a new Active Effect.
	 * 
	 * @param effect - The Effect type.
	 * @param duration - The duration of the Effect.
	 * @param amplifier - The amplifier of the Effect.
	 * @param hideParticles - True if the particles should be hidden. */
	public ActiveEffect(Effect effect, int duration, int amplifier, boolean hideParticles)
	{
		super(Utils.EFFECT);
		this.effect = effect;
		this.duration = duration;
		this.amplifier = amplifier;
		this.hideParticles = hideParticles;
	}

	/** @return This Effect's amplifier. */
	public int getAmplifier()
	{
		return this.amplifier;
	}

	/** @return This Effect's duration. */
	public int getDuration()
	{
		return this.duration;
	}

	/** @return The Effect type. */
	public Effect getEffect()
	{
		return this.effect;
	}

	/** @return True if the particles should be hidden. */
	public boolean isHideParticles()
	{
		return this.hideParticles;
	}

	@Override
	public String toCommand()
	{
		return this.effect.getId() + " " + this.duration + " " + this.amplifier + " " + this.hideParticles;
	}

}
