package za.ac.cput.util;


import java.util.regex.Pattern;

public class UserHelper {

    public static boolean validId(long a){
        if(a <= 0){
            return true;
        }
        return false;
    }

    public static boolean isNullOrEmpty(String a){
        if(a == null || a.isEmpty()){
            return true;
        }
        return false;
    }

    public static boolean isValidNumber(String c){
        String regex = "(\\+?27|0)(\\d{9})";
        if (c.matches(regex)) {
            return false;
        }
        return true;
    }

    public static boolean isvalidEmail(String d){

        final String regex2 = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        final Pattern compile = Pattern.compile(regex2, Pattern.CASE_INSENSITIVE);

        if(compile.matcher(d).matches()){
            return false;
        }
        return true;
    }

}