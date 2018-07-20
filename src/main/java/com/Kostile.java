package com;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.function.UnaryOperator;

public class Kostile extends Elements {
    public String text(){
        return "";
    }

    @Override
    public void replaceAll(UnaryOperator<Element> operator) {
        return;
    }
}
