package techguns.items.guns;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import techguns.TGPackets;
import techguns.entities.projectiles.EnumBulletFirePos;
import techguns.entities.projectiles.SonicShotgunProjectile;
import techguns.packets.PacketSpawnParticleOnEntity;
import techguns.util.MathUtil;

public class SonicShotgun extends GenericGun {

    public SonicShotgun(String name, ProjectileSelector projectileSelector, boolean semiAuto, int minFiretime,
                        int clipsize, int reloadtime, float damage, SoundEvent firesound, SoundEvent reloadsound, int TTL,
                        float accuracy) {
        super(name, projectileSelector, semiAuto, minFiretime, clipsize, reloadtime, damage, firesound, reloadsound, TTL,
                accuracy);
    }

    @Override
    protected void spawnProjectile(World world, EntityLivingBase player, ItemStack itemstack, float spread,
                                   float offset, float damagebonus, EnumBulletFirePos firePos, Entity target) {

        int count = 5; //Base projectile count per ring
        int rings = 2; //number of rings

        spread = 0.075f;

        //Center
        ArrayList<Entity> entitiesHit = new ArrayList<>();
        SonicShotgunProjectile proj = new SonicShotgunProjectile(world, player, damage * damagebonus, speed, this.ticksToLive, 0f, this.damageDropStart, this.damageDropEnd, this.damageMin * damagebonus, this.penetration, getDoBlockDamage(player), firePos);
        proj.entitiesHit = entitiesHit;
        proj.mainProjectile = true;
        if (offset > 0.0f) {
            proj.shiftForward(offset);
        }
        world.spawnEntity(proj);
        TGPackets.wrapper.sendToAllAround(new PacketSpawnParticleOnEntity("SonicShotgunTrail", proj), TGPackets.targetPointAroundEnt(proj, 100.0f));

        //Rings
        for (int j = 1; j <= rings; j++) {

            float angle = (float) ((Math.PI * 2.0) / (double) (count * j));
            for (int i = 0; i < (count * j); i++) {

                proj = new SonicShotgunProjectile(world, player, damage * damagebonus, speed, this.ticksToLive, 0f, this.damageDropStart, this.damageDropEnd, this.damageMin * damagebonus, this.penetration, getDoBlockDamage(player), firePos);
                proj.entitiesHit = entitiesHit;
                Vec3d dir = new Vec3d(1, 0, 0);

                dir = dir.rotateYaw(spread * j);
                dir = dir.rotatePitch(angle * i);

                dir = MathUtil.rotateVec3dAroundZ(dir, (float) (player.rotationPitch * MathUtil.D2R));

                dir = dir.rotateYaw((float) ((-player.rotationYawHead - 90.0) * MathUtil.D2R));

                proj.motionX = dir.x * speed;
                proj.motionY = dir.y * speed;
                proj.motionZ = dir.z * speed;

                if (offset > 0.0f) {
                    proj.shiftForward(offset);
                }

                world.spawnEntity(proj);
            }
        }
    }
}
