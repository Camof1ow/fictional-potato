package com.example.japanesenamegenerator.diner.application.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceData {

    // Root level members
    private List<Place> place;
    private int place_totalcount;

    @Getter
    // Inner class for placeList
    public static class Place {

        private String confirmid;
        private int x;
        private int y;
        private double lon;
        private double lat;
        private String name;
        private String tel;
        private String address;
        private int reviewCount;
        private String homepage;
        private String img;
        private String sourceId;
        private String source;
        private String roadview;
        private String full_category_ids;
        private String last_cate_id;
        private String last_cate_name;
        private String last_cate_depth;
        private String cate_name_depth1;
        private String cate_name_depth2;
        private String cate_name_depth3;
        private String cate_name_depth4;
        private String cate_name_depth5;
        private String hub_data;
        private String brand;
        private String brandName;
        private String oil1;
        private String oil2;
        private String oil3;
        private String oil4;
        private String oilTime;
        private String oil_sel24;
        private String phoneSynonyms;
        private String related_place;
        private String new_address;
        private String courseinfo;
        private String geoinfo;
        private String requiringtime;
        private String tvshow_info;
        private String tvshow_name;
        private String address_disp;
        private String new_address_disp;
        private String distance;
        private String catetype;
        private String new_zipcode;
        private String openoff_status;
        private String shape_support_types;
        private List<String> production_tags;
        private double rating_average;
        private int rating_count;
        private String addinfo_appointment;
        private String addinfo_delivery;
        private String addinfo_fordisabled;
        private String addinfo_nursery;
        private String addinfo_package;
        private String addinfo_parking;
        private String addinfo_pet;
        private String addinfo_smokingroom;
        private String addinfo_wifi;
        private String cvs_lotto;
        private String cvs_medicine;
        private String cvs_parcel;
        private String cvs_withdrawal;
        private String meta_keywords_disp;
        private String issue_keywords_disp;
        private String pay_keywords_disp;
        private String oil_carwash;
        private String oil_convenience;
        private String oil_maintenance;
        private String oil_self;
        private String mobility_parking_exit_type;
        private String display_restrict_type;
        private String mystore_type;
        private String storeview_id;
        private int grade_count;
        private int kplace_ratings_count;
        private double kplace_rating;

        private DisplayNameInfo displayNameInfo;
        private List<KnaviGuideInfo> knaviGuideInfos;

        public static class DisplayNameInfo {
            private String dpName1;
            private String dpName2;
        }

        public static class KnaviGuideInfo {
            private int id;
            private long kid;
            private String name;
            private String rpFlag;
            private String type;
            private double x;
            private double y;
        }


    }

}

