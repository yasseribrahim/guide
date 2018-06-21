package com.azhar.university.guide.domain.models;

/**
 * Created by Yasser.Ibrahim on 6/21/2018.
 */

public class MoreItem {
    private int id;
    private int contentId;
    private int iconId;

    public MoreItem(int id, int contentId, int iconId) {
        this.id = id;
        this.contentId = contentId;
        this.iconId = iconId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MoreItem moreItem = (MoreItem) o;

        return id == moreItem.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "MoreItem{" +
                "id=" + id +
                ", contentId=" + contentId +
                ", iconId=" + iconId +
                '}';
    }
}
