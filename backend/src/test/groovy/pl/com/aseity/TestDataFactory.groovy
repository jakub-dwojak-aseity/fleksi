package pl.com.aseity

import lombok.AccessLevel
import lombok.NoArgsConstructor
import pl.com.aseity.user.domain.User
import pl.com.aseity.user.domain.UserRole
import pl.com.aseity.business.domain.Business
import pl.com.aseity.shift.domain.Shift
import pl.com.aseity.shift.domain.ShiftStatus
import pl.com.aseity.application.domain.Application
import pl.com.aseity.application.domain.ApplicationStatus
import pl.com.aseity.attendance.domain.Attendance
import pl.com.aseity.payout.domain.Payout
import pl.com.aseity.payout.domain.PayoutStatus

import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDateTime

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class TestDataFactory {

    // Expected constants for test assertions
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    static final class Expected {
        // User constants
        static final String WORKER_EMAIL = "worker@example.com"
        static final String WORKER_PHONE = "+48123456789"
        static final String WORKER_FIRST_NAME = "Jan"
        static final String WORKER_LAST_NAME = "Kowalski"
        static final UserRole WORKER_ROLE = UserRole.WORKER
        static final Boolean WORKER_VERIFIED = true

        static final String BUSINESS_OWNER_EMAIL = "owner@business.com"
        static final String BUSINESS_OWNER_PHONE = "+48987654321"
        static final String BUSINESS_OWNER_FIRST_NAME = "Anna"
        static final String BUSINESS_OWNER_LAST_NAME = "Nowak"
        static final UserRole BUSINESS_OWNER_ROLE = UserRole.BUSINESS_OWNER
        static final Boolean BUSINESS_OWNER_VERIFIED = true

        // Business constants
        static final String BUSINESS_NAME = "Test Restaurant"
        static final String BUSINESS_NIP = "1234567890"
        static final String BUSINESS_ADDRESS = "ul. Testowa 123, 00-001 Warszawa"
        static final String BUSINESS_PHONE = "+48111222333"
        static final String BUSINESS_EMAIL = "contact@testrestaurant.com"

        // Shift constants
        static final String SHIFT_TITLE = "Kitchen Helper"
        static final String SHIFT_DESCRIPTION = "Help with food preparation and cleaning"
        static final String SHIFT_LOCATION = "Restaurant Kitchen"
        static final BigDecimal SHIFT_HOURLY_RATE = new BigDecimal("25.00")
        static final Integer SHIFT_MAX_WORKERS = 3
        static final ShiftStatus SHIFT_STATUS_OPEN = ShiftStatus.OPEN
        static final ShiftStatus SHIFT_STATUS_FILLED = ShiftStatus.FILLED
        static final ShiftStatus SHIFT_STATUS_COMPLETED = ShiftStatus.COMPLETED

        // Application constants
        static final ApplicationStatus APPLICATION_STATUS_PENDING = ApplicationStatus.PENDING
        static final ApplicationStatus APPLICATION_STATUS_ACCEPTED = ApplicationStatus.ACCEPTED
        static final ApplicationStatus APPLICATION_STATUS_REJECTED = ApplicationStatus.REJECTED

        // Payout constants
        static final BigDecimal PAYOUT_AMOUNT = new BigDecimal("200.00")
        static final PayoutStatus PAYOUT_STATUS_PENDING = PayoutStatus.PENDING
        static final PayoutStatus PAYOUT_STATUS_PAID = PayoutStatus.PAID

        // Date/time constants
        static final LocalDateTime SHIFT_START_TIME = LocalDateTime.of(2024, 1, 15, 8, 0)
        static final LocalDateTime SHIFT_END_TIME = LocalDateTime.of(2024, 1, 15, 16, 0)
        static final Instant NOW = Instant.parse("2024-01-10T10:00:00Z")
        static final Instant CHECK_IN_TIME = Instant.parse("2024-01-15T08:00:00Z")
        static final Instant CHECK_OUT_TIME = Instant.parse("2024-01-15T16:00:00Z")
    }

    static User createWorker() {
        return User.builder()
                .email(Expected.WORKER_EMAIL)
                .phone(Expected.WORKER_PHONE)
                .firstName(Expected.WORKER_FIRST_NAME)
                .lastName(Expected.WORKER_LAST_NAME)
                .role(Expected.WORKER_ROLE)
                .verified(Expected.WORKER_VERIFIED)
                .build()
    }

    static User createBusinessOwner() {
        return User.builder()
                .email(Expected.BUSINESS_OWNER_EMAIL)
                .phone(Expected.BUSINESS_OWNER_PHONE)
                .firstName(Expected.BUSINESS_OWNER_FIRST_NAME)
                .lastName(Expected.BUSINESS_OWNER_LAST_NAME)
                .role(Expected.BUSINESS_OWNER_ROLE)
                .verified(Expected.BUSINESS_OWNER_VERIFIED)
                .build()
    }

    static Business createBusiness(User owner) {
        return Business.builder()
                .name(Expected.BUSINESS_NAME)
                .nip(Expected.BUSINESS_NIP)
                .address(Expected.BUSINESS_ADDRESS)
                .phone(Expected.BUSINESS_PHONE)
                .email(Expected.BUSINESS_EMAIL)
                .owner(owner)
                .build()
    }

    static Shift createShift(Business business) {
        return Shift.builder()
                .title(Expected.SHIFT_TITLE)
                .description(Expected.SHIFT_DESCRIPTION)
                .location(Expected.SHIFT_LOCATION)
                .startTime(Expected.SHIFT_START_TIME)
                .endTime(Expected.SHIFT_END_TIME)
                .hourlyRate(Expected.SHIFT_HOURLY_RATE)
                .maxWorkers(Expected.SHIFT_MAX_WORKERS)
                .status(Expected.SHIFT_STATUS_OPEN)
                .business(business)
                .build()
    }

    static Application createApplication(User worker, Shift shift) {
        return Application.builder()
                .worker(worker)
                .shift(shift)
                .status(Expected.APPLICATION_STATUS_PENDING)
                .build()
    }

    static Application createAcceptedApplication(User worker, Shift shift) {
        return Application.builder()
                .worker(worker)
                .shift(shift)
                .status(Expected.APPLICATION_STATUS_ACCEPTED)
                .build()
    }

    static Attendance createAttendance(Application application) {
        return Attendance.builder()
                .application(application)
                .checkInTime(Expected.CHECK_IN_TIME)
                .build()
    }

    static Attendance createCompletedAttendance(Application application) {
        return Attendance.builder()
                .application(application)
                .checkInTime(Expected.CHECK_IN_TIME)
                .checkOutTime(Expected.CHECK_OUT_TIME)
                .build()
    }

    static Payout createPayout(Attendance attendance) {
        return Payout.builder()
                .attendance(attendance)
                .amount(Expected.PAYOUT_AMOUNT)
                .status(Expected.PAYOUT_STATUS_PENDING)
                .build()
    }

    static Payout createPaidPayout(Attendance attendance) {
        return Payout.builder()
                .attendance(attendance)
                .amount(Expected.PAYOUT_AMOUNT)
                .status(Expected.PAYOUT_STATUS_PAID)
                .build()
    }

    // Helper methods for creating test scenarios
    static Map<String, Object> createCompleteWorkflow() {
        def owner = createBusinessOwner()
        def worker = createWorker()
        def business = createBusiness(owner)
        def shift = createShift(business)
        def application = createAcceptedApplication(worker, shift)
        def attendance = createCompletedAttendance(application)
        def payout = createPaidPayout(attendance)

        return [
                owner: owner,
                worker: worker,
                business: business,
                shift: shift,
                application: application,
                attendance: attendance,
                payout: payout
        ]
    }
}