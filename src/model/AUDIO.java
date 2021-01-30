package model;

public enum AUDIO {

    STAR_IMPACT("zapsplat_fantasy_magic_chime_ping_wand_fairy_godmother_010_38296.mp3"),
    METEOR_IMPACT("zapsplat_explosion_fire_burst_backdraft_002_44089.mp3"),
    PILL_IMPACT("zapsplat_fantasy_magic_chime_ping_wand_fairy_godmother_014_38300.mp3");

    private String path;

    AUDIO(String path){
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
