package com.bootsysoftware.weatherapp.adapters;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bootsysoftware.weatherapp.R;
import com.bootsysoftware.weatherapp.model.Hour;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Matthew Boydston on 9/19/2016.
 */
public class HourAdapter extends RecyclerView.Adapter<HourAdapter.HourViewHolder> {
    private Hour[] mHours;

    public HourAdapter(Hour[] hours){
        mHours = hours;
    }

    @Override
    public HourViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_list_item, parent, false);
        HourViewHolder viewHolder = new HourViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HourViewHolder holder, int position) {
        holder.bindHour(mHours[position]);
    }

    @Override
    public int getItemCount() {
        return mHours.length;
    }

    public class HourViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.timeLabel) TextView mTimeLabel;
        @BindView(R.id.summaryLabel) TextView mSummaryLabel;
        @BindView(R.id.temperatureLabel) TextView mTemperatureLabel;
        @BindView(R.id.iconImageView) ImageView mIconImageView;

        //public TextView mTimeLabel;
        //public TextView mSummaryLabel;
        //public TextView mTemperatureLabel;
        //public ImageView mIconImageView;

        public HourViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            //mTimeLabel = (TextView) itemView.findViewById(R.id.timeLabel);
            //mSummaryLabel = (TextView) itemView.findViewById(R.id.summaryLabel);
            //mTemperatureLabel = (TextView) itemView.findViewById(R.id.temperatureLabel);
            //mIconImageView = (ImageView) itemView.findViewById(R.id.iconImageView);
        }

        public void bindHour(Hour hour){
            mTimeLabel.setText(hour.getHour());
            mSummaryLabel.setText(hour.getSummary());
            mTemperatureLabel.setText(hour.getTemperature() + "");
            mIconImageView.setImageResource(hour.getIconId());

        }
    }
}
