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

import java.util.ArrayList;
import java.util.List;


public class ButlerFragment extends Fragment implements View.OnClickListener {

    private ListView mChatListView;
    private EditText et_text;
    private Button btn_send;

    private List<ChatListData> list = new ArrayList<>();
    private ChatListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_butler, null);
        findView(view);
        return view;
    }

    //初始化view
    private void findView(View view) {
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
}
