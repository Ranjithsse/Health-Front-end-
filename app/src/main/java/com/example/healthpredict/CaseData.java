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
    public String age = "";
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

    // Page 15: Medication & Treatment
    public String primaryMedication = "ACE Inhibitors";
    public String treatmentType = "Preventative";

    // Page 17: Intervention Type
    public String interventionType = "Non-Invasive";

    // Page 18: Monitoring Level
    public String monitoringLevel = "Standard Monitoring";

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

    public void copyFrom(CaseData other) {
        this.patientId = other.patientId;
        this.patientName = other.patientName;
        this.date = other.date;
        this.gender = other.gender;
        this.age = other.age;
        this.bloodGroup = other.bloodGroup;
        this.smokingStatus = other.smokingStatus;
        this.medicalConditions = new ArrayList<>(other.medicalConditions);
        this.physicalActivity = other.physicalActivity;
        this.primarySystem = other.primarySystem;
        this.bloodPressure = other.bloodPressure;
        this.glucoseLevel = other.glucoseLevel;
        this.fileUri = other.fileUri;
        this.tissueDensity = other.tissueDensity;
        this.calcification = other.calcification;
        this.vascularity = other.vascularity;
        this.inflammation = other.inflammation;
        this.primaryMedication = other.primaryMedication;
        this.treatmentType = other.treatmentType;
        this.interventionType = other.interventionType;
        this.monitoringLevel = other.monitoringLevel;
        this.adjuvantTherapyRequired = other.adjuvantTherapyRequired;
        this.riskScore = other.riskScore;
        this.riskLevel = other.riskLevel;
        this.accuracy = other.accuracy;
        this.aiInsight = other.aiInsight;
        this.oneYearPrediction = other.oneYearPrediction;
        this.oneYearRisk = other.oneYearRisk;
        this.threeYearPrediction = other.threeYearPrediction;
        this.threeYearRisk = other.threeYearRisk;
        this.fiveYearPrediction = other.fiveYearPrediction;
        this.fiveYearRisk = other.fiveYearRisk;
        this.providerNotes = other.providerNotes;
    }

    public void reset() {
        patientId = "";
        patientName = "";
        date = "";
        gender = "";
        age = "";
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
        primaryMedication = "ACE Inhibitors";
        treatmentType = "Preventative";
        interventionType = "Non-Invasive";
        monitoringLevel = "Standard Monitoring";
        adjuvantTherapyRequired = false;
        riskScore = "";
        riskLevel = "";
        accuracy = "";
        aiInsight = "";
        providerNotes = "";
        oneYearPrediction = "98.2%";
        oneYearRisk = "Low";
        threeYearPrediction = "85.5%";
        threeYearRisk = "Moderate";
        fiveYearPrediction = "72.1%";
        fiveYearRisk = "Moderate";
    }
}
