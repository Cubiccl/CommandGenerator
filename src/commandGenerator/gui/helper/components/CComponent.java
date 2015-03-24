package commandGenerator.gui.helper.components;


public interface CComponent
{
	/** Resets the component to its original situation */
	public abstract void reset();

	/** Sets all of this component's parts enabled */
	public abstract void setEnabledContent(boolean enable);

	/** Updates this component so it adapts to the selected language. */
	public abstract void updateLang();

}
