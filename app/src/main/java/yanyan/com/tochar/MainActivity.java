package yanyan.com.tochar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.hjq.permissions.Permission;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.Tencent;

import java.util.ArrayList;
import java.util.List;

import yanyan.com.tochar.adpter.HomeListAdapter;
import yanyan.com.tochar.beans.HomeListBean;
import yanyan.com.tochar.utils.CoreUtil;

import yanyan.com.tochar.utils.PermissionsUtil;

public class MainActivity extends AppCompatActivity  {


    private Tencent tencent;
    private GridView gridView;
    private List<HomeListBean> listBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        gridView=findViewById(R.id.main_grid);
        intGridView();

        //检查更新
        CoreUtil.detectionUpdate(this);

        tencent = Tencent.createInstance("101556816", this);

        PermissionsUtil.authorization(new String[]{Permission.READ_EXTERNAL_STORAGE,
                Permission.WRITE_EXTERNAL_STORAGE}, this);

    }

    public void intGridView(){
        listBeans=new ArrayList<>();
        HomeListBean b1=new HomeListBean(R.drawable.b1,"静态图转字符图",
                "将自己的单张图转为字符图",Color.RED,new OneActivity());

        HomeListBean b2=new HomeListBean(R.drawable.b1,"多图转动态字符图",
                "将自己的多张图片转成动态字符图",Color.RED,new MoreActivity());

        HomeListBean b3=new HomeListBean(R.drawable.b1,"动态图转字符图",
                "将自己的动态图转为动态字符图",Color.RED,new GifToCharActivity());

        HomeListBean b4=new HomeListBean(R.drawable.b1,"多图转动态图",
                "合成多张图片为一张动态图",Color.RED,new GifActivity());
        listBeans.add(b1);
        listBeans.add(b2);
        listBeans.add(b3);
        listBeans.add(b4);
        HomeListAdapter adapter=new HomeListAdapter(this,listBeans);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HomeListBean listBean = listBeans.get(position);
                if(listBean.getActivity()!=null){
                    Intent in=new Intent();
                    in.setClass(MainActivity.this,listBeans.get(position).getActivity().getClass());
                    startActivity(in);
                }
            }
        });


    }


    private void init() {
    }


    /**
     * 右上角点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.tochar_gif:
                Intent in = new Intent();
                in.setClass(this, GifActivity.class);
                startActivity(in);
                break;
            //检查更新
            case R.id.tochar_update:
                CoreUtil.detectionUpdate2(this);
                break;
            //分享
            case R.id.tochar_share:
                final Bundle params = new Bundle();
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                params.putString(QQShare.SHARE_TO_QQ_TITLE, "图片转字符图");
                params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "我正在使用图片转字符图，里面有各类好玩的功能，你也来试试吧");
                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "https://ps.ssl.qhimg.com/sdmt/124_135_100/t0193a2198f757a2c76.jpg");
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "https://ps.ssl.qhimg.com/sdmt/124_135_100/t0193a2198f757a2c76.jpg");
                tencent.shareToQQ(this, params, null);
                break;
            //关于
            case R.id.tochar_about:
                CoreUtil.about(this);
                break;
            //加群
            case R.id.tochar_join:
                CoreUtil.joinQQGroup(this, "AOzZSjzgi_pYpAbHJbFubhDLju3qcRfn");
                ;
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }





}