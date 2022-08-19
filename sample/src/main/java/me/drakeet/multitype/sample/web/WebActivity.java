package me.drakeet.multitype.sample.web;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import me.drakeet.multitype.sample.MenuBaseActivity;
import me.drakeet.multitype.sample.R;
import me.drakeet.multitype.sample.web.impl.UMWebFragment;

public class WebActivity extends MenuBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        Fragment fragment = new UMWebFragment();
        setupFragment(R.id.container, fragment, fragment.getClass().getSimpleName());
    }

    public void setupFragment(int containerId, Fragment fragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment, tag)
                .commit();
    }

}
