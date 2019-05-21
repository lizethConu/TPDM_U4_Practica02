package com.example.tpdm_u4_practica2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Mosca {
    Bitmap imagen;
    int x,y,desplazamientoX,desplazamientoY,viva;

    public Mosca(Lienzo lienzo,int imagen, int posx, int posy,int tam) {
        this.imagen = BitmapFactory.decodeResource(lienzo.getResources(),imagen);
        if(tam==1) {
            this.imagen = Bitmap.createScaledBitmap(this.imagen, 120, 120, false);
        }else{
            this.imagen = Bitmap.createScaledBitmap(this.imagen, 500, 500, false);
        }
        x = posx;
        y = posy;
        viva=1;
        if(Math.random()*10<5){
            desplazamientoX=15;
        }else{
            desplazamientoX=-15;
        }
        if(Math.random()*10<5){
            desplazamientoY=15;
        }else{
            desplazamientoY=-15;
        }
    }


    public void dibujar(Canvas c, Paint p) {
        c.drawBitmap(imagen, x, y, p);
    }

    public void mover(int ancho,int alto){
        if(x<=0) desplazamientoX=10;
        if(y<=0) desplazamientoY=15;
        if(x>=ancho) desplazamientoX=(desplazamientoX*-1);
        if(y>=alto) desplazamientoY=(desplazamientoY*-1);
        x+=desplazamientoX;
        y+=desplazamientoY;
    }

    public boolean estaEnArea(int Xdedo, int Ydedo){
        int x2 = x+imagen.getWidth();
        int y2= y+imagen.getHeight();
        if(Xdedo>=x&&Xdedo<=x2){
            if(Ydedo >= y && Ydedo <= y2){
                return true;
            }
        }
        return false;
    }


    public boolean estaEnColision(Mosca objetoB){
        int x2 = x+imagen.getWidth();
        int y2= y+imagen.getHeight();
        if(objetoB.estaEnArea(x,y)){
            return true;
        }if(objetoB.estaEnArea(x2,y)){
            return true;
        }if(objetoB.estaEnArea(x,y2)){
            return true;
        }if(objetoB.estaEnArea(x2,y2)){
            return true;
        }

        return false;
    }


}