package com.a75f;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by apple on 21/03/17.
 */

public class CustomView extends View{

    int setVal = 75;
    int coverAngle = 40;
    float initX = 0;
    int color = Color.rgb(0,0,255);
    Point center;


    public CustomView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        center = new Point(canvas.getWidth()/2 , canvas.getHeight());
        drawWheel(canvas);
        drawOuterRim(canvas);
        drawVal(canvas);


    }

    private void drawZebras(Canvas canvas){
        int inner_radius = canvas.getWidth()/2 + 75;
        int outer_radius = canvas.getWidth()/2 + 150;
        int arc_sweep = -180;
        int arc_ofset = 0;

        RectF outer_rect = new RectF(center.x-outer_radius, center.y-outer_radius, center.x+outer_radius, center.y+outer_radius);
        RectF inner_rect = new RectF(center.x-inner_radius, center.y-inner_radius, center.x+inner_radius, center.y+inner_radius);

        Path path = new Path();
        path.arcTo(outer_rect, arc_ofset, arc_sweep);
        path.arcTo(inner_rect, arc_ofset + arc_sweep, -arc_sweep);
        Paint fill = new Paint();
        fill.setColor(Color.TRANSPARENT);
        canvas.drawPath(path, fill);

        Paint fill1 = new Paint();
        fill1.setColor(Color.WHITE);
        for(int  i = 0; i < 20; i++){
            canvas.drawLine(canvas.getWidth()/2 + (inner_radius * (float)Math.cos(Math.toRadians(90f + (i * 18f) ))),
                    canvas.getHeight()  - (inner_radius * (float)Math.sin(Math.toRadians(90f + (i * 18f) ))) ,
                    canvas.getWidth()/2 + (outer_radius * (float)Math.cos(Math.toRadians(90f + (i * 18f) ))),
                    canvas.getHeight() - (outer_radius * (float)Math.sin(Math.toRadians(90f + (i * 18f) ))),
                    fill1);
        }


        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1);
        paint.setColor(Color.BLUE);
        paint.setTextSize(100);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("72", getWidth()/2, getHeight()/4 - 100, paint);

    }
    private void drawWheel(Canvas canvas){
        int outer_radius = canvas.getWidth()/2 + 170;
        int arc_sweep = -180;
        int arc_ofset = 0;

        RectF outer_rect = new RectF(center.x-outer_radius, center.y-outer_radius, center.x+outer_radius, center.y+outer_radius);

        Path path = new Path();
        path.arcTo(outer_rect, arc_ofset, arc_sweep);

        Paint fill = new Paint();
        fill.setColor(color);
        canvas.drawPath(path, fill);
    }
    private void drawOuterRim(Canvas canvas){
        int inner_radius = canvas.getWidth()/2 + 170;
        int outer_radius = canvas.getWidth()/2 + 190;

        RectF outer_rect = new RectF(center.x-outer_radius, center.y-outer_radius, center.x+outer_radius, center.y+outer_radius);
        Path path = new Path();

        Paint fill = new Paint();

        path.arcTo(outer_rect, -180, coverAngle);

        fill.setColor(color);
        fill.setStyle(Paint.Style.FILL);
        fill.setStrokeWidth(2f);
        canvas.drawPath(path, fill);

        drawZebras(canvas);

        path.arcTo(outer_rect, -180, 180);

        fill.setColor(color);
        fill.setStyle(Paint.Style.STROKE);
        fill.setStrokeWidth(2f);
        canvas.drawPath(path, fill);

    }
    private void drawVal(Canvas canvas){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1);
        paint.setColor(Color.GRAY);
        paint.setTextSize(300);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(setVal + "", getWidth()/2, getHeight()/2 - 100, paint);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_UP) {
            invalidate();
            return true;
        }
        float h2 = getHeight();

        if(action == MotionEvent.ACTION_DOWN){

            float nextX = event.getX();

            if(event.getY() <= h2 - 200){
                float val = event.getX();
                if(setVal >= 50 && setVal <= 100){
                    setVal = 50  + (int)(val * 50 / (getWidth() - 25));

                    coverAngle = 40 + (2 * (setVal - 50));

                }
            }

            if(setVal <= 75)
                color = Color.rgb(0, 0, 255 - (setVal - 50));
            else
                color = Color.rgb(255- ((100 - setVal)), 0, 0);
            initX = nextX;
            invalidate();
        }

        /**
         * Stopping swiping temperature control
         */

        if(action == MotionEvent.ACTION_MOVE){
            float nextX = event.getX();

            if(nextX < initX && event.getY() <= h2 - 200){
                if(setVal > 50) {
                    setVal--;
                }

            }
            else if(nextX > initX && event.getY() <= h2 - 200){
                if(setVal < 100) {
                    setVal++;
                }

            }

            coverAngle = 40 + (2 * (setVal - 50));
            if(setVal <= 75)
                color = Color.rgb(0, 0, 255 - ((setVal - 50) * 5));
            else
                color = Color.rgb(255- ((100 - setVal) * 5), 0, 0);
            initX = nextX;
            invalidate();
        }
        return true;
    }


}