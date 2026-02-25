package com.example.healthpredict;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CaseData implements Serializable {
    private static CaseData instance;

    // Page 1: Assessment
    public String patientId = "";
    public String patientName = ""; 
    public String date = "";

    // Page 2: Demographics
    public String gender = "";
    public String bloodGroup = "";
    public String smokingStatus = "";

    // Page 3: Medical Conditions
    public List<String> medicalConditions = new ArrayList<>();

    // Page 4: Physical Activity
    public String physicalActivity = "";

    // Page 5: Primary Body System
    public String primarySystem = "";

    // Page 6: Vitals
    public String bloodPressure = "";
    public String glucoseLevel = "";

    // Page 8 & 9: Files
    public String fileUri = "";

    // Page 14: Imaging Parameters
    public String tissueDensity = "Normal";
    public String calcification = "Minimal";
    public String vascularity = "Good";
    public String inflammation = "None";

    // Page 17: Intervention Type
    public String interventionType = "";

    // Page 18: Monitoring Level
    public String monitoringLevel = "";

    // Page 19: Adjuvant Therapy
    public boolean adjuvantTherapyRequired = false;

    // AI Prediction Results
    public String riskScore = "";
    public String riskLevel = "";
    public String accuracy = "";
    public String aiInsight = "";

    // Multi-Year Outlook Data
    public String oneYearPrediction = "98.2%";
    public String oneYearRisk = "Low";
    
    public String threeYearPrediction = "85.5%";
    public String threeYearRisk = "Moderate";
    
    public String fiveYearPrediction = "72.1%";
    public String fiveYearRisk = "Moderate";

    // Provider Notes
    public String providerNotes = "";

    public CaseData() {}

    public static synchronized CaseData getInstance() {
        if (instance == null) {
            instance = new CaseData();
        }
        return instance;
    }

    public void reset() {
        patientId = "";
        patientName = "";
        date = "";
        gender = "";
        bloodGroup = "";
        smokingStatus = "";
        medicalConditions.clear();
        physicalActivity = "";
        primarySystem = "";
        bloodPressure = "";
        glucoseLevel = "";
        fileUri = "";
        tissueDensity = "Normal";
        calcification = "Minimal";
        vascularity = "Good";
        inflammation = "None";
        interventionType = "";
        monitoringLevel = "";
        adjuvantTherapyRequired = false;
        riskScore = "";
        riskLevel = "";
        accuracy = "";
        aiInsight = "";
        providerNotes = "";
        oneYearPrediction = "98.2%";
        threeYearPrediction = "85.5%";
        fiveYearPrediction = "72.1%";
    }
}
