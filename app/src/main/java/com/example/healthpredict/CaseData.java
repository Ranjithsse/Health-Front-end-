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
    @SerializedName("patientId")
    public String patientId = "";

    @SerializedName("patientName")
    public String patientName = "";

    @SerializedName("date")
    public String date = "";

    // Page 2: Demographics
    @SerializedName("gender")
    public String gender = "";

    @SerializedName("age")
    public String age = "";

    @SerializedName("bloodGroup")
    public String bloodGroup = "";

    @SerializedName("smokingStatus")
    public String smokingStatus = "";

    @SerializedName("bmi")
    public String bmi = "";

    // Page 3: Medical Conditions
    @SerializedName("medicalConditions")
    public List<String> medicalConditions = new ArrayList<>();

    // Page 4: Physical Activity
    @SerializedName("physicalActivity")
    public String physicalActivity = "";

    // Page 5: Primary Body System
    @SerializedName("primarySystem")
    public String primarySystem = "";

    // Page 6: Vitals
    @SerializedName("bloodPressure")
    public String bloodPressure = "";

    @SerializedName("glucoseLevel")
    public String glucoseLevel = "";

    // Page 8 & 9: Files
    @SerializedName("fileUri")
    public String fileUri = "";

    // Page 14: Imaging Parameters
    @SerializedName("tissueDensity")
    public String tissueDensity = "Normal";

    @SerializedName("calcification")
    public String calcification = "Minimal";

    @SerializedName("vascularity")
    public String vascularity = "Good";

    @SerializedName("inflammation")
    public String inflammation = "None";

    // Page 15: Medication & Treatment
    @SerializedName("primaryMedication")
    public String primaryMedication = "";

    @SerializedName("dosage")
    public String dosage = "";

    @SerializedName("duration")
    public String duration = "";

    @SerializedName("treatmentType")
    public String treatmentType = "";

    // Page 17: Intervention Type
    @SerializedName("interventionType")
    public String interventionType = "Non-Invasive";

    // Page 18: Monitoring Level
    @SerializedName("monitoringLevel")
    public String monitoringLevel = "Standard Monitoring";

    // Page 19: Adjuvant Therapy
    @SerializedName("adjuvantTherapyRequired")
    public boolean adjuvantTherapyRequired = false;

    // Tissue breakdown percentages
    @SerializedName("healthyTissuePct")
    public double healthyTissuePct = 0.0;

    @SerializedName("fibrousTissuePct")
    public double fibrousTissuePct = 0.0;

    @SerializedName("inflamedTissuePct")
    public double inflamedTissuePct = 0.0;

    // Micro-Structure / Cell Analysis
    @SerializedName("cellularity")
    public String cellularity = "Normal";

    @SerializedName("vascularityPerfusion")
    public String vascularityPerfusion = "Good";

    @SerializedName("typeACellsPct")
    public double typeACellsPct = 15.0;

    @SerializedName("typeBCellsPct")
    public double typeBCellsPct = 60.0;

    @SerializedName("typeCCellsPct")
    public double typeCCellsPct = 25.0;

    // AI Prediction Results
    @SerializedName("riskScore")
    public String riskScore = "";

    @SerializedName("riskLevel")
    public String riskLevel = "";

    @SerializedName("accuracy")
    public String accuracy = "";

    @SerializedName("aiInsight")
    public String aiInsight = "";

    // Multi-Year Outlook Data
    @SerializedName("oneYearPrediction")
    public String oneYearPrediction = "98.2%";

    @SerializedName("oneYearRisk")
    public String oneYearRisk = "Low";

    @SerializedName("oneYearInsight")
    public String oneYearInsight = "";

    @SerializedName("threeYearPrediction")
    public String threeYearPrediction = "85.5%";

    @SerializedName("threeYearRisk")
    public String threeYearRisk = "Moderate";

    @SerializedName("threeYearInsight")
    public String threeYearInsight = "";

    @SerializedName("fiveYearPrediction")
    public String fiveYearPrediction = "72.1%";

    @SerializedName("fiveYearRisk")
    public String fiveYearRisk = "Moderate";

    @SerializedName("fiveYearInsight")
    public String fiveYearInsight = "";

    // Provider Notes
    @SerializedName("providerNotes")
    public String providerNotes = "";

    @SerializedName("riskFactors")
    public java.util.List<java.util.Map<String, String>> riskFactors = new java.util.ArrayList<>();

    public CaseData() {
    }

    public static synchronized CaseData getInstance() {
        if (instance == null) {
            instance = new CaseData();
        }
        return instance;
    }

    public void copyFrom(CaseData other) {
        if (other == null)
            return;

        if (other.id != 0)
            this.id = other.id;
        if (other.status != null)
            this.status = other.status;
        if (other.patientId != null)
            this.patientId = other.patientId;
        if (other.patientName != null)
            this.patientName = other.patientName;
        if (other.date != null)
            this.date = other.date;
        if (other.gender != null)
            this.gender = other.gender;
        if (other.age != null)
            this.age = other.age;
        if (other.bloodGroup != null)
            this.bloodGroup = other.bloodGroup;
        if (other.smokingStatus != null)
            this.smokingStatus = other.smokingStatus;
        if (other.bmi != null)
            this.bmi = other.bmi;

        if (other.medicalConditions != null) {
            this.medicalConditions = new ArrayList<>(other.medicalConditions);
        }

        if (other.physicalActivity != null)
            this.physicalActivity = other.physicalActivity;
        if (other.primarySystem != null)
            this.primarySystem = other.primarySystem;
        if (other.bloodPressure != null)
            this.bloodPressure = other.bloodPressure;
        if (other.glucoseLevel != null)
            this.glucoseLevel = other.glucoseLevel;
        if (other.fileUri != null)
            this.fileUri = other.fileUri;

        if (other.tissueDensity != null)
            this.tissueDensity = other.tissueDensity;
        if (other.calcification != null)
            this.calcification = other.calcification;
        if (other.vascularity != null)
            this.vascularity = other.vascularity;
        if (other.inflammation != null)
            this.inflammation = other.inflammation;

        if (other.primaryMedication != null)
            this.primaryMedication = other.primaryMedication;
        if (other.dosage != null)
            this.dosage = other.dosage;
        if (other.duration != null)
            this.duration = other.duration;
        if (other.treatmentType != null)
            this.treatmentType = other.treatmentType;

        if (other.interventionType != null)
            this.interventionType = other.interventionType;
        if (other.monitoringLevel != null)
            this.monitoringLevel = other.monitoringLevel;
        this.adjuvantTherapyRequired = other.adjuvantTherapyRequired;

        // Numeric fields: always copy if from a non-null context (other != null)
        this.healthyTissuePct = other.healthyTissuePct;
        this.fibrousTissuePct = other.fibrousTissuePct;
        this.inflamedTissuePct = other.inflamedTissuePct;

        if (other.cellularity != null)
            this.cellularity = other.cellularity;
        if (other.vascularityPerfusion != null)
            this.vascularityPerfusion = other.vascularityPerfusion;
        
        this.typeACellsPct = other.typeACellsPct;
        this.typeBCellsPct = other.typeBCellsPct;
        this.typeCCellsPct = other.typeCCellsPct;

        if (other.riskScore != null)
            this.riskScore = other.riskScore;
        if (other.riskLevel != null)
            this.riskLevel = other.riskLevel;
        if (other.accuracy != null)
            this.accuracy = other.accuracy;
        if (other.aiInsight != null)
            this.aiInsight = other.aiInsight;

        if (other.oneYearPrediction != null)
            this.oneYearPrediction = other.oneYearPrediction;
        if (other.oneYearRisk != null)
            this.oneYearRisk = other.oneYearRisk;
        if (other.oneYearInsight != null)
            this.oneYearInsight = other.oneYearInsight;

        if (other.threeYearPrediction != null)
            this.threeYearPrediction = other.threeYearPrediction;
        if (other.threeYearRisk != null)
            this.threeYearRisk = other.threeYearRisk;
        if (other.threeYearInsight != null)
            this.threeYearInsight = other.threeYearInsight;

        if (other.fiveYearPrediction != null)
            this.fiveYearPrediction = other.fiveYearPrediction;
        if (other.fiveYearRisk != null)
            this.fiveYearRisk = other.fiveYearRisk;
        if (other.fiveYearInsight != null)
            this.fiveYearInsight = other.fiveYearInsight;

        if (other.providerNotes != null)
            this.providerNotes = other.providerNotes;
            
        if (other.riskFactors != null)
            this.riskFactors = new java.util.ArrayList<>(other.riskFactors);
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
        bmi = "";
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
        healthyTissuePct = 0.0;
        fibrousTissuePct = 0.0;
        inflamedTissuePct = 0.0;
        cellularity = "Normal";
        vascularityPerfusion = "Good";
        typeACellsPct = 15.0;
        typeBCellsPct = 60.0;
        typeCCellsPct = 25.0;
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
        if (riskFactors != null) riskFactors.clear();
    }
}
