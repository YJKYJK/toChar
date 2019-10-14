package yanyan.com.tochar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.gs.buluo.common.widget.LoadingDialog;
import com.hjq.permissions.Permission;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
import yanyan.com.tochar.adpter.FullyGridLayoutManager;
import yanyan.com.tochar.adpter.GridImageAdapter;
import yanyan.com.tochar.gif.GifMakeUtil;
import yanyan.com.tochar.utils.PermissionsUtil;
import yanyan.com.tochar.utils.PictureSelectUtil;
import yanyan.com.tochar.utils.ToastUtil;


public class GifActivity extends Activity {
    private TextView timeTitle;
    private SeekBar timeSeekBar;
    private Integer time = 100;
    private RecyclerView recyclerView;
    private GridImageAdapter adapter;
    //图片集合
    private List<LocalMedia> imgList = new ArrayList<>();


    List<String> pathList;

    private Switch gifSwitch;
    private LinearLayout linearLayout;
    private EditText editGifWidth,editGifHeight;
    private GifImageView gifImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);

        timeTitle = findViewById(R.id.gif_timeTitle);
        timeSeekBar = findViewById(R.id.gif_time_seekBar);
        init();


        recyclerView = findViewById(R.id.gif_recycler);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(GifActivity.this, 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new GridImageAdapter(GifActivity.this, onAddPicClickListener);
        adapter.setList(imgList);
        adapter.setSelectMax(20);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                int id = v.getId();
                if (id == R.id.ll_del) {
                    ToastUtil.showShotToast(GifActivity.this, "哈哈哈");
                }
            }
        });


        PermissionsUtil.authorization(new String[]{Permission.READ_EXTERNAL_STORAGE,
                Permission.WRITE_EXTERNAL_STORAGE}, GifActivity.this);


        timeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                timeTitle.setText("播放时间间隔:" + progress + "毫秒");
                time = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    private void init() {
        linearLayout = findViewById(R.id.gif_layout2);
        gifSwitch = findViewById(R.id.gif_switch);
        editGifWidth=findViewById(R.id.gif_width);
        editGifHeight=findViewById(R.id.gif_heigth);
        gifImageView=findViewById(R.id.gif_image);
        gifSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayout.setVisibility(View.VISIBLE);
                } else {
                    linearLayout.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imgList = PictureSelector.obtainMultipleResult(data);
        adapter.setList(imgList);
        adapter.notifyDataSetChanged();
        defaultSize(imgList);

    }

    /**
     * 默认大小
     * @param imgList
     */
    private void defaultSize( List<LocalMedia>imgList){

        int width=500;
        int height=0;
        if(imgList.size()>0){
            String path=imgList.get(0).getPath();

            Bitmap bitmap = PictureSelectUtil.getBitmap(path);
            height=bitmap.getHeight();
            height=height*500/bitmap.getWidth();
            editGifWidth.setText(width+"");
            editGifHeight.setText(height+"");

        }

    }

    /**
     * 生成图片
     */
    public void createGifAndSave(View view) {
        pathList = new ArrayList<>();
        if (imgList != null && imgList.size() > 0) {
            for (LocalMedia localMedia : imgList) {
                pathList.add(localMedia.getPath());
            }
            createGif(pathList);
        } else {
            ToastUtil.showShotToast(GifActivity.this, "请选择图片");
        }
    }

    /**
     * 生成GIF
     *
     * @param paths
     */
    public void   createGif(List<String> paths) {
        int gifWidth=500;
        int gifHeight=500;
        gifWidth=Integer.parseInt(editGifWidth.getText().toString());
        gifHeight=Integer.parseInt(editGifHeight.getText().toString());

        try {
            LoadingDialog.getInstance().show(GifActivity.this, "正在生成", false);

            //使用该方法生成gif图
            int finalGifWidth = gifWidth;
            int finalGifHeight = gifHeight;
            Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> e) throws Exception {

                    String gifPath = PictureSelectUtil.getPhotoFileName(GifActivity.this, ".gif");
                    try {
                        String gif = GifMakeUtil.createGif(gifPath, paths, time, finalGifWidth, finalGifHeight);
                        e.onComplete();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    GifDrawable drawable=new GifDrawable(gif);
                                    gifImageView.setImageDrawable(drawable);
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        });
                        // 最后通知图库更新
                        GifActivity.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + gifPath)));
                    } catch (Exception e1) {
                        e.onError(e1);
                    }
                }
            }).subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(String s) {

                }

                @Override
                public void onError(Throwable e) {
                    LoadingDialog.getInstance().dismissDialog();
                    ToastUtil.showShotToast(GifActivity.this, "生成失败");

                }

                @Override
                public void onComplete() {
                    LoadingDialog.getInstance().dismissDialog();
                    ToastUtil.showShotToast(GifActivity.this, "生成成功");

                }
            });
        } catch (Exception e) {
            ToastUtil.showShotToast(this, "生成动态图片异常");
        }
    }

    /**
     * 图片添加
     */
    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            PictureSelectUtil.selectPohtoMaxNum(GifActivity.this, 20, 1);
        }
    };

}







