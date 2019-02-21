package hw.txtreader;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.bifan.txtreaderlib.main.TxtConfig;
import com.bifan.txtreaderlib.main.TxtReaderView;
import com.bifan.txtreaderlib.ui.HwTxtPlayActivity;

import hw.txtreader.entity.ReadSetting;

/**
 Created by 张建宇 on 2019/2/1. */
public class CustTxtPlayerActivity extends HwTxtPlayActivity {

    private static int min_size = SettingSp.ReadOptions.def_fontsize;

    @Override
    protected void onStart() {
        super.onStart();
        mMenuHolder.mTextSizeAdd.setOnClickListener(new TextChangeClickListener(mTxtReaderView, this, true));
        SettingSp.saveBooks(this, FilePath);

    }

    @Override
    protected void onLoadDataSuccess() {
        super.onLoadDataSuccess();
        ReadSetting readSetting = SettingSp.loadReadingSetting(this);
        int textSize = readSetting.textSize;
//        mTxtReaderView.setTextSize(textSize);
    }

    protected static void loadTxtToRead(Context context, String FilePath){
        loadTxtToRead(context, FilePath, null);
    }
    protected static void loadTxtToRead(Context context, String FilePath, String FileName) {
        Intent intent = new Intent();
        intent.putExtra("FilePath", FilePath);
        intent.putExtra("FileName", FileName);
        intent.setClass(context, CustTxtPlayerActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("zjy", "CustTxtPlayerActivity->onStop(): ==" );
        ReadSetting readSetting = new ReadSetting();
        if (mTxtReaderView != null) {
            readSetting.textSize = mTxtReaderView.getTextSize();
            Log.e("zjy", "CustTxtPlayerActivity->onDestroy(): ==" + FilePath);
            SettingSp.saveReadSetting(this, readSetting);
        } else {
            Log.e("zjy", "CustTxtPlayerActivity->onDestroy(): ==null !!!!");
        }
    }

    @Override
    protected void onDestroy() {
        Log.e("zjy", "CustTxtPlayerActivity->onDestroy(): ==");
        super.onDestroy();
    }
    protected void loadFile() {
//        int lastIndexOf = FilePath.lastIndexOf(".");
//        String fName = FilePath.substring(0, lastIndexOf);
//        fName += "_new.txt";
//        TxtTitileFillUtil.fillZhangjie(FilePath,fName);
//        FilePath=fName;
        super.loadFile();

    }
//    @Override
//    protected void loadFile() {
//        TxtConfig.savePageSwitchDuration(this, 400);
//        if (ContentStr == null) {
//            if (TextUtils.isEmpty(FilePath) || !(new File(FilePath).exists())) {
//                toast("文件不存在");
//                return;
//            }
//
//        }
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //延迟加载避免闪一下的情况出现
//                if (ContentStr == null) {
//                    loadOurFile();
//                } else {
//                    //                    loadStr();
//                }
//            }
//        }, 100);
//
//    }

    private static class TextChangeClickListener implements View.OnClickListener {
        TxtReaderView mTxtReaderView;
        private CustTxtPlayerActivity mActivity;
        private Boolean Add;

        public TextChangeClickListener(Boolean pre) {
            Add = pre;
        }

        public TextChangeClickListener(TxtReaderView mTxtReaderView, CustTxtPlayerActivity mActivity, Boolean add) {
            this.mTxtReaderView = mTxtReaderView;
            this.mActivity = mActivity;
            Add = add;
        }

        @Override
        public void onClick(View view) {
            if (mActivity.FileExist) {
                int textSize = mTxtReaderView.getTextSize();
                if (Add) {
                    if (textSize + 2 <= TxtConfig.MAX_TEXT_SIZE) {
                        mTxtReaderView.setTextSize(textSize + 2);
                        mActivity.mMenuHolder.mTextSize.setText(textSize + 2 + "");
                    }
                } else {
                    if (textSize - 2 >= CustTxtPlayerActivity.min_size) {
                        mTxtReaderView.setTextSize(textSize - 2);
                        mActivity.mMenuHolder.mTextSize.setText(textSize - 2 + "");
                    }
                }
            }
        }
    }
}
