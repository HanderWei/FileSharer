package me.chen_wei.filesharer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hander on 16/7/4.
 * <p/>
 * Email : hander_wei@163.com
 */
public class FilesAdapter extends BaseAdapter {

    List<File> fileList;
    Context context;
    LayoutInflater inflater;

    public FilesAdapter(Context context, List<File> files) {
        this.context = context;
        this.fileList = files;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return fileList == null ? 0 : fileList.size();
    }

    @Override
    public Object getItem(int i) {
        return fileList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {

            view = inflater.inflate(R.layout.item_file, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        File file = fileList.get(position);
        String fileName = file.getName();
        holder.name.setText(fileName);

        if (file.isDirectory()) {
            holder.icon.setImageResource(R.drawable.folder);
        } else if (fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".gif") || fileName.endsWith(".jpeg")) {
            holder.icon.setImageResource(R.drawable.image);
        } else {
            holder.icon.setImageResource(R.drawable.file);
        }

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.file_icon)
        ImageView icon;
        @BindView(R.id.file_name)
        TextView name;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
