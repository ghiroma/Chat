package interfaces.cliente;

import java.applet.Applet;
import java.applet.AudioClip;

import client.ChatClient;

public class SoundClient {
   private AudioClip clip;
   public SoundClient(String name){
      try
      {
    	  							//ChatClient = main class
        clip = Applet.newAudioClip(ClienteInicial.class.getResource(name));
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
