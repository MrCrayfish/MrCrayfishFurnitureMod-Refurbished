package com.mrcrayfish.furniture.refurbished.client.gui;

/**
 * Author: MrCrayfish
 */
public interface ICustomSelectionList<T>
{
    T getEntry(double mouseX, double mouseY);

    int getStartEntryY();

    void arrangeEntries();
}
