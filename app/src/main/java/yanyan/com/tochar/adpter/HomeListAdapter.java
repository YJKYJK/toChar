package yanyan.com.tochar.adpter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import yanyan.com.tochar.R;
import yanyan.com.tochar.beans.HomeListBean;
import yanyan.com.tochar.gif.ImageUtil;

public class HomeListAdapter extends BaseAdapter {
    private List<HomeListBean> listBeans;
    private LayoutInflater inflater;
    private Context context;

    public HomeListAdapter(Context context, List<HomeListBean> listBeans){
        this.context=context;
        this.listBeans=listBeans;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return listBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return listBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.item_home_list,null);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.item_home_list_img);
            TextView title= (TextView) convertView.findViewById(R.id.item_home_list_title);
            TextView text= (TextView) convertView.findViewById(R.id.item_home_list_text);
            HomeListBean homeListBean = listBeans.get(position);
            if(homeListBean.getImage()!=null && homeListBean.getImage()!=0){
             imageView.setImageResource(homeListBean.getImage());
            }else {
                imageView.setBackgroundColor(homeListBean.getColor());
            }

            title.setText(homeListBean.getTitle());
            text.setText(homeListBean.getText());
        }
        return convertView;
    }
}
