package com.next.groupmeal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by serge on 2/21/2018.
 */

public class UserSignUp
{
    protected String email;
    protected String password;
    private Pattern pattern;
    private Matcher matcher;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(\\.[_A-Z-a-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public UserSignUp()
    {
        this.pattern = Pattern.compile(EMAIL_PATTERN);
    }

    public void setEmail(String mEmail)
    {
        this.email = mEmail;
    }

    public void setPassword(String mPassword)
    {
        this.password = mPassword;
    }

    public String getEmail()
    {
        return this.email;
    }
    public String getPassword()
    {
        return this.password;
    }

    public boolean ValidateEmail(final String email)
    {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }





}
