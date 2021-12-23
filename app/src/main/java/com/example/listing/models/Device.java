package com.example.listing.models;

        import androidx.annotation.NonNull;
        import androidx.room.ColumnInfo;
        import androidx.room.Entity;
        import androidx.room.Ignore;
        import androidx.room.PrimaryKey;

        import com.example.listing.Kotlin.Loader;
        import com.fasterxml.jackson.annotation.JsonAutoDetect;
        import com.fasterxml.jackson.annotation.JsonIgnore;
        import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
        import com.fasterxml.jackson.annotation.JsonProperty;
        import com.fasterxml.jackson.core.JsonProcessingException;
        import com.fasterxml.jackson.core.type.TypeReference;
        import com.fasterxml.jackson.databind.DeserializationFeature;
        import com.fasterxml.jackson.databind.MapperFeature;
        import com.fasterxml.jackson.databind.ObjectMapper;
        import com.google.gson.annotations.Expose;

        import java.io.Serializable;
        import java.util.ArrayList;
        import java.util.List;

@Entity(tableName = "DeviceTable")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE,creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Device implements Serializable {

    public Device() {

    }
    @JsonProperty("DevId")
    String Devid;

    @JsonProperty("Name")
    String Name;


    @JsonProperty("Macid")
    String Macid;


    @JsonProperty("Sno")
    String Sno;

    public Device(String devid, String name, String macid, String sno) {
        Devid = devid;
        Name = name;
        Macid = macid;
        Sno = sno;
    }

    public String getDevid() {
        return Devid;
    }

    public void setDevid(String devid) {
        Devid = devid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMacid() {
        return Macid;
    }

    public void setMacid(String macid) {
        Macid = macid;
    }

    public String getSno() {
        return Sno;
    }

    public void setSno(String sno) {
        Sno = sno;
    }
}
