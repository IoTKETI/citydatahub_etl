package com.encore.smartcity.nifiapi.responses.templateentity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Position {

    @SerializedName("x")
    @Expose
    public Float x;
    @SerializedName("y")
    @Expose
    public Float y;

}
