//package yanyan.com.tochar;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.drawable.Drawable;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.Toolbar;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.SeekBar;
//
//import com.gs.buluo.common.widget.LoadingDialog;
//import com.hjq.permissions.Permission;
//import com.luck.picture.lib.PictureSelector;
//import com.luck.picture.lib.entity.LocalMedia;
//import com.tencent.connect.share.QQShare;
//import com.tencent.tauth.Tencent;
//
//import java.io.ByteArrayOutputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import io.reactivex.Observable;
//import io.reactivex.ObservableEmitter;
//import io.reactivex.ObservableOnSubscribe;
//import io.reactivex.Observer;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.disposables.Disposable;
//import io.reactivex.schedulers.Schedulers;
//import pl.droidsonroids.gif.GifDrawable;
//import pl.droidsonroids.gif.GifImageView;
//import yanyan.com.tochar.adpter.FullyGridLayoutManager;
//import yanyan.com.tochar.adpter.GridImageAdapter;
//import yanyan.com.tochar.beans.ImageToTxtBean;
//import yanyan.com.tochar.beans.ThreadInfo;
//import yanyan.com.tochar.gif.AnimatedGifEncoder;
//import yanyan.com.tochar.gif.ImageUtil;
//import yanyan.com.tochar.thread.ThreadTask;
//import yanyan.com.tochar.utils.CoreUtil;
//import yanyan.com.tochar.utils.ImageToCharUtil;
//import yanyan.com.tochar.utils.PermissionsUtil;
//import yanyan.com.tochar.utils.PictureSelectUtil;
//import yanyan.com.tochar.utils.ProgressDiaolog;
//import yanyan.com.tochar.utils.ToastUtil;
//
//public class MainActivityTest extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
//
//    private GifImageView imageView;
//    private RecyclerView recyclerView;
//    private GridImageAdapter adapter;
//
//    private CheckBox isColorBox;
//    private boolean isColor=true;
//    private  ImageToTxtBean imageToTxtBean=null;
//
//    private List imgList= new ArrayList<>();//图片集合
//
//    private LinearLayout sizeLayout;
//    private CheckBox imageSizeSetting;
//    private EditText imageWidth;
//    private EditText imageHeigth;
//    private CheckBox gifSetting;
//    private LinearLayout gifSettingLayout;
//    private CheckBox gifOldSize;
//    private SeekBar gifSpeed;
//
//    private ImageToTxtBean image=null;
//
//    private Tencent tencent;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        init();
//
//        //检查更新
//        CoreUtil.detectionUpdate(this);
//
//        tencent= Tencent.createInstance("101556816",this);
//        PermissionsUtil.authorization(new String[]{Permission.READ_EXTERNAL_STORAGE,
//                Permission.WRITE_EXTERNAL_STORAGE}, this);
//
//    }
//
//
//    private void init(){
//        imageView = findViewById(R.id.toChar_image);
//        recyclerView=findViewById(R.id.toChar_recycler);
//        sizeLayout=findViewById(R.id.toChar_sizeLayout);
//        imageSizeSetting=findViewById(R.id.toChar_imgSize);
//        imageSizeSetting.setOnCheckedChangeListener(this);
//        gifSetting=findViewById(R.id.toChar_gif_seting);
//        gifSetting.setOnCheckedChangeListener(this);
//        gifSettingLayout=findViewById(R.id.toChar_gif_Layout);
//        imageWidth=findViewById(R.id.toChar_width);
//        imageHeigth=findViewById(R.id.toChar_heigth);
//        gifOldSize=findViewById(R.id.toChar_gif_size);
//        gifSpeed=findViewById(R.id.toChar_gif_speed);
//
//
//        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(manager);
//        adapter = new GridImageAdapter(this, new GridImageAdapter.onAddPicClickListener() {
//            @Override
//            public void onAddPicClick() {
//                PictureSelectUtil.selectImageAndGif(MainActivityTest.this,true,3306);
//            }
//        });
//        adapter.setList(imgList);
//        adapter.setSelectMax(1);
//        recyclerView.setAdapter(adapter);
//
//
//        isColorBox=findViewById(R.id.toChar_isColor);
//        isColorBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                isColor=isChecked;
//            }
//        });
//    }
//
//
//    /**
//     * 右上角点击事件
//     * @param item
//     * @return
//     */
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//
//        switch (id){
//            case R.id.tochar_gif:
//                Intent in=new Intent();
//                in.setClass(this,GifActivity.class);
//                startActivity(in);
//                   break;
//                   //检查更新
//            case R.id.tochar_update:CoreUtil.detectionUpdate2(this);break;
//                   //分享
//            case R.id.tochar_share:
//                final Bundle params = new Bundle();
//                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
//                params.putString(QQShare.SHARE_TO_QQ_TITLE, "图片转字符图");
//                params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  "我正在使用图片转字符图，里面有各类好玩的功能，你也来试试吧");
//                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,"https://ps.ssl.qhimg.com/sdmt/124_135_100/t0193a2198f757a2c76.jpg");
//                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "https://ps.ssl.qhimg.com/sdmt/124_135_100/t0193a2198f757a2c76.jpg");
//                tencent.shareToQQ(this, params, null);
//            break;
//            //关于
//            case R.id.tochar_about:CoreUtil.about(this);break;
//            //加群
//            case R.id.tochar_join:CoreUtil.joinQQGroup(this,"AOzZSjzgi_pYpAbHJbFubhDLju3qcRfn");;break;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode==RESULT_OK){
//            imgList = PictureSelector.obtainMultipleResult(data);
//            adapter.setList(imgList);
//            adapter.notifyDataSetChanged();
//        }
//    }
//
//    /**
//     *
//     * @param
//     */
//    public void createImg(View view) throws IOException {
//
//
//        if(imgList.size()>0){
//            LocalMedia localMedia= (LocalMedia) imgList.get(0);
//            String path=localMedia.getPath();
//            List<Bitmap> bitmaps=new ArrayList<>();
//            if(isGif(path)){
//                //分解gif
//                bitmaps = getBitmapToGif(path);
//            }else {
//                bitmaps.add(PictureSelectUtil.getBitmap(path));
//            }
//
//            createCharImage(bitmaps);
//
//        }else {
//            ToastUtil.showShotToast(this,"请选择图片");
//        }
//    }
//
//
//
//
//
//
//    /**
//     * 判断图片是否动态图
//     * @param path
//     * @return
//     */
//    private boolean isGif(String path){
//        boolean res=false;
//        int gif = path.indexOf("gif", path.lastIndexOf("."));
//        if(gif>0){
//            res=true;
//        }
//        gif = path.indexOf("GIF", path.lastIndexOf("."));
//        if(gif>0){
//            res=true;
//        }
//        return res;
//    }
//
//
//    /**
//     *生成
//     * @param
//     */
//    public void createCharImage( List<Bitmap> bitmaps){
//        LoadingDialog.getInstance().show(MainActivityTest.this, "正在生成", false);
//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> e) throws Exception {
//                try{
//                     ImageToTxtBean charImg = createCharImg(bitmaps);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if(charImg.isGif()){
//                                GifDrawable gifDrawable= null;
//                                try {
//                                    gifDrawable = new GifDrawable((String) charImg.getImg());
//                                } catch (IOException e1) {
//                                    e1.printStackTrace();
//                                }
//                                imageView.setImageDrawable(gifDrawable);
//                            }else {
//                                imageView.setImageBitmap((Bitmap) charImg.getImg());
//                            }
//                            image=charImg;
//                        }
//                    });
//                    e.onComplete();
//                }catch (Exception e1){
//                    e.onError(e1);
//                }
//
//            }
//        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(String s) {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                LoadingDialog.getInstance().dismissDialog();
//                ToastUtil.showShotToast(MainActivityTest.this, "生成失败");
//
//            }
//            @Override
//            public void onComplete() {
//                LoadingDialog.getInstance().dismissDialog();
//                ToastUtil.showShotToast(MainActivityTest.this, "生成成功");
//            }
//        });
//    }
//
//    /**
//     * 创建字符图
//     * @param bitmaps
//     * @return
//     */
//    public ImageToTxtBean  createCharImg(List<Bitmap> bitmaps){
//        ImageToTxtBean res=null;
//        List<Bitmap>charBitmaps=  new ArrayList<>();
//        if(bitmaps!=null && bitmaps.size()>0){
//            //处理动态
//            if(bitmaps.size()>1){
//                String s = this.bitmapsToGif(bitmaps);
//                res=new ImageToTxtBean(true,s);
//
//            }else {
//                //处理静态
//                if(isColor){
//                    //有颜色
//                    Bitmap b=ImageToCharUtil.createAsciiPicColor(bitmaps.get(0),MainActivityTest.this);
//                    res=new ImageToTxtBean(false,b);
//                }else {
//                    //无颜色
//                    Bitmap b=ImageToCharUtil.createAsciiPic(bitmaps.get(0),MainActivityTest.this);
//                    res=new ImageToTxtBean(false,b);
//                }
//            }
//        }
//
//        return res;
//    }
//
//
//
//    /**
//     * 合成gif
//     * @param bitmaps
//     * @return
//     */
//    private String  bitmapsToGif(List<Bitmap> bitmaps){
//        int gifWidth=bitmaps.get(0).getWidth();
//        int gifHeight=bitmaps.get(0).getHeight();
//        int ftp=100;//gif图的速度
//        //判断是否写入图片大小
//        if(imageSizeSetting.isChecked()){
//            gifWidth=Integer.parseInt(imageWidth.getText().toString());
//            gifHeight=Integer.parseInt(imageHeigth.getText().toString());
//        }else if(gifSetting.isChecked() && gifOldSize.isChecked()){
//            //使用原图时为默认大小
//
//
//        }else{
//            if(gifWidth>500){
//                gifWidth=500;
//                gifHeight=gifHeight*500/gifWidth;
//            }
//        }
//
//        //用户输入大小小于0时
//        if(gifWidth<1 || gifHeight<1){
//            gifWidth=500;
//            gifHeight=bitmaps.get(0).getHeight()*500/gifWidth;
//        }
//
//        //速度
//        if(gifSetting.isChecked()){
//            ftp=gifSpeed.getProgress();
//        }
//
//        String gifPath = PictureSelectUtil.getPhotoFileName(MainActivityTest.this, ".gif");
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        AnimatedGifEncoder localAnimatedGifEncoder = new AnimatedGifEncoder();
//        localAnimatedGifEncoder.start(baos);//start
//        localAnimatedGifEncoder.setRepeat(0);//设置生成gif的开始播放时间。0为立即开始播放
//        localAnimatedGifEncoder.setDelay(ftp);
//
//
//        if(isColor){
//            for (Bitmap bitmap:bitmaps){
//                ThreadInfo threadInfo=new ThreadInfo();
//                ThreadTask task=new ThreadTask(threadInfo);
//
//
//
//                Bitmap b=ImageToCharUtil.createAsciiPicColor(bitmap,MainActivityTest.this);
//                b=ImageUtil.resizeImage(b, gifWidth, gifHeight);
//                localAnimatedGifEncoder.addFrame(b);
//                b.recycle();
//                b=null;
//            }
//        }else {
//            for (Bitmap bitmap:bitmaps){
//                Bitmap b=ImageToCharUtil.createAsciiPic(bitmap,MainActivityTest.this);
//                b=ImageUtil.resizeImage(b, gifWidth, gifHeight);
//                localAnimatedGifEncoder.addFrame(b);
//            }
//        }
//        //bitmaps.clear();
//        System.gc();
//        localAnimatedGifEncoder.finish();//finish
//
//        try {
//            FileOutputStream fos = new FileOutputStream(gifPath);
//            baos.writeTo(fos);
//            baos.flush();
//            fos.flush();
//            baos.close();
//            fos.close();
//        }catch (Exception e){
//
//        }
//
//        return gifPath;
//
//    }
//
//    /**
//     *分解gif
//     */
//    private List<Bitmap> getBitmapToGif(String path){
//        List<Bitmap> res=new ArrayList<>();
//        try {
//            GifDrawable gifDrawable=new GifDrawable(path);
//            int numberOfFrames = gifDrawable.getNumberOfFrames();
//            for (int i=0;i<numberOfFrames;i++){
//                Bitmap bitmap = gifDrawable.seekToFrameAndGet(i);
//                res.add(bitmap);
//            }
//
//            System.gc();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return res;
//    }
//
//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        int id=buttonView.getId();
//        switch (id){
//            case R.id.toChar_imgSize:
//                if(isChecked){
//                    sizeLayout.setVisibility(View.VISIBLE);
//                }else {
//                    sizeLayout.setVisibility(View.GONE);
//                }
//                break;
//            case R.id.toChar_gif_seting:
//                if(isChecked){
//                    gifSettingLayout.setVisibility(View.VISIBLE);
//                }else {
//                    gifSettingLayout.setVisibility(View.GONE);
//                }
//
//        }
//    }
//
//    /**
//     * 图片保存
//     * @param view
//     */
//    public void doSaveImage(View view){
//        Bitmap bitmap=null;
//        if(image!=null){
//            if(image.isGif()){
//                this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + image.getImg())));
//                ToastUtil.showShotToast(this,"图片保存成功");
//                image=null;
//            }else {
//                bitmap=(Bitmap) image.getImg();
//                String s = PictureSelectUtil.savePhotoToSD(bitmap, this);
//                bitmap=BitmapFactory.decodeFile(s);
//                imageView.setImageBitmap(bitmap);
//                image=null;
//            }
//
//        }else {
//            Drawable drawable = imageView.getDrawable();
//            if(drawable!=null){
//                ToastUtil.showShotToast(this,"该图已保存");
//            }else {
//                ToastUtil.showShotToast(this,"请生成后再保存");
//            }
//        }
//    }
//
//    ProgressDiaolog p=null;
//    public void test(View view){
//        Intent intent=new Intent().setClass(this,GifToCharActivity.class);
//        startActivity(intent);
//
//
//
//
//    }
//
//    public void test2(View view){
//     Intent intent=new Intent().setClass(this,MoreActivity.class);
//        startActivity(intent);
//    }
//
//}
