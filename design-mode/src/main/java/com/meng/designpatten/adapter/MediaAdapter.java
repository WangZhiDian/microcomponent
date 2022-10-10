package com.meng.designpatten.adapter;

public class MediaAdapter implements MediaPlay {

    @Override
    public void play(String type) {

        if ("mp3".equals(type)) {
            System.out.println(" i am mp3");
        } else if ("mp4".equals(type)) {
            AbstructMedia media = new Mp4Player();
            media.playMp4();
        } else if("vlc".equals(type)) {
            AbstructMedia media = new VlcPlayer();
            media.playVlc();
        } else {
            System.out.println("no type is right");
        }

    }
}
