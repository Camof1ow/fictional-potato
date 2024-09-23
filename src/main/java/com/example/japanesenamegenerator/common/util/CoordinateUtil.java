package com.example.japanesenamegenerator.common.util;

import org.locationtech.proj4j.*;

import java.util.HashMap;
import java.util.Map;

public class CoordinateUtil {
    private static final CoordinateReferenceSystem crsWGS84;
    private static final CoordinateReferenceSystem crsKakao;

    static {
        CRSFactory crsFactory = new CRSFactory();
        crsWGS84 = crsFactory.createFromName("EPSG:4326");
        crsKakao = crsFactory.createFromParameters("EPSG:5181",
                "+proj=tmerc +lat_0=38 +lon_0=127 +k=1 +x_0=200000 +y_0=500000 +ellps=GRS80 +units=m +no_defs");

    }

    public static Map<String,Integer> convertWCongnamul(Double lat, Double lon) {

        CoordinateTransformFactory ctFactory = new CoordinateTransformFactory();
        CoordinateTransform transformer = ctFactory.createTransform(crsWGS84, crsKakao);

        ProjCoordinate srcCoord = new ProjCoordinate(lon, lat);
        ProjCoordinate destCoord = new ProjCoordinate();
        transformer.transform(srcCoord, destCoord);

        // Kakao 좌표계를 위한 보정
        double xKakao = destCoord.x * 2.5;
        double yKakao = destCoord.y * 2.5;

        Map<String,Integer> map = new HashMap<>();
        map.put("x", (int) Math.round(xKakao));
        map.put("y", (int) Math.round(yKakao));

        return map;
    }


    public static Map<String,Double> convertToWGS84(Integer x, Integer y) {

        CoordinateTransformFactory ctFactory = new CoordinateTransformFactory();
        CoordinateTransform transformer = ctFactory.createTransform(crsKakao, crsWGS84);

        double xWGS84 = x / 2.5;
        double yWGS84 = y / 2.5;

        ProjCoordinate srcCoord = new ProjCoordinate(xWGS84, yWGS84);
        ProjCoordinate destCoord = new ProjCoordinate();
        transformer.transform(srcCoord, destCoord);

        Map<String,Double> map = new HashMap<>();
        map.put("lon", destCoord.x);
        map.put("lat", destCoord.y);
        return map;
    }


}
