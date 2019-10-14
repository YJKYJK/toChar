package yanyan.com.tochar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import pl.droidsonroids.gif.GifDrawable;
import yanyan.com.tochar.adpter.FullyGridLayoutManager;
import yanyan.com.tochar.adpter.GridImageAdapter;
import yanyan.com.tochar.beans.ImageToTxtBean;
import yanyan.com.tochar.beans.ThreadInfo;
import yanyan.com.tochar.gif.AnimatedGifEncoder;
import yanyan.com.tochar.gif.ImageUtil;
import yanyan.com.tochar.thread.ThreadTask;
import yanyan.com.tochar.utils.EmptyUtils;
import yanyan.com.tochar.utils.PictureSelectUtil;
import yanyan.com.tochar.utils.ProgressDiaolog;
import yanyan.com.tochar.utils.ToastUtil;

public class GifToCharActivity extends Activity implements CompoundButton.OnCheckedChangeListener {
    private RecyclerView recyclerView;
    private CheckBox isStr;
    private CheckBox isColor;
    private ImageView imageView;
    private EditText str;
    private LinearLayout layout1;
    private LinearLayout layout2;
    private TextView speedText;
    private SeekBar setSpeed;
    private LinearLayout sizeLayout;
    private CheckBox setSize;
    private EditText widthEdit;
    private EditText heigthEdit;



    //线程池
    ThreadPoolExecutor pool=  null;
    private ImageToTxtBean imageToTxtBean;
    private GridImageAdapter adapter;
    private List imgList= new ArrayList<>();//图片集合
    private int speed=100;
    private ProgressDiaolog diaolog=null;
    private String gifPath="";

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giftochar);
        init();//初始化
        //创建线程池
        pool=  new ThreadPoolExecutor(8,10,0,
                TimeUnit.SECONDS,new LinkedBlockingQueue<>());

    }

    private void init(){
        recyclerView=findViewById(R.id.gifto_recycler);
        isStr=findViewById(R.id.gifto_isStr);
        isStr.setOnCheckedChangeListener(this);
        isColor=findViewById(R.id.gifto_isColor);
        isColor.setOnCheckedChangeListener(this);
        imageView=findViewById(R.id.gifto_image);
        layout1=findViewById(R.id.gifto_layout1);
        str=findViewById(R.id.gifto_str);
        layout2=findViewById(R.id.gifto_layout2);
        setSpeed=findViewById(R.id.gifto_speed);
        speedText=findViewById(R.id.gifto_speedText);
        sizeLayout=findViewById(R.id.gifto_sizeLayout);
        setSize=findViewById(R.id.gifto_setSize);
        setSize.setOnCheckedChangeListener(this);
        widthEdit=findViewById(R.id.gifto_width);
        heigthEdit=findViewById(R.id.gifto_heigth);

        //初始化图片选择
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new GridImageAdapter(this, new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                PictureSelectUtil.selectImageAndGif(GifToCharActivity.this,true,3306);
            }
        });
        adapter.setList(imgList);
        adapter.setSelectMax(1);
        recyclerView.setAdapter(adapter);

        /**
         * 速度选择
         */
        setSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                speedText.setText("动图速度配置: "+progress+" 毫秒");
                speed=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /**
     * 选择图片事件
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            imgList = PictureSelector.obtainMultipleResult(data);
            adapter.setList(imgList);
            adapter.notifyDataSetChanged();
        }
    }


    /**
     * 创建  点击事件
     * @param view
     */
    public void createGifToImg(View view){
        if(imgList!=null && imgList.size()>0){
            boolean color=this.isColor.isChecked();
            boolean isStr=this.isStr.isChecked();
            String textStr="";
            if(isStr){
                textStr=this.str.getText().toString();
            }
            diaolog=new ProgressDiaolog(GifToCharActivity.this,"图片制作中");
            diaolog.show();
            gifPath="";
           LocalMedia localMedia=(LocalMedia)imgList.get(0);
           gifPath=localMedia.getPath();
          if(isGif(gifPath)){
              create(color,textStr,gifPath);
          }else {
              ToastUtil.showLongToast(this,"请选择以gif格式结尾的动态图片");
          }
        }else {
            ToastUtil.showLongToast(this,"请选择图片");
        }
    }

    private void create(boolean isColor,String str,String gifPath){

        new Thread(new Runnable() {
            @Override
            public void run() {
                //清空目录
                //生成随机文件名
                String name=UUID.randomUUID().toString().substring(0,6);
                //初始化map
                ThreadInfo.initMap();
                //获取图片信息
                int numberOfFrames = 0;
                GifDrawable drawable=null;
                if(isGif(gifPath)){

                }
                try {
                   drawable=new GifDrawable(gifPath);
                    numberOfFrames=drawable.getNumberOfFrames();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //获取图片处理
                for(int i=0;i<numberOfFrames;i++) {
                    while (true){
                        int index=ThreadInfo.getIndex();
                        if(i-index>4){
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }else {
                            break;
                        }
                    }
                    Bitmap bitmap = drawable.seekToFrameAndGet(i);
                    ThreadInfo threadInfo=new ThreadInfo(GifToCharActivity.this,i+"-"+name,bitmap,isColor,str);
                    ThreadTask task=new ThreadTask(threadInfo);//创建线程
                    pool.execute(task);
                    //处理进度
                    int pro=ThreadInfo.getIndex()*100/(numberOfFrames*2+1);

                    Message message=new Message();
                    message.arg1=pro;
                    handler.sendMessage(message);

                }

                boolean isCreateChar=false;
                while (true){
                    Integer integer= ThreadInfo.getIndex();
                    //处理进度
                    int pro=integer*100/(numberOfFrames*2+1);
                    Message message=new Message();
                    message.arg1=pro;
                    handler.sendMessage(message);
                    if(numberOfFrames==integer){
                        isCreateChar=true;
                        break;
                    }
                }

                //字符图写出后合成动态图
                if (isCreateChar){
                    createGif();
                }
            }
        }).start();

    }


    public void createGif(){
        //获取图片大小
        int[] size = getSize();
        //获取图片地址
        HashMap<Integer,String> map= ThreadInfo.getMap();
        //获取速度
        int speed=setSpeed.getProgress();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        AnimatedGifEncoder localAnimatedGifEncoder = new AnimatedGifEncoder();
        localAnimatedGifEncoder.start(baos);//start
        localAnimatedGifEncoder.setRepeat(0);//设置生成gif的开始播放时间。0为立即开始播放
        localAnimatedGifEncoder.setDelay(speed);

        for (int i=0;i<map.size();i++){
            Bitmap bitmap=PictureSelectUtil.getBitmap(map.get(i));
            bitmap=ImageUtil.resizeImage(bitmap, size[0],size[1]);
            localAnimatedGifEncoder.addFrame(bitmap);
            int pro=((map.size()+i)*100)/(map.size()*2+1);
            Message message=new Message();
            message.arg1=pro;
            handler.sendMessage(message);

        }
        localAnimatedGifEncoder.finish();
        //生成的图片地址
        String gifPath = PictureSelectUtil.getPhotoFileName(GifToCharActivity.this, ".gif");

        try {
            FileOutputStream fos=new FileOutputStream(gifPath);
            baos.writeTo(fos);
            baos.flush();
            fos.flush();
            baos.close();
            fos.close();

            Message message=new Message();
            message.arg1=100;
            message.obj=gifPath;
            handler.sendMessage(message);


        }catch (Exception e){

        }


    }


    /**
     * 选择框改变事件
     * @param buttonView
     * @param isChecked
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id=buttonView.getId();
        switch (id){
            case R.id.gifto_isStr:
                if(isChecked){
                    layout1.setVisibility(View.VISIBLE);
                }else {
                    layout1.setVisibility(View.GONE);
                }
                break;
            case R.id.gifto_setSize:
                if(isChecked){
                    sizeLayout.setVisibility(View.VISIBLE);
                }else {
                    sizeLayout.setVisibility(View.GONE);
                }
        }
    }

    /**
     * 查看字符
     * @param view
     */
    public void lookText(View view) {
        Intent intent = new Intent(this, TextActivity.class);
        Drawable drawable = imageView.getDrawable();
        if (drawable != null) {
            List<String> list = ThreadInfo.getTextList();
            if (list != null && list.size() > 0) {
                if(list.size()>20){
                   List<String>newList=new ArrayList<>();
                    for(int j=0;j<20;j++){
                        newList.add(list.get(j));
                    }
                    list=newList;
                }
                intent.putExtra("list", (Serializable) list);
                startActivity(intent);
            } else {
                ToastUtil.showLongToast(this, "您还没有生成");
            }

        }else {
            ToastUtil.showLongToast(this, "您还没有生成");
        }
    }


    /**
     * 判断图片是否动态图
     * @param path
     * @return
     */
    private boolean isGif(String path){
        boolean res=false;
        int gif = path.indexOf("gif", path.lastIndexOf("."));
        if(gif>0){
            res=true;
        }
        gif = path.indexOf("GIF", path.lastIndexOf("."));
        if(gif>0){
            res=true;
        }
        return res;
    }




    /**
     * 保存到本地
     * @param view
     */
    public void giftoSave(View view){
        if(EmptyUtils.isNotEmpty(gifPath)){
            this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + gifPath)));
            ToastUtil.showLongToast(this,"图片已保存,路径为:"+gifPath);
        }else {
            ToastUtil.showLongToast(this,"出了点错误,请重新生成");
        }

    }

    final Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int pro= msg.arg1;
            diaolog.setProgress(pro);
            if(pro==100){
                String gifPath=(String) msg.obj;
                try {
                    GifDrawable gifDrawable=new GifDrawable(gifPath);
                    imageView.setImageDrawable(gifDrawable);
                    layout2.setVisibility(View.VISIBLE);
                    GifToCharActivity.this.gifPath=gifPath;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ToastUtil.showLongToast(GifToCharActivity.this,"生成成功");
            }else if(pro==50){
                // diaolog.setTitle("正在合成GIF");
            }
        }
    };

    /**
     * 计算图片的大小
     * @return
     */
    public int[] getSize(){
        int  width=500;
        int  heigth=500;
        LocalMedia localMedia= (LocalMedia) imgList.get(0);
        String path= localMedia.getPath();
        Bitmap   bitmap =PictureSelectUtil.getBitmap(path);
        if(setSize.isChecked()){
            int w=Integer.parseInt(widthEdit.getText().toString());
            int h=Integer.parseInt(heigthEdit.getText().toString());
            if(w>0 && h>0){
                width=w;
                heigth=h;
            }
        }else {
            width=bitmap.getWidth();
            heigth=bitmap.getHeight();
            if(width>heigth){
                if(heigth>500){
                    width=width*500/heigth;
                    heigth=500;
                }
            }else {
                if(width>500){
                    heigth=heigth*500/width;
                    width=500;
                }
            }

        }
        int [] size={width,heigth};
        return size;
    }

}
