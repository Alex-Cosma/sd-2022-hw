using Hospital.BusinessLogic.Models;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace Hospital.Models
{
    public class AppointmentViewModel
    {
        public Guid Id { set; get; }

        [Display(Name ="Date")]
        public DateTime DateTime { set; get; }

        public Guid? ClientId { set; get; }

        public UserViewModel Client { set; get; }

        public Guid? DoctorId { set; get; }

        public UserViewModel Doctor { set; get; }

        [Display(Name = "Feedback")]
        public string Description { set; get; }

        public Guid HospitalId { set; get; }

        public HospitalViewModel Hospital { set; get; }
    }
}
