package com.azhar.university.guide.domain.models.parse;

import android.util.Base64;

import com.azhar.university.guide.domain.utils.Constants;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

/**
 * Created by Yasser.Ibrahim on 6/12/2018.
 */

public class User {
    private String email;
    private String fullName;
    private String photoString;

    public User() {
    }

    public User(ParseUser user) {
        this.email = user.getEmail();
        this.fullName = (String) user.get(Constants.KEY_FULL_NAME);
        ParseFile parseFile = user.getParseFile(Constants.KEY_PHOTO);
        try {
            if (parseFile != null)
                this.photoString = Base64.encodeToString(parseFile.getData(), Base64.NO_WRAP);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhotoString() {
        return photoString;
    }

    public void setPhotoString(String photoString) {
        this.photoString = photoString;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", photoString='" + photoString + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }
}
