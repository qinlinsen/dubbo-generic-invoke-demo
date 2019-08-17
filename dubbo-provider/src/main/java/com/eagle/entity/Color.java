package com.eagle.entity;

import org.checkerframework.checker.units.qual.A;

import java.io.Serializable;

/**
 * @author qinlinsen
 */
public class Color implements Serializable {
    private String red;

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    private World world;


    public String getRed() {
        return red;
    }

    public void setRed(String red) {
        this.red = red;
    }
}
