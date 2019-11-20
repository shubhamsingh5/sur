package com.example.sur;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLPullParserHandler {
    private List<Note> notes = new ArrayList<>();
    private Note note;
    private String text;

    public List<Note> getNotes() {
        return notes;
    }

    public List<Note> parse(InputStream is) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();

            parser.setInput(is, null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("note")) {
                            // create a new instance of employee
                            note = new Note();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("note")) {
                            // add employee object to list
                            notes.add(note);
                        } else if (tagname.equalsIgnoreCase("step")) {
                            note.setStep(text);
                        } else if (tagname.equalsIgnoreCase("octave")) {
                            note.setOctave(text);
                        } else if (tagname.equalsIgnoreCase("alter")) {
                            note.setAlter(text);
                        } else if (tagname.equalsIgnoreCase("accidental")) {
                            note.setAlter(text);
                        }
                        break;

                    default:
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return notes;
    }
}