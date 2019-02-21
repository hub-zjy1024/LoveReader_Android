package hw.txtreader.adapter;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.util.List;

import hw.txtreader.R;
import hw.txtreader.entity.Book;

/**
 Created by 张建宇 on 2019/2/1. */
public class BoolistAdapter extends CommonAdapter<Book>{
    public BoolistAdapter(Context context, List<Book> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, Book item) {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        String showName = item.filePath;
        int index = 0;
        int count = 0;
        while ((index = showName.lastIndexOf("/")) != -1) {
            count++;
            if (count == 2) {
                break;
            }
        }
        showName = "..." + showName.substring(index);
        helper.setText(R.id.item_recent_bookname,showName);
    }
}
