using Hospital.Models;
using System;

namespace Hospital.BusinessLogic.Models
{
    public class DepartmentHospitalViewModel
    {
        public Guid DepartmentId { set; get; }

        public DepartmentViewModel Department { set; get; }

        public Guid HospitalId { set; get; }

        public HospitalViewModel Hospital { set; get; }
    }
}
