package de.Roboter007.voxelsociety.ui.screen.menus;

import de.Roboter007.voxelsociety.api.RecCalculator;
import de.Roboter007.voxelsociety.ui.UiStyle;
import de.Roboter007.voxelsociety.ui.UiUtilities;
import de.Roboter007.voxelsociety.ui.elements.VoxelTaskButton;
import de.Roboter007.voxelsociety.ui.elements.VoxelTextField;
import de.Roboter007.voxelsociety.ui.screen.AmbientMenu;
import de.Roboter007.voxelsociety.ui.screen.MenuHandler;
import de.Roboter007.voxelsociety.utils.VoxelPanel;
import de.Roboter007.voxelsociety.world.World;
import de.Roboter007.voxelsociety.world.Worlds;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class WorldCreationMenu extends AmbientMenu {


    public WorldCreationMenu() {
        super("World Creation Menu");
    }

    private String name = "";
    private long seed;
    private int maxXSize;
    private int maxYSize;

    @Override
    public void draw(UiUtilities uiUtilities) {
        super.draw(uiUtilities);
        Graphics2D graphics2D = uiUtilities.getGraphics2D();

        RecCalculator recCalculator = new RecCalculator();

        graphics2D.setFont(new Font("Century Schoolbook Bold", Font.BOLD, 25));
        Rectangle2D stringBounds = graphics2D.getFont().getStringBounds(this.getName(), graphics2D.getFontRenderContext());
        recCalculator.calc((int) stringBounds.getWidth(), (int) stringBounds.getHeight());
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(this.getName(), recCalculator.getX(), 60);

        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 100, VoxelPanel.screenWidth,700);

        recCalculator.calc(200, 80);
        drawElement(0, uiUtilities, new VoxelTaskButton("Cancel", UiStyle.DEFAULT, recCalculator.getX(), recCalculator.getY() + 300, 200, 80, () -> MenuHandler.setFocusedScreen(new WorldSelectionMenu())));

        recCalculator.calc(400, 80);
        drawElement(1, uiUtilities, new VoxelTaskButton("Create World", UiStyle.DEFAULT, recCalculator.getX(), recCalculator.getY() + 200, 400, 80, () -> {
            if(name.isEmpty()) {
                name = "world" + Worlds.REGISTERED_WORLDS.size();
            }
            if(seed == 0) {
                seed = new Random().nextLong();
            }
            if(maxXSize == 0) {
                System.out.println("Error: Please enter the maxXSize");
                return;
            }
            if(maxYSize == 0) {
                System.out.println("Error: Please enter the maxYSize");
                return;
            }
            Worlds.register(new World(name, maxXSize, maxYSize, seed));

            MenuHandler.setFocusedScreen(new WorldSelectionMenu());

            updateElements();
        }));


        drawElement(2, uiUtilities, new VoxelTextField("name", UiStyle.DEFAULT, recCalculator.getX(), recCalculator.getY() + 50, 400, 10, true, (input) -> this.name = input));
        drawElement(3, uiUtilities, new VoxelTextField("seed", UiStyle.DEFAULT, recCalculator.getX(), recCalculator.getY() + 80, 400, 10, true, (input) -> {
            if(!input.isEmpty() && isValidLong(input)) {
                this.seed = Long.parseLong(input);
            }
        }));

        drawElement(4, uiUtilities, new VoxelTextField("maxXSize", UiStyle.DEFAULT, recCalculator.getX(), recCalculator.getY() + 110, 400, 10, true, (input) -> {
            if(!input.isEmpty() && input.matches("\\d+")) {
                this.maxXSize = Integer.parseInt(input);
            }
        }));

        drawElement(5, uiUtilities, new VoxelTextField("maxYSize", UiStyle.DEFAULT, recCalculator.getX(), recCalculator.getY() + 140, 400, 10, true, (input) -> {
            if(!input.isEmpty() && input.matches("\\d+")) {
                this.maxYSize = Integer.parseInt(input);
            }
        }));
    }

    public static boolean isValidLong(String str) {
        if(str == null)
            return false;
        int len = str.length();
        if (str.charAt(0) == '+') {
            return str.matches("\\+\\d+") && (len < 20 || len == 20 && str.compareTo("+9223372036854775807") <= 0);
        } else if (str.charAt(0) == '-') {
            return str.matches("-\\d+") && (len < 20 || len == 20 && str.compareTo("-9223372036854775808") <= 0);
        } else {
            return str.matches("\\d+") && (len < 19 || len == 19 && str.compareTo("9223372036854775807") <= 0);
        }
    }


}

