package de.Roboter007.voxelsociety.world.entity;

import de.Roboter007.voxelsociety.utils.texture.Texture;
import de.Roboter007.voxelsociety.world.World;
import de.Roboter007.voxelsociety.world.entity.animation.AnimationState;
import de.Roboter007.voxelsociety.utils.texture.PathLocation;

public abstract class AnimatedEntity extends Entity {
    protected AnimationState animationState = AnimationState.DOWN;
    protected int spriteNumber = 1;

    protected int animationCooldown = 0;
    protected final int maxAnimationCooldown;
    protected Texture pathLocation = Texture.voxelSociety("player", "player_" + this.animationState.getName() + "_" + spriteNumber + ".png");


    public AnimatedEntity(World world, int maxAnimationCooldown) {
        this.maxAnimationCooldown = maxAnimationCooldown;
        this.world = world;
    }

    public void update() {
        animationCooldown++;
        if (animationCooldown > maxAnimationCooldown) {
            if (spriteNumber == 1) {
                spriteNumber = 2;
            } else {
                spriteNumber = 1;
            }
            animationCooldown = 0;
        }
    }

    public abstract void updateTexture();

    @Override
    public PathLocation getTextureLocation() {
        return pathLocation;
    }
}
