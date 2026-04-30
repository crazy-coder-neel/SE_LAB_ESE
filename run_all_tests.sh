#!/bin/bash
# =====================================================
# Smart Waste Management System - Automated Test Runner
# Tests all 15 black-box test cases from the Excel sheet
# =====================================================

cd "/home/exam1/Desktop/SE LAB ESE"
PASS=0
FAIL=0
TOTAL=15

echo "======================================================"
echo "  SMART WASTE MANAGEMENT - AUTOMATED TEST SUITE"
echo "  Testing all 15 black-box test cases"
echo "======================================================"
echo ""

# --------------------------------------------------
# TC_01: Citizen can report overflowing bin after login
# --------------------------------------------------
echo -n "TC_01: Citizen reports overflowing bin after login ... "
OUTPUT=$(echo -e "1\ncitizen1\npass123\n1\nBIN001\nMain Street, Ward-A\n85\nBin is overflowing with garbage\n3\n2" | java com.smartwaste.main.SmartWasteApp 2>&1)
if echo "$OUTPUT" | grep -q "Complaint submitted successfully" && echo "$OUTPUT" | grep -q "Login successful"; then
    echo "PASS"
    ((PASS++))
else
    echo "FAIL"
    ((FAIL++))
fi

# --------------------------------------------------
# TC_02: Report without login - system asks to login
# --------------------------------------------------
echo -n "TC_02: Report without login blocked ................ "
OUTPUT=$(echo -e "3\n2" | java com.smartwaste.main.SmartWasteApp 2>&1)
# Without login, user only sees Main Menu with Login/Exit, no report menu option
if echo "$OUTPUT" | grep -q "MAIN MENU" && echo "$OUTPUT" | grep -q "1. Login" && ! echo "$OUTPUT" | grep -q "1. Report Overflowing Bin"; then
    echo "PASS"
    ((PASS++))
else
    echo "FAIL"
    ((FAIL++))
fi

# --------------------------------------------------
# TC_03: Valid bin ID accepted
# --------------------------------------------------
echo -n "TC_03: Valid bin ID accepted ........................ "
OUTPUT=$(echo -e "1\ncitizen1\npass123\n1\nBIN001\nMain Street\n85\nOverflowing garbage\n3\n2" | java com.smartwaste.main.SmartWasteApp 2>&1)
if echo "$OUTPUT" | grep -q "Complaint submitted successfully" && echo "$OUTPUT" | grep -q "BIN001"; then
    echo "PASS"
    ((PASS++))
else
    echo "FAIL"
    ((FAIL++))
fi

# --------------------------------------------------
# TC_04: Invalid bin ID rejected
# --------------------------------------------------
echo -n "TC_04: Invalid/blank bin ID rejected ............... "
OUTPUT=$(echo -e "1\ncitizen1\npass123\n1\nBINXXX\nSome Location\n90\nTest\n1\n\nSome Location\n90\nTest\n3\n2" | java com.smartwaste.main.SmartWasteApp 2>&1)
if echo "$OUTPUT" | grep -q "Invalid Bin ID"; then
    echo "PASS"
    ((PASS++))
else
    echo "FAIL"
    ((FAIL++))
fi

# --------------------------------------------------
# TC_05: Empty description rejected
# --------------------------------------------------
echo -n "TC_05: Empty description rejected .................. "
OUTPUT=$(echo -e "1\ncitizen1\npass123\n1\nBIN001\nMain Street\n85\n\n3\n2" | java com.smartwaste.main.SmartWasteApp 2>&1)
if echo "$OUTPUT" | grep -q "Description cannot be empty"; then
    echo "PASS"
    ((PASS++))
else
    echo "FAIL"
    ((FAIL++))
fi

# --------------------------------------------------
# TC_06: Fill level >= 80 accepted (complaint accepted)
# --------------------------------------------------
echo -n "TC_06: Fill level above 80%% accepted ............... "
OUTPUT=$(echo -e "1\ncitizen1\npass123\n1\nBIN001\nMain Street\n85\nOverflow complaint\n3\n2" | java com.smartwaste.main.SmartWasteApp 2>&1)
if echo "$OUTPUT" | grep -q "Complaint submitted successfully" && echo "$OUTPUT" | grep -q "Overflow"; then
    echo "PASS"
    ((PASS++))
else
    echo "FAIL"
    ((FAIL++))
fi

# --------------------------------------------------
# TC_07: Fill level < 80 rejected
# --------------------------------------------------
echo -n "TC_07: Fill level below 80%% rejected ............... "
OUTPUT=$(echo -e "1\ncitizen1\npass123\n1\nBIN001\nMain Street\n50\nTest complaint\n3\n2" | java com.smartwaste.main.SmartWasteApp 2>&1)
if echo "$OUTPUT" | grep -q "Complaint rejected" && echo "$OUTPUT" | grep -q "Empty"; then
    echo "PASS"
    ((PASS++))
else
    echo "FAIL"
    ((FAIL++))
fi

# --------------------------------------------------
# TC_08: Citizen can report bins in own ward
# --------------------------------------------------
echo -n "TC_08: Own ward bin accepted ....................... "
# citizen1 is Ward-A, BIN001 is Ward-A
OUTPUT=$(echo -e "1\ncitizen1\npass123\n1\nBIN001\nWard-A Main Road\n90\nOwn ward bin overflowing\n3\n2" | java com.smartwaste.main.SmartWasteApp 2>&1)
if echo "$OUTPUT" | grep -q "Complaint submitted successfully"; then
    echo "PASS"
    ((PASS++))
else
    echo "FAIL"
    ((FAIL++))
fi

# --------------------------------------------------
# TC_09: Citizen cannot report bin from another ward
# --------------------------------------------------
echo -n "TC_09: Other ward bin denied ....................... "
# citizen1 is Ward-A, BIN003 is Ward-B
OUTPUT=$(echo -e "1\ncitizen1\npass123\n1\nBIN003\nWard-B Street\n90\nCross ward complaint\n3\n2" | java com.smartwaste.main.SmartWasteApp 2>&1)
if echo "$OUTPUT" | grep -q "Access denied" && echo "$OUTPUT" | grep -q "only report bins in your own ward"; then
    echo "PASS"
    ((PASS++))
else
    echo "FAIL"
    ((FAIL++))
fi

# --------------------------------------------------
# TC_10: Duplicate complaint prevented
# --------------------------------------------------
echo -n "TC_10: Duplicate complaint prevented ............... "
# Submit same bin twice without resolving
OUTPUT=$(echo -e "1\ncitizen1\npass123\n1\nBIN001\nMain Street\n85\nFirst complaint\n1\nBIN001\nMain Street\n90\nSecond complaint\n3\n2" | java com.smartwaste.main.SmartWasteApp 2>&1)
if echo "$OUTPUT" | grep -q "Duplicate complaint detected"; then
    echo "PASS"
    ((PASS++))
else
    echo "FAIL"
    ((FAIL++))
fi

# --------------------------------------------------
# TC_11: Valid bin location accepted
# --------------------------------------------------
echo -n "TC_11: Valid location accepted ..................... "
OUTPUT=$(echo -e "1\ncitizen1\npass123\n1\nBIN002\nWard-A Central Park\n85\nBin overflowing\n3\n2" | java com.smartwaste.main.SmartWasteApp 2>&1)
if echo "$OUTPUT" | grep -q "Complaint submitted successfully" && echo "$OUTPUT" | grep -q "Ward-A Central Park"; then
    echo "PASS"
    ((PASS++))
else
    echo "FAIL"
    ((FAIL++))
fi

# --------------------------------------------------
# TC_12: Empty location rejected
# --------------------------------------------------
echo -n "TC_12: Empty location rejected ..................... "
OUTPUT=$(echo -e "1\ncitizen1\npass123\n1\nBIN001\n\n85\nTest complaint\n3\n2" | java com.smartwaste.main.SmartWasteApp 2>&1)
if echo "$OUTPUT" | grep -q "Location is required"; then
    echo "PASS"
    ((PASS++))
else
    echo "FAIL"
    ((FAIL++))
fi

# --------------------------------------------------
# TC_13: Admin receives complaint notification
# --------------------------------------------------
echo -n "TC_13: Admin receives notification ................. "
# Citizen submits, then admin logs in and sees notification
OUTPUT=$(echo -e "1\ncitizen1\npass123\n1\nBIN001\nMain Street\n85\nOverflowing bin\n3\n1\nadmin1\nadmin123\n1\n4\n2" | java com.smartwaste.main.SmartWasteApp 2>&1)
if echo "$OUTPUT" | grep -q "Admin has been notified" && echo "$OUTPUT" | grep -q "ALERT.*New overflowing bin complaint"; then
    echo "PASS"
    ((PASS++))
else
    echo "FAIL"
    ((FAIL++))
fi

# --------------------------------------------------
# TC_14: Admin assigns collection vehicle
# --------------------------------------------------
echo -n "TC_14: Vehicle assigned successfully ............... "
# Citizen submits, admin assigns vehicle
OUTPUT=$(echo -e "1\ncitizen1\npass123\n1\nBIN001\nMain Street\n85\nOverflowing bin\n3\n1\nadmin1\nadmin123\n2\nRPT001\nVH001\n4\n2" | java com.smartwaste.main.SmartWasteApp 2>&1)
if echo "$OUTPUT" | grep -q "Vehicle VH001 assigned to Report RPT001 successfully"; then
    echo "PASS"
    ((PASS++))
else
    echo "FAIL"
    ((FAIL++))
fi

# --------------------------------------------------
# TC_15: Citizen sees resolved status after collection
# --------------------------------------------------
echo -n "TC_15: Citizen sees Resolved status ................ "
# Full flow: citizen submits -> admin assigns -> driver completes -> citizen checks
OUTPUT=$(echo -e "1\ncitizen1\npass123\n1\nBIN001\nMain Street\n85\nOverflowing bin\n3\n1\nadmin1\nadmin123\n2\nRPT001\nVH001\n4\n1\ndriver1\ndriver123\n2\nRPT001\n3\n1\ncitizen1\npass123\n2\n3\n2" | java com.smartwaste.main.SmartWasteApp 2>&1)
if echo "$OUTPUT" | grep -q "Resolved" && echo "$OUTPUT" | grep -q "Task RPT001 marked as completed"; then
    echo "PASS"
    ((PASS++))
else
    echo "FAIL"
    ((FAIL++))
fi

# --------------------------------------------------
# SUMMARY
# --------------------------------------------------
echo ""
echo "======================================================"
echo "  TEST RESULTS SUMMARY"
echo "======================================================"
echo "  Total  : $TOTAL"
echo "  Passed : $PASS"
echo "  Failed : $FAIL"
echo "======================================================"
if [ $FAIL -eq 0 ]; then
    echo "  STATUS: ALL TESTS PASSED!"
else
    echo "  STATUS: SOME TESTS FAILED"
fi
echo "======================================================"
