package techguns.items.guns;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import techguns.TGPackets;
import techguns.api.damagesystem.DamageType;
import techguns.api.render.IItemTGRenderer;
import techguns.client.audio.TGSoundCategory;
import techguns.entities.projectiles.EnumBulletFirePos;
import techguns.entities.projectiles.GrenadeProjectile;
import techguns.items.GenericItem;
import techguns.packets.PacketPlaySound;
import techguns.util.TextUtil;

public class GenericGrenade extends GenericItem implements IItemTGRenderer {

    public float damage = 20.0f;
    public float radius = 3.0f;
    public int maxBounces = 3;

    public float damageMin = 10.0f;
    public float radiusMin = 5.0f;

    public float fullChargeTime = 30.0f;

    public int ticksToLive = 200;
    public float speed = 0.75f;

    public float spread = 0.1f;

    public float penetration = 0f;
    public static ArrayList<GenericGrenade> grenades = new ArrayList<>();

    protected SoundEvent startSound = null;

    IGrenadeProjectileFactory<? extends GrenadeProjectile> projectile_factory;

    public GenericGrenade(String name, int stacksize, IGrenadeProjectileFactory<? extends GrenadeProjectile> projectile_factory) {
        super(name, false);
        setMaxStackSize(stacksize);
        setNoRepair();
        this.projectile_factory = projectile_factory;
        grenades.add(this);
    }

    public GenericGrenade setStartSound(SoundEvent sound) {
        this.startSound = sound;
        return this;
    }

    public GenericGrenade setDamageAndRadius(float damage, float radiusMax, float damageMin, float radiusMin) {
        this.damage = damage;
        this.radius = radiusMax;
        this.damageMin = damageMin;
        this.radiusMin = radiusMin;
        return this;
    }

    public GenericGrenade setTicksToLive(int ticksToLive) {
        this.ticksToLive = ticksToLive;
        return this;
    }

    public GenericGrenade setSpeed(float speed) {
        this.speed = speed;
        return this;
    }

    public GenericGrenade setSpread(float spread) {
        this.spread = spread;
        return this;
    }

    public GenericGrenade setPenetration(float penetration) {
        this.penetration = penetration;
        return this;
    }

    @Override
    public @NotNull ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @NotNull EnumHand handIn) {
        playerIn.setActiveHand(handIn);
        if (!worldIn.isRemote && this.startSound != null) {
            TGPackets.wrapper.sendToAllAround(new PacketPlaySound(this.startSound, playerIn, 1, 1, false, false, true, true, TGSoundCategory.PLAYER_EFFECT), TGPackets.targetPointAroundEnt(playerIn, 24.0f));
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @Override
    public void onPlayerStoppedUsing(@NotNull ItemStack stack, @NotNull World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        int j = this.getMaxItemUseDuration(stack) - timeLeft;

        boolean leftHand = entityLiving.getActiveHand() == EnumHand.OFF_HAND;
        if (entityLiving.getPrimaryHand() == EnumHandSide.LEFT) {
            leftHand = !leftHand;
        }

        EnumBulletFirePos firePos = leftHand ? EnumBulletFirePos.LEFT : EnumBulletFirePos.RIGHT;

        float f = j / (this.fullChargeTime);

        if (f > 1.0F) {
            f = 1.0F;
        }

        if (entityLiving instanceof EntityPlayer ply) {
            if (!ply.capabilities.isCreativeMode) {
                stack.shrink(1);
            }
        }

        if (!worldIn.isRemote) {
            spawnGrenade(worldIn, entityLiving, f, firePos);
        }
        entityLiving.swingArm(entityLiving.getActiveHand());
    }


    public void spawnGrenade(World world, EntityLivingBase entityLiving, float charge, EnumBulletFirePos firePos) {
        float grav = (0.015f / charge);

        GrenadeProjectile proj = this.projectile_factory.createProjectile(world, entityLiving, this.damage, speed, this.ticksToLive, spread, this.radius, this.radiusMin, this.damageMin, penetration, GenericGun.getDoBlockDamage(entityLiving), firePos, radiusMin, grav, charge, maxBounces);

        world.spawnEntity(proj);
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(@NotNull ItemStack item) {
        return 72000;
    }

    protected DamageType getDamageType() {
        return DamageType.EXPLOSION;
    }

    @Override
    public void addInformation(@NotNull ItemStack stack, World worldIn, @NotNull List<String> tooltip, @NotNull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
            tooltip.add(TextUtil.trans("techguns.gun.tooltip.damageType") + ": " + this.getDamageType().toString());
            tooltip.add(TextUtil.trans("techguns.gun.tooltip.damage") + ": " + this.damage);
            tooltip.add(TextUtil.trans("techguns.gun.tooltip.radius") + ": " + this.radius);
            tooltip.add(TextUtil.trans("techguns.gun.tooltip.bounces") + ": " + this.maxBounces);
        } else {
            tooltip.add(TextUtil.trans("techguns.gun.tooltip.damage") + ": " + this.damage);
            tooltip.add(TextUtil.trans("techguns.gun.tooltip.shift1") + " " + ChatFormatting.GREEN + TextUtil.trans("techguns.gun.tooltip.shift2") + " " + ChatFormatting.GRAY + TextUtil.trans("techguns.gun.tooltip.shift3"));
        }
    }
}
