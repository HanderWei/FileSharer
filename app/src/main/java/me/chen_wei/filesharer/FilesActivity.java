package me.chen_wei.filesharer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hander on 16/7/4.
 * <p/>
 * Email : hander_wei@163.com
 */
public class FilesActivity extends AppCompatActivity {

    public static final String TAG = "FilesActivity";

    protected static final int CHOOSE_FILE_RESULT_CODE = 20;

    @BindView(R.id.lv_files)
    ListView filesLv;

    private FilesAdapter adapter;

    private Context context;

    private List<File> files = new ArrayList<>();

    private Uri imageUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_files);

        ButterKnife.bind(this);

        context = this;

        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        ActionBar actionBar = getSupportActionBar();

        String path = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        File file;
        if (path.equals("root")) {
            actionBar.setTitle("根路径");
            file = Environment.getExternalStorageDirectory();
        } else {
            file = new File(path);
            actionBar.setTitle(file.getName());
        }
        if (file.isDirectory()) {
            files = Arrays.asList(file.listFiles());
            adapter = new FilesAdapter(this, files);
            filesLv.setAdapter(adapter);
        }

        View empty = getLayoutInflater().inflate(R.layout.empty_view, null, false);
        addContentView(empty, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
        filesLv.setEmptyView(empty);

        filesLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                File file = (File) adapter.getItem(i);
                if (file.isDirectory()) {
                    Intent intent = new Intent(context, FilesActivity.class);
                    intent.putExtra(Intent.EXTRA_TEXT, file.getAbsolutePath());
                    startActivityForResult(intent, CHOOSE_FILE_RESULT_CODE);
                }
            }
        });

        filesLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                File file = (File) adapter.getItem(i);
                String fileName = file.getName();
                Log.e(TAG, "文件名 ： " + fileName);

                Log.e(TAG, "文件路径 ： " + file.getAbsolutePath());
                if (file.isDirectory()) {
                    return false;
                } else if (fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".gif") || fileName.endsWith(".jpeg")) {
                    imageUri = Uri.fromFile(file);
                    Log.e(TAG, "文件URI：" + imageUri.toString());
//                    UCrop.of(imageUri, imageUri)
//                            .start(FilesActivity.this);
                    Crop.of(imageUri, imageUri).asSquare().start(FilesActivity.this);
                } else {
                    Intent intent = new Intent();
                    intent.setData(Uri.fromFile(file));
                    setResult(CHOOSE_FILE_RESULT_CODE, intent);
                    finish();
                }
                return false;
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == UCropActivity.RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
//            final Uri resultUri = UCrop.getOutput(data);
//            Intent intent = new Intent(this, WiFiDirectActivity.class);
//            intent.setData(resultUri);
//            setResult(CHOOSE_FILE_RESULT_CODE, intent);
//            finish();
//        }
        if (requestCode == Crop.REQUEST_CROP && resultCode == RESULT_OK) {
            Intent intent = new Intent(this, WiFiDirectActivity.class);
            intent.setAction("me.chen_wei.wifip2p");
            intent.setData(imageUri);
            Log.e(TAG, "Crop : " + imageUri);
            setResult(CHOOSE_FILE_RESULT_CODE, intent);
            finish();
        } else if (resultCode == CHOOSE_FILE_RESULT_CODE){
            Intent intent = new Intent(this, WiFiDirectActivity.class);
            intent.setAction("me.chen_wei.wifip2p");
            intent.setData(data.getData());
            Log.e(TAG, "FilesActivity : " + data.getData());
            setResult(CHOOSE_FILE_RESULT_CODE, intent);
            finish();
        }
    }

}
