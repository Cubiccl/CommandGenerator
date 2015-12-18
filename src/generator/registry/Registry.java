package generator.registry;

import generator.CommandGenerator;
import generator.interfaces.ITranslate;
import generator.main.Utils;
import generator.registry.command.Command;

import java.util.ArrayList;
import java.util.HashMap;

/** Contains all game Objects. */
public class Registry implements ITranslate
{
	private Register<Achievement> achievements;
	private Register<Attribute> attributes;
	private Register<Block> blocks;
	private Register<Command> commands;
	private Register<Effect> effects;
	private Register<Enchantment> enchantments;
	private Register<Entity> entities;
	private Register<Item> items;
	private HashMap<String, ArrayList<ObjectBase>> lists;
	private Register<Particle> particles;
	private Register<Sound> sounds;

	public Registry()
	{
		this.achievements = new Register<Achievement>();
		this.attributes = new Register<Attribute>();
		this.blocks = new Register<Block>();
		this.commands = new Register<Command>();
		this.effects = new Register<Effect>();
		this.enchantments = new Register<Enchantment>();
		this.entities = new Register<Entity>();
		this.items = new Register<Item>();
		this.particles = new Register<Particle>();
		this.sounds = new Register<Sound>();
		this.lists = new HashMap<String, ArrayList<ObjectBase>>();
	}

	/** Adds an object to a list.
	 * 
	 * @param listId - The ID of the list.
	 * @param object - The input Object. */
	public void addObjectToList(String listId, ObjectBase object)
	{
		if (!this.lists.containsKey(listId)) this.lists.put(listId, new ArrayList<ObjectBase>());
		if (!this.lists.get(listId).contains(object)) this.lists.get(listId).add(object);
	}

	/** Finalizes the registry. Creates language & textures. */
	public void complete()
	{
		this.achievements.complete(Utils.getObjectTypeName(Utils.ACHIEVEMENT));
		this.attributes.complete(Utils.getObjectTypeName(Utils.ATTRIBUTE));
		this.blocks.complete(Utils.getObjectTypeName(Utils.BLOCK));
		this.commands.complete(Utils.getObjectTypeName(Utils.COMMAND));
		this.effects.complete(Utils.getObjectTypeName(Utils.EFFECT));
		this.enchantments.complete(Utils.getObjectTypeName(Utils.ENCHANTMENT));
		this.entities.complete(Utils.getObjectTypeName(Utils.ENTITY));
		this.items.complete(Utils.getObjectTypeName(Utils.ITEM));
		this.particles.complete(Utils.getObjectTypeName(Utils.PARTICLE));
		this.sounds.complete(Utils.getObjectTypeName(Utils.SOUND));
	}

	/** @param id - The ID of the target Achievement.
	 * @return The Achievement with the given ID. */
	public Achievement getAchievementFromId(String id)
	{
		return this.achievements.getObjectFromId(id);
	}

	/** @param id - The ID of the target Attribute.
	 * @return The Attribute with the given ID. */
	public Attribute getAttributeFromId(String id)
	{
		return this.attributes.getObjectFromId(id);
	}

	/** @param id - The ID of the target Block.
	 * @return The Block with the given ID. */
	public Block getBlockFromId(String id)
	{
		return this.blocks.getObjectFromId(id);
	}

	/** @param id - The ID of the target Command.
	 * @return The Command with the given ID. */
	public Command getCommandFromId(String id)
	{
		return this.commands.getObjectFromId(id);
	}

	/** @return The list of all Commands. */
	public Command[] getCommands()
	{
		return this.commands.getList().toArray(new Command[0]);
	}

	/** @param id - The ID of the target Effect.
	 * @return The Effect with the given ID. */
	public Effect getEffectFromId(String id)
	{
		return this.effects.getObjectFromId(id);
	}

	/** @param id - The ID of the target Enchantment.
	 * @return The Enchantment with the given ID. */
	public Enchantment getEnchantmentFromId(String id)
	{
		return this.enchantments.getObjectFromId(id);
	}

	/** @param id - The ID of the target Entity.
	 * @return The Entity with the given ID. */
	public Entity getEntityFromId(String id)
	{
		return this.entities.getObjectFromId(id);
	}

	/** @param id - The ID of the target Item.
	 * @return The Item with the given ID. */
	public Item getItemFromId(String id)
	{
		return this.items.getObjectFromId(id);
	}

	/** @param listId - The ID of the list.
	 * @return A list containing a certain type of Objects. */
	public ArrayList<ObjectBase> getList(String listId)
	{
		if (!this.lists.containsKey(listId))
		{
			CommandGenerator.log("There is no list with ID " + listId);
			return new ArrayList<ObjectBase>();
		}
		return this.lists.get(listId);
	}

	/** @param id - The ID of the target Particle.
	 * @return The Particle with the given ID. */
	public Particle getParticleFromId(String id)
	{
		return this.particles.getObjectFromId(id);
	}

	/** @param id - The ID of the target Sound.
	 * @return The Sound with the given ID. */
	public Sound getSoundFromId(String id)
	{
		return this.sounds.getObjectFromId(id);
	}

	/** Registers a new Achievement.
	 * 
	 * @param achievement - The input Achievement. */
	public void registerAchievement(Achievement achievement)
	{
		this.achievements.register(achievement);
	}

	/** Registers a new Attribute.
	 * 
	 * @param attribute - The input Attribute. */
	public void registerAttribute(Attribute attribute)
	{
		this.attributes.register(attribute);
	}

	/** Registers a new Block.
	 * 
	 * @param achievement - The input Block. */
	public void registerBlock(Block block)
	{
		this.blocks.register(block);
	}

	/** Registers a new Command.
	 * 
	 * @param command - The input Command. */
	public void registerCommand(Command command)
	{
		this.commands.register(command);
	}

	/** Registers a new Effect.
	 * 
	 * @param effect - The input Effect. */
	public void registerEffect(Effect effect)
	{
		this.effects.register(effect);
	}

	/** Registers a new Enchantment.
	 * 
	 * @param enchantment - The input Enchantment. */
	public void registerEnchantment(Enchantment enchantment)
	{
		this.enchantments.register(enchantment);
	}

	/** Registers a new Entity.
	 * 
	 * @param achievement - The input Entity. */
	public void registerEntity(Entity entity)
	{
		this.entities.register(entity);
	}

	/** Registers a new Item.
	 * 
	 * @param achievement - The input Item. */
	public void registerItem(Item item)
	{
		this.items.register(item);
	}

	/** Registers a new Particle.
	 * 
	 * @param achievement - The input Particle. */
	public void registerParticle(Particle particle)
	{
		this.particles.register(particle);
	}

	/** Registers a new Sound.
	 * 
	 * @param achievement - The input Sound. */
	public void registerSound(Sound sound)
	{
		this.sounds.register(sound);
	}

	@Override
	public void updateLang()
	{
		this.achievements.updateLang();
		this.attributes.updateLang();
		this.blocks.updateLang();
		this.commands.updateLang();
		this.effects.updateLang();
		this.enchantments.updateLang();
		this.entities.updateLang();
		this.items.updateLang();
		this.particles.updateLang();
		this.sounds.updateLang();
	}

}
