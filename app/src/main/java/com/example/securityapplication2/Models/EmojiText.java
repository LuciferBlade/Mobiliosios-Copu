package com.example.securityapplication2.Models;

import android.content.Context;

import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;

public class EmojiText {
    private String text = "Apsaugos sistema";
    private String emoji = "\uD83D\uDEE1️";

    public EmojiText(Context context) {
        EmojiCompat.Config config = new BundledEmojiCompatConfig(context);
        EmojiCompat.init(config);
    }

    public String getText() {
        //CharSequence processed = EmojiCompat.get().process(emoji + " Apsaugos sistema " + emoji);
        //return processed.toString();
        return emoji + text + emoji;
    }

    public void setText(String text) {
        this.text = text;
    }

    /*public void setEmoji(String emoji) {
        this.emoji = emoji;
    }*/
}
