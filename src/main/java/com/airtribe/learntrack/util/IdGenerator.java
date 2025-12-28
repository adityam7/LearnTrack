package com.airtribe.learntrack.util;

import com.airtribe.learntrack.exception.IdRangeExhaustedException;

/**
 * ID Generator with range-based allocation and collision handling
 *
 * Range Allocation Strategy:
 * - Persons:     100 - 999    (900 IDs)
 * - Students:    1000 - 1999  (1000 IDs)
 * - Courses:     2000 - 2999  (1000 IDs)
 * - Enrollments: 3000 - 3999  (1000 IDs)
 * - Trainers:    4000 - 4999 (1000 IDs)
 *
 * This prevents ID collision between entity types and enforces capacity limits
 */
public class IdGenerator {

    // ==================== RANGE CONFIGURATION ====================

    // Person ID Range
    private static final int PERSON_ID_MIN = 100;
    private static final int PERSON_ID_MAX = 999;
    private static final int PERSON_ID_CAPACITY = PERSON_ID_MAX - PERSON_ID_MIN + 1;

    // Student ID Range
    private static final int STUDENT_ID_MIN = 1000;
    private static final int STUDENT_ID_MAX = 1999;
    private static final int STUDENT_ID_CAPACITY = STUDENT_ID_MAX - STUDENT_ID_MIN + 1;

    // Course ID Range
    private static final int COURSE_ID_MIN = 2000;
    private static final int COURSE_ID_MAX = 2999;
    private static final int COURSE_ID_CAPACITY = COURSE_ID_MAX - COURSE_ID_MIN + 1;

    // Enrollment ID Range
    private static final int ENROLLMENT_ID_MIN = 3000;
    private static final int ENROLLMENT_ID_MAX = 3999;
    private static final int ENROLLMENT_ID_CAPACITY = ENROLLMENT_ID_MAX - ENROLLMENT_ID_MIN + 1;

    // Trainer ID Range
    private static final int TRAINER_ID_MIN = 4000;
    private static final int TRAINER_ID_MAX = 4999;
    private static final int TRAINER_ID_CAPACITY = TRAINER_ID_MAX - TRAINER_ID_MIN + 1;

    // Warning threshold (alert when 90% capacity reached)
    private static final double WARNING_THRESHOLD = 0.9;

    // ==================== COUNTERS ====================

    private static int personIdCounter = PERSON_ID_MIN;
    private static int studentIdCounter = STUDENT_ID_MIN;
    private static int courseIdCounter = COURSE_ID_MIN;
    private static int enrollmentIdCounter = ENROLLMENT_ID_MIN;
    private static int trainerIdCounter = TRAINER_ID_MIN;

    // Track how many IDs have been issued
    private static int personIdsIssued = 0;
    private static int studentIdsIssued = 0;
    private static int courseIdsIssued = 0;
    private static int enrollmentIdsIssued = 0;
    private static int trainerIdsIssued = 0;

    // ==================== CONSTRUCTOR ====================

    private IdGenerator() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    // ==================== PERSON ID GENERATION ====================

    /**
     * Generates next unique Person ID within allocated range
     *
     * @return unique person ID
     * @throws IdRangeExhaustedException if all IDs in range are used
     */
    public static synchronized int getNextPersonId() {
        validatePersonIdAvailable();

        int id = personIdCounter++;
        personIdsIssued++;

        checkPersonCapacityWarning();

        return id;
    }

    /**
     * Validates a manually assigned Person ID is within valid range
     *
     * @param id ID to validate
     * @throws IllegalArgumentException if ID is outside valid range
     */
    public static void validatePersonId(int id) {
        if (id < PERSON_ID_MIN || id > PERSON_ID_MAX) {
            throw new IllegalArgumentException(
                    String.format("Person ID %d is outside valid range [%d-%d]",
                            id, PERSON_ID_MIN, PERSON_ID_MAX)
            );
        }
    }

    private static void validatePersonIdAvailable() {
        if (personIdCounter > PERSON_ID_MAX) {
            throw new IdRangeExhaustedException(
                    String.format("Person ID range exhausted! Maximum capacity of %d persons reached. " +
                                    "Range [%d-%d] is full.",
                            PERSON_ID_CAPACITY, PERSON_ID_MIN, PERSON_ID_MAX)
            );
        }
    }

    private static void checkPersonCapacityWarning() {
        double usagePercent = (double) personIdsIssued / PERSON_ID_CAPACITY;

        if (usagePercent >= WARNING_THRESHOLD) {
            int remaining = getRemainingPersonIds();
            System.err.println(
                    String.format("⚠️  WARNING: Person ID capacity at %.1f%% - Only %d IDs remaining!",
                            usagePercent * 100, remaining)
            );
        }
    }

    // ==================== STUDENT ID GENERATION ====================

    /**
     * Generates next unique Student ID within allocated range
     *
     * @return unique student ID
     * @throws IdRangeExhaustedException if all IDs in range are used
     */
    public static synchronized int getNextStudentId() {
        // Validate range not exhausted
        validateStudentIdAvailable();

        int id = studentIdCounter++;
        studentIdsIssued++;

        // Warn if approaching capacity
        checkStudentCapacityWarning();

        return id;
    }

    /**
     * Validates a manually assigned Student ID is within valid range
     *
     * @param id ID to validate
     * @throws IllegalArgumentException if ID is outside valid range
     */
    public static void validateStudentId(int id) {
        if (id < STUDENT_ID_MIN || id > STUDENT_ID_MAX) {
            throw new IllegalArgumentException(
                    String.format("Student ID %d is outside valid range [%d-%d]",
                            id, STUDENT_ID_MIN, STUDENT_ID_MAX)
            );
        }
    }

    /**
     * Checks if Student ID range is exhausted
     *
     * @throws IdRangeExhaustedException if no more IDs available
     */
    private static void validateStudentIdAvailable() {
        if (studentIdCounter > STUDENT_ID_MAX) {
            throw new IdRangeExhaustedException(
                    String.format("Student ID range exhausted! Maximum capacity of %d students reached. " +
                                    "Range [%d-%d] is full.",
                            STUDENT_ID_CAPACITY, STUDENT_ID_MIN, STUDENT_ID_MAX)
            );
        }
    }

    /**
     * Warns when approaching Student ID capacity limit
     */
    private static void checkStudentCapacityWarning() {
        double usagePercent = (double) studentIdsIssued / STUDENT_ID_CAPACITY;

        if (usagePercent >= WARNING_THRESHOLD) {
            int remaining = getRemainingStudentIds();
            System.err.println(
                    String.format("⚠️  WARNING: Student ID capacity at %.1f%% - Only %d IDs remaining!",
                            usagePercent * 100, remaining)
            );
        }
    }

    // ==================== COURSE ID GENERATION ====================

    /**
     * Generates next unique Course ID within allocated range
     *
     * @return unique course ID
     * @throws IdRangeExhaustedException if all IDs in range are used
     */
    public static synchronized int getNextCourseId() {
        validateCourseIdAvailable();

        int id = courseIdCounter++;
        courseIdsIssued++;

        checkCourseCapacityWarning();

        return id;
    }

    /**
     * Validates a manually assigned Course ID is within valid range
     *
     * @param id ID to validate
     * @throws IllegalArgumentException if ID is outside valid range
     */
    public static void validateCourseId(int id) {
        if (id < COURSE_ID_MIN || id > COURSE_ID_MAX) {
            throw new IllegalArgumentException(
                    String.format("Course ID %d is outside valid range [%d-%d]",
                            id, COURSE_ID_MIN, COURSE_ID_MAX)
            );
        }
    }

    private static void validateCourseIdAvailable() {
        if (courseIdCounter > COURSE_ID_MAX) {
            throw new IdRangeExhaustedException(
                    String.format("Course ID range exhausted! Maximum capacity of %d courses reached. " +
                                    "Range [%d-%d] is full.",
                            COURSE_ID_CAPACITY, COURSE_ID_MIN, COURSE_ID_MAX)
            );
        }
    }

    private static void checkCourseCapacityWarning() {
        double usagePercent = (double) courseIdsIssued / COURSE_ID_CAPACITY;

        if (usagePercent >= WARNING_THRESHOLD) {
            int remaining = getRemainingCourseIds();
            System.err.println(
                    String.format("⚠️  WARNING: Course ID capacity at %.1f%% - Only %d IDs remaining!",
                            usagePercent * 100, remaining)
            );
        }
    }

    // ==================== ENROLLMENT ID GENERATION ====================

    /**
     * Generates next unique Enrollment ID within allocated range
     *
     * @return unique enrollment ID
     * @throws IdRangeExhaustedException if all IDs in range are used
     */
    public static synchronized int getNextEnrollmentId() {
        validateEnrollmentIdAvailable();

        int id = enrollmentIdCounter++;
        enrollmentIdsIssued++;

        checkEnrollmentCapacityWarning();

        return id;
    }

    /**
     * Validates a manually assigned Enrollment ID is within valid range
     *
     * @param id ID to validate
     * @throws IllegalArgumentException if ID is outside valid range
     */
    public static void validateEnrollmentId(int id) {
        if (id < ENROLLMENT_ID_MIN || id > ENROLLMENT_ID_MAX) {
            throw new IllegalArgumentException(
                    String.format("Enrollment ID %d is outside valid range [%d-%d]",
                            id, ENROLLMENT_ID_MIN, ENROLLMENT_ID_MAX)
            );
        }
    }

    private static void validateEnrollmentIdAvailable() {
        if (enrollmentIdCounter > ENROLLMENT_ID_MAX) {
            throw new IdRangeExhaustedException(
                    String.format("Enrollment ID range exhausted! Maximum capacity of %d enrollments reached. " +
                                    "Range [%d-%d] is full.",
                            ENROLLMENT_ID_CAPACITY, ENROLLMENT_ID_MIN, ENROLLMENT_ID_MAX)
            );
        }
    }

    private static void checkEnrollmentCapacityWarning() {
        double usagePercent = (double) enrollmentIdsIssued / ENROLLMENT_ID_CAPACITY;

        if (usagePercent >= WARNING_THRESHOLD) {
            int remaining = getRemainingEnrollmentIds();
            System.err.println(
                    String.format("⚠️  WARNING: Enrollment ID capacity at %.1f%% - Only %d IDs remaining!",
                            usagePercent * 100, remaining)
            );
        }
    }

    // ==================== TRAINER ID GENERATION ====================

    /**
     * Generates next unique Trainer ID within allocated range
     *
     * @return unique trainer ID
     * @throws IdRangeExhaustedException if all IDs in range are used
     */
    public static synchronized int getNextTrainerId() {
        validateTrainerIdAvailable();

        int id = trainerIdCounter++;
        trainerIdsIssued++;

        checkTrainerCapacityWarning();

        return id;
    }

    /**
     * Validates a manually assigned Trainer ID is within valid range
     *
     * @param id ID to validate
     * @throws IllegalArgumentException if ID is outside valid range
     */
    public static void validateTrainerId(int id) {
        if (id < TRAINER_ID_MIN || id > TRAINER_ID_MAX) {
            throw new IllegalArgumentException(
                    String.format("Trainer ID %d is outside valid range [%d-%d]",
                            id, TRAINER_ID_MIN, TRAINER_ID_MAX)
            );
        }
    }

    private static void validateTrainerIdAvailable() {
        if (trainerIdCounter > TRAINER_ID_MAX) {
            throw new IdRangeExhaustedException(
                    String.format("Trainer ID range exhausted! Maximum capacity of %d trainers reached. " +
                                    "Range [%d-%d] is full.",
                            TRAINER_ID_CAPACITY, TRAINER_ID_MIN, TRAINER_ID_MAX)
            );
        }
    }

    private static void checkTrainerCapacityWarning() {
        double usagePercent = (double) trainerIdsIssued / TRAINER_ID_CAPACITY;

        if (usagePercent >= WARNING_THRESHOLD) {
            int remaining = getRemainingTrainerIds();
            System.err.println(
                    String.format("⚠️  WARNING: Trainer ID capacity at %.1f%% - Only %d IDs remaining!",
                            usagePercent * 100, remaining)
            );
        }
    }

    // ==================== CAPACITY QUERIES ====================

    /**
     * Gets remaining Person IDs available
     *
     * @return number of IDs remaining in range
     */
    public static int getRemainingPersonIds() {
        return PERSON_ID_MAX - personIdCounter + 1;
    }

    /**
     * Gets remaining Student IDs available
     *
     * @return number of IDs remaining in range
     */
    public static int getRemainingStudentIds() {
        return STUDENT_ID_MAX - studentIdCounter + 1;
    }

    /**
     * Gets remaining Course IDs available
     *
     * @return number of IDs remaining in range
     */
    public static int getRemainingCourseIds() {
        return COURSE_ID_MAX - courseIdCounter + 1;
    }

    /**
     * Gets remaining Enrollment IDs available
     *
     * @return number of IDs remaining in range
     */
    public static int getRemainingEnrollmentIds() {
        return ENROLLMENT_ID_MAX - enrollmentIdCounter + 1;
    }

    /**
     * Gets remaining Trainer IDs available
     *
     * @return number of IDs remaining in range
     */
    public static int getRemainingTrainerIds() {
        return TRAINER_ID_MAX - trainerIdCounter + 1;
    }

    /**
     * Gets total Person ID capacity
     */
    public static int getPersonIdCapacity() {
        return PERSON_ID_CAPACITY;
    }

    /**
     * Gets total Student ID capacity
     */
    public static int getStudentIdCapacity() {
        return STUDENT_ID_CAPACITY;
    }

    /**
     * Gets total Course ID capacity
     */
    public static int getCourseIdCapacity() {
        return COURSE_ID_CAPACITY;
    }

    /**
     * Gets total Enrollment ID capacity
     */
    public static int getEnrollmentIdCapacity() {
        return ENROLLMENT_ID_CAPACITY;
    }

    /**
     * Gets total Trainer ID capacity
     */
    public static int getTrainerIdCapacity() {
        return TRAINER_ID_CAPACITY;
    }

    /**
     * Gets percentage of Person IDs used
     *
     * @return usage percentage (0.0 to 1.0)
     */
    public static double getPersonIdUsagePercent() {
        return (double) personIdsIssued / PERSON_ID_CAPACITY;
    }

    /**
     * Gets percentage of Student IDs used
     *
     * @return usage percentage (0.0 to 1.0)
     */
    public static double getStudentIdUsagePercent() {
        return (double) studentIdsIssued / STUDENT_ID_CAPACITY;
    }

    /**
     * Gets percentage of Course IDs used
     *
     * @return usage percentage (0.0 to 1.0)
     */
    public static double getCourseIdUsagePercent() {
        return (double) courseIdsIssued / COURSE_ID_CAPACITY;
    }

    /**
     * Gets percentage of Enrollment IDs used
     *
     * @return usage percentage (0.0 to 1.0)
     */
    public static double getEnrollmentIdUsagePercent() {
        return (double) enrollmentIdsIssued / ENROLLMENT_ID_CAPACITY;
    }

    /**
     * Gets percentage of Trainer IDs used
     *
     * @return usage percentage (0.0 to 1.0)
     */
    public static double getTrainerIdUsagePercent() {
        return (double) trainerIdsIssued / TRAINER_ID_CAPACITY;
    }

    // ==================== SYNCHRONIZATION & IMPORT ====================

    /**
     * Registers an externally created Person ID (for data import)
     * Validates range and updates counter
     *
     * @param id Person ID to register
     * @throws IllegalArgumentException if ID is outside valid range
     * @throws IdRangeExhaustedException if this would exceed capacity
     */
    public static synchronized void registerPersonId(int id) {
        validatePersonId(id);

        if (personIdsIssued >= PERSON_ID_CAPACITY) {
            throw new IdRangeExhaustedException(
                    "Cannot register Person ID - capacity already reached"
            );
        }

        personIdsIssued++;

        if (id >= personIdCounter) {
            personIdCounter = id + 1;

            if (personIdCounter > PERSON_ID_MAX) {
                System.err.println("⚠️  WARNING: Person ID counter at maximum range limit!");
            }
        }
    }

    /**
     * Registers an externally created Student ID (for data import)
     * Validates range and updates counter
     *
     * @param id Student ID to register
     * @throws IllegalArgumentException if ID is outside valid range
     * @throws IdRangeExhaustedException if this would exceed capacity
     */
    public static synchronized void registerStudentId(int id) {
        validateStudentId(id);

        // Check if this would exceed capacity
        if (studentIdsIssued >= STUDENT_ID_CAPACITY) {
            throw new IdRangeExhaustedException(
                    "Cannot register Student ID - capacity already reached"
            );
        }

        studentIdsIssued++;

        // Advance counter past this ID if necessary
        if (id >= studentIdCounter) {
            studentIdCounter = id + 1;

            // Check if advancing counter exceeds range
            if (studentIdCounter > STUDENT_ID_MAX) {
                System.err.println("⚠️  WARNING: Student ID counter at maximum range limit!");
            }
        }
    }

    /**
     * Registers an externally created Course ID
     */
    public static synchronized void registerCourseId(int id) {
        validateCourseId(id);

        if (courseIdsIssued >= COURSE_ID_CAPACITY) {
            throw new IdRangeExhaustedException(
                    "Cannot register Course ID - capacity already reached"
            );
        }

        courseIdsIssued++;

        if (id >= courseIdCounter) {
            courseIdCounter = id + 1;

            if (courseIdCounter > COURSE_ID_MAX) {
                System.err.println("⚠️  WARNING: Course ID counter at maximum range limit!");
            }
        }
    }

    /**
     * Registers an externally created Enrollment ID
     */
    public static synchronized void registerEnrollmentId(int id) {
        validateEnrollmentId(id);

        if (enrollmentIdsIssued >= ENROLLMENT_ID_CAPACITY) {
            throw new IdRangeExhaustedException(
                    "Cannot register Enrollment ID - capacity already reached"
            );
        }

        enrollmentIdsIssued++;

        if (id >= enrollmentIdCounter) {
            enrollmentIdCounter = id + 1;

            if (enrollmentIdCounter > ENROLLMENT_ID_MAX) {
                System.err.println("⚠️  WARNING: Enrollment ID counter at maximum range limit!");
            }
        }
    }

    /**
     * Registers an externally created Trainer ID
     */
    public static synchronized void registerTrainerId(int id) {
        validateTrainerId(id);

        if (trainerIdsIssued >= TRAINER_ID_CAPACITY) {
            throw new IdRangeExhaustedException(
                    "Cannot register Trainer ID - capacity already reached"
            );
        }

        trainerIdsIssued++;

        if (id >= trainerIdCounter) {
            trainerIdCounter = id + 1;

            if (trainerIdCounter > TRAINER_ID_MAX) {
                System.err.println("⚠️  WARNING: Trainer ID counter at maximum range limit!");
            }
        }
    }

    // ==================== DIAGNOSTICS & MONITORING ====================

    /**
     * Prints comprehensive capacity report to console
     */
    public static void printCapacityReport() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║         ID GENERATOR CAPACITY REPORT           ║");
        System.out.println("╠════════════════════════════════════════════════╣");

        // Persons
        System.out.printf("║ PERSONS:                                       ║%n");
        System.out.printf("║   Range:      [%d - %d]                    ║%n",
                PERSON_ID_MIN, PERSON_ID_MAX);
        System.out.printf("║   Capacity:   %d IDs                           ║%n",
                PERSON_ID_CAPACITY);
        System.out.printf("║   Used:       %d IDs (%.1f%%)                   ║%n",
                personIdsIssued, getPersonIdUsagePercent() * 100);
        System.out.printf("║   Remaining:  %d IDs                           ║%n",
                getRemainingPersonIds());
        System.out.printf("║   Next ID:    %d                               ║%n",
                personIdCounter <= PERSON_ID_MAX ? personIdCounter : -1);

        System.out.println("║                                                ║");

        // Students
        System.out.printf("║ STUDENTS:                                      ║%n");
        System.out.printf("║   Range:      [%d - %d]                  ║%n",
                STUDENT_ID_MIN, STUDENT_ID_MAX);
        System.out.printf("║   Capacity:   %d IDs                          ║%n",
                STUDENT_ID_CAPACITY);
        System.out.printf("║   Used:       %d IDs (%.1f%%)                  ║%n",
                studentIdsIssued, getStudentIdUsagePercent() * 100);
        System.out.printf("║   Remaining:  %d IDs                          ║%n",
                getRemainingStudentIds());
        System.out.printf("║   Next ID:    %d                              ║%n",
                studentIdCounter <= STUDENT_ID_MAX ? studentIdCounter : -1);

        System.out.println("║                                                ║");

        // Courses
        System.out.printf("║ COURSES:                                       ║%n");
        System.out.printf("║   Range:      [%d - %d]                  ║%n",
                COURSE_ID_MIN, COURSE_ID_MAX);
        System.out.printf("║   Capacity:   %d IDs                          ║%n",
                COURSE_ID_CAPACITY);
        System.out.printf("║   Used:       %d IDs (%.1f%%)                  ║%n",
                courseIdsIssued, getCourseIdUsagePercent() * 100);
        System.out.printf("║   Remaining:  %d IDs                          ║%n",
                getRemainingCourseIds());
        System.out.printf("║   Next ID:    %d                              ║%n",
                courseIdCounter <= COURSE_ID_MAX ? courseIdCounter : -1);

        System.out.println("║                                                ║");

        // Enrollments
        System.out.printf("║ ENROLLMENTS:                                   ║%n");
        System.out.printf("║   Range:      [%d - %d]                  ║%n",
                ENROLLMENT_ID_MIN, ENROLLMENT_ID_MAX);
        System.out.printf("║   Capacity:   %d IDs                          ║%n",
                ENROLLMENT_ID_CAPACITY);
        System.out.printf("║   Used:       %d IDs (%.1f%%)                 ║%n",
                enrollmentIdsIssued, getEnrollmentIdUsagePercent() * 100);
        System.out.printf("║   Remaining:  %d IDs                          ║%n",
                getRemainingEnrollmentIds());
        System.out.printf("║   Next ID:    %d                              ║%n",
                enrollmentIdCounter <= ENROLLMENT_ID_MAX ? enrollmentIdCounter : -1);

        System.out.println("║                                                ║");

        // Trainers
        System.out.printf("║ TRAINERS:                                      ║%n");
        System.out.printf("║   Range:      [%d - %d]                 ║%n",
                TRAINER_ID_MIN, TRAINER_ID_MAX);
        System.out.printf("║   Capacity:   %d IDs                          ║%n",
                TRAINER_ID_CAPACITY);
        System.out.printf("║   Used:       %d IDs (%.1f%%)                  ║%n",
                trainerIdsIssued, getTrainerIdUsagePercent() * 100);
        System.out.printf("║   Remaining:  %d IDs                          ║%n",
                getRemainingTrainerIds());
        System.out.printf("║   Next ID:    %d                             ║%n",
                trainerIdCounter <= TRAINER_ID_MAX ? trainerIdCounter : -1);

        System.out.println("╚════════════════════════════════════════════════╝\n");
    }

    /**
     * Checks if any ID ranges are approaching capacity
     *
     * @return true if any range is >= 90% full
     */
    public static boolean isApproachingCapacity() {
        return getPersonIdUsagePercent() >= WARNING_THRESHOLD
                || getStudentIdUsagePercent() >= WARNING_THRESHOLD
                || getCourseIdUsagePercent() >= WARNING_THRESHOLD
                || getEnrollmentIdUsagePercent() >= WARNING_THRESHOLD
                || getTrainerIdUsagePercent() >= WARNING_THRESHOLD;
    }

    /**
     * Gets a summary string of current capacity status
     */
    public static String getCapacitySummary() {
        return String.format(
                "Persons: %d/%d (%.1f%%) | Students: %d/%d (%.1f%%) | Courses: %d/%d (%.1f%%) | Enrollments: %d/%d (%.1f%%) | Trainers: %d/%d (%.1f%%)",
                personIdsIssued, PERSON_ID_CAPACITY, getPersonIdUsagePercent() * 100,
                studentIdsIssued, STUDENT_ID_CAPACITY, getStudentIdUsagePercent() * 100,
                courseIdsIssued, COURSE_ID_CAPACITY, getCourseIdUsagePercent() * 100,
                enrollmentIdsIssued, ENROLLMENT_ID_CAPACITY, getEnrollmentIdUsagePercent() * 100,
                trainerIdsIssued, TRAINER_ID_CAPACITY, getTrainerIdUsagePercent() * 100
        );
    }

    // ==================== TESTING & RESET ====================

    /**
     * Resets all counters to initial state
     * ⚠️  WARNING: Only use for testing!
     */
    public static synchronized void resetCounters() {
        personIdCounter = PERSON_ID_MIN;
        studentIdCounter = STUDENT_ID_MIN;
        courseIdCounter = COURSE_ID_MIN;
        enrollmentIdCounter = ENROLLMENT_ID_MIN;
        trainerIdCounter = TRAINER_ID_MIN;
        personIdsIssued = 0;
        studentIdsIssued = 0;
        courseIdsIssued = 0;
        enrollmentIdsIssued = 0;
        trainerIdsIssued = 0;

        System.out.println("⚠️  ID Generator counters have been reset!");
    }
}
