package com.example.healthpredict.network;

import com.example.healthpredict.CaseData;
import com.google.gson.annotations.SerializedName;

public class PredictionResponse {
    @SerializedName("message")
    public String message;

    @SerializedName("case")
    public CaseData caseData;
    
    @SerializedName("explainability")
    public java.util.Map<String, Object> explainability;
}
