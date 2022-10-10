package com.meng.designpatten.adapter;


public class AdapterDemo {

    public static void main(String[] args) {

        MediaAdapter media = new MediaAdapter();
        media.play("mp3");
        media.play("mp4");
        media.play("vlc");
    }

}
