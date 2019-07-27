package sin.bse.json2db.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="SCRIP_STAGING")
public class ScripStaging {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty("scrip_cd")
    @SerializedName("scrip_cd")
    @Column(name = "scripcd")
    private Integer scripCd;
    @JsonProperty("skcripname")
    @Column(name = "scripname")
    private String scripname;
    @JsonProperty("scrip_grp")
    @SerializedName("scrip_grp")
    @Column(name = "scripgrp")
    private String scripGrp;
    @JsonProperty("openrate")
    @Column(name = "openrate")
    private Double openrate;
    @JsonProperty("highrate")
    @Column(name = "highrate")
    private Double highrate;
    @JsonProperty("lowrate")
    @Column(name = "lowrate")
    private Double lowrate;
    @JsonProperty("ltradert")
    @Column(name = "ltradert")
    private Double ltradert;
    @JsonProperty("prevdayclose")
    @Column(name = "prevdayclose")
    private Double prevdayclose;
    @JsonProperty("change_val")
    @SerializedName("change_val")
    @Column(name = "changeval")
    private Double changeVal;
    @JsonProperty("change_percent")
    @SerializedName("change_percent")
    @Column(name = "changepercent")
    private Double changePercent;
    @JsonProperty("index_code")
    @Column(name = "indexcode")
    private String indexCode;
    @JsonProperty("trd_val")
    @SerializedName("trd_val")
    @Column(name = "trdval")
    private Double trdVal;
    @JsonProperty("trd_vol")
    @SerializedName("trd_vol")
    @Column(name = "trdvol")
    private Integer trdVol;
    @JsonProperty("nooftrd")
    @Column(name = "nooftrd")
    private Integer nooftrd;
    @JsonProperty("trend")
    @Column(name = "trend")
    private String trend;
    @JsonProperty("dt_tm")
    @SerializedName("dt_tm")
    @Column(name = "dttm")
    private String dtTm;
    @JsonProperty("Ishighflag")
    @Column(name = "Ishighflag")
    private Integer ishighflag;
    @JsonProperty("IsLowflag")
    @Column(name = "IsLowflag")
    private Integer isLowflag;
    @JsonProperty("URL")
    @Column(name = "URL")
    private String uRL;
    @JsonProperty("NSUrl")
    @Column(name = "NSUrl")
    private String nSUrl;
}
