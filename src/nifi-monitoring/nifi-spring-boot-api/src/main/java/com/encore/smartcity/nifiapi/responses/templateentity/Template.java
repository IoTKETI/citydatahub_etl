package com.encore.smartcity.nifiapi.responses.templateentity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Template {

    /*@SerializedName("uri")
    @Expose
    public String uri;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("groupId")
    @Expose
    public String groupId;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("timestamp")
    @Expose
    public String timestamp;
    @SerializedName("encodingVersion")
    @Expose
    public String encodingVersion;
    @SerializedName("snippet")
    @Expose
    public Snippet snippet;*/

    private String description;
    private String groupId;
    private String id;
    private String name;
    private Timestamp timestamp;
    private String uri;

}
