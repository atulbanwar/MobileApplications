package com.assgn.mad.inclassassgn07;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by atulb on 10/3/2016.
 */
public class PodcastUtil {
    static public class PodcastPullParser {
        static public ArrayList<Podcast> parsePodcastItems(InputStream inputStream) throws XmlPullParserException, IOException, ParseException {
            XmlPullParser xmlPullParser = XmlPullParserFactory.newInstance().newPullParser();
            Podcast podcast = null;
            ArrayList<Podcast> podcasts = new ArrayList<Podcast>();

            xmlPullParser.setInput(inputStream, "UTF-8");

            int event = xmlPullParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_TAG:

                        if (xmlPullParser.getName().equals("entry")) {
                            podcast = new Podcast();
                        } else if (podcast != null) {
                            if (xmlPullParser.getName().equals("title")) {
                                podcast.setTitle(xmlPullParser.nextText().trim());
                            } else if (xmlPullParser.getName().equals("summary")) {
                                podcast.setSummary(xmlPullParser.nextText().trim());
                            } else if (xmlPullParser.getName().equals("im:releaseDate")) {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss-HH:mm");
                                Date date = dateFormat.parse(xmlPullParser.nextText().trim());
                                podcast.setReleaseDate(date);
                            } else if (xmlPullParser.getName().equals("im:image") && xmlPullParser.getAttributeValue(null, "height").equals("55")) {
                                podcast.setSmallImageUrl(xmlPullParser.nextText().trim());
                            } else if (xmlPullParser.getName().equals("im:image") && xmlPullParser.getAttributeValue(null, "height").equals("170")) {
                                podcast.setLargeImageUrl(xmlPullParser.nextText().trim());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (xmlPullParser.getName().equals("entry")) {
                            podcasts.add(podcast);
                            podcast = null;
                        }
                        break;
                    default:
                        break;
                }

                event = xmlPullParser.next();
            }

            return podcasts;
        }
    }
}
