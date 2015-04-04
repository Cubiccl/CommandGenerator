package commandGenerator.arguments.command.arguments;

import java.util.List;

import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;

public interface INBTArgument
{
	
	public TagCompound getNBT();

	public void setupNBT(List<Tag> data);

}
