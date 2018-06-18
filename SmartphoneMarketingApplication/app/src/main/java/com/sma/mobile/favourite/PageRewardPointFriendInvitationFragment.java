package com.sma.mobile.favourite;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sma.mobile.R;

/**
 * Created by longtran on 03/10/2017.
 */

public class PageRewardPointFriendInvitationFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TEXT = "PARAM";

    @Nullable
    private String pageText;

    public PageRewardPointFriendInvitationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param pageText Parameter 1.
     * @return A new instance of fragment PageFragment.
     */
    public static PageRewardPointFriendInvitationFragment newInstance(@NonNull final String pageText) {
        PageRewardPointFriendInvitationFragment fragment = new PageRewardPointFriendInvitationFragment();

        Bundle args = new Bundle();
        args.putString(ARG_TEXT, pageText);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pageText = getArguments().getString(ARG_TEXT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_page_rewards_points, container, false);
        AppCompatTextView appCompatTextViewRewardPointSubject = (AppCompatTextView) view.findViewById(R.id.app_compat_text_view_reward_point_subject_id);
        AppCompatTextView appCompatTextViewRewardPoint = (AppCompatTextView) view.findViewById(R.id.app_compat_text_view_reward_point_id);
        AppCompatImageView appCompatImageView = (AppCompatImageView)view.findViewById(R.id.app_compat_image_view_reward_point_id);
        appCompatTextViewRewardPointSubject.setText("Điểm mời bạn bè");
        appCompatTextViewRewardPoint.setText("0 điểm");
        appCompatImageView.setImageResource(R.drawable.invite_friend_point_user);
        return view;
    }
}
