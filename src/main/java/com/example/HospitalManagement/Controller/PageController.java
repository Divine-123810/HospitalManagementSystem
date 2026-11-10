package com.example.HospitalManagement.Controller;

import com.example.HospitalManagement.Model.Appointment;
import com.example.HospitalManagement.Model.Appointment.AppointmentStatus;
import com.example.HospitalManagement.Model.Doctors;
import com.example.HospitalManagement.Model.patients;
import com.example.HospitalManagement.Service.AppointmentService;
import com.example.HospitalManagement.Service.DoctorService;
import com.example.HospitalManagement.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PageController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/")
    public String home(@RequestParam(required = false) String searchBy,
                       @RequestParam(required = false) String value, Model model) {

        List<Appointment> appointments = null;

        if (searchBy != null && value != null && !value.isBlank()) {
            try {
                if (searchBy.equals("date")) {
                    appointments = appointmentService.getAppointmentsByDate(value);                      // derived query
                } else if (searchBy.equals("doctor")) {
                    appointments = appointmentService.getAppointmentsByDoctor(Long.parseLong(value));    // JPQL
                } else if (searchBy.equals("patient")) {
                    appointments = appointmentService.getAppointmentsByPatient(Long.parseLong(value));   // native SQL
                }
            } catch (NumberFormatException e) {
                model.addAttribute("error", "Doctor and patient IDs must be numbers.");
            }
        }

        if (appointments == null) {
            appointments = appointmentService.getAllAppointments();
        }

        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("doctors", doctorService.getAllDoctors());
        model.addAttribute("appointments", appointments);
        model.addAttribute("statuses", AppointmentStatus.values());
        model.addAttribute("searchBy", searchBy);
        model.addAttribute("value", value);
        return "index";
    }

    @PostMapping("/web/patients")
    public String addPatient(@RequestParam String name,
                             @RequestParam String email,
                             RedirectAttributes ra) {
        try {
            patientService.savePatient(new patients(null, name, email));
            ra.addFlashAttribute("success", "Patient added.");
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("error", "Could not save patient (email may already exist).");
        }
        return "redirect:/";
    }

    @PostMapping("/web/patients/{id}/delete")
    public String deletePatient(@PathVariable Long id, RedirectAttributes ra) {
        patientService.deletePatient(id);
        ra.addFlashAttribute("success", "Patient deleted.");
        return "redirect:/";
    }

    @PostMapping("/web/doctors")
    public String addDoctor(@RequestParam String name,
                            @RequestParam String email,
                            RedirectAttributes ra) {
        try {
            doctorService.saveDoctor(new Doctors(null, name, email));
            ra.addFlashAttribute("success", "Doctor added.");
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("error", "Could not save doctor (email may already exist).");
        }
        return "redirect:/";
    }

    @PostMapping("/web/doctors/{id}/delete")
    public String deleteDoctor(@PathVariable Long  id, RedirectAttributes ra) {
        doctorService.deleteDoctor(id);
        ra.addFlashAttribute("success", "Doctor deleted.");
        return "redirect:/";
    }

    @PostMapping("/web/appointments")
    public String addAppointment(@RequestParam Long patientId,
                                 @RequestParam Long doctorId,
                                 @RequestParam String date,
                                 @RequestParam String time,
                                 @RequestParam AppointmentStatus status,
                                 RedirectAttributes ra) {
        appointmentService.saveAppointment(new Appointment(patientId, doctorId, date, time, status));
        ra.addFlashAttribute("success", "Appointment added.");
        return "redirect:/";
    }

    @PostMapping("/web/appointments/{id}/delete")
    public String deleteAppointment(@PathVariable Long id, RedirectAttributes ra) {
        appointmentService.deleteAppointment(id);
        ra.addFlashAttribute("success", "Appointment deleted.");
        return "redirect:/";
    }
}