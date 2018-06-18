package gs.maps.nestedscroll;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by longtran on 08/04/2018.
 */

public interface JBottomSheetCallback {

    /**
     * Called when the bottom sheet is being dragged.
     *
     * @param bottomSheet The bottom sheet view.
     * @param slideOffset The new offset of this bottom sheet within [-1,1] range. Offset
     *                    increases as this bottom sheet is moving upward. From 0 to 1 the sheet
     *                    is between collapsed and expanded states and from -1 to 0 it is
     *                    between hidden and collapsed states.
     */
    public void onSlide(@NonNull View bottomSheet, float slideOffset);
}
