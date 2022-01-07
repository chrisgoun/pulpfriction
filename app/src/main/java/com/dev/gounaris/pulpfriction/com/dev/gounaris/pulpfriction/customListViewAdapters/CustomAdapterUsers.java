package com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.customListViewAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.dev.gounaris.pulpfriction.R;
import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.objects.JobAd;
import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.objects.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.dev.gounaris.pulpfriction.R.layout.list_users_layout;

public class CustomAdapterUsers extends ArrayAdapter<User> {

    public CustomAdapterUsers(@NonNull Context context, ArrayList<User> users) {
        super(context, list_users_layout,users);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(list_users_layout,parent,false);
        User user = getItem(position);

        //Views
        ImageView profilePic = customView.findViewById(R.id.ProfilePhoto);
        TextView userName = customView.findViewById(R.id.name);
        TextView userAge = customView.findViewById(R.id.age);
        ViewFlipper vFRating = customView.findViewById(R.id.vFRating);
        RatingBar rating = customView.findViewById(R.id.ratingBar);
        TextView ratingsNum = customView.findViewById(R.id.ratings);

        //Variables
        String profilePicUrl = user.getProfilePicUrl();
        String userNameTx = user.getName() + " " + user.getLastName();
        Calendar cl = Calendar.getInstance();
        Calendar c = Calendar.getInstance();
        cl.set(Integer.valueOf(user.getbYear().toString()), Integer.valueOf(user.getbMonth().toString()), Integer.valueOf(user.getbDay().toString()));
        Integer age = c.get(Calendar.YEAR) - cl.get(Calendar.YEAR);
        if (cl.get(Calendar.DAY_OF_YEAR)<c.get(Calendar.DAY_OF_YEAR)){
            age--;
        }
        String userAgeTx = age.toString();
        Double db = ((user.getrKnowledge() + user.getrBehavior() + user.getrAppearance() + user.getrConsistency())/4.0);
        Float ratingNm = db.floatValue();
        String ratingsNumTx ="(" + user.getRatingsNum().toString() + ")";

        //Loading
        Picasso.with(getContext()).load(profilePicUrl).placeholder(R.mipmap.ic_launcher_round).fit().centerCrop().into(profilePic);
        userName.setText(userNameTx);
        userAge.setText(userAgeTx);
        if (user.getRatingsNum()>0) {
            vFRating.setDisplayedChild(0);
        }else {
            vFRating.setDisplayedChild(1);
        }
        rating.setRating(ratingNm);
        ratingsNum.setText(ratingsNumTx);

        return customView;
    }
}
