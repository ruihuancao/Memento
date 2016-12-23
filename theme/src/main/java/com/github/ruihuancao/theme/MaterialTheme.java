package com.github.ruihuancao.theme;


import com.crh.android.theme.R;

import java.util.ArrayList;
import java.util.List;

public class MaterialTheme {

    public static final MaterialTheme THEME_RED =
            new MaterialTheme(R.string.material_theme_red, R.style.AppTheme_Red, R.drawable.red_circle);
    public static final MaterialTheme THEME_ORANGE =
            new MaterialTheme(R.string.material_theme_orange, R.style.AppTheme_Orange, R.drawable.orange_circle);
    public static final MaterialTheme THEME_YELLOW =
            new MaterialTheme(R.string.material_theme_lime, R.style.AppTheme_Lime, R.drawable.lime_circle);
    public static final MaterialTheme THEME_GREEN =
            new MaterialTheme(R.string.material_theme_green, R.style.AppTheme_Green, R.drawable.green_circle);
    public static final MaterialTheme THEME_TEAL =
            new MaterialTheme(R.string.material_theme_teal, R.style.AppTheme_Teal, R.drawable.teal_circle);
    public static final MaterialTheme THEME_BLUE =
            new MaterialTheme(R.string.material_theme_blue, R.style.AppTheme_Blue, R.drawable.blue_circle);
    public static final MaterialTheme THEME_PURPLE =
            new MaterialTheme(R.string.material_theme_purple, R.style.AppTheme_Purple, R.drawable.purple_circle);

    private static List<MaterialTheme> sThemeList;

    private int nameResId;

    private int themeResId;

    private int drawResId;

    public MaterialTheme(int nameResId, int themeResId,int drawResId) {

        this.nameResId = nameResId;
        this.themeResId = themeResId;
        this.drawResId = drawResId;
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

    public int getDrawResId() {
        return drawResId;
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
