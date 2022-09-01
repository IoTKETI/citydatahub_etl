package com.encore.smartcity.nifiapi.responses.templateentity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permissions {

    @SerializedName("canRead")
    @Expose
    public Boolean canRead;
    @SerializedName("canWrite")
    @Expose
    public Boolean canWrite;

}
