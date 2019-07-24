package com.example.handschoolapplication.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handschoolapplication.R;
import com.example.handschoolapplication.utils.FilterEmojiTextWatcher;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhw on 2018/8/21.
 */

@SuppressLint("ValidFragment")
public class KeyMapDialog extends DialogFragment {


    private Dialog dialog;//声明一个Dialog
    private String etHint;//声明一个文本提示框  用来表明回复或者评价对象
    private ProgressDialog progressDialog;//声明一个progresDialog用于等待接口请求
    private SendBackListener sendListener;
    private String flag;//声明一个标识  来满足发送不同的地方

    public interface SendBackListener {
        void sendMessage(String comment);
        void sendMessages(String comment);
    }

    @SuppressLint("ValidFragment")
    public KeyMapDialog(String etHint, SendBackListener sendListener,String flag) {
        this.etHint = etHint;
        this.sendListener = sendListener;
        this.flag = flag;
        Log.e("aaa",
                "(KeyMapDialog.java:51)<---->" + "KeyMapDialog(String etHint, SendBackListener sendListener)");

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.e("aaa",
                "(KeyMapDialog.java:53)<---->" + "aaaaa");
        dialog = new Dialog(getActivity(), R.style.bottom_dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = View.inflate(getActivity(), R.layout.dialog_keymap_comment, null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);//设置外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.alpha = 1;
        lp.dimAmount = 0.5f;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        window.setAttributes(lp);
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        final EditText etComment = (EditText) view.findViewById(R.id.et_comment);
        final TextView tvSend = (TextView) view.findViewById(R.id.tv_send_comment);
        etComment.setHint(etHint);


        etComment.addTextChangedListener(new FilterEmojiTextWatcher(getActivity()));

//        etComment.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.length() > 0) {
//                    tvSend.setEnabled(true);
//                    tvSend.setBackgroundResource(R.drawable.btn_background_blue);
//                } else {
//                    tvSend.setEnabled(false);
//                    tvSend.setBackgroundResource(R.drawable.btn_background_grey);
//                }
//            }
//        });

        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = etComment.getText().toString().trim();
                if (TextUtils.isEmpty(trim)) {
                    Toast.makeText(getActivity(), "发表的内容不能为空哦", Toast.LENGTH_SHORT).show();
                } else {

                    //声明一个progressDialog
                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    if(flag.equals("0")){
                        sendListener.sendMessage(trim);
                    }else {
                        sendListener.sendMessages(trim);
                    }

                }
            }
        });

        etComment.setFocusable(true);
        etComment.setFocusableInTouchMode(true);
        etComment.requestFocus();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           public void run() {
                               Log.e("aaa",
                                       "(KeyMapDialog.java:125)<---->"+"执行run（）方法");
                               InputMethodManager inputManager =
                                       (InputMethodManager) etComment.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                               inputManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
                               inputManager.showSoftInput(etComment, 0);
                           }
                       },
                500);

        final Handler handler = new Handler();


        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideSoftkeyboard();
                    }
                }, 200);
            }
        });


        return dialog;
    }

    private void hideSoftkeyboard() {
        try {
            ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (NullPointerException e) {

        }
    }

    public void hideProgressdialog() {
        progressDialog.cancel();
    }
}
