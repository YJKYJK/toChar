package yanyan.com.tochar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import java.util.List;

import yanyan.com.tochar.beans.ImageToTxtBean;
import yanyan.com.tochar.beans.Page;
import yanyan.com.tochar.utils.CoreUtil;
import yanyan.com.tochar.utils.ProgressDiaolog;
import yanyan.com.tochar.utils.ToastUtil;

public class TextActivity extends Activity {

    private Button up;
    private Button dw;
    private Button copy;
    private SeekBar textSize;
    private EditText text;

    private Page page;
    private ImageToTxtBean imageToTxtBean;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fmt_text);
        init();//初始化

    }

    private void init(){
        up=findViewById(R.id.text_up);
        dw=findViewById(R.id.text_dw);
        copy=findViewById(R.id.text_copy);
        textSize=findViewById(R.id.text_textSize);
        text=findViewById(R.id.text_context);

        Intent i=getIntent();
        List<String> list= (List<String>) i.getSerializableExtra("list");
        if(list!=null && list.size()>0){
            page=new Page(0,list.size(),list);
            text.setText(list.get(0));
        }

        textSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                text.setTextSize(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }




    public void setPage(View view){
        int id=view.getId();
        int index=page.getIndex();
        int size=page.getSize();
        switch (id){
            case R.id.text_up:
                if(index>0){
                    text.setText(page.getTestList().get(index-1));
                    page.setIndex(index-1);
                }else {
                    ToastUtil.showLongToast(this,"已是第一页");
                }
                break;

            case R.id.text_dw:
                if(size-1>index){
                    text.setText(page.getTestList().get(index+1));
                    page.setIndex(index+1);
                }else {
                    ToastUtil.showLongToast(this,"已是最后一页");
                }

        }
    }

    public void copyText(View view){
        CoreUtil.copy(text.getText().toString(),this);
    }

}

//        转字符图是一款将图片转成字符的APP，里面包含单图转字符图、多图转字符图、动态图转字符图、多图转动态图等功能。
////                将图片的每一个像素转换为每一个字符，支持jpg、png、bmp、gif等各种常见的格式。
////                同时当您将动态图或多张图转为字符图时,您将会看到一组由动态字符组成的画面,满满的科技感。
////                小伙伴们赶紧试试吧!
