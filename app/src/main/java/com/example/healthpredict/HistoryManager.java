package com.example.healthpredict;

import java.util.ArrayList;
import java.util.List;

public class HistoryManager {
    private static HistoryManager instance;
    private List<CaseData> caseHistory = new ArrayList<>();

    private HistoryManager() {
        // Add some mock data initially
        addMockData();
    }

    public static synchronized HistoryManager getInstance() {
        if (instance == null) {
            instance = new HistoryManager();
        }
        return instance;
    }

    public void addCase(CaseData caseData) {
        // Clone the case data using the copy constructor/method to ensure all fields are captured
        CaseData newCase = new CaseData();
        newCase.copyFrom(caseData);
        
        // Add to the beginning of the list for "Recent" effect
        caseHistory.add(0, newCase);
    }

    public List<CaseData> getCaseHistory() {
        return caseHistory;
    }

    private void addMockData() {
        CaseData mock1 = new CaseData();
        mock1.patientId = "P-1020";
        mock1.patientName = "Robert Wilson";
        mock1.date = "Jan 20, 2025";
        mock1.gender = "Male";
        mock1.age = "45";
        mock1.primarySystem = "Cardiovascular";
        mock1.riskLevel = "High";
        mock1.riskScore = "92%";
        mock1.oneYearPrediction = "12.5%";
        mock1.oneYearRisk = "High";
        mock1.interventionType = "Surgical";
        mock1.monitoringLevel = "Intensive";
        mock1.providerNotes = "Patient shows significant arterial thickening. Immediate follow-up required.";
        caseHistory.add(mock1);

        CaseData mock2 = new CaseData();
        mock2.patientId = "P-1021";
        mock2.patientName = "Emily Davis";
        mock2.date = "Jan 19, 2025";
        mock2.gender = "Female";
        mock2.age = "32";
        mock2.primarySystem = "Respiratory";
        mock2.riskLevel = "Low";
        mock2.riskScore = "15%";
        mock2.oneYearPrediction = "98.2%";
        mock2.oneYearRisk = "Low";
        mock2.interventionType = "Non-Invasive";
        mock2.monitoringLevel = "Standard";
        mock2.providerNotes = "Routine check-up. All parameters within normal range.";
        caseHistory.add(mock2);

        CaseData mock3 = new CaseData();
        mock3.patientId = "P-1022";
        mock3.patientName = "Michael Brown";
        mock3.date = "Jan 18, 2025";
        mock3.gender = "Male";
        mock3.age = "58";
        mock3.primarySystem = "Neurological";
        mock3.riskLevel = "Moderate";
        mock3.riskScore = "45%";
        mock3.oneYearPrediction = "85.5%";
        mock3.oneYearRisk = "Moderate";
        mock3.interventionType = "Pharmacological";
        mock3.monitoringLevel = "Bi-weekly";
        mock3.providerNotes = "Monitor cognitive response to new medication.";
        caseHistory.add(mock3);
    }
}
