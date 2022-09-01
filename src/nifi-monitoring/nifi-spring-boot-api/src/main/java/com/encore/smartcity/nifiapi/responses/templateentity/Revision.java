package com.encore.smartcity.nifiapi.responses.templateentity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Revision {

    @SerializedName("clientId")
    @Expose
    public String clientId;
    @SerializedName("version")
    @Expose
    public Integer version;
    @SerializedName("lastModifier")
    @Expose
    public String lastModifier;

}
