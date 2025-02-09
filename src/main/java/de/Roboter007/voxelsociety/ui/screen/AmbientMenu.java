package de.Roboter007.voxelsociety.ui.screen;

import de.Roboter007.voxelsociety.sound.Playlist;
import de.Roboter007.voxelsociety.sound.SoundType;
import de.Roboter007.voxelsociety.ui.UiUtilities;
import de.Roboter007.voxelsociety.ui.screen.menus.menu.GameMenu;

//ToDo: Fix PauseScreen force choose the song because it is not cached -> look into InGameMenu.class
public abstract class AmbientMenu extends GameMenu {
    public static final Playlist playlist = new Playlist(SoundType.MUSIC, "waiting");

    public AmbientMenu(String name) {
        super(name);
    }

    public AmbientMenu(String name, int renderOrder) {
        super(name, renderOrder);
    }

    @Override
    public void draw(UiUtilities uiUtilities) {
        playlist.updateVolume();
        if(playlist.noTrackSelected()) {
            System.out.println("New Music selected...");
            playlist.playRandomTrack();
        }

    }

    @Override
    public void onChangeFrom(GameMenu newMenu) {
        if(!(newMenu instanceof AmbientMenu)) {
            playlist.pauseCurrentTrack();
        } else {
            playlist.resumeCurrentTrack();
        }
    }

    @Override
    public void onChangeTo() {
        if(!(MenuHandler.getFocusedScreen() instanceof AmbientMenu)) {
            playlist.pauseCurrentTrack();
        }
    }
}
