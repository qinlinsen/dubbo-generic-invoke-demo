package com.eagle.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.eagle.api.ClothService;
import com.eagle.entity.Color;
import com.eagle.entity.Shape;

/**
 * @author qinlinsen
 */
@Service(timeout = 40000)
public class ClothServiceImpl implements ClothService {
    @Override
    public Shape cloth(Color color) {
        Shape shape = new Shape();
        shape.setShape(color.getRed());
        return shape;
    }
}
