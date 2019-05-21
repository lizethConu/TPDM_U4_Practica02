package com.example.tpdm_u4_practica2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import java.util.Timer;
import java.util.TimerTask;

public class Lienzo extends View {
    Timer timer,timer2;
    Mosca[] moscas=new Mosca[35];
    Mosca jefe;
    int mensajeganar=0;
    int contadorjefe=0;
    int contadormoscas=0;
    int decrometro=60;
    int width= this.getResources().getDisplayMetrics().widthPixels;
    int height= this.getResources().getDisplayMetrics().heightPixels;
    public Lienzo(Context context) {
        super(context);
        iniciar();
        jefe =new Mosca(this,R.drawable.mosca,-500,-500,0);
        timer = new Timer();
        timer2 = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for(int i=0;i<35;i++){
                    if(moscas[i]!=null){
                        moscas[i].mover(width,height);
                    }
                }
                if(jefe!=null){
                    jefe.mover(width,height);
                }
            }
        },0,10);
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                if(decrometro==0){
                    mensajeganar=2;
                }else{
                    decrometro--;
                }
            }
        },0,1000);
    }

    public void iniciar(){
        for(int i=0;i<35;i++){
            moscas[i]=new Mosca(this,R.drawable.mosca,(int) (Math.random()*(width))+5,(int) (Math.random()*(height))+25,1);
        }
    }

    @Override
    protected void onDraw(Canvas c) {
        Paint p = new Paint();
        for(int i=0;i<35;i++){
            if(contadormoscas==30){
                if(jefe!=null){
                    jefe.dibujar(c,p);
                }
            }else if(moscas[i]!=null){
                moscas[i].dibujar(c,p);
            }
        }
        if(mensajeganar==1){
            p.setColor(Color.WHITE);
            c.drawRect(0,0,width,height,p);
            p.setColor(Color.GREEN);
            p.setTextSize(120f);
            c.drawText("GANASTE!!!!", (width/2)-400,height/2,p);
        }else if (mensajeganar==2){
            p.setColor(Color.WHITE);
            c.drawRect(0,0,width,height,p);
            p.setColor(Color.RED);
            p.setTextSize(120f);
            c.drawText("PERDISTE!!!!", (width/2)-400,height/2,p);
        }else {
            p.setColor(Color.BLACK);
            p.setTextSize(60f);
            c.drawText("PUNTUACION: " + contadormoscas, 0, 50, p);
            c.drawText("TIEMPO: " + decrometro, 500, 50, p);
            invalidate();
        }
    }


    public boolean onTouchEvent (MotionEvent me){
        int accion = me.getAction();
        int posx = (int) me.getX();
        int posy = (int) me.getY();
        switch (accion){
            case MotionEvent.ACTION_DOWN:
                if(contadormoscas!=30) {
                    for (int i = 0; i < 35; i++) {
                        if (moscas[i] != null) {
                            if (moscas[i].estaEnArea(posx, posy)) {
                                moscas[i] = null;
                                contadormoscas++;
                                if (contadormoscas == 30) {
                                    for (int j = 0; i < 35; i++) {
                                        moscas[j] = null;
                                    }
                                    jefe.x = width / 2;
                                    jefe.y = height / 2;
                                    break;
                                }
                                break;
                            }
                        }
                    }
                }else if(jefe.estaEnArea(posx,posy)){
                    if(jefe!=null){
                        contadorjefe++;
                        if(contadormoscas==30 && contadorjefe==5){
                            jefe=null;
                            mensajeganar=1;
                        }
                        break;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        invalidate();
        return true;
    }

}