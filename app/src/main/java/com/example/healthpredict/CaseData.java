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

    // Tissue breakdown percentages
    @SerializedName("healthy_tissue_pct")
    public double healthyTissuePct = 0.0;

    @SerializedName("fibrous_tissue_pct")
    public double fibrousTissuePct = 0.0;

    @SerializedName("inflamed_tissue_pct")
    public double inflamedTissuePct = 0.0;

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
        if (other == null)
            return;

        if (other.id != 0)
            this.id = other.id;
        if (other.status != null && !other.status.isEmpty())
            this.status = other.status;
        if (other.patientId != null && !other.patientId.isEmpty())
            this.patientId = other.patientId;
        if (other.patientName != null && !other.patientName.isEmpty())
            this.patientName = other.patientName;
        if (other.date != null && !other.date.isEmpty())
            this.date = other.date;
        if (other.gender != null && !other.gender.isEmpty())
            this.gender = other.gender;
        if (other.age != null && !other.age.isEmpty())
            this.age = other.age;
        if (other.bloodGroup != null && !other.bloodGroup.isEmpty())
            this.bloodGroup = other.bloodGroup;
        if (other.smokingStatus != null && !other.smokingStatus.isEmpty())
            this.smokingStatus = other.smokingStatus;

        if (other.medicalConditions != null && !other.medicalConditions.isEmpty()) {
            this.medicalConditions = new ArrayList<>(other.medicalConditions);
        }

        if (other.physicalActivity != null && !other.physicalActivity.isEmpty())
            this.physicalActivity = other.physicalActivity;
        if (other.primarySystem != null && !other.primarySystem.isEmpty())
            this.primarySystem = other.primarySystem;
        if (other.bloodPressure != null && !other.bloodPressure.isEmpty())
            this.bloodPressure = other.bloodPressure;
        if (other.glucoseLevel != null && !other.glucoseLevel.isEmpty())
            this.glucoseLevel = other.glucoseLevel;
        if (other.fileUri != null && !other.fileUri.isEmpty())
            this.fileUri = other.fileUri;

        if (other.tissueDensity != null && !other.tissueDensity.isEmpty())
            this.tissueDensity = other.tissueDensity;
        if (other.calcification != null && !other.calcification.isEmpty())
            this.calcification = other.calcification;
        if (other.vascularity != null && !other.vascularity.isEmpty())
            this.vascularity = other.vascularity;
        if (other.inflammation != null && !other.inflammation.isEmpty())
            this.inflammation = other.inflammation;

        if (other.primaryMedication != null && !other.primaryMedication.isEmpty())
            this.primaryMedication = other.primaryMedication;
        if (other.dosage != null && !other.dosage.isEmpty())
            this.dosage = other.dosage;
        if (other.duration != null && !other.duration.isEmpty())
            this.duration = other.duration;
        if (other.treatmentType != null && !other.treatmentType.isEmpty())
            this.treatmentType = other.treatmentType;

        if (other.interventionType != null && !other.interventionType.isEmpty())
            this.interventionType = other.interventionType;
        if (other.monitoringLevel != null && !other.monitoringLevel.isEmpty())
            this.monitoringLevel = other.monitoringLevel;
        this.adjuvantTherapyRequired = other.adjuvantTherapyRequired;

        if (other.healthyTissuePct != 0.0)
            this.healthyTissuePct = other.healthyTissuePct;
        if (other.fibrousTissuePct != 0.0)
            this.fibrousTissuePct = other.fibrousTissuePct;
        if (other.inflamedTissuePct != 0.0)
            this.inflamedTissuePct = other.inflamedTissuePct;

        if (other.riskScore != null && !other.riskScore.isEmpty())
            this.riskScore = other.riskScore;
        if (other.riskLevel != null && !other.riskLevel.isEmpty())
            this.riskLevel = other.riskLevel;
        if (other.accuracy != null && !other.accuracy.isEmpty())
            this.accuracy = other.accuracy;
        if (other.aiInsight != null && !other.aiInsight.isEmpty())
            this.aiInsight = other.aiInsight;

        if (other.oneYearPrediction != null && !other.oneYearPrediction.isEmpty())
            this.oneYearPrediction = other.oneYearPrediction;
        if (other.oneYearRisk != null && !other.oneYearRisk.isEmpty())
            this.oneYearRisk = other.oneYearRisk;
        if (other.oneYearInsight != null && !other.oneYearInsight.isEmpty())
            this.oneYearInsight = other.oneYearInsight;

        if (other.threeYearPrediction != null && !other.threeYearPrediction.isEmpty())
            this.threeYearPrediction = other.threeYearPrediction;
        if (other.threeYearRisk != null && !other.threeYearRisk.isEmpty())
            this.threeYearRisk = other.threeYearRisk;
        if (other.threeYearInsight != null && !other.threeYearInsight.isEmpty())
            this.threeYearInsight = other.threeYearInsight;

        if (other.fiveYearPrediction != null && !other.fiveYearPrediction.isEmpty())
            this.fiveYearPrediction = other.fiveYearPrediction;
        if (other.fiveYearRisk != null && !other.fiveYearRisk.isEmpty())
            this.fiveYearRisk = other.fiveYearRisk;
        if (other.fiveYearInsight != null && !other.fiveYearInsight.isEmpty())
            this.fiveYearInsight = other.fiveYearInsight;

        if (other.providerNotes != null && !other.providerNotes.isEmpty())
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
        healthyTissuePct = 0.0;
        fibrousTissuePct = 0.0;
        inflamedTissuePct = 0.0;
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
