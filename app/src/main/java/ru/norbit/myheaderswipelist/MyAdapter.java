package ru.norbit.myheaderswipelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

import static android.R.attr.resource;

/**
 * Created by safarali.alisultanov on 02.11.2016.
 */

public class MyAdapter extends ArrayAdapter<String> implements StickyListHeadersAdapter {

    private final LayoutInflater mInflater;
    private final ViewBinderHelper binderHelper;

    public MyAdapter(Context context, List<String> objects) {
        super(context, R.layout.row_list, objects);
        mInflater = LayoutInflater.from(context);
        binderHelper = new ViewBinderHelper();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.row_list, parent, false);

            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.text);
            holder.swipeLayout = (SwipeRevealLayout) convertView.findViewById(R.id.swipe_layout);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        final String item = getItem(position);
        if (item != null) {
            holder.swipeLayout.setSwipeListener(new SwipeRevealLayout.SimpleSwipeListener() {
                @Override
                public void onSlide(SwipeRevealLayout view, float slideOffset) {
                    if (slideOffset >= 0.95) {
                        view.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                remove(item);
                            }
                        },100);
                    }
                }
            });
            binderHelper.bind(holder.swipeLayout, item);

            holder.text.setText(item);
        }

        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        long ch = getItem(position).subSequence(0,1).charAt(0);
        return ch;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = mInflater.inflate(R.layout.header, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        //set header text as first char in name
        final String item = getItem(position);
        if (item != null) {
            String headerText = "" + item.subSequence(0,1).charAt(0);
            holder.text.setText(headerText);
        }
//        String headerText = "" + countries.get(position).subSequence(0, 1).charAt(0);
//        holder.text.setText(headerText);
        return convertView;
    }

    private class ViewHolder {
        TextView text;
        SwipeRevealLayout swipeLayout;
    }

    private class HeaderViewHolder {
        TextView text;
    }
}
