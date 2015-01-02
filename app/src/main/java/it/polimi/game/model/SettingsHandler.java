package it.polimi.game.model;

import android.util.Log;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.ToggleButton;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class SettingsHandler {
    private static SettingsHandler instance = null;

    public static SettingsHandler getInstance() {
        if(instance == null) {
            instance = new SettingsHandler();
        }
        return instance;
    }

    public void statisticInitialization(){
        try {
            File fXmlFile =new File(Game.getInstance().getGameActivity().getFilesDir(), "/setting.xml");
            if(fXmlFile.exists()){
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(fXmlFile);
                doc.getDocumentElement().normalize();
                Node node = doc.getFirstChild();
                        Element eElement = (Element) node;
                        String ss=eElement.getElementsByTagName("sound").item(0).getTextContent();
                        String ms=eElement.getElementsByTagName("music").item(0).getTextContent();
                        String as= eElement.getElementsByTagName("animations").item(0).getTextContent();
                        String ns=eElement.getElementsByTagName("nseeds").item(0).getTextContent();
                        Boolean sound,music,animations;
                        Integer nseeds=Integer.parseInt(ns);
                        if(ss.equals("true")){sound=true;}else{sound=false;}
                        if(ms.equals("true")){music=true;}else{music=false;}
                        if(as.equals("true")){animations=true;}else{animations=false;}
                        Game.getInstance().saveStatistic(music,sound,animations,nseeds);
                }

            else{
                Log.v("sett","Configuration file doesn't exist");
                this.createConfigFile();
            }
        } catch (IOException e) {
            Log.v("s","no file detected");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

    }

    protected void createConfigFile(){
        saveStatistic(true,true,true,1);
    }

    public void saveStatistic(Boolean music,Boolean sound,Boolean animations,Integer nseeds){
        Game.getInstance().saveStatistic(music,sound,animations,nseeds);
        try {
            File file = new File(Game.getInstance().getGameActivity().getFilesDir(), "/setting.xml");
            FileWriter fw = new FileWriter(file);
            fw.write("<settings><music>"+music.toString()+"</music><sound>"+sound.toString()+"</sound><animations>"+animations.toString()+"</animations><nseeds>"+nseeds.toString()+"</nseeds></settings>");
            fw.flush();
            fw.close();
            this.statisticInitialization();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    protected SettingsHandler(){

    }
}
