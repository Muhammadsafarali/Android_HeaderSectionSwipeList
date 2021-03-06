package ru.norbit.myheaderswipelist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

import static android.R.attr.resource;

/**
 * Created by safarali.alisultanov on 02.11.2016.
 */

public class MyAdapter extends ArrayAdapter<String> implements StickyListHeadersAdapter {

    private final Context mContext;
    private final LayoutInflater mInflater;
    private final ViewBinderHelper binderHelper;

    public MyAdapter(Context context, List<String> objects) {
        super(context, R.layout.row_list, objects);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        binderHelper = new ViewBinderHelper();
    }

    private void printMsg(String msg) {
        Log.e("LOG", msg);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.row_list, parent, false);

            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.text);
            holder.swipeLayout = (SwipeRevealLayout) convertView.findViewById(R.id.swipe_layout);
            View frame = convertView.findViewById(R.id.frame);
            frame.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Toast.makeText(mContext,
                            "долгое удержание.",
                            Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
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
                    if (slideOffset >= 0.6) {
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
