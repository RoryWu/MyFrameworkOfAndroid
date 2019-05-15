package com.jake.example.jakelearnandroid.view;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

/**
 *
 * @author XINYE
 *
 */
public class MyScrollView extends ScrollView {
    private int subChildCount = 0;
    private ViewGroup firstChild = null;
    private int downY = 0;
    private int currentPage = 0;
    private ArrayList<Integer> pointList = new ArrayList<Integer>();

    public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyScrollView(Context context) {
        super(context);
        init();
    }
    private void init() {
        setHorizontalScrollBarEnabled(false);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        receiveChildInfo();
    }
    public void receiveChildInfo() {

        firstChild = (ViewGroup) getChildAt(0);
        if(firstChild != null){
            subChildCount = firstChild.getChildCount();
            for(int i = 0;i < subChildCount;i++){
                View childView = firstChild.getChildAt(i);
                if(childView.getHeight() > 0){
                    pointList.add(childView.getHeight());
                }
            }
        }

    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:{

            }break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:{
                if( Math.abs((ev.getY() - downY)) > getHeight() / 4){
                    if(ev.getY() - downY > 0){
                        smoothScrollToPrePage();
                    }else{
                        smoothScrollToNextPage();
                    }
                }else{
                    smoothScrollToCurrent();
                }
                return true;
            }
        }
        return super.onTouchEvent(ev);
    }

    private void smoothScrollToCurrent() {
//        smoothScrollTo(pointList.get(currentPage), 0);
        smoothScrollTo(0,pointList.get(currentPage));
    }

    private void smoothScrollToNextPage() {
        if(currentPage < subChildCount - 1){
            currentPage++;
            smoothScrollTo(0,pointList.get(currentPage));
//            smoothScrollTo(pointList.get(currentPage), 0);
        }
    }

    private void smoothScrollToPrePage() {
        if(currentPage > 0){
            currentPage--;
            smoothScrollTo(0,pointList.get(currentPage));
//            smoothScrollTo(pointList.get(currentPage), 0);
        }
    }
    /**
     * 下一页
     */
    public void nextPage(){
        smoothScrollToNextPage();
    }
    /**
     * 上一页
     */
    public void prePage(){
        smoothScrollToPrePage();
    }
    /**
     * 跳转到指定的页面
     * @param page
     * @return
     */
    public boolean gotoPage(int page){
        if(page > 0 && page < subChildCount - 1){
//            smoothScrollTo(pointList.get(page), 0);
            smoothScrollTo(0,pointList.get(currentPage));
            currentPage = page;
            return true;
        }
        return false;
    }
}