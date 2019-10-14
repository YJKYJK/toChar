package yanyan.com.tochar.thread;

import android.util.Log;

import yanyan.com.tochar.MoreActivity;
import yanyan.com.tochar.beans.AppInfo;
import yanyan.com.tochar.beans.ThreadInfo;
import yanyan.com.tochar.beans.ToChanrBean;
import yanyan.com.tochar.utils.PictureSelectUtil;
import yanyan.com.tochar.utils.ToCharUtil;

public class ThreadTask implements Runnable{
    private ThreadInfo threadInfo;

    public ThreadTask(ThreadInfo threadInfo) {
        this.threadInfo = threadInfo;
    }

    public ThreadTask() {
    }

    @Override
    public void run() {
        ToChanrBean toChanrBean=null;
        if(threadInfo.isColor()){
            toChanrBean= ToCharUtil.createAsciiPicColor(threadInfo.getBitmap(),threadInfo.getTxt(), threadInfo.getContext());
        }else {
            toChanrBean=ToCharUtil.createAsciiPic(threadInfo.getBitmap(),threadInfo.getTxt()
                   ,threadInfo.getContext());
        }
        //将图片缓存到本地
        String s = PictureSelectUtil.cacheBitmap(toChanrBean.getBitmap(), threadInfo.getFileName(), threadInfo.getContext());
        //将字符保存到列表
        //将路径储存到map里
        ThreadInfo.setIndex(s,toChanrBean.getText());
        threadInfo=null;
        toChanrBean=null;

    }

    public ThreadInfo getThreadInfo() {
        return threadInfo;
    }

    public void setThreadInfo(ThreadInfo threadInfo) {
        this.threadInfo = threadInfo;
    }
}
