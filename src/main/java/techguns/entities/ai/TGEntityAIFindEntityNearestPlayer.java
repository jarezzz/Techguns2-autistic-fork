package techguns.entities.ai;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.scoreboard.Team;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class TGEntityAIFindEntityNearestPlayer extends EntityAIBase {

    private final EntityLiving entity;
    private final Predicate<Entity> predicate;
    private final EntityAINearestAttackableTarget.Sorter sorter;
    private EntityLivingBase entityTarget;

    public TGEntityAIFindEntityNearestPlayer(EntityLiving entityIn) {
        this.entity = entityIn;
        this.predicate = new Predicate<Entity>()
        {
            public boolean apply(@Nullable Entity p_apply_1_)
            {
                if (!(p_apply_1_ instanceof EntityPlayer))
                {
                    return false;
                }
                else if (((EntityPlayer)p_apply_1_).capabilities.disableDamage)
                {
                    return false;
                }
                else
                {
                    double d0 = TGEntityAIFindEntityNearestPlayer.this.maxTargetRange();

                    if (p_apply_1_.isSneaking())
                    {
                        d0 *= 0.800000011920929D;
                    }

                    if (p_apply_1_.isInvisible())
                    {
                        float f = ((EntityPlayer)p_apply_1_).getArmorVisibility();

                        if (f < 0.1F)
                        {
                            f = 0.1F;
                        }

                        d0 *= (double)(0.7F * f);
                    }

                    return (double)p_apply_1_.getDistance(TGEntityAIFindEntityNearestPlayer.this.entity) > d0 ? false : EntityAITarget.isSuitableTarget(TGEntityAIFindEntityNearestPlayer.this.entity, (EntityLivingBase)p_apply_1_, false, true);
                }
            }
        };
        this.sorter = new EntityAINearestAttackableTarget.Sorter(entityIn);
    }


    public boolean shouldExecute()
    {
        double d0 = this.maxTargetRange();
        List<EntityPlayer> list = this.entity.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, this.entity.getEntityBoundingBox().grow(d0, -24.0D, d0), this.predicate);
        Collections.sort(list, this.sorter);

        if (list.isEmpty())
        {
            return false;
        }
        else
        {
            this.entityTarget = list.get(0);
            return true;
        }
    }

    public boolean shouldContinueExecuting()
    {
        EntityLivingBase entitylivingbase = this.entity.getAttackTarget();

        if (entitylivingbase == null)
        {
            return false;
        }
        else if (!entitylivingbase.isEntityAlive())
        {
            return false;
        }
        else if (entitylivingbase instanceof EntityPlayer && ((EntityPlayer)entitylivingbase).capabilities.disableDamage)
        {
            return false;
        }
        else
        {
            Team team = this.entity.getTeam();
            Team team1 = entitylivingbase.getTeam();

            if (team != null && team1 == team)
            {
                return false;
            }
            else
            {
                double d0 = this.maxTargetRange();

                if (this.entity.getDistanceSq(entitylivingbase) > d0 * d0)
                {
                    return false;
                }
                else
                {
                    return !(entitylivingbase instanceof EntityPlayerMP) || !((EntityPlayerMP)entitylivingbase).interactionManager.isCreative();
                }
            }
        }
    }

    public void startExecuting()
    {
        this.entity.setAttackTarget(this.entityTarget);
        super.startExecuting();
    }

    public void resetTask()
    {
        this.entity.setAttackTarget((EntityLivingBase)null);
        super.startExecuting();
    }

    protected double maxTargetRange()
    {
        IAttributeInstance iattributeinstance = this.entity.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
        return iattributeinstance == null ? 16.0D : iattributeinstance.getAttributeValue();
    }
}
