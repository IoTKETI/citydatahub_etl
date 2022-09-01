package com.encore.smartcity.nifiapi.responses.templateentity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bulletin {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("groupId")
    @Expose
    public String groupId;
    @SerializedName("sourceId")
    @Expose
    public String sourceId;
    @SerializedName("timestamp")
    @Expose
    public String timestamp;
    @SerializedName("nodeAddress")
    @Expose
    public String nodeAddress;
    @SerializedName("canRead")
    @Expose
    public Boolean canRead;
    @SerializedName("bulletin")
    @Expose
    public Bulletin_ bulletin;

}
