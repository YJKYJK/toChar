package yanyan.com.tochar;

import android.graphics.Bitmap;

import com.alibaba.fastjson.JSON;
import com.luck.picture.lib.entity.LocalMedia;

import org.json.JSONObject;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import yanyan.com.tochar.beans.AppInfo;
import yanyan.com.tochar.utils.PictureSelectUtil;
import yanyan.com.tochar.utils.StringUtil;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void testjson(){

           String a="aagfaawwerwdfdfserergfgfgfgf";
        String middleStr = StringUtil.getMiddleStr(a, "er", "gf");
        System.out.println(middleStr);


    }


    @Test
    public void ttt(){

        final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1,1,
                1,TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(100));


        for(int i = 0;i<30;i++){
        Threads runnable=new Threads(i);
            threadPoolExecutor.execute(runnable);
        }
    }

    public class  Threads implements Runnable{

        private int i;

        public Threads(int i) {
            this.i = i;
        }

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            System.out.println("这是："+ this.i);
        }
    }
@Test
    public void ss(){
    List<String> list=new ArrayList<>();
       list.add(2,"s");

    }


@Test
    public void getSize(){
        int  width=500;
        int  heigth=500;
       boolean flag=false;
        if(flag){

        }else {
            width=1920;
            heigth=1080;
            if(width>heigth){
                if(heigth>500){
                    width=width*500/heigth;
                    heigth=500;
                }
            }else {
                if(width>500){
                    heigth=heigth*(500/width);
                    width=500;
                }
            }

        }
        int [] size={width,heigth};
        System.out.print(width+"---"+heigth);
    }

    }
