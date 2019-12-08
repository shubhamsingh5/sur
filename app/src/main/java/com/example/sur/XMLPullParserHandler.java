package com.example.sur;

import com.example.sur.model.Note;
import com.example.sur.model.Page;
import com.example.sur.model.Score;

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
    private Score score;
    private Page page;
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
            score = new Score();
            page = new Page();
            score.addPage(page);
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("note")) {
                            note = new Note();
                            page.addNote(note);
                        }
                        if (tagname.equalsIgnoreCase("print")) {
                            // check if printing new page
                            String isPage = parser.getAttributeValue(null, "new-page");
                            if (isPage != null && isPage.equals("yes")) {
                                page = new Page();
                                score.addPage(page);
                            }
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("note")) {
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