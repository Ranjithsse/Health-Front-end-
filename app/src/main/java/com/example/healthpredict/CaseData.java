package com.example.healthpredict;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CaseData implements Serializable {
    private static CaseData instance;

    @SerializedName("id")
    public int id;

    @SerializedName("status")
    public String status = "";

    // Page 1: Assessment
    @SerializedName("patient_id")
    public String patientId = "";

    @SerializedName("patient_name")
    public String patientName = "";

    @SerializedName("date")
    public String date = "";

    // Page 2: Demographics
    @SerializedName("gender")
    public String gender = "";

    @SerializedName("age")
    public String age = "";

    @SerializedName("blood_group")
    public String bloodGroup = "";

    @SerializedName("smoking_status")
    public String smokingStatus = "";

    // Page 3: Medical Conditions
    @SerializedName("medical_conditions")
    public List<String> medicalConditions = new ArrayList<>();

    // Page 4: Physical Activity
    @SerializedName("physical_activity")
    public String physicalActivity = "";

    // Page 5: Primary Body System
    @SerializedName("primary_system")
    public String primarySystem = "";

    // Page 6: Vitals
    @SerializedName("blood_pressure")
    public String bloodPressure = "";

    @SerializedName("glucose_level")
    public String glucoseLevel = "";

    // Page 8 & 9: Files
    @SerializedName("file_uri")
    public String fileUri = "";

    // Page 14: Imaging Parameters
    @SerializedName("tissue_density")
    public String tissueDensity = "Normal";

    @SerializedName("calcification")
    public String calcification = "Minimal";

    @SerializedName("vascularity")
    public String vascularity = "Good";

    @SerializedName("inflammation")
    public String inflammation = "None";

    // Page 15: Medication & Treatment
    public String primaryMedication = "ACE Inhibitors";
    public String dosage = "";
    public String duration = "";
    public String treatmentType = "Preventative";

    // Page 17: Intervention Type
    @SerializedName("intervention_type")
    public String interventionType = "Non-Invasive";

    // Page 18: Monitoring Level
    @SerializedName("monitoring_level")
    public String monitoringLevel = "Standard Monitoring";

    // Page 19: Adjuvant Therapy
    @SerializedName("adjuvant_therapy_required")
    public boolean adjuvantTherapyRequired = false;

    // AI Prediction Results
    @SerializedName("risk_score")
    public String riskScore = "";

    @SerializedName("risk_level")
    public String riskLevel = "";

    @SerializedName("accuracy")
    public String accuracy = "";

    @SerializedName("ai_insight")
    public String aiInsight = "";

    // Multi-Year Outlook Data
    @SerializedName("one_year_prediction")
    public String oneYearPrediction = "98.2%";

    @SerializedName("one_year_risk")
    public String oneYearRisk = "Low";

    @SerializedName("one_year_insight")
    public String oneYearInsight = "";

    @SerializedName("three_year_prediction")
    public String threeYearPrediction = "85.5%";

    @SerializedName("three_year_risk")
    public String threeYearRisk = "Moderate";

    @SerializedName("three_year_insight")
    public String threeYearInsight = "";

    @SerializedName("five_year_prediction")
    public String fiveYearPrediction = "72.1%";

    @SerializedName("five_year_risk")
    public String fiveYearRisk = "Moderate";

    @SerializedName("five_year_insight")
    public String fiveYearInsight = "";

    // Provider Notes
    @SerializedName("provider_notes")
    public String providerNotes = "";

    public CaseData() {
    }

    public static synchronized CaseData getInstance() {
        if (instance == null) {
            instance = new CaseData();
        }
        return instance;
    }

    public void copyFrom(CaseData other) {
        this.id = other.id;
        this.status = other.status;
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
        this.dosage = other.dosage;
        this.duration = other.duration;
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
        this.oneYearInsight = other.oneYearInsight;
        this.threeYearPrediction = other.threeYearPrediction;
        this.threeYearRisk = other.threeYearRisk;
        this.threeYearInsight = other.threeYearInsight;
        this.fiveYearPrediction = other.fiveYearPrediction;
        this.fiveYearRisk = other.fiveYearRisk;
        this.fiveYearInsight = other.fiveYearInsight;
        this.providerNotes = other.providerNotes;
    }

    public void reset() {
        id = 0;
        status = "";
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
        dosage = "";
        duration = "";
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
        oneYearInsight = "";
        threeYearPrediction = "85.5%";
        threeYearRisk = "Moderate";
        threeYearInsight = "";
        fiveYearPrediction = "72.1%";
        fiveYearRisk = "Moderate";
        fiveYearInsight = "";
    }
}
