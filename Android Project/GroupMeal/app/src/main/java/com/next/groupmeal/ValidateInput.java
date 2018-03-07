package com.next.groupmeal;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
/**
 * Created by serge on 2/22/2018.
 */

public class ValidateInput
{
    private String mEmail;
    private String mPassword;
    private Pattern mPattern;
    private Matcher mMatcher;

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(\\.[_A-Z-a-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    //private static final String PASSWORD_PATTERN ="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\\\S+$).{8,}";

    public ValidateInput()
    {
        this.mPattern = Pattern.compile(EMAIL_PATTERN);
        //this.mPattern = Pattern.compile(PASSWORD_PATTERN);
    }

    public void setEmail(String email)
    {
        this.mEmail = email;
    }

    public void setPassword(String password)
    {
        this.mPassword = password;
    }

    public String getEmail()
    {
        return this.mEmail;
    }
    public String getPassword()
    {
        return this.mPassword;
    }

    public boolean CheckEmail(final String email)
    {
        this.mMatcher = mPattern.matcher(email);

        return mMatcher.matches();
    }

//    public boolean CheckPassword (final String password)
//    {
//        this.mMatcher = mPattern.matcher(password);
//
//        return mMatcher.matches();
//    }
}
