package com.encore.smartcity.nifiapi.responses.templateentity;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateEntity {

    /*@SerializedName("revision")
    @Expose
    public Revision revision;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("uri")
    @Expose
    public String uri;
    @SerializedName("position")
    @Expose
    public Position position;
    @SerializedName("permissions")
    @Expose
    public Permissions permissions;
    @SerializedName("bulletins")
    @Expose
    public List<Bulletin> bulletins = null;
    @SerializedName("disconnectedNodeAcknowledged")
    @Expose
    public Boolean disconnectedNodeAcknowledged;
    @SerializedName("template")
    @Expose
    public Template template;*/

    @SerializedName("template")
    private Template template;

}
