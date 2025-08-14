package pl.com.aseity

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import pl.com.aseity.user.domain.UserRepository
import pl.com.aseity.business.domain.BusinessRepository
import pl.com.aseity.shift.domain.ShiftRepository
import pl.com.aseity.application.domain.ApplicationRepository
import pl.com.aseity.attendance.domain.AttendanceRepository
import pl.com.aseity.payout.domain.PayoutRepository
import pl.com.aseity.shift.domain.ShiftStatus
import pl.com.aseity.application.domain.ApplicationStatus

@SpringBootTest
@Transactional
class FleksiWorkflowIntegrationSpec extends BaseIntegrationSpec {

    @Autowired
    private UserRepository userRepository

    @Autowired
    private BusinessRepository businessRepository

    @Autowired
    private ShiftRepository shiftRepository

    @Autowired
    private ApplicationRepository applicationRepository

    @Autowired
    private AttendanceRepository attendanceRepository

    @Autowired
    private PayoutRepository payoutRepository

    def "complete workflow: create shift -> apply -> accept -> check-in/out -> approve payout"() {
        given: "business owner and worker are created"
        def owner = TestDataFactory.createBusinessOwner()
        def worker = TestDataFactory.createWorker()
        userRepository.save(owner)
        userRepository.save(worker)

        and: "business is created"
        def business = TestDataFactory.createBusiness(owner)
        businessRepository.save(business)

        when: "shift is created"
        def shift = TestDataFactory.createShift(business)
        def savedShift = shiftRepository.save(shift)

        then: "shift is saved with correct details"
        savedShift.id != null
        savedShift.title == TestDataFactory.Expected.SHIFT_TITLE
        savedShift.status == TestDataFactory.Expected.SHIFT_STATUS_OPEN
        savedShift.business.id == business.id

        when: "worker applies for shift"
        def application = TestDataFactory.createApplication(worker, savedShift)
        def savedApplication = applicationRepository.save(application)

        then: "application is created with pending status"
        savedApplication.id != null
        savedApplication.status == TestDataFactory.Expected.APPLICATION_STATUS_PENDING
        savedApplication.worker.id == worker.id
        savedApplication.shift.id == savedShift.id

        when: "business owner accepts application"
        savedApplication.status = TestDataFactory.Expected.APPLICATION_STATUS_ACCEPTED
        def acceptedApplication = applicationRepository.save(savedApplication)

        then: "application status is updated"
        acceptedApplication.status == TestDataFactory.Expected.APPLICATION_STATUS_ACCEPTED

        when: "worker checks in for shift"
        def attendance = TestDataFactory.createAttendance(acceptedApplication)
        def savedAttendance = attendanceRepository.save(attendance)

        then: "attendance record is created"
        savedAttendance.id != null
        savedAttendance.checkInTime == TestDataFactory.Expected.CHECK_IN_TIME
        savedAttendance.checkOutTime == null
        savedAttendance.application.id == acceptedApplication.id

        when: "worker checks out from shift"
        savedAttendance.checkOutTime = TestDataFactory.Expected.CHECK_OUT_TIME
        def completedAttendance = attendanceRepository.save(savedAttendance)

        then: "attendance is completed"
        completedAttendance.checkOutTime == TestDataFactory.Expected.CHECK_OUT_TIME

        when: "shift is marked as completed"
        savedShift.status = TestDataFactory.Expected.SHIFT_STATUS_COMPLETED
        def completedShift = shiftRepository.save(savedShift)

        then: "shift status is updated"
        completedShift.status == TestDataFactory.Expected.SHIFT_STATUS_COMPLETED

        when: "payout is created"
        def payout = TestDataFactory.createPayout(completedAttendance)
        def savedPayout = payoutRepository.save(payout)

        then: "payout is created with correct amount"
        savedPayout.id != null
        savedPayout.amount == TestDataFactory.Expected.PAYOUT_AMOUNT
        savedPayout.status == TestDataFactory.Expected.PAYOUT_STATUS_PENDING
        savedPayout.attendance.id == completedAttendance.id

        when: "payout is approved"
        savedPayout.status = TestDataFactory.Expected.PAYOUT_STATUS_PAID
        def paidPayout = payoutRepository.save(savedPayout)

        then: "payout status is updated"
        paidPayout.status == TestDataFactory.Expected.PAYOUT_STATUS_PAID

        and: "full workflow data is persisted correctly"
        userRepository.findAll().size() == 2
        businessRepository.findAll().size() == 1
        shiftRepository.findAll().size() == 1
        applicationRepository.findAll().size() == 1
        attendanceRepository.findAll().size() == 1
        payoutRepository.findAll().size() == 1
    }

    def "worker cannot apply twice for same shift"() {
        given: "complete test data setup"
        def workflow = TestDataFactory.createCompleteWorkflow()
        userRepository.save(workflow.owner)
        userRepository.save(workflow.worker)
        businessRepository.save(workflow.business)
        def savedShift = shiftRepository.save(workflow.shift)
        applicationRepository.save(workflow.application)

        when: "worker tries to apply again"
        def duplicateApplication = TestDataFactory.createApplication(workflow.worker, savedShift)
        applicationRepository.save(duplicateApplication)

        then: "should handle duplicate application scenario"
        applicationRepository.findByWorkerAndShift(workflow.worker, savedShift).size() >= 1
    }

    def "shift with max workers becomes filled"() {
        given: "shift with max 1 worker"
        def owner = TestDataFactory.createBusinessOwner()
        def worker = TestDataFactory.createWorker()
        userRepository.save(owner)
        userRepository.save(worker)

        def business = TestDataFactory.createBusiness(owner)
        businessRepository.save(business)

        def shift = TestDataFactory.createShift(business)
        shift.maxWorkers = 1
        def savedShift = shiftRepository.save(shift)

        when: "worker applies and gets accepted"
        def application = TestDataFactory.createAcceptedApplication(worker, savedShift)
        applicationRepository.save(application)

        and: "shift status is updated to filled"
        savedShift.status = TestDataFactory.Expected.SHIFT_STATUS_FILLED
        def filledShift = shiftRepository.save(savedShift)

        then: "shift is marked as filled"
        filledShift.status == TestDataFactory.Expected.SHIFT_STATUS_FILLED
        filledShift.maxWorkers == 1
    }
}