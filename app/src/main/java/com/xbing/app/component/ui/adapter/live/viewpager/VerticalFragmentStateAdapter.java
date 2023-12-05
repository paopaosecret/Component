package com.xbing.app.component.ui.adapter.live.viewpager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.xbing.app.component.bean.LiveRoomInfo;
import com.xbing.app.component.ui.fragment.ColorFragment;

public class VerticalFragmentStateAdapter extends FragmentStateAdapter {


    public VerticalFragmentStateAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new ColorFragment(new LiveRoomInfo("2", 0xFF00FF00));
            case 2:
                return new ColorFragment(new LiveRoomInfo("3", 0xFF0000FF));
            case 3:
                return new ColorFragment(new LiveRoomInfo("4", 0xFFFFFF00));
            case 4:
                return new ColorFragment(new LiveRoomInfo("5", 0xFFFF00FF));
            case 5:
                return new ColorFragment(new LiveRoomInfo("6", 0xFF00FF00));
            case 6:
                return new ColorFragment(new LiveRoomInfo("7", 0xFF000FFF));
            case 7:
                return new ColorFragment(new LiveRoomInfo("8", 0xFFFFFFF0));
            case 8:
                return new ColorFragment(new LiveRoomInfo("9", 0xFFFFF0FF));
            default:
                return new ColorFragment(new LiveRoomInfo("1", 0xFFFF0FF0));
        }
    }

    @Override
    public int getItemCount() {
        return 9;
    }
}
