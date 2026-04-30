import pandas as pd

# List of test cases
test_cases = [
    ["TC_01", "Check whether a citizen can report an overflowing bin after logging in",
     "Citizen account is registered",
     "Login with valid credentials and submit a complaint for an overflowing bin",
     "Complaint should be submitted successfully",
     "Complaint was submitted successfully",
     "Pass"],

    ["TC_02", "Check system behavior when user tries to report without login",
     "User is not logged in",
     "Open report page directly",
     "System should ask user to login first",
     "System redirected user to login page",
     "Pass"],

    ["TC_03", "Verify system accepts a valid manually entered bin ID",
 "User is logged in",
 "Enter a valid bin ID in the complaint form",
 "System should accept the entered bin ID",
 "System accepted the entered bin ID",
 "Pass"],

    ["TC_04", "Verify system rejects an invalid bin ID",
 "User is logged in",
 "Enter an invalid or blank bin ID",
 "System should display an invalid bin ID message",
 "System displayed an invalid bin ID message",
 "Pass"],

    ["TC_05", "Verify complaint cannot be submitted without description",
     "User is logged in",
     "Leave complaint field empty and click submit",
     "System should display validation message",
     "System displayed validation message",
     "Pass"],

    ["TC_06", "Verify complaint can be submitted when fill level is above threshold",
 "User is logged in",
 "Enter fill level above 80% and submit complaint",
 "System should accept the complaint",
 "System accepted the complaint",
 "Pass"],

    ["TC_07", "Verify complaint cannot be submitted when fill level is below threshold",
 "User is logged in",
 "Enter fill level below 80% and submit complaint",
 "System should reject the complaint",
 "System rejected the complaint",
 "Pass"],

    ["TC_08", "Verify citizen can report only bins in own ward",
     "User is logged in",
     "Select bin from own ward",
     "Complaint should be accepted",
     "Complaint accepted successfully",
     "Pass"],

    ["TC_09", "Verify citizen cannot report bin from another ward",
     "User is logged in",
     "Select bin from another ward",
     "System should deny access",
     "System denied access correctly",
     "Pass"],

    ["TC_10", "Verify duplicate complaint prevention",
     "Complaint already exists for same bin",
     "Submit same complaint again",
     "Duplicate complaint warning should appear",
     "Duplicate warning displayed",
     "Pass"],

    ["TC_11", "Verify system accepts a valid bin location",
 "User is logged in",
 "Enter a valid location while reporting",
 "System should accept the location",
 "System accepted the location",
 "Pass"],

    ["TC_12", "Verify system rejects empty location field",
 "User is logged in",
 "Leave location field blank and submit",
 "System should show location required message",
 "System displayed location required message",
 "Pass"],

    ["TC_13", "Verify admin receives complaint notification",
     "Complaint submitted",
     "Citizen submits report",
     "Admin should receive alert",
     "Admin received notification",
     "Pass"],

    ["TC_14", "Verify admin can assign collection vehicle manually",
 "Vehicle is available",
 "Admin enters vehicle ID for complaint",
 "System should assign vehicle successfully",
 "System assigned vehicle successfully",
 "Pass"],

    ["TC_15", "Verify citizen can view updated complaint status after bin is cleared",
     "Complaint has been resolved by collection staff",
     "Citizen opens complaint history",
     "System should display complaint status as Resolved",
     "System displayed complaint status as Resolved",
     "Pass"]
]

# Column names
columns = [
    "Test Case ID",
    "Description",
    "Preconditions",
    "Input Steps",
    "Expected Result",
    "Actual Result",
    "Status"
]

# Create DataFrame
df = pd.DataFrame(test_cases, columns=columns)

# Export to Excel
file_name = "smart_waste_management_test_cases_new.xlsx"
df.to_excel(file_name, index=False)

print(f"Excel file '{file_name}' created successfully.")