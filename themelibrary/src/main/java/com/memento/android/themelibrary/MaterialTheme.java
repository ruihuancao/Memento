package com.memento.android.themelibrary;

import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Material theme object to be used to display and set themes.
 *
 * @author Steven Byle
 */
public class MaterialTheme implements Serializable {
    // App themes
    public static final MaterialTheme THEME_RED =
            new MaterialTheme(R.string.material_theme_red, R.style.AppTheme_Red, R.color.material_red_500);
    public static final MaterialTheme THEME_ORANGE =
            new MaterialTheme(R.string.material_theme_orange, R.style.AppTheme_Orange, R.color.material_orange_500);
    public static final MaterialTheme THEME_YELLOW =
            new MaterialTheme(R.string.material_theme_lime, R.style.AppTheme_Lime, R.color.material_lime_500);
    public static final MaterialTheme THEME_GREEN =
            new MaterialTheme(R.string.material_theme_green, R.style.AppTheme_Green, R.color.material_green_500);
    public static final MaterialTheme THEME_TEAL =
            new MaterialTheme(R.string.material_theme_teal, R.style.AppTheme_Teal, R.color.material_teal_500);
    public static final MaterialTheme THEME_BLUE =
            new MaterialTheme(R.string.material_theme_blue, R.style.AppTheme_Blue, R.color.material_blue_500);
    public static final MaterialTheme THEME_PURPLE =
            new MaterialTheme(R.string.material_theme_purple, R.style.AppTheme_Purple, R.color.material_purple_500);

    private static List<MaterialTheme> sThemeList;

    @StringRes
    private int nameResId;
    @StyleRes
    private int themeResId;

    private int colorResId;

    public MaterialTheme(@StringRes int nameResId, @StyleRes int themeResId, @ColorRes int colorResId) {

        this.nameResId = nameResId;
        this.themeResId = themeResId;
        this.colorResId = colorResId;
    }

    public static List<MaterialTheme> getThemeList() {
        if (sThemeList == null) {
            sThemeList = new ArrayList<MaterialTheme>();
            sThemeList.add(THEME_RED);
            sThemeList.add(THEME_ORANGE);
            sThemeList.add(THEME_YELLOW);
            sThemeList.add(THEME_GREEN);
            sThemeList.add(THEME_TEAL);
            sThemeList.add(THEME_BLUE);
            sThemeList.add(THEME_PURPLE);
        }
        return sThemeList;
    }

    public int getNameResId() {
        return nameResId;
    }

    public int getThemeResId() {
        return themeResId;
    }

    public int getColorResId() {
        return colorResId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MaterialTheme that = (MaterialTheme) o;

        if (nameResId != that.nameResId) {
            return false;
        }
        return themeResId == that.themeResId;
    }

    @Override
    public int hashCode() {
        int result = nameResId;
        result = 31 * result + themeResId;
        return result;
    }
}
