package com.android.smartbutler.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.smartbutler.R;
import com.android.smartbutler.adapter.ChatListAdapter;
import com.android.smartbutler.entity.ChatListData;
import com.android.smartbutler.util.SharedPreferencesUtil;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import java.util.ArrayList;
import java.util.List;


public class ButlerFragment extends Fragment implements View.OnClickListener {

    private ListView mChatListView;
    private EditText et_text;
    private Button btn_send;

    private List<ChatListData> list = new ArrayList<>();
    private ChatListAdapter adapter;

    //TTS
    private SpeechSynthesizer mTts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_butler, null);
        findView(view);

        return view;
    }

    //初始化view
    private void findView(View view) {
        //1.创建SpeechSynthesizer对象, 第二个参数：本地合成时传InitListener
        mTts = SpeechSynthesizer.createSynthesizer(getActivity(), null);
        //2.合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端


        mChatListView = view.findViewById(R.id.mChatListView);
        et_text = view.findViewById(R.id.et_text);
        btn_send = view.findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);

        //设置适配器
        adapter = new ChatListAdapter(getActivity(), list);
        mChatListView.setAdapter(adapter);

        addLeftItem("你好，我是小管家");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_text:
                addLeftItem("左边");
                break;
            case R.id.btn_send:
                addRightItem(et_text.getText().toString());
                et_text.setText("");
                break;
        }
    }

    //添加左边文本
    private void addLeftItem(String text) {
        boolean isSpeak = SharedPreferencesUtil.getBoolean(getActivity(), "isSpeak", false);
        if (isSpeak){
            startSpeak(text);
        }

        ChatListData data = new ChatListData();
        data.setType(ChatListAdapter.VALUE_LEFT_TYPE);
        data.setText(text);
        list.add(data);
        //通知adaptor刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        mChatListView.setSelection(mChatListView.getBottom());
    }

    //添加右边文本
    private void addRightItem(String text) {
        ChatListData data = new ChatListData();
        data.setType(ChatListAdapter.VALUE_RIGHT_TYPE);
        data.setText(text);
        list.add(data);
        //通知adaptor刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        mChatListView.setSelection(mChatListView.getBottom());
        addLeftItem("收到");
    }

    //开始说话
    private void startSpeak(String text) {
        mTts.startSpeaking(text, mSynListener);
    }

    //合成监听器
    private SynthesizerListener mSynListener = new SynthesizerListener() {
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {
        }

        //缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
        }

        //开始播放
        public void onSpeakBegin() {
        }

        //暂停播放
        public void onSpeakPaused() {
        }

        //播放进度回调
        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }

        //恢复播放回调接口
        public void onSpeakResumed() {
        }

        //会话事件回调接口
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
        }
    };
}
