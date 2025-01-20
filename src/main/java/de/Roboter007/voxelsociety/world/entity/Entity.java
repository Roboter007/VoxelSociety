package de.Roboter007.voxelsociety.world.entity;

import de.Roboter007.voxelsociety.world.World;
import de.Roboter007.voxelsociety.world.collision.CollisionBox;
import de.Roboter007.voxelsociety.utils.texture.PathLocation;
import de.Roboter007.voxelsociety.world.pos.IntPosition;

import java.awt.*;

public abstract class Entity {

    public IntPosition blockPosition = new IntPosition();
    public int speed;
    public World world;

    public String direction;
    public CollisionBox collisionBox;

    abstract void update();
    public abstract void tick();
    abstract void drawPlayer(Graphics2D graphics2D);
    abstract void drawScreen(Graphics2D graphics2D);

    public abstract PathLocation getTextureLocation();
}
