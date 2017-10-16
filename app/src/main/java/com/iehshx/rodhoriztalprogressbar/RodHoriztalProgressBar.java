package com.iehshx.rodhoriztalprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

/**
 * Created by lidongdong on 2017/4/7.
 */
public class RodHoriztalProgressBar extends ProgressBar {

    private static final int DEFAULT_REACH_COLOR = 0xff24F569;
    private static final int DEFAULT_UNREACH_COLOR = 0xffC0C0C0;
    private static final int DEFAULT_REACH_HEIGHT = 2;
    private static final int DEFAULT_UNREACH_HEIGHT = 2;
    private static final int DEFAULT_TEXT_COLOR = DEFAULT_REACH_COLOR;
    private static final int DEFAULT_TEXT_SIZE = 12;
    private static final int DEFAULT_TEXT_OFFSET = 5;
    private final Drawable mProgress;//当前进度提示文本框
    private final Drawable mKedu;//背景刻度
    private final int mkeduWidget;
    private int mkeduHeight;
    private final int mTextOffsetLine;
    private int mProgressWidght;
    private int mProgressHeight;

    protected int mReachColor = DEFAULT_REACH_COLOR;
    protected int mUnReachColor = DEFAULT_UNREACH_COLOR;
    protected int mReachHeight = dp2px(DEFAULT_REACH_HEIGHT);
    protected int mUnReachHeight = dp2px(DEFAULT_UNREACH_HEIGHT);
    protected int mTextColor = DEFAULT_TEXT_COLOR;
    protected int mTextSize = sp2px(DEFAULT_TEXT_SIZE);
    protected int mTextOffset = dp2px(DEFAULT_TEXT_OFFSET);

    protected Paint mPaint = new Paint();

    public RodHoriztalProgressBar(Context context) {
        this(context, null);
    }

    public RodHoriztalProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RodHoriztalProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.RodHoriztalProgressBar);
        mReachColor = ta.getColor(R.styleable.RodHoriztalProgressBar_progressbar_reach_color, mReachColor);
        mUnReachColor = ta.getColor(R.styleable.RodHoriztalProgressBar_progressbar_unreach_color, mUnReachColor);
        mReachHeight = (int) ta.getDimension(R.styleable.RodHoriztalProgressBar_progressbar_reach_height, mReachHeight);
        mUnReachHeight = (int) ta.getDimension(R.styleable.RodHoriztalProgressBar_progressbar_unreach_height, mUnReachHeight);
        mTextColor = ta.getColor(R.styleable.RodHoriztalProgressBar_progressbar_text_color, mTextColor);
        mTextSize = (int) ta.getDimension(R.styleable.RodHoriztalProgressBar_progressbar_text_size, mTextSize);
        mTextOffset = (int) ta.getDimension(R.styleable.RodHoriztalProgressBar_progressbar_text_offset, mTextOffset);
        mTextOffsetLine = (int) ta.getDimension(R.styleable.RodHoriztalProgressBar_progressbar_text_offset, mTextOffset);
        mKedu = ta.getDrawable(R.styleable.RodHoriztalProgressBar_progressbar_rod_bg);
        mProgress = ta.getDrawable(R.styleable.RodHoriztalProgressBar_progressbar_text_bg);
        ta.recycle();
        if (mProgress != null) {
            mProgressWidght = mProgress.getIntrinsicWidth();
            mProgressHeight = mProgress.getIntrinsicHeight();
        }
        if (mKedu != null)
            mkeduHeight = mKedu.getIntrinsicHeight();
        mkeduWidget = mKedu.getIntrinsicWidth();
        mPaint.setTextSize(mTextSize);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightVal = mProgressHeight + mTextOffsetLine + mReachHeight + mkeduHeight;
        setMeasuredDimension(mkeduWidget, heightVal);
    }

    public int getTextWidth(String content) {
        int width = 0;
        if (content != null && content.length() > 0) {
            int length = content.length();
            float[] widths = new float[length];
            mPaint.getTextWidths(content, widths);
            for (int i = 0; i < length; i++) {
                width += (int) Math.ceil(widths[i]);
            }
        }
        return width;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        String text = getProgress() + "%";
        boolean noNeedUnrechBar = false;
        float radio = getProgress() * 1.0f / getMax();
        float progressX = mkeduWidget * radio;
        if (progressX > mkeduWidget) {
            progressX = mkeduWidget;
            noNeedUnrechBar = true;
        }
        //画文本框
        if (mProgress != null) {
            //计算文本框应该在的X坐标
            float bgStartX = (int) progressX - (mProgressWidght / 2);
            float bgEndX = progressX + (mProgressWidght / 2);
            if (bgStartX>=mkeduWidget-mProgressWidght){
                bgStartX = mkeduWidget - mProgressWidght;
            }
           //文本框的坐标校验
            if (bgEndX>=mkeduWidget){
                bgEndX = mkeduWidget;
            }
            if (bgEndX<mProgressWidght){
                bgEndX = mProgressWidght;
            }
            if (bgStartX<=0){
                bgStartX = 0;
            }
            mProgress.setBounds((int) bgStartX, 0, (int) bgEndX, mProgressHeight);
            mProgress.draw(canvas);
            //计算文本的X坐标和Y坐标
            int  textWH = getTextWidth(text)/2;//文本的一半长度
            int  progressBgWH = mProgressWidght /2; //进度文本框的一半
            float progressTxtStartX = bgStartX + ( progressBgWH - textWH);
            float progressTxtStartY = mProgressHeight / 2 + 5;
            if (progressTxtStartX >= mkeduWidget - progressBgWH){
                progressTxtStartX = mkeduWidget - progressBgWH- textWH;
            }
            if (progressTxtStartX <= (progressBgWH - textWH)){
                progressTxtStartX =  progressBgWH - textWH;
            }

            mPaint.setColor(mTextColor);
            canvas.drawText(text, progressTxtStartX, progressTxtStartY, mPaint);
        }

        //画布移动到进度条
        canvas.save();
        canvas.translate(getPaddingLeft(), mProgressHeight + mTextOffsetLine);

        //draw 进度条
        mPaint.setColor(mReachColor);
        mPaint.setStrokeWidth(mReachHeight);
        canvas.drawLine(0, 0 + mReachHeight, progressX, 0 + mReachHeight, mPaint);
        //draw text
        //画刻度
        if (mKedu != null) {
            mKedu.setBounds(0, 0, mkeduWidget, mkeduHeight);
            mKedu.draw(canvas);
        }
        if (!noNeedUnrechBar) {
            mPaint.setColor(mUnReachColor);
            mPaint.setStrokeWidth(mUnReachHeight);
            float startX = progressX;
            canvas.drawLine(startX, mProgressHeight, mkeduWidget, mProgressHeight, mPaint);
        }
        canvas.restore();
    }

    protected int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    protected int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }
}
