package com.example.firebaseuser.ToolBarBottom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.firebaseuser.R;

public class ToolBarBottom extends LinearLayout {
    ToolBarFunction toolBarFunction;
    LinearLayout tool_bar_bottom;
    ImageView btnHome,btnNews,btnContact,btnShop,btnAccount;
    void init(){
        inflate(getContext(), R.layout.bar_bottom,this);
        tool_bar_bottom = findViewById(R.id.bar_bottom);
        btnHome = findViewById(R.id.btnHome);
        btnNews = findViewById(R.id.btnNews);
        btnContact = findViewById(R.id.btnContact);
        btnShop = findViewById(R.id.btnShop);
        btnAccount = findViewById(R.id.btnAccount);
        btnAccount.setOnClickListener(onClickListener);
        btnContact.setOnClickListener(onClickListener);
        btnHome.setOnClickListener(onClickListener);
        btnNews.setOnClickListener(onClickListener);
        btnShop.setOnClickListener(onClickListener);
    }
    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnHome:
                    toolBarFunction.getHomeFragment();
                    break;
                case R.id.btnNews:
                    toolBarFunction.getNewsFragment();
                    break;
                case R.id.btnShop:
                    toolBarFunction.getShopFragment();
                    break;
                case R.id.btnContact:
                    toolBarFunction.getContactFragment();
                    break;
                case R.id.btnAccount:
                    toolBarFunction.getAccountfragment();
                    break;
                default:
                    Toast.makeText(getContext(),"Don't selected",Toast.LENGTH_SHORT).show();
            }
        }
    };
    public ToolBarBottom(Context context) {
        super(context);
        init();
    }

    public ToolBarBottom(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ToolBarBottom(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ToolBarBottom(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
