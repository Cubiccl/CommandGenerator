package commandGenerator.gui.helper.components;

import java.util.Map;

public interface CComponent
{
	/** Sets all of this component's parts enabled */
	public abstract void setEnabledContent(boolean enable);

	/** Updates this component so it adapts to the selected language. */
	public abstract void updateLang();

	/** Setups the component as if it was filled by the user thanks to the data argument.
	 * 
	 * @param data
	 *            - <i>Map:String->Object</i> - The data to use. */
	public abstract void setupFrom(Map<String, Object> data);

	/** Resets the component to its original situation */
	public abstract void reset();

}
