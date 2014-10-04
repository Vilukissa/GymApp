package com.calicode.gymapp.app.navigation;

import com.calicode.gymapp.app.navigation.NavigationLocation.NavigationFlags;
import com.calicode.gymapp.app.util.Log;
import com.calicode.gymapp.app.util.componentprovider.componentinterfaces.SessionComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Navigator implements SessionComponent {

    public interface OnNavigateListener {
        void onNavigate(NavigationLocation location);
    }

    private NavigationLocation mLocation;
    private Map<Class<? extends OnNavigateListener>, OnNavigateListener> mListeners =
            new HashMap<Class<? extends OnNavigateListener>, OnNavigateListener>();
    private List<NavigationLocation> mLocationStack = new ArrayList<NavigationLocation>();

    public Navigator() {
        mLocation = NavigationLocation.MAIN;
        mLocationStack.add(mLocation);
    }

    public void addListener(OnNavigateListener listener) {
        Log.debug("Adding navigation listener: " + listener.getClass().getSimpleName());
        mListeners.put(listener.getClass(), listener);
        listener.onNavigate(mLocation);
    }

    public void removeListener(OnNavigateListener listener) {
        Log.debug("Removing navigation listener: " + listener.getClass().getSimpleName());
        mListeners.remove(listener.getClass());
    }

    public NavigationLocation getLocation() {
        return mLocation;
    }

    private boolean isStackEmpty() {
        return mLocationStack.size() == 1;
    }

    public boolean onBackPress() {
        if (!isStackEmpty()) {
            Log.debug("Removing last location item (" + mLocation.name() + ") from the stack");
            mLocationStack.remove(mLocationStack.size() - 1);
            mLocation = mLocationStack.get(mLocationStack.size() - 1);

            while (mLocation.getNavigationFlags().contains(NavigationFlags.DONT_ADD_TO_STACK)) {
                Log.debug("Removing DONT_ADD_TO_STACK location item from the stack");
                // Current location is marked not to be handled as it belongs to stack
                mLocationStack.remove(mLocationStack.size() - 1);
                mLocation = mLocationStack.get(mLocationStack.size() - 1);
            }

            notifyLocationChange();
            return true;
        }
        return false;
    }

    public void navigateBack() {
        onBackPress();
    }

    public void navigate(NavigationLocation location) {
        if (mLocation != location) {
            Log.debug("Navigating to " + location.getFragmentClass().getSimpleName());

            mLocation = location;
            mLocationStack.add(mLocation);
            notifyLocationChange();
        }
    }

    private void notifyLocationChange() {
        for (Map.Entry<Class<? extends OnNavigateListener>, OnNavigateListener> entry
                : mListeners.entrySet()) {
            entry.getValue().onNavigate(mLocation);
        }
    }

    @Override
    public void destroy() {
        mListeners.clear();
        mLocation = NavigationLocation.MAIN;
    }
}
