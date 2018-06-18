package gs.maps.nestedscroll;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.sma.mobile.R;

public class BottomSheetFragment extends Fragment implements View.OnLayoutChangeListener {

    private BottomSheetBehavior<?> behavior;
    private JBottomSheetCallback jBottomSheetCallback;


    public JBottomSheetCallback getJBottomSheetCallback() {
        return jBottomSheetCallback;
    }

    public void setJBottomSheetCallback(JBottomSheetCallback jBottomSheetCallback) {
        this.jBottomSheetCallback = jBottomSheetCallback;
    }

    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet_content, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.addOnLayoutChangeListener(this);
    }

    /**
     * @param peekHeight
     */
    public final void setPeekHeight(int peekHeight) {
        behavior.setPeekHeight(256);
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    @Override
    public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        view.removeOnLayoutChangeListener(this);

//        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) content.getLayoutParams();
//        final int peekHeight = content.getHeight() + params.topMargin + params.bottomMargin;

        behavior = BottomSheetBehavior.from(view);
        behavior.setPeekHeight(256);
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {

            {
                onSlide(null, 0);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                getJBottomSheetCallback().onSlide(bottomSheet, slideOffset);
//                float inverseOffset = 1 - offset;
//                int imageBottom = image.getBottom();
//
//                image.setAlpha(2.5f * offset - 1.5f);
//                image.setTranslationY(-imageBottom * inverseOffset);
//                button.setTranslationY((peekHeight / 2 - imageBottom) * inverseOffset);
//                content.setTranslationY(image.getTranslationY());
            }

            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
            }

        });
    }

}
