package edu.freshfutures.seeka;

import edu.freshfutures.seeka.interfaces.Item;

/**
 * Created by tokmang on 10/14/2015.
 */
public class SectionItem implements Item {

    private final String title;

    public SectionItem(String ttl) {
        this.title = ttl;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean isSection() {
        // TODO Auto-generated method stub
        return true;
    }

}
