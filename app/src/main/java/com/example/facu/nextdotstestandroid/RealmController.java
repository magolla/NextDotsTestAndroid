package com.example.facu.nextdotstestandroid;

import android.app.Activity;
import android.app.Application;

import com.example.facu.realmModels.SavedComics;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by facu on 06/02/17.
 */

public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }


    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }


    //find all objects in the Book.class
    public RealmResults<SavedComics> getBooks() {

        return realm.where(SavedComics.class).findAll();
    }

    //query a single item with the given id
    public SavedComics getBook(String id) {

        return realm.where(SavedComics.class).equalTo("id", id).findFirst();
    }


//    //query example
//    public RealmResults<SavedComics> queryedBooks() {
//
//        return realm.where(SavedComics.class)
//                .contains("author", "Author 0")
//                .or()
//                .contains("title", "Realm")
//                .findAll();
//
//    }
}
