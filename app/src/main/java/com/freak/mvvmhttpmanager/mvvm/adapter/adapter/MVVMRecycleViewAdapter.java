package com.freak.mvvmhttpmanager.mvvm.adapter.adapter;

import android.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.freak.mvvmhttpmanager.R;
import com.freak.mvvmhttpmanager.databinding.ItemRecycleviewBinding;
import com.freak.mvvmhttpmanager.mvvm.adapter.model.MVVMRecycleViewModel;

/**
 * @author Freak
 * @date 2019/5/17.
 */

public class MVVMRecycleViewAdapter extends BaseQuickAdapter<MVVMRecycleViewModel, BaseViewHolder> {

    public MVVMRecycleViewAdapter() {
        super(R.layout.item_recycleview);
    }

    @Override
    protected void convert(BaseViewHolder helper, MVVMRecycleViewModel item) {
        ItemRecycleviewBinding binding = DataBindingUtil.bind(helper.itemView);
        binding.setAdapter(item);
        binding.executePendingBindings();
    }
}
