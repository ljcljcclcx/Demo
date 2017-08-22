package com.clcx.demo.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.clcx.demo.R;
import com.clcx.demo.base.BaseActivity;
import com.clcx.demo.ui.hotfix.AtyHOTFIX;
import com.clcx.demo.ui.main.RecycAdaMain;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MainActivity extends BaseActivity {
    @Bind(R.id.main_recyc)
    RecyclerView main_recyc;
    private RecycAdaMain adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    public void initView() {
        adapter = new RecycAdaMain(this);
        main_recyc.setLayoutManager(new GridLayoutManager(this, 3));
        adapter.setItems(getbeans());
        main_recyc.setAdapter(adapter);
        adapter.setOnItemClickListener((v, position) -> {
            MainBean bean= (MainBean) adapter.getItem(position);
            startActivity(bean.getIntent());
        });
    }

    @Override
    public void onClick(View v) {

    }

    private List<MainBean> getbeans() {
        List<MainBean> getbeans = new ArrayList<>();
        getbeans.add(new MainBean("热修复", new Intent(this, AtyHOTFIX.class)));
        getbeans.add(new MainBean("EventBus", new Intent(this, AtyHOTFIX.class)));
        getbeans.add(new MainBean("Rxjava", new Intent(this, AtyHOTFIX.class)));
        getbeans.add(new MainBean("MVP架构", new Intent(this, AtyHOTFIX.class)));
        return getbeans;
    }

    public class MainBean {
        private String title;
        private Intent intent;

        public MainBean(String title, Intent intent) {
            this.title = title;
            this.intent = intent;
        }

        public String getTitle() {
            return title;
        }

        public Intent getIntent() {
            return intent;
        }
    }
}
