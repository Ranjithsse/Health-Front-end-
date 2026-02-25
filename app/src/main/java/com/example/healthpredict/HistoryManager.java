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
        // Clone the case data to avoid reference issues
        CaseData newCase = new CaseData();
        newCase.patientId = caseData.patientId;
        newCase.patientName = caseData.patientName;
        newCase.date = caseData.date;
        newCase.gender = caseData.gender;
        newCase.bloodGroup = caseData.bloodGroup;
        newCase.primarySystem = caseData.primarySystem;
        newCase.riskScore = caseData.riskScore;
        newCase.riskLevel = caseData.riskLevel;
        newCase.accuracy = caseData.accuracy;
        newCase.providerNotes = caseData.providerNotes;
        
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
        mock1.date = "2024-03-20";
        mock1.riskLevel = "High";
        mock1.riskScore = "92%";
        caseHistory.add(mock1);

        CaseData mock2 = new CaseData();
        mock2.patientId = "P-1021";
        mock2.patientName = "Emily Davis";
        mock2.date = "2024-03-19";
        mock2.riskLevel = "Low";
        mock2.riskScore = "15%";
        caseHistory.add(mock2);
    }
}
