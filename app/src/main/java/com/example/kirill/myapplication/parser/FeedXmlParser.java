package com.example.kirill.myapplication.parser;

import android.util.Xml;

import com.example.kirill.myapplication.vo.FeedVO;
import com.example.kirill.myapplication.vo.MediaContentVO;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FeedXmlParser {

    // We don't use namespaces
    private static final String ns = null;

    public List<FeedVO> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private List<FeedVO> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<FeedVO> entries = new ArrayList<>();
        parser.require(XmlPullParser.START_TAG, ns, "rss");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("channel")) {
                entries.addAll(readChannel(parser));
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    private List<FeedVO> readChannel(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<FeedVO> entries = new ArrayList<>();
        parser.require(XmlPullParser.START_TAG, ns, "channel");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if ("item".equals(name)) {
                entries.add(readEntry(parser));
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    private FeedVO readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "item");
        String title = null;
        String description = null;
        MediaContentVO mediaContent = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if ("title".equals(name)) {
                title = readTitle(parser);
            } else if ("itunes:summary".equals(name)) {
                description = readDescription(parser);
            } else if ("media:content".equals(name)) {
                mediaContent = readMediaContent(parser);
            } else {
                skip(parser);
            }
        }
        return new FeedVO(title, description, mediaContent);
    }

    private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "title");
        return title;
    }

    private String readDescription(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "itunes:summary");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "itunes:summary");
        return title;
    }

    private MediaContentVO readMediaContent(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "media:content");
        String url = parser.getAttributeValue(ns, "url");
        int fileSize = Integer.valueOf(parser.getAttributeValue(ns, "fileSize"));
        String type = parser.getAttributeValue(ns, "type");
        parser.nextTag();
        return new MediaContentVO(url, fileSize, type);
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

}
