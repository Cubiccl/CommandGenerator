package generator.registry;

import java.util.ArrayList;
import java.util.HashMap;

import generator.CommandGenerator;
import generator.interfaces.ITranslate;

public class Registry implements ITranslate
{
	private Register<Achievement> achievements;
	private Register<Attribute> attributes;
	private Register<Block> blocks;
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
		this.effects = new Register<Effect>();
		this.enchantments = new Register<Enchantment>();
		this.entities = new Register<Entity>();
		this.items = new Register<Item>();
		this.particles = new Register<Particle>();
		this.sounds = new Register<Sound>();
		this.lists = new HashMap<String, ArrayList<ObjectBase>>();
	}

	public void addObjectToList(String listId, ObjectBase object)
	{
		if (!this.lists.containsKey(listId)) this.lists.put(listId, new ArrayList<ObjectBase>());
		if (!this.lists.get(listId).contains(object)) this.lists.get(listId).add(object);
	}

	public Achievement getAchievementFromId(String id)
	{
		return this.achievements.getObjectFromId(id);
	}

	public Attribute getAttributeFromId(String id)
	{
		return this.attributes.getObjectFromId(id);
	}

	public Block getBlockFromId(String id)
	{
		return this.blocks.getObjectFromId(id);
	}

	public Effect getEffectFromId(String id)
	{
		return this.effects.getObjectFromId(id);
	}

	public Enchantment getEnchantmentFromId(String id)
	{
		return this.enchantments.getObjectFromId(id);
	}

	public Entity getEntityFromId(String id)
	{
		return this.entities.getObjectFromId(id);
	}

	public Item getItemFromId(String id)
	{
		return this.items.getObjectFromId(id);
	}

	public ArrayList<ObjectBase> getList(String listId)
	{
		if (!this.lists.containsKey(listId))
		{
			CommandGenerator.log("There is no list with ID " + listId);
			return new ArrayList<ObjectBase>();
		}
		return this.lists.get(listId);
	}

	public Particle getParticleFromId(String id)
	{
		return this.particles.getObjectFromId(id);
	}

	public Sound getSoundFromId(String id)
	{
		return this.sounds.getObjectFromId(id);
	}

	public void registerAchievement(Achievement achievement)
	{
		this.achievements.register(achievement);
	}

	public void registerAttribute(Attribute achievement)
	{
		this.attributes.register(achievement);
	}

	public void registerBlock(Block block)
	{
		this.blocks.register(block);
	}

	public void registerEffect(Effect achievement)
	{
		this.effects.register(achievement);
	}

	public void registerEnchantment(Enchantment achievement)
	{
		this.enchantments.register(achievement);
	}

	public void registerEntity(Entity entity)
	{
		this.entities.register(entity);
	}

	public void registerItem(Item item)
	{
		this.items.register(item);
	}

	public void registerParticle(Particle particle)
	{
		this.particles.register(particle);
	}

	public void registerSound(Sound sound)
	{
		this.sounds.register(sound);
	}

	@Override
	public void updateLang()
	{
		this.items.updateLang();
	}

}
