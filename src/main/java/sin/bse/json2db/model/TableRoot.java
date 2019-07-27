package sin.bse.json2db.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TableRoot {

    @SerializedName("Table")
    private List<ScripStaging> table;
}
