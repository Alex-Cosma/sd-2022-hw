using Hospital.BusinessLogic.Models;
using Microsoft.AspNetCore.Mvc.Rendering;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace Hospital.Models
{
    public class AppointmentAddViewModel
    {
        public Guid Id { set; get; }

        [Display(Name = "Date")]
        public DateTime DateTime { set; get; }

        public SelectList Doctors { set; get; }

        public UserViewModel Doctor { set; get; }

        public List<UserViewModel> Details { set; get; }
    }
}
