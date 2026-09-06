package techguns.entities.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import techguns.blocks.BlockTGDoor2x1;

import static net.minecraft.block.BlockDoor.OPEN;

public class EntityAIAttackTGMelee extends EntityAIBase
{
    World world;
    protected EntityCreature attacker;
    protected int attackTick;
    double speedTowardsTarget;
    boolean longMemory;
    Path path;
    private int delayCounter;
    private double targetX;
    private double targetY;
    private double targetZ;
    protected final int attackInterval = 20;
    private int failedPathFindingPenalty = 0;
    private boolean canPenalize = false;
    private final EntityAIOpenTGDoor doorOpenAI;

    public EntityAIAttackTGMelee(EntityCreature creature, double speedIn, boolean useLongMemory)
    {
        this.attacker = creature;
        this.world = creature.world;
        this.speedTowardsTarget = speedIn;
        this.longMemory = useLongMemory;
        this.setMutexBits(3);

        if (this.attacker.getNavigator() instanceof PathNavigateGround) {
            ((PathNavigateGround) this.attacker.getNavigator()).setBreakDoors(true);
            ((PathNavigateGround) this.attacker.getNavigator()).setEnterDoors(true);
        }
        this.doorOpenAI = new EntityAIOpenTGDoor(this.attacker, true);
        this.attacker.tasks.addTask(1, this.doorOpenAI);
    }

    public boolean shouldExecute()
    {
        EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();

        if (entitylivingbase == null)
        {
            return false;
        }
        else if (!entitylivingbase.isEntityAlive())
        {
            return false;
        }
        else
        {
            if (canPenalize)
            {
                if (--this.delayCounter <= 0)
                {
                    this.path = this.attacker.getNavigator().getPathToEntityLiving(entitylivingbase);
                    this.delayCounter = 4 + this.attacker.getRNG().nextInt(7);
                    return this.path != null;
                }
                else
                {
                    return true;
                }
            }
            this.path = this.attacker.getNavigator().getPathToEntityLiving(entitylivingbase);

            if (this.path != null)
            {
                return true;
            }
            else
            {
                return this.getAttackReachSqr(entitylivingbase) >= this.attacker.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
            }
        }
    }

    public boolean shouldContinueExecuting()
    {
        EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();

        if (entitylivingbase == null)
        {
            return false;
        }
        else if (!entitylivingbase.isEntityAlive())
        {
            return false;
        }
        else if (!this.longMemory)
        {
            return !this.attacker.getNavigator().noPath();
        }
        else if (!this.attacker.isWithinHomeDistanceFromPosition(new BlockPos(entitylivingbase)))
        {
            return false;
        }
        else
        {
            return !(entitylivingbase instanceof EntityPlayer) || !((EntityPlayer)entitylivingbase).isSpectator() && !((EntityPlayer)entitylivingbase).isCreative();
        }
    }

    public void startExecuting()
    {
        this.attacker.getNavigator().setPath(this.path, this.speedTowardsTarget);
        this.delayCounter = 0;
    }

    public void resetTask()
    {
        EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();

        if (entitylivingbase instanceof EntityPlayer && (((EntityPlayer)entitylivingbase).isSpectator() || ((EntityPlayer)entitylivingbase).isCreative()))
        {
            this.attacker.setAttackTarget((EntityLivingBase)null);
        }

        this.attacker.getNavigator().clearPath();
    }

    public void updateTask()
    {
        EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
        this.attacker.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
        double d0 = this.attacker.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
        --this.delayCounter;

        if ((this.longMemory || this.attacker.getEntitySenses().canSee(entitylivingbase)) && this.delayCounter <= 0 && (this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D || entitylivingbase.getDistanceSq(this.targetX, this.targetY, this.targetZ) >= 1.0D || this.attacker.getRNG().nextFloat() < 0.05F))
        {
            this.targetX = entitylivingbase.posX;
            this.targetY = entitylivingbase.getEntityBoundingBox().minY;
            this.targetZ = entitylivingbase.posZ;
            this.delayCounter = 4 + this.attacker.getRNG().nextInt(7);

            if (this.canPenalize)
            {
                this.delayCounter += failedPathFindingPenalty;
                if (this.attacker.getNavigator().getPath() != null)
                {
                    net.minecraft.pathfinding.PathPoint finalPathPoint = this.attacker.getNavigator().getPath().getFinalPathPoint();
                    if (finalPathPoint != null && entitylivingbase.getDistanceSq(finalPathPoint.x, finalPathPoint.y, finalPathPoint.z) < 1)
                        failedPathFindingPenalty = 0;
                    else
                        failedPathFindingPenalty += 10;
                }
                else
                {
                    failedPathFindingPenalty += 10;
                }
            }

            if (d0 > 1024.0D)
            {
                this.delayCounter += 10;
            }
            else if (d0 > 256.0D)
            {
                this.delayCounter += 5;
            }

            if (!this.attacker.getNavigator().tryMoveToEntityLiving(entitylivingbase, this.speedTowardsTarget))
            {
                this.delayCounter += 15;
            }

            if (this.attacker.getNavigator().getPath() != null) {
                this.doorOpenAI.updateTask();
            }
        }

        this.attackTick = Math.max(this.attackTick - 1, 0);
        this.checkAndPerformAttack(entitylivingbase, d0);
    }

    protected void checkAndPerformAttack(EntityLivingBase p_190102_1_, double p_190102_2_)
    {
        double d0 = this.getAttackReachSqr(p_190102_1_);

        if (p_190102_2_ <= d0 && this.attackTick <= 0)
        {
            this.attackTick = 20;
            this.attacker.swingArm(EnumHand.MAIN_HAND);
            this.attacker.attackEntityAsMob(p_190102_1_);
        }
    }

    protected double getAttackReachSqr(EntityLivingBase attackTarget)
    {
        return (double)(this.attacker.width * 2.0F * this.attacker.width * 2.0F + attackTarget.width);
    }

    private class EntityAIOpenTGDoor extends EntityAIBase
    {
        private final EntityLiving entity;
        private BlockPos doorPosition;
        private boolean hasStoppedDoorInteraction;
        private float entityPositionX;
        private float entityPositionZ;

        public EntityAIOpenTGDoor(EntityLiving entityIn, boolean shouldClose)
        {
            this.entity = entityIn;
            this.setMutexBits(3);
        }

        @Override
        public boolean shouldExecute()
        {
            if (!this.entity.collidedHorizontally)
            {
                return false;
            }
            else
            {
                PathNavigateGround pathnavigateground = (PathNavigateGround)this.entity.getNavigator();
                Path path = pathnavigateground.getPath();

                if (path != null && !path.isFinished() && pathnavigateground.getEnterDoors())
                {
                    for (int i = 0; i < Math.min(path.getCurrentPathIndex() + 2, path.getCurrentPathLength()); ++i)
                    {
                        PathPoint pathpoint = path.getPathPointFromIndex(i);
                        BlockPos pos = new BlockPos(pathpoint.x, pathpoint.y, pathpoint.z);

                        if (this.entity.getDistanceSq(pos.getX(), this.entity.posY, pos.getZ()) <= 2.25D)
                        {
                            this.doorPosition = this.getDoorPosition(pos);

                            if (this.doorPosition != null)
                            {
                                return true;
                            }
                        }
                    }

                    this.doorPosition = this.getDoorPosition(new BlockPos(this.entity).up());
                    return this.doorPosition != null;
                }
                else
                {
                    return false;
                }
            }
        }

        @Override
        public void startExecuting()
        {
            this.hasStoppedDoorInteraction = false;
            this.entityPositionX = (float)((double)this.doorPosition.getX() + 0.5D - this.entity.posX);
            this.entityPositionZ = (float)((double)this.doorPosition.getZ() + 0.5D - this.entity.posZ);
        }

        @Override
        public boolean shouldContinueExecuting()
        {
            return !this.hasStoppedDoorInteraction;
        }

        @Override
        public void updateTask()
        {
            if (this.doorPosition != null)
            {
                double distanceSq = this.entity.getDistanceSq(
                        this.doorPosition.getX() + 0.5D,
                        this.doorPosition.getY() + 0.5D,
                        this.doorPosition.getZ() + 0.5D
                );

                boolean nearDoor = distanceSq < 2.25D;

                if (nearDoor)
                {
                    this.interactWithDoor(this.doorPosition, true);
                }

                float f = (float)((double)this.doorPosition.getX() + 0.5D - this.entity.posX);
                float f1 = (float)((double)this.doorPosition.getZ() + 0.5D - this.entity.posZ);
                float f2 = this.entityPositionX * f + this.entityPositionZ * f1;

                if (f2 < 0.0F || !nearDoor)
                {
                    this.hasStoppedDoorInteraction = true;
                }
            }
            else
            {
                this.hasStoppedDoorInteraction = true;
            }
        }

        private BlockPos getDoorPosition(BlockPos pos)
        {
            IBlockState iblockstate = this.entity.world.getBlockState(pos);
            Block block = iblockstate.getBlock();

            if (block instanceof BlockDoor && iblockstate.getMaterial() == Material.WOOD)
            {
                return pos;
            }
            else if (block instanceof BlockTGDoor2x1)
            {
                return pos;
            }

            return null;
        }

        private void interactWithDoor(BlockPos pos, boolean open) {
            IBlockState state = this.entity.world.getBlockState(pos);
            Block block = state.getBlock();
            if (block instanceof BlockTGDoor2x1) {
                if (!((Boolean) state.getValue(OPEN)).booleanValue()) {
                    EnumFacing facing = EnumFacing.fromAngle(this.entity.rotationYaw);
                    float hitX = 0.5f;
                    float hitY = 0.5f;
                    float hitZ = 0.5f;

                    block.onBlockActivated(this.entity.world, pos, state, null, EnumHand.MAIN_HAND, facing, hitX, hitY, hitZ);
                }
            } else if (block instanceof BlockDoor) {
                if (!((Boolean) state.getValue(OPEN)).booleanValue()) {
                    EnumFacing facing = EnumFacing.fromAngle(this.entity.rotationYaw);
                    float hitX = 0.5f;
                    float hitY = 0.5f;
                    float hitZ = 0.5f;

                    block.onBlockActivated(this.entity.world, pos, state, null, EnumHand.MAIN_HAND, facing, hitX, hitY, hitZ);
                }
            }
        }
    }
}
