package interfaces.servidor;

import java.applet.Applet;
import java.applet.AudioClip;


public class SoundServer {
   private AudioClip clip;
   public SoundServer(String name){
      try
      {
        clip = Applet.newAudioClip(Principal.class.getResource(name));
      }catch (Throwable e){
         e.printStackTrace();
      }
   }
   public void play(){
      try{
         new Thread(){
            public void run(){
               clip.play();
            }
         }.start();
      }catch(Throwable e){
         e.printStackTrace();
      }
   }
}

