package yanyan.com.tochar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gs.buluo.common.widget.LoadingDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.IOException;
import java.io.Serializable;
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
import yanyan.com.tochar.adpter.FullyGridLayoutManager;
import yanyan.com.tochar.adpter.GridImageAdapter;
import yanyan.com.tochar.beans.ImageToTxtBean;
import yanyan.com.tochar.beans.ToChanrBean;
import yanyan.com.tochar.utils.EmptyUtils;
import yanyan.com.tochar.utils.PictureSelectUtil;
import yanyan.com.tochar.utils.StringUtil;
import yanyan.com.tochar.utils.ToCharUtil;
import yanyan.com.tochar.utils.ToastUtil;

public class OneActivity extends Activity implements CompoundButton.OnCheckedChangeListener{
   private RecyclerView recyclerView;
   private CheckBox isStr;
   private CheckBox isColor;
   private ImageView imageView;
  private EditText str;
  private LinearLayout layout1;
    private LinearLayout layout2;

    private  ToChanrBean toChanrBean=null;
    private GridImageAdapter adapter;
    private List imgList= new ArrayList<>();//图片集合
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        init();//初始化

    }

    private void init(){
        recyclerView=findViewById(R.id.one_recycler);
        isStr=findViewById(R.id.one_isStr);
        isStr.setOnCheckedChangeListener(this);
        isColor=findViewById(R.id.one_isColor);
        isColor.setOnCheckedChangeListener(this);
        imageView=findViewById(R.id.one_image);
        layout1=findViewById(R.id.one_layout1);
        str=findViewById(R.id.one_str);
        layout2=findViewById(R.id.one_layout2);

        //初始化图片选择
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new GridImageAdapter(this, new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                PictureSelectUtil.selectPohto(OneActivity.this,3306);
            }
        });
        adapter.setList(imgList);
        adapter.setSelectMax(1);
        recyclerView.setAdapter(adapter);
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



public void createOneImg(View view){
        if(imgList!=null && imgList.size()>0){
            LocalMedia localMedia= (LocalMedia) imgList.get(0);
            String path= localMedia.getPath();
            Bitmap bitmap = PictureSelectUtil.getBitmap(path);
            boolean color=this.isColor.isChecked();
            boolean isStr=this.isStr.isChecked();
            String textStr="";
            if(isStr){
                textStr=this.str.getText().toString();
            }

           create(bitmap,color,textStr);
        }else {
            ToastUtil.showLongToast(this,"请选择图片");
        }
}

private void create(Bitmap bitmap, boolean isColor,String str){
    LoadingDialog.getInstance().show(OneActivity.this, "正在生成", false);//加载

    Observable.create(new ObservableOnSubscribe<String>() {
        @Override
        public void subscribe(ObservableEmitter<String> e) throws Exception {
            try{
                if(isColor){
                   toChanrBean= ToCharUtil.createAsciiPicColor(bitmap,str,OneActivity.this);
                }else {
                    toChanrBean=ToCharUtil.createAsciiPic(bitmap,str,OneActivity.this);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(toChanrBean.getBitmap());
                    }
                });
                e.onComplete();
            }catch (Exception e1){
                e.onError(e1);
            }
        }
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
        @Override
        public void onSubscribe(Disposable d) {

        }
        @Override
        public void onNext(String s) {

        }
        @Override
        public void onError(Throwable e) {
            LoadingDialog.getInstance().dismissDialog();
            ToastUtil.showShotToast(OneActivity.this, "生成失败");
        }
        @Override
        public void onComplete() {
            LoadingDialog.getInstance().dismissDialog();
            ToastUtil.showShotToast(OneActivity.this, "生成成功");
            layout2.setVisibility(View.VISIBLE);
        }
    });

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
            case R.id.one_isStr:
                if(isChecked){
                    layout1.setVisibility(View.VISIBLE);
                }else {
                    layout1.setVisibility(View.GONE);
                }
                break;
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
            List<String> list = new ArrayList<>();
            list.add(toChanrBean.getText());
            if (list != null && list.size() > 0) {
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
     * 保存到本地
     * @param view
     */
    public void oneSave(View view){
        if(toChanrBean!=null && toChanrBean.getBitmap()!=null){
            Bitmap bitmap=toChanrBean.getBitmap();
            String s = PictureSelectUtil.savePhotoToSD(bitmap, this);
            if(EmptyUtils.isNotEmpty(s)){
                ToastUtil.showLongToast(this,"保存成功");
            }
        }else {
            ToastUtil.showLongToast(this,"您还没有生成");
        }

    }
}
