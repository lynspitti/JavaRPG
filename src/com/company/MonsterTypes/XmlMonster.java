package com.company.MonsterTypes;

import com.company.*;

import javax.swing.filechooser.FileSystemView;

import com.company.Console;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DBJ on 20-02-2015.
 */
public class XmlMonster extends Monster {

    private Element MonsterXml = null;
    private String MonsterName = "Monster";

    private void GetMonsterXml(){
        if (MonsterXml == null) {
            File f = new File("Monster.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            if(f.exists() && !f.isDirectory()) {
                try {
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    Document document = builder.parse(f);
                    document.getDocumentElement().normalize();

                    Element root = document.getDocumentElement();
                    MonsterXml = (Element)root.getChildNodes().item(Console.RandomInt(0, root.getChildNodes().getLength()));
                    MonsterName = MonsterXml.getAttribute("name");
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    DocumentBuilder docBuilder = factory.newDocumentBuilder();

                    // Root elementet
                    Document doc = docBuilder.newDocument();
                    Element rootElement = doc.createElement("MonsterRoot");
                    doc.appendChild(rootElement);

                    // Monster elemented
                    MonsterXml  = doc.createElement("Monster");
                    rootElement.appendChild(MonsterXml);

                    // set name attributen på monster elementet
                    MonsterXml.setAttribute("name", "SomeThing");
                    MonsterName = "SomeThing";

                    // Attacks element
                    Element attackelement = doc.createElement("Attacks");

                    Element Meleeelement = doc.createElement("Melee");
                    Element commentelement = doc.createElement("Comment");
                    commentelement.setTextContent("Hit with fist");
                    Meleeelement.appendChild(commentelement);
                    attackelement.appendChild(Meleeelement);

                    MonsterXml.appendChild(attackelement);

                    // Skriv indholdet til en xml fil
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource source = new DOMSource(doc);
                    StreamResult result = new StreamResult(new File("Monster.xml"));

                    // Brug følgende for at udskrive til konsollen
                    // StreamResult result = new StreamResult(System.out);

                    transformer.transform(source, result);
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (TransformerConfigurationException e) {
                    e.printStackTrace();
                } catch (TransformerException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Element GetNodeByName(Element parrent, String name){
        NodeList GetNode = parrent.getElementsByTagName(name);
        for( int i = 0; i < GetNode.getLength(); i++ ) {
            return (Element) GetNode.item( i );
        }
        return null;
    }

    private String GetNodeText(Element getNode, String defaultText){
        try{
            if (getNode!= null){
                if (getNode.getChildNodes().getLength() > 0){
                    if (getNode.getElementsByTagName("Comment") != null){
                        int length = getNode.getChildNodes().getLength();
                        int failedComment = 0;
                        if (length > 1)Console.RandomInt(0, length);
                        if (getNode.getChildNodes().item(failedComment).getNodeName() == "Comment"){
                            String getNodeText = getNode.getChildNodes().item(failedComment).getTextContent();
                            if (!getNodeText.isEmpty()){
                                return getNodeText;
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e){}
        return defaultText;
    }

    @Override
    public void failAttack() {
        GetMonsterXml();

        Element Failed = GetNodeByName(MonsterXml,"Failed");

        Console.Msg(GetNodeText(Failed,MonsterName + " Missed it's attack"), true,true);
    }

    @Override
    public int Attack() {
        GetMonsterXml();
        int Damage = 0;
        ArrayList<String> Attacks = new ArrayList<String>();

        Attacks.add("Failed");

        Element MeleeXml = GetNodeByName(MonsterXml,"Melee");
        if (MeleeXml!= null) {
            Attacks.add(GetNodeText(MeleeXml, MonsterName + " used Melee."));
        }
        Element MagicXml = GetNodeByName(MonsterXml,"Magic");
        if (MagicXml!= null) {
            Attacks.add(GetNodeText(MagicXml, MonsterName + " used Magic."));
        }
        Element RangedXml = GetNodeByName(MonsterXml,"Ranged");
        if (RangedXml!= null) {
            Attacks.add(GetNodeText(RangedXml, MonsterName + " used Ranged."));
        }


        int ran = Console.RandomInt(0, Attacks.size());
        /**
         * the random is noticeably less likely to pick the start and the ending value, so those result in a failAttack()
         */
        if (ran == 0){
            failAttack();
        }
        else {
            int attackcount = ran;
            if (MeleeXml!= null && attackcount == 1)Damage = MeleeAtt();
            else attackcount--;
            if (MagicXml!= null && attackcount == 1)Damage = MagicAtt();
            else if (RangedXml != null) Damage = RangedAtt();
            Damage = randomDamage(Damage);
            Console.Msg(Attacks.get(ran) + "\nand dealt " + Damage + " Damage", false,true);
        }
        return Damage;
    }
}
