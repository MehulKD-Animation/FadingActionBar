package com.manuelpeinado.fadingactionbar;

import android.content.res.TypedArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.app.Activity;
public class BeautifulFadingActionBarHelper extends FadingActionBarHelper{
    private static final String TAG_ACTIONBAR_SHADOW = "TAG_ACTIONBAR_SHADOW";
	private int mActionBarShadowId = -1;
    private boolean mIsShowActionBarShadow = true;
	private Activity mActivity;
	public final <T extends BeautifulFadingActionBarHelper> T actionBarShadow(int drawResId){
		mActionBarShadowId = drawResId;
		return (T)this;
	}

    @Override
    public void initActionBar(Activity activity) {
        super.initActionBar(activity);
        if(mActionBarShadowId > 0){
            addActionBarShadow(activity, mActionBarShadowId);
        }
    }

    /**
     * switch action bar shandow
     * @param state true if what show, false if hide
     */
    public void switchActionBarShadow(boolean state){
        mIsShowActionBarShadow = state;
        if(isActionBarNull()){
            return;
        }
        View shadowView = findShadow();
        if (state) {
            shadowView.setVisibility(View.VISIBLE);
        } else {
            shadowView.setVisibility(View.INVISIBLE);
        }
    }

    public boolean isShowActionBarShadow(){
        return mIsShowActionBarShadow;
    }


    /**
     * add action bar shadow,
     * find FrameLayout of actionbar layout,add view to the FrameLayout and set imageRes
     *
     * @param activity
     * @param res
     */
    private void addActionBarShadow(Activity activity,int res){
        mActivity = activity;
        int id = activity.getResources().getIdentifier("content", "id", "android");
        View view = activity.findViewById(id);
        if (view instanceof FrameLayout) {
            View shadowView = view.findViewWithTag(TAG_ACTIONBAR_SHADOW);
            if(shadowView == null){
                shadowView = new ImageView(activity);
                shadowView.setTag(TAG_ACTIONBAR_SHADOW);
                shadowView.setBackgroundResource(res);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);

                TypedArray styledAttributes = activity.getTheme().obtainStyledAttributes(
                        new int[] { android.R.attr.actionBarSize });
                layoutParams.topMargin = (int) styledAttributes.getDimension(0, 0);
                ((FrameLayout) view).addView(shadowView, layoutParams);
            }
        }
    }

    /**
     * find actionbar shadow by Tag
     * @return shadow view, null if not contain
     */
    private View findShadow(){
        int id = mActivity.getResources().getIdentifier("content", "id", "android");
        View view = mActivity.findViewById(id);
        if (view instanceof FrameLayout) {
            return view.findViewWithTag(TAG_ACTIONBAR_SHADOW);
        }
        return null;
    }

    @Override
    protected void onNewScroll(int scrollPosition) {
        super.onNewScroll(scrollPosition);
        if(isActionBarNull()){
            return;
        }
        int newAlpha = getAlpha(scrollPosition);
        View view = findShadow();
        view.setAlpha(((float)newAlpha)/255.0f);
    }
}
