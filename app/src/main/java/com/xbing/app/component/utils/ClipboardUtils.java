package com.xbing.app.component.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

public class ClipboardUtils {

    /**
     *  系统剪贴板-复制:   s为内容
     */
    public static void copy(Context context, String s) {
        // 获取系统剪贴板
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
        ClipData clipData = ClipData.newPlainText(null, s);
        // 把数据集设置（复制）到剪贴板
        clipboard.setPrimaryClip(clipData);
    }


    /**
     * 系统剪贴板-获取:
     */
    public static String getCopy(Context context) {
        // 获取系统剪贴板
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 返回数据
        ClipData clipData = clipboard.getPrimaryClip();
        if (clipData != null && clipData.getItemCount() > 0) {
            // 从数据集中获取（粘贴）第一条文本数据
            return clipData.getItemAt(0).getText().toString();
        }
        return null;
    }
}
