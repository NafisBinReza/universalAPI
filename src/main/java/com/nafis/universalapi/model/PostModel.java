package com.nafis.universalapi.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.nafis.universalapi.model.common.Params;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostModel {
    private String url;
    private List<Params> paramsList;
    private List<Params> headerList;
    @JsonAnySetter
    private Map<String, Object> body;
}
