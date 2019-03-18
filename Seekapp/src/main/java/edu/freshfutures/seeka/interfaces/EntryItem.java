package edu.freshfutures.seeka.interfaces;

/**
 * Created by tokmang on 10/14/2015.
 */
public class EntryItem implements Item {

    public final int id;
    public final String value;
    public final String figures;

    public EntryItem(int ttl, String value, String figures) {
        this.id = ttl;
        this.value = value;
        this.figures = figures;
    }

    @Override
    public boolean isSection() {
        // TODO Auto-generated method stub
        return false;
    }
}
