package yanyan.com.tochar.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import yanyan.com.tochar.R;

public class ProgressDiaolog extends Dialog {
   private TextView title;
   private ProgressBar progressBar;
   private TextView progreesTag;


   private String titleStr;
   private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress);
        title=findViewById(R.id.dialog_progress_title);
        progressBar=findViewById(R.id.dialog_progress_bar);
        progreesTag=findViewById(R.id.dialog_progress_tag);
        setCancelable(false);
        progreesTag.setText("已完成 : 0%");
        if(EmptyUtils.isNotEmpty(titleStr)){
            title.setText(titleStr);
        }
    }




    public ProgressDiaolog(Context context){
        super(context);
    }

    public ProgressDiaolog( Context context,String str) {
        super(context);
        this.context=context;
       this.titleStr=str;
    }


    public void setProgress(Integer progress){
        progressBar.setProgress(progress);
        int p = progressBar.getProgress();
        progreesTag.setText("已完成 : " + p + "%");
        if(p==100){
            this.dismiss();
        }
    }


    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public TextView getProgreesTag() {
        return progreesTag;
    }

    public void setProgreesTag(TextView progreesTag) {
        this.progreesTag = progreesTag;
    }

    public String getTitleStr() {
        return titleStr;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
        title.setText(titleStr);
    }
}
