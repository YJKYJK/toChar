package yanyan.com.tochar.utils;

public class StringUtil {


    public  static String getMiddleStr(String all,String leftStr,String rightStr){
        int start=0;
        int end=all.length();
        int i = all.indexOf(leftStr);
        if(i>=0){
            start=i+leftStr.length();
        }
        int y = all.indexOf(rightStr, start);
        if(y>0){
            end=y;
        }

        return all.substring(start,end);


    }
}
