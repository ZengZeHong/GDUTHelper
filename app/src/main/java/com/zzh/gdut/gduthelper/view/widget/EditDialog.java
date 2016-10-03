package com.zzh.gdut.gduthelper.view.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.zzh.gdut.gduthelper.R;
import com.zzh.gdut.gduthelper.util.ToastUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by ZengZeHong on 2016/10/2.
 */

public class EditDialog implements TextWatcher {
    private static final String TAG = "EditDialog";
    public static final int TAG_DORMITORY = 0;
    public static final int TAG_POLITY = 1;
    public static final int TAG_PHONE_NUMBER = 2;
    public static final int TAG_EMAIL = 3;
    public static final int TAG_ADDRESS = 4;
    public static final int TAG_GRATUATE = 5;
    private MaterialEditText materialEditText;
    private MaterialDialog dialog;
    private Context context;
    private String hint;
    private String floatHint;
    private int tag;
    private boolean isError = false;
    private String errorLine;

    public interface OnPositiveClick {
        void onPositiveClick(String result);
    }

    public EditDialog(Context context) {
        this.context = context;
    }

    public EditDialog setHint(String hint) {
        this.hint = hint;
        return this;
    }

    public EditDialog setTag(int tag) {
        this.tag = tag;
        return this;
    }

    public EditDialog setFloatHint(String floatHint) {
        this.floatHint = floatHint;
        return this;
    }

    public void show(final OnPositiveClick onPositiveClick) {
        dialog = new MaterialDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit, null);
        materialEditText = (MaterialEditText) view.findViewById(R.id.et_dialog);
        materialEditText.setHint(hint);
        materialEditText.setFloatingLabelText(floatHint);
        materialEditText.addTextChangedListener(this);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        dialog.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isError || materialEditText.getText().toString().equals("")) {
                    dialog.dismiss();
                    onPositiveClick.onPositiveClick(materialEditText.getText().toString());
                }else
                    ToastUtil.showToast(context, errorLine);

            }
        });
        dialog.show();

    }


    @Override
    public void afterTextChanged(Editable s) {
        if (tag == TAG_EMAIL) {
            Pattern pattern = Pattern.compile("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(s.toString());
            if (!matcher.matches() && !s.toString().equals("")) {
                isError = true;
                materialEditText.setHelperText("邮箱格式错误");
                errorLine = "邮箱格式错误";
            } else {
                isError = false;
                materialEditText.setHelperText("");
            }
        }else if(tag == TAG_PHONE_NUMBER){
            Pattern pattern = Pattern.compile("\\d+", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(s.toString());
            if (!matcher.matches() && !s.toString().equals("") ) {
                isError = true;
                materialEditText.setHelperText("手机格式错误");
                errorLine = "手机错误";
            } else {
                isError = false;
                materialEditText.setHelperText("");
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

}

