package com.nafis.universalapi.model;

import com.nafis.universalapi.model.common.Params;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostModel {
    private String url;
    private int paramCount;
    private List<Params> paramsList;
    private JSONObject body;
}
