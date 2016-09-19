package com.bootsysoftware.weatherapp.adapters;

import android.content.Context;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private Context mContext;

    public HourAdapter(Context context, Hour[] hours){
        mContext = context;
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

    public class HourViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.timeLabel) TextView mTimeLabel;
        @BindView(R.id.summaryLabel) TextView mSummaryLabel;
        @BindView(R.id.temperatureLabel) TextView mTemperatureLabel;
        @BindView(R.id.iconImageView) ImageView mIconImageView;

        public HourViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bindHour(Hour hour){
            mTimeLabel.setText(hour.getHour());
            mSummaryLabel.setText(hour.getSummary());
            mTemperatureLabel.setText(hour.getTemperature() + "");
            mIconImageView.setImageResource(hour.getIconId());

        }

        @Override
        public void onClick(View v) {
            String time = mTimeLabel.getText().toString();
            String temperature = mTemperatureLabel.getText().toString();
            String summary = mSummaryLabel.getText().toString();
            String message = String.format("At %s it will be %s and %s",
                    time, temperature, summary);
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        }
    }
}
