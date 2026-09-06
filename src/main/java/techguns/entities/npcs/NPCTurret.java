package techguns.entities.npcs;

import java.util.UUID;

import com.google.common.base.Predicate;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import techguns.*;
import techguns.api.npc.INPCTechgunsShooter;
import techguns.api.npc.factions.ITGNpcTeam;
import techguns.api.npc.factions.TGNpcFaction;
import techguns.entities.ai.EntityAIHurtByTargetTGFactions;
import techguns.entities.ai.EntityAIRangedAttack;
import techguns.entities.ai.TurretEntityAINearestAttackableTarget;
import techguns.entities.ai.TurretEntityAIWatchClosest;
import techguns.factions.TGNpcFactions;
import techguns.items.guns.GenericGun;
import techguns.tileentities.TurretTileEnt;

public class NPCTurret extends EntityCreature implements IAnimals, IRangedAttackMob, INPCTechgunsShooter, ITGNpcTeam{

	private static final ResourceLocation[] textures = new ResourceLocation[] {
			new ResourceLocation(Tags.MOD_ID,"textures/blocks/turret_base.png"),
			new ResourceLocation(Tags.MOD_ID,"textures/blocks/turret_base2.png"),
			new ResourceLocation(Tags.MOD_ID,"textures/blocks/turret_base3.png"),
			new ResourceLocation(Tags.MOD_ID,"textures/blocks/turret_base4.png"),
			new ResourceLocation(Tags.MOD_ID,"textures/blocks/turret_base5.png")
	};
	
	
	protected TurretEntityAIWatchClosest aiWatch=null;
	protected EntityAILookIdle aiIdle =null;
    protected EntityAIHurtByTargetTGFactions aiHurt=null;
    protected TurretEntityAINearestAttackableTarget aiTarget=null;

	private EntityAIRangedAttack aiRangedAttack = null; //EntityMob, Movespeed, ?, MaxRangedAttackTime, Range?
	
    public TurretTileEnt mountedTileEnt;
    
    public boolean active=true;
    protected EnumFacing turretFacing=EnumFacing.UP;

	public NPCTurret(World world) {
		super(world);
		
       this.setAITasks();
       this.setNoGravity(true);
        
        this.mountedTileEnt=null;
        this.height=0.9f;

	}
    
	public NPCTurret(World world, TurretTileEnt mountedTileEnt) {
		super(world);
		
        this.setAITasks();
        this.setNoGravity(true);

        this.mountedTileEnt=mountedTileEnt;
        if (!world.isRemote)
        {
            this.setCombatTask();
        }
        this.height=0.9f;
	}
	
	public static ResourceLocation getTexture(int tier){
		return textures[tier];
	}
	
	public ResourceLocation getTexture(){
		int tier=0;
		if(this.mountedTileEnt!=null){
			tier = this.mountedTileEnt.getTurretTier();
		}
		return getTexture(tier);
	}
	
    @Override
	public float getEyeHeight() {
		if(this.turretFacing==EnumFacing.DOWN) {
			return 0.15f;
		}
		return 0.75f;
	}

	/**
     * Returns true if this entity can attack entities of the specified class.
     */
	@Override
    public boolean canAttackClass(@NotNull Class target)
    {
        return true;
    }
	
	@Override
	public @NotNull ItemStack getHeldItemMainhand() {
		return getWeapon();
	}

	protected ItemStack getWeapon(){
		if (mountedTileEnt != null) return mountedTileEnt.getWeapon();
		return ItemStack.EMPTY;
	}
	
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0f);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(20.0D);
    }
	
	
    /**
     * Called when the mob's health reaches 0.
     */
    @Override
    public void onDeath(@NotNull DamageSource damageSource) {
    	if(this.mountedTileEnt!=null) this.mountedTileEnt.onTurretDeath();
    }
    
    /**
     * Drop 0-2 items of this living's type. @param par1 - Whether this entity has recently been hit by a player. @param
     * par2 - Level of Looting used to kill this mob.
     */
    @Override
    protected void dropFewItems(boolean hitByPlayer, int level){}


	/**
     * Sets this turrets base, called from the base on nbt read
     * @param ent
     */
    public void setTileEnt(TurretTileEnt ent){
    	this.mountedTileEnt=ent;
    	this.setCombatTask();
    }
    
    
    
    @Override
	public void onUpdate() {
    	super.onUpdate();
		this.setPosition(this.prevPosX, this.prevPosY, this.prevPosZ);
		if(! world.isRemote){
			if (this.mountedTileEnt==null){
				//try to relink
				int x=(int) Math.floor(this.posX);
				
				int y =((int) Math.round(this.posY))-1;
				if (this.turretFacing==EnumFacing.DOWN){
					y=((int) Math.round(this.posY))+1;
				}
				
				int z = (int) Math.floor(this.posZ);
				//System.out.println("Trying to relink:"+x+":"+y+":"+z);
				TileEntity tile = this.world.getTileEntity(new BlockPos(x,y,z));
				if (tile instanceof TurretTileEnt){
					((TurretTileEnt) tile).setMountedTurret(this);
					this.mountedTileEnt=(TurretTileEnt) tile;
					this.mountedTileEnt.needUpdate();
					this.setCombatTask();
				} else {
					this.attackEntityFrom(DamageSource.OUT_OF_WORLD, 1.0f);
				}
				
			} else {
				if (this.mountedTileEnt.mountedTurret!=this) {
					this.setDead();
				}
			}
		}
	}
    
	/**
     * Attack the specified entity using a ranged attack.
     */
    @Override
    public void attackEntityWithRangedAttack(@NotNull EntityLivingBase target, float distance)
    {

    	if (!this.getHeldItemMainhand().isEmpty()) {
    		Item gun = this.getHeldItemMainhand().getItem();
    		if (gun instanceof GenericGun) {
    			if (this.mountedTileEnt!=null && this.mountedTileEnt.consumeAmmo()){
    				((GenericGun) gun).fireWeaponFromNPC(this,1.0f,1.0f);
    			}
    		}
	
    	}
    	
    }

	@Override
	protected boolean canDespawn() {
		return false;
	}

	public void setAITasks(){
		if (aiWatch==null){
			//this.aiWatch = new TurretEntityAIWatchClosest(this, EntityLiving.class, 80.0F,20.0D);
			this.aiWatch = new TurretEntityAIWatchClosest(this, EntityLivingBase.class, 80.0F, 20.0D);
		}
		if(aiIdle==null){
			this.aiIdle = new EntityAILookIdle(this);
		}
		if(aiHurt==null){
			this.aiHurt =new EntityAIHurtByTargetTGFactions(this, false);
		}
		if(aiTarget==null){
			this.aiTarget= new TurretEntityAINearestAttackableTarget(this, EntityLivingBase.class, 0, true, false, (Predicate<EntityLivingBase>) entity -> {
				if(entity instanceof IMob){
					return true;
				}

				if(NPCTurret.this.mountedTileEnt!=null){
					byte pvp = NPCTurret.this.mountedTileEnt.getPvpSetting();
					UUID owner =NPCTurret.this.mountedTileEnt.getOwner();

					if (pvp!= 0 && owner!=null){

					//	System.out.println("Checking for Target:"+entity);

						if(entity instanceof NPCTurret){
							NPCTurret otherTurret = (NPCTurret) entity;
							if (otherTurret.mountedTileEnt!=null){
								UUID otherOwner = otherTurret.mountedTileEnt.getOwner();
								return TGNpcFactions.shouldAttack(owner, otherOwner, NPCTurret.this.mountedTileEnt.getPvpSetting());
							}


						} else if (entity instanceof EntityPlayer) {
						//	System.out.println("Checking for Player Target:"+entity);
							UUID ply = ((EntityPlayer)entity).getGameProfile().getId();
							if (ply != null){
								if (owner.equals(ply)){
									return false;
								}
								return TGNpcFactions.shouldAttack(owner, ply, NPCTurret.this.mountedTileEnt.getPvpSetting());
							}
						}

					}
					if ( NPCTurret.this.mountedTileEnt.attackAnimals && entity instanceof IAnimals && ! (entity instanceof NPCTurret)){
						if (entity instanceof EntityTameable){
							return !((EntityTameable) entity).isTamed();

						} else if (entity instanceof EntityHorse){
							return !((EntityHorse) entity).isTame();

						} else if (entity instanceof IEntityOwnable){
							return ((IEntityOwnable) entity).getOwner() == null;
						}

						return true;
					}
				}
				//System.out.println("Turret is null");
				//Fallbacks for no UUID/owner set etc...

				/**
				 * no turret vs turret
				 */
				if (entity instanceof NPCTurret){
					return false;
				}

				if(entity instanceof EntityPlayer){
					return false;
				}

				return false;
			},20.0D);
		}
		this.tasks.addTask(2, this.aiWatch);
        this.tasks.addTask(3, this.aiIdle);
        this.targetTasks.addTask(1, this.aiHurt);
        this.targetTasks.addTask(2, this.aiTarget);
	}
	
	public void disable(){
		this.active=false;
		this.tasks.removeTask(this.aiWatch);
		this.tasks.removeTask(this.aiIdle);
		this.targetTasks.removeTask(this.aiHurt);
		this.targetTasks.removeTask(this.aiTarget);
		this.tasks.removeTask(this.aiRangedAttack);
	}
	
	/**
     * sets this entity's combat AI.
     */
    public void setCombatTask()
    {
        this.tasks.removeTask(this.aiRangedAttack);
        ItemStack itemstack = this.getHeldItemMainhand();

        if (!itemstack.isEmpty() && itemstack.getItem() instanceof GenericGun)
        {
        	GenericGun gun = (GenericGun) itemstack.getItem();
        	this.aiRangedAttack = gun.getAIAttack(this);
            this.tasks.addTask(1, this.aiRangedAttack);
            this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(gun.getAI_attackRange());
        }
    }

	@Override
	public int getTotalArmorValue() {
		if (this.mountedTileEnt!=null){
			return mountedTileEnt.getTurretArmorValue();
		} else {
			return 0;
		}
		
	}

	@Override
	public @NotNull NBTTagCompound writeToNBT(@NotNull NBTTagCompound compound) {
		NBTTagCompound tags= super.writeToNBT(compound);
		tags.setByte("turretFacing", (byte) this.turretFacing.getIndex());
		return tags;
	}

	@Override
	public void readFromNBT(@NotNull NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.turretFacing = EnumFacing.byIndex(compound.getByte("turretFacing"));
	}

	@Override
	public float getWeaponPosX() {
		return 0.05f;
	}

	@Override
	public float getWeaponPosY() {
		return 0.7f;
	}

	@Override
	public float getWeaponPosZ() {
		return 0.8f;
	}

	@Override
	public TGNpcFaction getTGFaction() {
		return TGNpcFaction.TURRET;
	}

	@Override
	public void setSwingingArms(boolean swingingArms) {
	}

	public void setTurretFacing(EnumFacing turretFacing) {
		this.turretFacing = turretFacing;
	}    

	
}

