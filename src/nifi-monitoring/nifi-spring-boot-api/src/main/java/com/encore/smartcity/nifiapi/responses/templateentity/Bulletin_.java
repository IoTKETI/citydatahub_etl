package com.encore.smartcity.nifiapi.responses.templateentity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bulletin_ {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("nodeAddress")
    @Expose
    public String nodeAddress;
    @SerializedName("category")
    @Expose
    public String category;
    @SerializedName("groupId")
    @Expose
    public String groupId;
    @SerializedName("sourceId")
    @Expose
    public String sourceId;
    @SerializedName("sourceName")
    @Expose
    public String sourceName;
    @SerializedName("level")
    @Expose
    public String level;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("timestamp")
    @Expose
    public String timestamp;

}
