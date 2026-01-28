package com.chertiavdev.models;

import java.util.Date;

public record Operation(
    Integer id,
    String title,
    Date start,
    Date end,
    String url,
    String color,
    String textColor,
    Float overlayOpacity
){
}
