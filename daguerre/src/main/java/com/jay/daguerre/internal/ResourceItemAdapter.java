package com.jay.daguerre.internal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.jay.daguerre.R;
import com.jay.daguerre.provider.ImageLoader;

/**
 * Created by jay on 2017/11/23 下午4:57
 */
class ResourceItemAdapter extends BaseRecyclerAdapter<Media.Resource, ResourceItemAdapter.ViewHolder> {


    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener;
    private OnItemClickListener mOnItemClickListener;

    ResourceItemAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = mLayoutInflater.inflate(R.layout.daguerre_resource_item, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageLoader imageLoader = ConfigParams.getInstance().getImageLoader();
        Media.Resource item = getItem(position);
        if (imageLoader != null) {
            imageLoader.loadImage(mContext, holder.mImage, item.data, item.isGif(), item.isVideo());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onListItemClick(v);
                }
            }
        });

        if (item.isGif()) {
            holder.mImageFlagIcon.setImageResource(R.drawable.daguerre_ic_badge_gif);
        } else if (item.isVideo()) {
            holder.mImageFlagIcon.setImageResource(R.drawable.daguerre_ic_badge_video);
        } else {
            holder.mImageFlagIcon.setVisibility(View.GONE);
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            holder.mCheckBox.setButtonDrawable(R.drawable.daguerre_btn_check_material);
//        } else {
//
//            StateListDrawable stateListDrawable = new StateListDrawable();
//
//            VectorDrawableCompat checkedDrawable = VectorDrawableCompat.create(mContext.getResources(), R.drawable.daguerre_circle_checked, mContext.getTheme());
//            VectorDrawableCompat uncheckedDrawable = VectorDrawableCompat.create(mContext.getResources(), R.drawable.daguerre_circle_unchecked, mContext.getTheme());
//
//            stateListDrawable.addState(new int[]{android.R.attr.state_checked}, checkedDrawable);
//            stateListDrawable.addState(new int[]{}, uncheckedDrawable);
//
//            int inset = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, mContext.getResources().getDisplayMetrics()));
//            InsetDrawable insetDrawable = new InsetDrawable(stateListDrawable, inset);
//            holder.mCheckBox.setButtonDrawable(insetDrawable);
//        }
        holder.mCheckBox.setChecked(getItem(position).isChecked);
        holder.mCheckBox.setOnCheckedChangeListener(mOnCheckedChangeListener);
    }

    void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
    }

    void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    interface OnItemClickListener {
        void onListItemClick(View itemView);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mImage;
        private final ImageView mImageFlagIcon;
        private final CheckBox mCheckBox;

        ViewHolder(View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.image);
            mImageFlagIcon = itemView.findViewById(R.id.image_flag_icon);
            mCheckBox = itemView.findViewById(android.R.id.checkbox);
        }
    }
}
