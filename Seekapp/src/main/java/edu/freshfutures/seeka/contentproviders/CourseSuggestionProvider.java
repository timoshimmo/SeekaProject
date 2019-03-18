package edu.freshfutures.seeka.contentproviders;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by tokmang on 7/29/2016.
 */
public class CourseSuggestionProvider extends SearchRecentSuggestionsProvider {

    public final static String AUTHORITY = "edu.freshfutures.seeka.ContentProviders.CourseSuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public CourseSuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
