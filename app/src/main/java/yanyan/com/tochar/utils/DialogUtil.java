package yanyan.com.tochar.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import yanyan.com.tochar.R;

public class DialogUtil extends Dialog {
    private TextView title;
    private Button left,right;
    private TextView text;


    private LeftOnlistener leftFun;
    private RightOnlistener rightFun;
    private String titleStr;
    private String leftStr,rightStr;
    private String textStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_common);
        title=findViewById(R.id.dialog_image_title);
        left=findViewById(R.id.dialog_image_leftbt);
        right=findViewById(R.id.dialog_image_rightbt);
        text=findViewById(R.id.dialog_image_text);
        initEvent();
        initDate();
    }



    public DialogUtil(Context context,
                      String titleStr,String textStr, String leftStr, String rightStr,
                      LeftOnlistener leftFun, RightOnlistener rightFun) {
        super(context);
        this.leftFun = leftFun;
        this.rightFun = rightFun;
        this.titleStr = titleStr;
        this.leftStr = leftStr;
        this.rightStr = rightStr;
        this.textStr=textStr;
    }


    public DialogUtil(Context context,
                      String titleStr, String textStr,String leftStr, String rightStr) {
        super(context);
        this.titleStr = titleStr;
        this.leftStr = leftStr;
        this.rightStr = rightStr;
        this.textStr = textStr;
    }

    private void initDate() {
        if(EmptyUtils.isNotEmpty(titleStr)){
            title.setText(titleStr);
        }

        if(textStr!=null){
            text.setText(textStr);
        }

        if (EmptyUtils.isNotEmpty(leftStr)){
            left.setText(leftStr);
        }

        if(EmptyUtils.isNotEmpty(rightStr)){
            right.setText(rightStr);
        }

        if(leftFun==null){
           leftFun= new LeftOnlistener() {
                @Override
                public void leftOnclick() {

                }
            };
        }


        if(rightFun==null){
        rightFun=new RightOnlistener() {
                @Override
                public void rightOnclick() {

                }
            };
        }
    }

    public void setLeftOnclick(LeftOnlistener leftFun){
        this.leftFun=leftFun;
    };

    public void setRightOnclick(RightOnlistener rightFun){
        this.rightFun=rightFun;
    };

    /**
     * 初始化方法
     */
    private void initEvent(){
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leftFun.leftOnclick();
                dismiss();
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightFun.rightOnclick();
                dismiss();
            }
        });
    }

    public interface LeftOnlistener{
        public void leftOnclick();
    }

    public interface RightOnlistener{
        public void rightOnclick();
    }

    public DialogUtil(Context context) {
        super(context);
    }




}
